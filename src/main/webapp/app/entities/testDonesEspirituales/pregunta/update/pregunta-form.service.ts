import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPregunta, NewPregunta } from '../pregunta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPregunta for edit and NewPreguntaFormGroupInput for create.
 */
type PreguntaFormGroupInput = IPregunta | PartialWithRequiredKeyOf<NewPregunta>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPregunta | NewPregunta> = Omit<T, 'fechaCreacion'> & {
  fechaCreacion?: string | null;
};

type PreguntaFormRawValue = FormValueOf<IPregunta>;

type NewPreguntaFormRawValue = FormValueOf<NewPregunta>;

type PreguntaFormDefaults = Pick<NewPregunta, 'id' | 'obligatoria' | 'activa' | 'fechaCreacion'>;

type PreguntaFormGroupContent = {
  id: FormControl<PreguntaFormRawValue['id'] | NewPregunta['id']>;
  numeroPregunta: FormControl<PreguntaFormRawValue['numeroPregunta']>;
  textoPregunta: FormControl<PreguntaFormRawValue['textoPregunta']>;
  obligatoria: FormControl<PreguntaFormRawValue['obligatoria']>;
  activa: FormControl<PreguntaFormRawValue['activa']>;
  fechaCreacion: FormControl<PreguntaFormRawValue['fechaCreacion']>;
  cuestionario: FormControl<PreguntaFormRawValue['cuestionario']>;
};

export type PreguntaFormGroup = FormGroup<PreguntaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PreguntaFormService {
  createPreguntaFormGroup(pregunta: PreguntaFormGroupInput = { id: null }): PreguntaFormGroup {
    const preguntaRawValue = this.convertPreguntaToPreguntaRawValue({
      ...this.getFormDefaults(),
      ...pregunta,
    });
    return new FormGroup<PreguntaFormGroupContent>({
      id: new FormControl(
        { value: preguntaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numeroPregunta: new FormControl(preguntaRawValue.numeroPregunta, {
        validators: [Validators.required, Validators.min(1)],
      }),
      textoPregunta: new FormControl(preguntaRawValue.textoPregunta, {
        validators: [Validators.required],
      }),
      obligatoria: new FormControl(preguntaRawValue.obligatoria, {
        validators: [Validators.required],
      }),
      activa: new FormControl(preguntaRawValue.activa, {
        validators: [Validators.required],
      }),
      fechaCreacion: new FormControl(preguntaRawValue.fechaCreacion, {
        validators: [Validators.required],
      }),
      cuestionario: new FormControl(preguntaRawValue.cuestionario, {
        validators: [Validators.required],
      }),
    });
  }

  getPregunta(form: PreguntaFormGroup): IPregunta | NewPregunta {
    return this.convertPreguntaRawValueToPregunta(form.getRawValue() as PreguntaFormRawValue | NewPreguntaFormRawValue);
  }

  resetForm(form: PreguntaFormGroup, pregunta: PreguntaFormGroupInput): void {
    const preguntaRawValue = this.convertPreguntaToPreguntaRawValue({ ...this.getFormDefaults(), ...pregunta });
    form.reset(
      {
        ...preguntaRawValue,
        id: { value: preguntaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PreguntaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      obligatoria: false,
      activa: false,
      fechaCreacion: currentTime,
    };
  }

  private convertPreguntaRawValueToPregunta(rawPregunta: PreguntaFormRawValue | NewPreguntaFormRawValue): IPregunta | NewPregunta {
    return {
      ...rawPregunta,
      fechaCreacion: dayjs(rawPregunta.fechaCreacion, DATE_TIME_FORMAT),
    };
  }

  private convertPreguntaToPreguntaRawValue(
    pregunta: IPregunta | (Partial<NewPregunta> & PreguntaFormDefaults),
  ): PreguntaFormRawValue | PartialWithRequiredKeyOf<NewPreguntaFormRawValue> {
    return {
      ...pregunta,
      fechaCreacion: pregunta.fechaCreacion ? pregunta.fechaCreacion.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
