import dayjs from 'dayjs/esm';

import { ISesionUsuario, NewSesionUsuario } from './sesion-usuario.model';

export const sampleWithRequiredData: ISesionUsuario = {
  id: 8909,
  fechaCreacion: dayjs('2025-05-31T01:22'),
  fechaExpiracion: dayjs('2025-05-31T22:26'),
  completada: true,
};

export const sampleWithPartialData: ISesionUsuario = {
  id: 6322,
  respuestasTemporales: '../fake-data/blob/hipster.txt',
  fechaCreacion: dayjs('2025-05-31T17:19'),
  fechaExpiracion: dayjs('2025-05-31T09:41'),
  completada: false,
};

export const sampleWithFullData: ISesionUsuario = {
  id: 8178,
  respuestasTemporales: '../fake-data/blob/hipster.txt',
  fechaCreacion: dayjs('2025-05-31T19:38'),
  fechaExpiracion: dayjs('2025-05-31T19:03'),
  completada: false,
};

export const sampleWithNewData: NewSesionUsuario = {
  fechaCreacion: dayjs('2025-05-31T23:21'),
  fechaExpiracion: dayjs('2025-05-31T05:30'),
  completada: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
