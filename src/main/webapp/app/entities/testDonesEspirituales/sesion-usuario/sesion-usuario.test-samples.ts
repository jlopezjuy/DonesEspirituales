import dayjs from 'dayjs/esm';

import { ISesionUsuario, NewSesionUsuario } from './sesion-usuario.model';

export const sampleWithRequiredData: ISesionUsuario = {
  id: 8909,
  fechaCreacion: dayjs('2025-05-31T02:12'),
  fechaExpiracion: dayjs('2025-05-31T23:15'),
  completada: true,
};

export const sampleWithPartialData: ISesionUsuario = {
  id: 6322,
  respuestasTemporales: '../fake-data/blob/hipster.txt',
  fechaCreacion: dayjs('2025-05-31T18:08'),
  fechaExpiracion: dayjs('2025-05-31T10:30'),
  completada: false,
};

export const sampleWithFullData: ISesionUsuario = {
  id: 8178,
  respuestasTemporales: '../fake-data/blob/hipster.txt',
  fechaCreacion: dayjs('2025-05-31T20:27'),
  fechaExpiracion: dayjs('2025-05-31T19:53'),
  completada: false,
};

export const sampleWithNewData: NewSesionUsuario = {
  fechaCreacion: dayjs('2025-06-01T00:10'),
  fechaExpiracion: dayjs('2025-05-31T06:19'),
  completada: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
