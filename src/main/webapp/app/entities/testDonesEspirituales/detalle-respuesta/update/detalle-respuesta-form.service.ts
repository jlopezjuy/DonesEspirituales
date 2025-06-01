import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDetalleRespuesta, NewDetalleRespuesta } from '../detalle-respuesta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetalleRespuesta for edit and NewDetalleRespuestaFormGroupInput for create.
 */
type DetalleRespuestaFormGroupInput = IDetalleRespuesta | PartialWithRequiredKeyOf<NewDetalleRespuesta>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDetalleRespuesta | NewDetalleRespuesta> = Omit<T, 'timestampRespuesta'> & {
  timestampRespuesta?: string | null;
};

type DetalleRespuestaFormRawValue = FormValueOf<IDetalleRespuesta>;

type NewDetalleRespuestaFormRawValue = FormValueOf<NewDetalleRespuesta>;

type DetalleRespuestaFormDefaults = Pick<NewDetalleRespuesta, 'id' | 'timestampRespuesta'>;

type DetalleRespuestaFormGroupContent = {
  id: FormControl<DetalleRespuestaFormRawValue['id'] | NewDetalleRespuesta['id']>;
  valorRespuesta: FormControl<DetalleRespuestaFormRawValue['valorRespuesta']>;
  timestampRespuesta: FormControl<DetalleRespuestaFormRawValue['timestampRespuesta']>;
  tiempoPreguntaSegundos: FormControl<DetalleRespuestaFormRawValue['tiempoPreguntaSegundos']>;
  escalaRespuesta: FormControl<DetalleRespuestaFormRawValue['escalaRespuesta']>;
  pregunta: FormControl<DetalleRespuestaFormRawValue['pregunta']>;
  respuestaUsuario: FormControl<DetalleRespuestaFormRawValue['respuestaUsuario']>;
};

export type DetalleRespuestaFormGroup = FormGroup<DetalleRespuestaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetalleRespuestaFormService {
  createDetalleRespuestaFormGroup(detalleRespuesta: DetalleRespuestaFormGroupInput = { id: null }): DetalleRespuestaFormGroup {
    const detalleRespuestaRawValue = this.convertDetalleRespuestaToDetalleRespuestaRawValue({
      ...this.getFormDefaults(),
      ...detalleRespuesta,
    });
    return new FormGroup<DetalleRespuestaFormGroupContent>({
      id: new FormControl(
        { value: detalleRespuestaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valorRespuesta: new FormControl(detalleRespuestaRawValue.valorRespuesta, {
        validators: [Validators.required, Validators.min(0), Validators.max(10)],
      }),
      timestampRespuesta: new FormControl(detalleRespuestaRawValue.timestampRespuesta, {
        validators: [Validators.required],
      }),
      tiempoPreguntaSegundos: new FormControl(detalleRespuestaRawValue.tiempoPreguntaSegundos, {
        validators: [Validators.min(0)],
      }),
      escalaRespuesta: new FormControl(detalleRespuestaRawValue.escalaRespuesta, {
        validators: [Validators.required],
      }),
      pregunta: new FormControl(detalleRespuestaRawValue.pregunta, {
        validators: [Validators.required],
      }),
      respuestaUsuario: new FormControl(detalleRespuestaRawValue.respuestaUsuario, {
        validators: [Validators.required],
      }),
    });
  }

  getDetalleRespuesta(form: DetalleRespuestaFormGroup): IDetalleRespuesta | NewDetalleRespuesta {
    return this.convertDetalleRespuestaRawValueToDetalleRespuesta(
      form.getRawValue() as DetalleRespuestaFormRawValue | NewDetalleRespuestaFormRawValue,
    );
  }

  resetForm(form: DetalleRespuestaFormGroup, detalleRespuesta: DetalleRespuestaFormGroupInput): void {
    const detalleRespuestaRawValue = this.convertDetalleRespuestaToDetalleRespuestaRawValue({
      ...this.getFormDefaults(),
      ...detalleRespuesta,
    });
    form.reset(
      {
        ...detalleRespuestaRawValue,
        id: { value: detalleRespuestaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DetalleRespuestaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestampRespuesta: currentTime,
    };
  }

  private convertDetalleRespuestaRawValueToDetalleRespuesta(
    rawDetalleRespuesta: DetalleRespuestaFormRawValue | NewDetalleRespuestaFormRawValue,
  ): IDetalleRespuesta | NewDetalleRespuesta {
    return {
      ...rawDetalleRespuesta,
      timestampRespuesta: dayjs(rawDetalleRespuesta.timestampRespuesta, DATE_TIME_FORMAT),
    };
  }

  private convertDetalleRespuestaToDetalleRespuestaRawValue(
    detalleRespuesta: IDetalleRespuesta | (Partial<NewDetalleRespuesta> & DetalleRespuestaFormDefaults),
  ): DetalleRespuestaFormRawValue | PartialWithRequiredKeyOf<NewDetalleRespuestaFormRawValue> {
    return {
      ...detalleRespuesta,
      timestampRespuesta: detalleRespuesta.timestampRespuesta ? detalleRespuesta.timestampRespuesta.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
