import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IInterpretacion, NewInterpretacion } from '../interpretacion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInterpretacion for edit and NewInterpretacionFormGroupInput for create.
 */
type InterpretacionFormGroupInput = IInterpretacion | PartialWithRequiredKeyOf<NewInterpretacion>;

type InterpretacionFormDefaults = Pick<NewInterpretacion, 'id'>;

type InterpretacionFormGroupContent = {
  id: FormControl<IInterpretacion['id'] | NewInterpretacion['id']>;
  puntuacionMinima: FormControl<IInterpretacion['puntuacionMinima']>;
  puntuacionMaxima: FormControl<IInterpretacion['puntuacionMaxima']>;
  nivel: FormControl<IInterpretacion['nivel']>;
  descripcionNivel: FormControl<IInterpretacion['descripcionNivel']>;
  recomendaciones: FormControl<IInterpretacion['recomendaciones']>;
  areasServicio: FormControl<IInterpretacion['areasServicio']>;
  donEspiritual: FormControl<IInterpretacion['donEspiritual']>;
};

export type InterpretacionFormGroup = FormGroup<InterpretacionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InterpretacionFormService {
  createInterpretacionFormGroup(interpretacion: InterpretacionFormGroupInput = { id: null }): InterpretacionFormGroup {
    const interpretacionRawValue = {
      ...this.getFormDefaults(),
      ...interpretacion,
    };
    return new FormGroup<InterpretacionFormGroupContent>({
      id: new FormControl(
        { value: interpretacionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      puntuacionMinima: new FormControl(interpretacionRawValue.puntuacionMinima, {
        validators: [Validators.required, Validators.min(0)],
      }),
      puntuacionMaxima: new FormControl(interpretacionRawValue.puntuacionMaxima, {
        validators: [Validators.required, Validators.min(0)],
      }),
      nivel: new FormControl(interpretacionRawValue.nivel, {
        validators: [Validators.required],
      }),
      descripcionNivel: new FormControl(interpretacionRawValue.descripcionNivel, {
        validators: [Validators.required],
      }),
      recomendaciones: new FormControl(interpretacionRawValue.recomendaciones),
      areasServicio: new FormControl(interpretacionRawValue.areasServicio),
      donEspiritual: new FormControl(interpretacionRawValue.donEspiritual, {
        validators: [Validators.required],
      }),
    });
  }

  getInterpretacion(form: InterpretacionFormGroup): IInterpretacion | NewInterpretacion {
    return form.getRawValue() as IInterpretacion | NewInterpretacion;
  }

  resetForm(form: InterpretacionFormGroup, interpretacion: InterpretacionFormGroupInput): void {
    const interpretacionRawValue = { ...this.getFormDefaults(), ...interpretacion };
    form.reset(
      {
        ...interpretacionRawValue,
        id: { value: interpretacionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InterpretacionFormDefaults {
    return {
      id: null,
    };
  }
}
