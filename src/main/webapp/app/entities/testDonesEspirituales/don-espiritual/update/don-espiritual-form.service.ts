import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDonEspiritual, NewDonEspiritual } from '../don-espiritual.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDonEspiritual for edit and NewDonEspiritualFormGroupInput for create.
 */
type DonEspiritualFormGroupInput = IDonEspiritual | PartialWithRequiredKeyOf<NewDonEspiritual>;

type DonEspiritualFormDefaults = Pick<NewDonEspiritual, 'id' | 'activo'>;

type DonEspiritualFormGroupContent = {
  id: FormControl<IDonEspiritual['id'] | NewDonEspiritual['id']>;
  nombre: FormControl<IDonEspiritual['nombre']>;
  nombreCorto: FormControl<IDonEspiritual['nombreCorto']>;
  descripcion: FormControl<IDonEspiritual['descripcion']>;
  caracteristicas: FormControl<IDonEspiritual['caracteristicas']>;
  versiculosBiblicos: FormControl<IDonEspiritual['versiculosBiblicos']>;
  activo: FormControl<IDonEspiritual['activo']>;
  ordenPresentacion: FormControl<IDonEspiritual['ordenPresentacion']>;
};

export type DonEspiritualFormGroup = FormGroup<DonEspiritualFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DonEspiritualFormService {
  createDonEspiritualFormGroup(donEspiritual: DonEspiritualFormGroupInput = { id: null }): DonEspiritualFormGroup {
    const donEspiritualRawValue = {
      ...this.getFormDefaults(),
      ...donEspiritual,
    };
    return new FormGroup<DonEspiritualFormGroupContent>({
      id: new FormControl(
        { value: donEspiritualRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(donEspiritualRawValue.nombre, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      nombreCorto: new FormControl(donEspiritualRawValue.nombreCorto, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      descripcion: new FormControl(donEspiritualRawValue.descripcion),
      caracteristicas: new FormControl(donEspiritualRawValue.caracteristicas),
      versiculosBiblicos: new FormControl(donEspiritualRawValue.versiculosBiblicos),
      activo: new FormControl(donEspiritualRawValue.activo, {
        validators: [Validators.required],
      }),
      ordenPresentacion: new FormControl(donEspiritualRawValue.ordenPresentacion, {
        validators: [Validators.min(1)],
      }),
    });
  }

  getDonEspiritual(form: DonEspiritualFormGroup): IDonEspiritual | NewDonEspiritual {
    return form.getRawValue() as IDonEspiritual | NewDonEspiritual;
  }

  resetForm(form: DonEspiritualFormGroup, donEspiritual: DonEspiritualFormGroupInput): void {
    const donEspiritualRawValue = { ...this.getFormDefaults(), ...donEspiritual };
    form.reset(
      {
        ...donEspiritualRawValue,
        id: { value: donEspiritualRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DonEspiritualFormDefaults {
    return {
      id: null,
      activo: false,
    };
  }
}
