import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConfiguracionSistema, NewConfiguracionSistema } from '../configuracion-sistema.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConfiguracionSistema for edit and NewConfiguracionSistemaFormGroupInput for create.
 */
type ConfiguracionSistemaFormGroupInput = IConfiguracionSistema | PartialWithRequiredKeyOf<NewConfiguracionSistema>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IConfiguracionSistema | NewConfiguracionSistema> = Omit<T, 'fechaActualizacion'> & {
  fechaActualizacion?: string | null;
};

type ConfiguracionSistemaFormRawValue = FormValueOf<IConfiguracionSistema>;

type NewConfiguracionSistemaFormRawValue = FormValueOf<NewConfiguracionSistema>;

type ConfiguracionSistemaFormDefaults = Pick<NewConfiguracionSistema, 'id' | 'fechaActualizacion'>;

type ConfiguracionSistemaFormGroupContent = {
  id: FormControl<ConfiguracionSistemaFormRawValue['id'] | NewConfiguracionSistema['id']>;
  clave: FormControl<ConfiguracionSistemaFormRawValue['clave']>;
  valor: FormControl<ConfiguracionSistemaFormRawValue['valor']>;
  descripcion: FormControl<ConfiguracionSistemaFormRawValue['descripcion']>;
  tipoDato: FormControl<ConfiguracionSistemaFormRawValue['tipoDato']>;
  fechaActualizacion: FormControl<ConfiguracionSistemaFormRawValue['fechaActualizacion']>;
};

export type ConfiguracionSistemaFormGroup = FormGroup<ConfiguracionSistemaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ConfiguracionSistemaFormService {
  createConfiguracionSistemaFormGroup(
    configuracionSistema: ConfiguracionSistemaFormGroupInput = { id: null },
  ): ConfiguracionSistemaFormGroup {
    const configuracionSistemaRawValue = this.convertConfiguracionSistemaToConfiguracionSistemaRawValue({
      ...this.getFormDefaults(),
      ...configuracionSistema,
    });
    return new FormGroup<ConfiguracionSistemaFormGroupContent>({
      id: new FormControl(
        { value: configuracionSistemaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      clave: new FormControl(configuracionSistemaRawValue.clave, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      valor: new FormControl(configuracionSistemaRawValue.valor, {
        validators: [Validators.required, Validators.maxLength(1000)],
      }),
      descripcion: new FormControl(configuracionSistemaRawValue.descripcion),
      tipoDato: new FormControl(configuracionSistemaRawValue.tipoDato, {
        validators: [Validators.required],
      }),
      fechaActualizacion: new FormControl(configuracionSistemaRawValue.fechaActualizacion, {
        validators: [Validators.required],
      }),
    });
  }

  getConfiguracionSistema(form: ConfiguracionSistemaFormGroup): IConfiguracionSistema | NewConfiguracionSistema {
    return this.convertConfiguracionSistemaRawValueToConfiguracionSistema(
      form.getRawValue() as ConfiguracionSistemaFormRawValue | NewConfiguracionSistemaFormRawValue,
    );
  }

  resetForm(form: ConfiguracionSistemaFormGroup, configuracionSistema: ConfiguracionSistemaFormGroupInput): void {
    const configuracionSistemaRawValue = this.convertConfiguracionSistemaToConfiguracionSistemaRawValue({
      ...this.getFormDefaults(),
      ...configuracionSistema,
    });
    form.reset(
      {
        ...configuracionSistemaRawValue,
        id: { value: configuracionSistemaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ConfiguracionSistemaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaActualizacion: currentTime,
    };
  }

  private convertConfiguracionSistemaRawValueToConfiguracionSistema(
    rawConfiguracionSistema: ConfiguracionSistemaFormRawValue | NewConfiguracionSistemaFormRawValue,
  ): IConfiguracionSistema | NewConfiguracionSistema {
    return {
      ...rawConfiguracionSistema,
      fechaActualizacion: dayjs(rawConfiguracionSistema.fechaActualizacion, DATE_TIME_FORMAT),
    };
  }

  private convertConfiguracionSistemaToConfiguracionSistemaRawValue(
    configuracionSistema: IConfiguracionSistema | (Partial<NewConfiguracionSistema> & ConfiguracionSistemaFormDefaults),
  ): ConfiguracionSistemaFormRawValue | PartialWithRequiredKeyOf<NewConfiguracionSistemaFormRawValue> {
    return {
      ...configuracionSistema,
      fechaActualizacion: configuracionSistema.fechaActualizacion
        ? configuracionSistema.fechaActualizacion.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
