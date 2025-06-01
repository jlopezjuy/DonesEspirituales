import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPreguntaDon, NewPreguntaDon } from '../pregunta-don.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPreguntaDon for edit and NewPreguntaDonFormGroupInput for create.
 */
type PreguntaDonFormGroupInput = IPreguntaDon | PartialWithRequiredKeyOf<NewPreguntaDon>;

type PreguntaDonFormDefaults = Pick<NewPreguntaDon, 'id' | 'activa'>;

type PreguntaDonFormGroupContent = {
  id: FormControl<IPreguntaDon['id'] | NewPreguntaDon['id']>;
  peso: FormControl<IPreguntaDon['peso']>;
  activa: FormControl<IPreguntaDon['activa']>;
  pregunta: FormControl<IPreguntaDon['pregunta']>;
  donEspiritual: FormControl<IPreguntaDon['donEspiritual']>;
};

export type PreguntaDonFormGroup = FormGroup<PreguntaDonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PreguntaDonFormService {
  createPreguntaDonFormGroup(preguntaDon: PreguntaDonFormGroupInput = { id: null }): PreguntaDonFormGroup {
    const preguntaDonRawValue = {
      ...this.getFormDefaults(),
      ...preguntaDon,
    };
    return new FormGroup<PreguntaDonFormGroupContent>({
      id: new FormControl(
        { value: preguntaDonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      peso: new FormControl(preguntaDonRawValue.peso, {
        validators: [Validators.required, Validators.min(1), Validators.max(10)],
      }),
      activa: new FormControl(preguntaDonRawValue.activa, {
        validators: [Validators.required],
      }),
      pregunta: new FormControl(preguntaDonRawValue.pregunta, {
        validators: [Validators.required],
      }),
      donEspiritual: new FormControl(preguntaDonRawValue.donEspiritual, {
        validators: [Validators.required],
      }),
    });
  }

  getPreguntaDon(form: PreguntaDonFormGroup): IPreguntaDon | NewPreguntaDon {
    return form.getRawValue() as IPreguntaDon | NewPreguntaDon;
  }

  resetForm(form: PreguntaDonFormGroup, preguntaDon: PreguntaDonFormGroupInput): void {
    const preguntaDonRawValue = { ...this.getFormDefaults(), ...preguntaDon };
    form.reset(
      {
        ...preguntaDonRawValue,
        id: { value: preguntaDonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PreguntaDonFormDefaults {
    return {
      id: null,
      activa: false,
    };
  }
}
