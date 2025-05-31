import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEscalaRespuesta, NewEscalaRespuesta } from '../escala-respuesta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEscalaRespuesta for edit and NewEscalaRespuestaFormGroupInput for create.
 */
type EscalaRespuestaFormGroupInput = IEscalaRespuesta | PartialWithRequiredKeyOf<NewEscalaRespuesta>;

type EscalaRespuestaFormDefaults = Pick<NewEscalaRespuesta, 'id'>;

type EscalaRespuestaFormGroupContent = {
  id: FormControl<IEscalaRespuesta['id'] | NewEscalaRespuesta['id']>;
  valor: FormControl<IEscalaRespuesta['valor']>;
  etiqueta: FormControl<IEscalaRespuesta['etiqueta']>;
  descripcion: FormControl<IEscalaRespuesta['descripcion']>;
  orden: FormControl<IEscalaRespuesta['orden']>;
};

export type EscalaRespuestaFormGroup = FormGroup<EscalaRespuestaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EscalaRespuestaFormService {
  createEscalaRespuestaFormGroup(escalaRespuesta: EscalaRespuestaFormGroupInput = { id: null }): EscalaRespuestaFormGroup {
    const escalaRespuestaRawValue = {
      ...this.getFormDefaults(),
      ...escalaRespuesta,
    };
    return new FormGroup<EscalaRespuestaFormGroupContent>({
      id: new FormControl(
        { value: escalaRespuestaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valor: new FormControl(escalaRespuestaRawValue.valor, {
        validators: [Validators.required, Validators.min(0), Validators.max(10)],
      }),
      etiqueta: new FormControl(escalaRespuestaRawValue.etiqueta, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      descripcion: new FormControl(escalaRespuestaRawValue.descripcion),
      orden: new FormControl(escalaRespuestaRawValue.orden, {
        validators: [Validators.required, Validators.min(1)],
      }),
    });
  }

  getEscalaRespuesta(form: EscalaRespuestaFormGroup): IEscalaRespuesta | NewEscalaRespuesta {
    return form.getRawValue() as IEscalaRespuesta | NewEscalaRespuesta;
  }

  resetForm(form: EscalaRespuestaFormGroup, escalaRespuesta: EscalaRespuestaFormGroupInput): void {
    const escalaRespuestaRawValue = { ...this.getFormDefaults(), ...escalaRespuesta };
    form.reset(
      {
        ...escalaRespuestaRawValue,
        id: { value: escalaRespuestaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EscalaRespuestaFormDefaults {
    return {
      id: null,
    };
  }
}
