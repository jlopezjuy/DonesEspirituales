import dayjs from 'dayjs/esm';

import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 23732,
  nombre: 'though phew',
  apellido: 'selfishly deliberately dandelion',
  email: 'V@nuyE.U_K',
  fechaRegistro: dayjs('2025-05-31T02:01'),
  activo: true,
};

export const sampleWithPartialData: IUsuario = {
  id: 20508,
  nombre: 'reconstitute',
  apellido: 'extent grizzled',
  email: ';b2D@Pv.#(*11',
  fechaNacimiento: dayjs('2025-05-31'),
  iglesia: 'colossal defiantly coil',
  fechaRegistro: dayjs('2025-05-31T16:27'),
  ultimaActividad: dayjs('2025-05-31T14:08'),
  activo: false,
};

export const sampleWithFullData: IUsuario = {
  id: 32608,
  nombre: 'outlaw lest',
  apellido: 'nocturnal yum up',
  email: '2@B}~gX.c',
  telefono: 'release impostor who',
  fechaNacimiento: dayjs('2025-05-31'),
  genero: 'PREFIERO_NO_DECIR',
  iglesia: 'gah past between',
  denominacion: 'given puppet',
  fechaRegistro: dayjs('2025-05-31T16:12'),
  ultimaActividad: dayjs('2025-05-31T18:21'),
  activo: true,
};

export const sampleWithNewData: NewUsuario = {
  nombre: 'beside pish strait',
  apellido: 'programme electrify',
  email: 'E@;.{n.gy',
  fechaRegistro: dayjs('2025-05-31T03:17'),
  activo: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
