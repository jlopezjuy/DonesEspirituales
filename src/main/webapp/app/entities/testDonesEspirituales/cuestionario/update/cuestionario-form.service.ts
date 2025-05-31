import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICuestionario, NewCuestionario } from '../cuestionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICuestionario for edit and NewCuestionarioFormGroupInput for create.
 */
type CuestionarioFormGroupInput = ICuestionario | PartialWithRequiredKeyOf<NewCuestionario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICuestionario | NewCuestionario> = Omit<T, 'fechaCreacion' | 'fechaActualizacion'> & {
  fechaCreacion?: string | null;
  fechaActualizacion?: string | null;
};

type CuestionarioFormRawValue = FormValueOf<ICuestionario>;

type NewCuestionarioFormRawValue = FormValueOf<NewCuestionario>;

type CuestionarioFormDefaults = Pick<NewCuestionario, 'id' | 'activo' | 'fechaCreacion' | 'fechaActualizacion'>;

type CuestionarioFormGroupContent = {
  id: FormControl<CuestionarioFormRawValue['id'] | NewCuestionario['id']>;
  titulo: FormControl<CuestionarioFormRawValue['titulo']>;
  descripcion: FormControl<CuestionarioFormRawValue['descripcion']>;
  instrucciones: FormControl<CuestionarioFormRawValue['instrucciones']>;
  totalPreguntas: FormControl<CuestionarioFormRawValue['totalPreguntas']>;
  activo: FormControl<CuestionarioFormRawValue['activo']>;
  fechaCreacion: FormControl<CuestionarioFormRawValue['fechaCreacion']>;
  fechaActualizacion: FormControl<CuestionarioFormRawValue['fechaActualizacion']>;
  version: FormControl<CuestionarioFormRawValue['version']>;
};

export type CuestionarioFormGroup = FormGroup<CuestionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CuestionarioFormService {
  createCuestionarioFormGroup(cuestionario: CuestionarioFormGroupInput = { id: null }): CuestionarioFormGroup {
    const cuestionarioRawValue = this.convertCuestionarioToCuestionarioRawValue({
      ...this.getFormDefaults(),
      ...cuestionario,
    });
    return new FormGroup<CuestionarioFormGroupContent>({
      id: new FormControl(
        { value: cuestionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(cuestionarioRawValue.titulo, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      descripcion: new FormControl(cuestionarioRawValue.descripcion),
      instrucciones: new FormControl(cuestionarioRawValue.instrucciones, {
        validators: [Validators.required],
      }),
      totalPreguntas: new FormControl(cuestionarioRawValue.totalPreguntas, {
        validators: [Validators.required, Validators.min(1), Validators.max(1000)],
      }),
      activo: new FormControl(cuestionarioRawValue.activo, {
        validators: [Validators.required],
      }),
      fechaCreacion: new FormControl(cuestionarioRawValue.fechaCreacion, {
        validators: [Validators.required],
      }),
      fechaActualizacion: new FormControl(cuestionarioRawValue.fechaActualizacion),
      version: new FormControl(cuestionarioRawValue.version, {
        validators: [Validators.required, Validators.min(1)],
      }),
    });
  }

  getCuestionario(form: CuestionarioFormGroup): ICuestionario | NewCuestionario {
    return this.convertCuestionarioRawValueToCuestionario(form.getRawValue() as CuestionarioFormRawValue | NewCuestionarioFormRawValue);
  }

  resetForm(form: CuestionarioFormGroup, cuestionario: CuestionarioFormGroupInput): void {
    const cuestionarioRawValue = this.convertCuestionarioToCuestionarioRawValue({ ...this.getFormDefaults(), ...cuestionario });
    form.reset(
      {
        ...cuestionarioRawValue,
        id: { value: cuestionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CuestionarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      activo: false,
      fechaCreacion: currentTime,
      fechaActualizacion: currentTime,
    };
  }

  private convertCuestionarioRawValueToCuestionario(
    rawCuestionario: CuestionarioFormRawValue | NewCuestionarioFormRawValue,
  ): ICuestionario | NewCuestionario {
    return {
      ...rawCuestionario,
      fechaCreacion: dayjs(rawCuestionario.fechaCreacion, DATE_TIME_FORMAT),
      fechaActualizacion: dayjs(rawCuestionario.fechaActualizacion, DATE_TIME_FORMAT),
    };
  }

  private convertCuestionarioToCuestionarioRawValue(
    cuestionario: ICuestionario | (Partial<NewCuestionario> & CuestionarioFormDefaults),
  ): CuestionarioFormRawValue | PartialWithRequiredKeyOf<NewCuestionarioFormRawValue> {
    return {
      ...cuestionario,
      fechaCreacion: cuestionario.fechaCreacion ? cuestionario.fechaCreacion.format(DATE_TIME_FORMAT) : undefined,
      fechaActualizacion: cuestionario.fechaActualizacion ? cuestionario.fechaActualizacion.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
