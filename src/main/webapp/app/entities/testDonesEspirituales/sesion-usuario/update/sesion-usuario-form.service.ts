import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISesionUsuario, NewSesionUsuario } from '../sesion-usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISesionUsuario for edit and NewSesionUsuarioFormGroupInput for create.
 */
type SesionUsuarioFormGroupInput = ISesionUsuario | PartialWithRequiredKeyOf<NewSesionUsuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISesionUsuario | NewSesionUsuario> = Omit<T, 'fechaCreacion' | 'fechaExpiracion'> & {
  fechaCreacion?: string | null;
  fechaExpiracion?: string | null;
};

type SesionUsuarioFormRawValue = FormValueOf<ISesionUsuario>;

type NewSesionUsuarioFormRawValue = FormValueOf<NewSesionUsuario>;

type SesionUsuarioFormDefaults = Pick<NewSesionUsuario, 'id' | 'fechaCreacion' | 'fechaExpiracion' | 'completada'>;

type SesionUsuarioFormGroupContent = {
  id: FormControl<SesionUsuarioFormRawValue['id'] | NewSesionUsuario['id']>;
  respuestasTemporales: FormControl<SesionUsuarioFormRawValue['respuestasTemporales']>;
  fechaCreacion: FormControl<SesionUsuarioFormRawValue['fechaCreacion']>;
  fechaExpiracion: FormControl<SesionUsuarioFormRawValue['fechaExpiracion']>;
  completada: FormControl<SesionUsuarioFormRawValue['completada']>;
  usuario: FormControl<SesionUsuarioFormRawValue['usuario']>;
  respuestaUsuario: FormControl<SesionUsuarioFormRawValue['respuestaUsuario']>;
};

export type SesionUsuarioFormGroup = FormGroup<SesionUsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SesionUsuarioFormService {
  createSesionUsuarioFormGroup(sesionUsuario: SesionUsuarioFormGroupInput = { id: null }): SesionUsuarioFormGroup {
    const sesionUsuarioRawValue = this.convertSesionUsuarioToSesionUsuarioRawValue({
      ...this.getFormDefaults(),
      ...sesionUsuario,
    });
    return new FormGroup<SesionUsuarioFormGroupContent>({
      id: new FormControl(
        { value: sesionUsuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      respuestasTemporales: new FormControl(sesionUsuarioRawValue.respuestasTemporales),
      fechaCreacion: new FormControl(sesionUsuarioRawValue.fechaCreacion, {
        validators: [Validators.required],
      }),
      fechaExpiracion: new FormControl(sesionUsuarioRawValue.fechaExpiracion, {
        validators: [Validators.required],
      }),
      completada: new FormControl(sesionUsuarioRawValue.completada, {
        validators: [Validators.required],
      }),
      usuario: new FormControl(sesionUsuarioRawValue.usuario, {
        validators: [Validators.required],
      }),
      respuestaUsuario: new FormControl(sesionUsuarioRawValue.respuestaUsuario),
    });
  }

  getSesionUsuario(form: SesionUsuarioFormGroup): ISesionUsuario | NewSesionUsuario {
    return this.convertSesionUsuarioRawValueToSesionUsuario(form.getRawValue() as SesionUsuarioFormRawValue | NewSesionUsuarioFormRawValue);
  }

  resetForm(form: SesionUsuarioFormGroup, sesionUsuario: SesionUsuarioFormGroupInput): void {
    const sesionUsuarioRawValue = this.convertSesionUsuarioToSesionUsuarioRawValue({ ...this.getFormDefaults(), ...sesionUsuario });
    form.reset(
      {
        ...sesionUsuarioRawValue,
        id: { value: sesionUsuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SesionUsuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaCreacion: currentTime,
      fechaExpiracion: currentTime,
      completada: false,
    };
  }

  private convertSesionUsuarioRawValueToSesionUsuario(
    rawSesionUsuario: SesionUsuarioFormRawValue | NewSesionUsuarioFormRawValue,
  ): ISesionUsuario | NewSesionUsuario {
    return {
      ...rawSesionUsuario,
      fechaCreacion: dayjs(rawSesionUsuario.fechaCreacion, DATE_TIME_FORMAT),
      fechaExpiracion: dayjs(rawSesionUsuario.fechaExpiracion, DATE_TIME_FORMAT),
    };
  }

  private convertSesionUsuarioToSesionUsuarioRawValue(
    sesionUsuario: ISesionUsuario | (Partial<NewSesionUsuario> & SesionUsuarioFormDefaults),
  ): SesionUsuarioFormRawValue | PartialWithRequiredKeyOf<NewSesionUsuarioFormRawValue> {
    return {
      ...sesionUsuario,
      fechaCreacion: sesionUsuario.fechaCreacion ? sesionUsuario.fechaCreacion.format(DATE_TIME_FORMAT) : undefined,
      fechaExpiracion: sesionUsuario.fechaExpiracion ? sesionUsuario.fechaExpiracion.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
