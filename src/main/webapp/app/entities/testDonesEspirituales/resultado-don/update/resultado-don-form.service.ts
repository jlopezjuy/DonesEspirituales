import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IResultadoDon, NewResultadoDon } from '../resultado-don.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResultadoDon for edit and NewResultadoDonFormGroupInput for create.
 */
type ResultadoDonFormGroupInput = IResultadoDon | PartialWithRequiredKeyOf<NewResultadoDon>;

type ResultadoDonFormDefaults = Pick<NewResultadoDon, 'id' | 'esDonPrincipal'>;

type ResultadoDonFormGroupContent = {
  id: FormControl<IResultadoDon['id'] | NewResultadoDon['id']>;
  puntuacionTotal: FormControl<IResultadoDon['puntuacionTotal']>;
  porcentaje: FormControl<IResultadoDon['porcentaje']>;
  rankingPosicion: FormControl<IResultadoDon['rankingPosicion']>;
  esDonPrincipal: FormControl<IResultadoDon['esDonPrincipal']>;
  interpretacion: FormControl<IResultadoDon['interpretacion']>;
  respuestaUsuario: FormControl<IResultadoDon['respuestaUsuario']>;
  donEspiritual: FormControl<IResultadoDon['donEspiritual']>;
};

export type ResultadoDonFormGroup = FormGroup<ResultadoDonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResultadoDonFormService {
  createResultadoDonFormGroup(resultadoDon: ResultadoDonFormGroupInput = { id: null }): ResultadoDonFormGroup {
    const resultadoDonRawValue = {
      ...this.getFormDefaults(),
      ...resultadoDon,
    };
    return new FormGroup<ResultadoDonFormGroupContent>({
      id: new FormControl(
        { value: resultadoDonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      puntuacionTotal: new FormControl(resultadoDonRawValue.puntuacionTotal, {
        validators: [Validators.required, Validators.min(0)],
      }),
      porcentaje: new FormControl(resultadoDonRawValue.porcentaje, {
        validators: [Validators.required, Validators.min(0), Validators.max(100)],
      }),
      rankingPosicion: new FormControl(resultadoDonRawValue.rankingPosicion, {
        validators: [Validators.required, Validators.min(1)],
      }),
      esDonPrincipal: new FormControl(resultadoDonRawValue.esDonPrincipal, {
        validators: [Validators.required],
      }),
      interpretacion: new FormControl(resultadoDonRawValue.interpretacion),
      respuestaUsuario: new FormControl(resultadoDonRawValue.respuestaUsuario, {
        validators: [Validators.required],
      }),
      donEspiritual: new FormControl(resultadoDonRawValue.donEspiritual, {
        validators: [Validators.required],
      }),
    });
  }

  getResultadoDon(form: ResultadoDonFormGroup): IResultadoDon | NewResultadoDon {
    return form.getRawValue() as IResultadoDon | NewResultadoDon;
  }

  resetForm(form: ResultadoDonFormGroup, resultadoDon: ResultadoDonFormGroupInput): void {
    const resultadoDonRawValue = { ...this.getFormDefaults(), ...resultadoDon };
    form.reset(
      {
        ...resultadoDonRawValue,
        id: { value: resultadoDonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ResultadoDonFormDefaults {
    return {
      id: null,
      esDonPrincipal: false,
    };
  }
}
