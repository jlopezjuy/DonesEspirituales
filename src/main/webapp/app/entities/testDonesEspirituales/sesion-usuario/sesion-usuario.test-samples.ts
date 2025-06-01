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
  respuestasTemporales: 'state membership overcooked',
  fechaCreacion: dayjs('2025-05-31T10:37'),
  fechaExpiracion: dayjs('2025-05-31T18:02'),
  completada: false,
};

export const sampleWithFullData: ISesionUsuario = {
  id: 8178,
  respuestasTemporales: 'edge personal daddy',
  fechaCreacion: dayjs('2025-05-31T15:40'),
  fechaExpiracion: dayjs('2025-05-31T10:25'),
  completada: true,
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
