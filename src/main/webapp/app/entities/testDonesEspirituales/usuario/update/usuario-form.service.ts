import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUsuario, NewUsuario } from '../usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuario for edit and NewUsuarioFormGroupInput for create.
 */
type UsuarioFormGroupInput = IUsuario | PartialWithRequiredKeyOf<NewUsuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUsuario | NewUsuario> = Omit<T, 'fechaRegistro' | 'ultimaActividad'> & {
  fechaRegistro?: string | null;
  ultimaActividad?: string | null;
};

type UsuarioFormRawValue = FormValueOf<IUsuario>;

type NewUsuarioFormRawValue = FormValueOf<NewUsuario>;

type UsuarioFormDefaults = Pick<NewUsuario, 'id' | 'fechaRegistro' | 'ultimaActividad' | 'activo'>;

type UsuarioFormGroupContent = {
  id: FormControl<UsuarioFormRawValue['id'] | NewUsuario['id']>;
  nombre: FormControl<UsuarioFormRawValue['nombre']>;
  apellido: FormControl<UsuarioFormRawValue['apellido']>;
  email: FormControl<UsuarioFormRawValue['email']>;
  telefono: FormControl<UsuarioFormRawValue['telefono']>;
  fechaNacimiento: FormControl<UsuarioFormRawValue['fechaNacimiento']>;
  genero: FormControl<UsuarioFormRawValue['genero']>;
  iglesia: FormControl<UsuarioFormRawValue['iglesia']>;
  denominacion: FormControl<UsuarioFormRawValue['denominacion']>;
  fechaRegistro: FormControl<UsuarioFormRawValue['fechaRegistro']>;
  ultimaActividad: FormControl<UsuarioFormRawValue['ultimaActividad']>;
  activo: FormControl<UsuarioFormRawValue['activo']>;
};

export type UsuarioFormGroup = FormGroup<UsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioFormService {
  createUsuarioFormGroup(usuario: UsuarioFormGroupInput = { id: null }): UsuarioFormGroup {
    const usuarioRawValue = this.convertUsuarioToUsuarioRawValue({
      ...this.getFormDefaults(),
      ...usuario,
    });
    return new FormGroup<UsuarioFormGroupContent>({
      id: new FormControl(
        { value: usuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombre: new FormControl(usuarioRawValue.nombre, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      apellido: new FormControl(usuarioRawValue.apellido, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      email: new FormControl(usuarioRawValue.email, {
        validators: [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
      }),
      telefono: new FormControl(usuarioRawValue.telefono, {
        validators: [Validators.maxLength(20)],
      }),
      fechaNacimiento: new FormControl(usuarioRawValue.fechaNacimiento),
      genero: new FormControl(usuarioRawValue.genero),
      iglesia: new FormControl(usuarioRawValue.iglesia, {
        validators: [Validators.maxLength(200)],
      }),
      denominacion: new FormControl(usuarioRawValue.denominacion, {
        validators: [Validators.maxLength(100)],
      }),
      fechaRegistro: new FormControl(usuarioRawValue.fechaRegistro, {
        validators: [Validators.required],
      }),
      ultimaActividad: new FormControl(usuarioRawValue.ultimaActividad),
      activo: new FormControl(usuarioRawValue.activo, {
        validators: [Validators.required],
      }),
    });
  }

  getUsuario(form: UsuarioFormGroup): IUsuario | NewUsuario {
    return this.convertUsuarioRawValueToUsuario(form.getRawValue() as UsuarioFormRawValue | NewUsuarioFormRawValue);
  }

  resetForm(form: UsuarioFormGroup, usuario: UsuarioFormGroupInput): void {
    const usuarioRawValue = this.convertUsuarioToUsuarioRawValue({ ...this.getFormDefaults(), ...usuario });
    form.reset(
      {
        ...usuarioRawValue,
        id: { value: usuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaRegistro: currentTime,
      ultimaActividad: currentTime,
      activo: false,
    };
  }

  private convertUsuarioRawValueToUsuario(rawUsuario: UsuarioFormRawValue | NewUsuarioFormRawValue): IUsuario | NewUsuario {
    return {
      ...rawUsuario,
      fechaRegistro: dayjs(rawUsuario.fechaRegistro, DATE_TIME_FORMAT),
      ultimaActividad: dayjs(rawUsuario.ultimaActividad, DATE_TIME_FORMAT),
    };
  }

  private convertUsuarioToUsuarioRawValue(
    usuario: IUsuario | (Partial<NewUsuario> & UsuarioFormDefaults),
  ): UsuarioFormRawValue | PartialWithRequiredKeyOf<NewUsuarioFormRawValue> {
    return {
      ...usuario,
      fechaRegistro: usuario.fechaRegistro ? usuario.fechaRegistro.format(DATE_TIME_FORMAT) : undefined,
      ultimaActividad: usuario.ultimaActividad ? usuario.ultimaActividad.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
