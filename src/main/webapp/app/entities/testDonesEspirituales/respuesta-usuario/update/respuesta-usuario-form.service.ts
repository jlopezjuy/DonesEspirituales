import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRespuestaUsuario, NewRespuestaUsuario } from '../respuesta-usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRespuestaUsuario for edit and NewRespuestaUsuarioFormGroupInput for create.
 */
type RespuestaUsuarioFormGroupInput = IRespuestaUsuario | PartialWithRequiredKeyOf<NewRespuestaUsuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRespuestaUsuario | NewRespuestaUsuario> = Omit<T, 'fechaInicio' | 'fechaCompletado'> & {
  fechaInicio?: string | null;
  fechaCompletado?: string | null;
};

type RespuestaUsuarioFormRawValue = FormValueOf<IRespuestaUsuario>;

type NewRespuestaUsuarioFormRawValue = FormValueOf<NewRespuestaUsuario>;

type RespuestaUsuarioFormDefaults = Pick<NewRespuestaUsuario, 'id' | 'fechaInicio' | 'fechaCompletado'>;

type RespuestaUsuarioFormGroupContent = {
  id: FormControl<RespuestaUsuarioFormRawValue['id'] | NewRespuestaUsuario['id']>;
  fechaInicio: FormControl<RespuestaUsuarioFormRawValue['fechaInicio']>;
  fechaCompletado: FormControl<RespuestaUsuarioFormRawValue['fechaCompletado']>;
  estado: FormControl<RespuestaUsuarioFormRawValue['estado']>;
  tiempoTotalSegundos: FormControl<RespuestaUsuarioFormRawValue['tiempoTotalSegundos']>;
  ipAddress: FormControl<RespuestaUsuarioFormRawValue['ipAddress']>;
  userAgent: FormControl<RespuestaUsuarioFormRawValue['userAgent']>;
  usuario: FormControl<RespuestaUsuarioFormRawValue['usuario']>;
  cuestionario: FormControl<RespuestaUsuarioFormRawValue['cuestionario']>;
};

export type RespuestaUsuarioFormGroup = FormGroup<RespuestaUsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RespuestaUsuarioFormService {
  createRespuestaUsuarioFormGroup(respuestaUsuario: RespuestaUsuarioFormGroupInput = { id: null }): RespuestaUsuarioFormGroup {
    const respuestaUsuarioRawValue = this.convertRespuestaUsuarioToRespuestaUsuarioRawValue({
      ...this.getFormDefaults(),
      ...respuestaUsuario,
    });
    return new FormGroup<RespuestaUsuarioFormGroupContent>({
      id: new FormControl(
        { value: respuestaUsuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fechaInicio: new FormControl(respuestaUsuarioRawValue.fechaInicio, {
        validators: [Validators.required],
      }),
      fechaCompletado: new FormControl(respuestaUsuarioRawValue.fechaCompletado),
      estado: new FormControl(respuestaUsuarioRawValue.estado, {
        validators: [Validators.required],
      }),
      tiempoTotalSegundos: new FormControl(respuestaUsuarioRawValue.tiempoTotalSegundos, {
        validators: [Validators.min(0)],
      }),
      ipAddress: new FormControl(respuestaUsuarioRawValue.ipAddress, {
        validators: [Validators.maxLength(45)],
      }),
      userAgent: new FormControl(respuestaUsuarioRawValue.userAgent, {
        validators: [Validators.maxLength(500)],
      }),
      usuario: new FormControl(respuestaUsuarioRawValue.usuario, {
        validators: [Validators.required],
      }),
      cuestionario: new FormControl(respuestaUsuarioRawValue.cuestionario, {
        validators: [Validators.required],
      }),
    });
  }

  getRespuestaUsuario(form: RespuestaUsuarioFormGroup): IRespuestaUsuario | NewRespuestaUsuario {
    return this.convertRespuestaUsuarioRawValueToRespuestaUsuario(
      form.getRawValue() as RespuestaUsuarioFormRawValue | NewRespuestaUsuarioFormRawValue,
    );
  }

  resetForm(form: RespuestaUsuarioFormGroup, respuestaUsuario: RespuestaUsuarioFormGroupInput): void {
    const respuestaUsuarioRawValue = this.convertRespuestaUsuarioToRespuestaUsuarioRawValue({
      ...this.getFormDefaults(),
      ...respuestaUsuario,
    });
    form.reset(
      {
        ...respuestaUsuarioRawValue,
        id: { value: respuestaUsuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RespuestaUsuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaInicio: currentTime,
      fechaCompletado: currentTime,
    };
  }

  private convertRespuestaUsuarioRawValueToRespuestaUsuario(
    rawRespuestaUsuario: RespuestaUsuarioFormRawValue | NewRespuestaUsuarioFormRawValue,
  ): IRespuestaUsuario | NewRespuestaUsuario {
    return {
      ...rawRespuestaUsuario,
      fechaInicio: dayjs(rawRespuestaUsuario.fechaInicio, DATE_TIME_FORMAT),
      fechaCompletado: dayjs(rawRespuestaUsuario.fechaCompletado, DATE_TIME_FORMAT),
    };
  }

  private convertRespuestaUsuarioToRespuestaUsuarioRawValue(
    respuestaUsuario: IRespuestaUsuario | (Partial<NewRespuestaUsuario> & RespuestaUsuarioFormDefaults),
  ): RespuestaUsuarioFormRawValue | PartialWithRequiredKeyOf<NewRespuestaUsuarioFormRawValue> {
    return {
      ...respuestaUsuario,
      fechaInicio: respuestaUsuario.fechaInicio ? respuestaUsuario.fechaInicio.format(DATE_TIME_FORMAT) : undefined,
      fechaCompletado: respuestaUsuario.fechaCompletado ? respuestaUsuario.fechaCompletado.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
