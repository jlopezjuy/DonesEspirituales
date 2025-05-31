import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAuditoriaRespuesta, NewAuditoriaRespuesta } from '../auditoria-respuesta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAuditoriaRespuesta for edit and NewAuditoriaRespuestaFormGroupInput for create.
 */
type AuditoriaRespuestaFormGroupInput = IAuditoriaRespuesta | PartialWithRequiredKeyOf<NewAuditoriaRespuesta>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAuditoriaRespuesta | NewAuditoriaRespuesta> = Omit<T, 'timestampCambio'> & {
  timestampCambio?: string | null;
};

type AuditoriaRespuestaFormRawValue = FormValueOf<IAuditoriaRespuesta>;

type NewAuditoriaRespuestaFormRawValue = FormValueOf<NewAuditoriaRespuesta>;

type AuditoriaRespuestaFormDefaults = Pick<NewAuditoriaRespuesta, 'id' | 'timestampCambio'>;

type AuditoriaRespuestaFormGroupContent = {
  id: FormControl<AuditoriaRespuestaFormRawValue['id'] | NewAuditoriaRespuesta['id']>;
  valorAnterior: FormControl<AuditoriaRespuestaFormRawValue['valorAnterior']>;
  valorNuevo: FormControl<AuditoriaRespuestaFormRawValue['valorNuevo']>;
  timestampCambio: FormControl<AuditoriaRespuestaFormRawValue['timestampCambio']>;
  motivoCambio: FormControl<AuditoriaRespuestaFormRawValue['motivoCambio']>;
  respuestaUsuario: FormControl<AuditoriaRespuestaFormRawValue['respuestaUsuario']>;
  detalleRespuesta: FormControl<AuditoriaRespuestaFormRawValue['detalleRespuesta']>;
};

export type AuditoriaRespuestaFormGroup = FormGroup<AuditoriaRespuestaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AuditoriaRespuestaFormService {
  createAuditoriaRespuestaFormGroup(auditoriaRespuesta: AuditoriaRespuestaFormGroupInput = { id: null }): AuditoriaRespuestaFormGroup {
    const auditoriaRespuestaRawValue = this.convertAuditoriaRespuestaToAuditoriaRespuestaRawValue({
      ...this.getFormDefaults(),
      ...auditoriaRespuesta,
    });
    return new FormGroup<AuditoriaRespuestaFormGroupContent>({
      id: new FormControl(
        { value: auditoriaRespuestaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valorAnterior: new FormControl(auditoriaRespuestaRawValue.valorAnterior, {
        validators: [Validators.min(0), Validators.max(10)],
      }),
      valorNuevo: new FormControl(auditoriaRespuestaRawValue.valorNuevo, {
        validators: [Validators.required, Validators.min(0), Validators.max(10)],
      }),
      timestampCambio: new FormControl(auditoriaRespuestaRawValue.timestampCambio, {
        validators: [Validators.required],
      }),
      motivoCambio: new FormControl(auditoriaRespuestaRawValue.motivoCambio, {
        validators: [Validators.maxLength(500)],
      }),
      respuestaUsuario: new FormControl(auditoriaRespuestaRawValue.respuestaUsuario, {
        validators: [Validators.required],
      }),
      detalleRespuesta: new FormControl(auditoriaRespuestaRawValue.detalleRespuesta, {
        validators: [Validators.required],
      }),
    });
  }

  getAuditoriaRespuesta(form: AuditoriaRespuestaFormGroup): IAuditoriaRespuesta | NewAuditoriaRespuesta {
    return this.convertAuditoriaRespuestaRawValueToAuditoriaRespuesta(
      form.getRawValue() as AuditoriaRespuestaFormRawValue | NewAuditoriaRespuestaFormRawValue,
    );
  }

  resetForm(form: AuditoriaRespuestaFormGroup, auditoriaRespuesta: AuditoriaRespuestaFormGroupInput): void {
    const auditoriaRespuestaRawValue = this.convertAuditoriaRespuestaToAuditoriaRespuestaRawValue({
      ...this.getFormDefaults(),
      ...auditoriaRespuesta,
    });
    form.reset(
      {
        ...auditoriaRespuestaRawValue,
        id: { value: auditoriaRespuestaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AuditoriaRespuestaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestampCambio: currentTime,
    };
  }

  private convertAuditoriaRespuestaRawValueToAuditoriaRespuesta(
    rawAuditoriaRespuesta: AuditoriaRespuestaFormRawValue | NewAuditoriaRespuestaFormRawValue,
  ): IAuditoriaRespuesta | NewAuditoriaRespuesta {
    return {
      ...rawAuditoriaRespuesta,
      timestampCambio: dayjs(rawAuditoriaRespuesta.timestampCambio, DATE_TIME_FORMAT),
    };
  }

  private convertAuditoriaRespuestaToAuditoriaRespuestaRawValue(
    auditoriaRespuesta: IAuditoriaRespuesta | (Partial<NewAuditoriaRespuesta> & AuditoriaRespuestaFormDefaults),
  ): AuditoriaRespuestaFormRawValue | PartialWithRequiredKeyOf<NewAuditoriaRespuestaFormRawValue> {
    return {
      ...auditoriaRespuesta,
      timestampCambio: auditoriaRespuesta.timestampCambio ? auditoriaRespuesta.timestampCambio.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
