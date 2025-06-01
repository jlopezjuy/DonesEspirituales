import dayjs from 'dayjs/esm';

import { IAuditoriaRespuesta, NewAuditoriaRespuesta } from './auditoria-respuesta.model';

export const sampleWithRequiredData: IAuditoriaRespuesta = {
  id: 5429,
  valorNuevo: 4,
  timestampCambio: dayjs('2025-05-31T05:41'),
};

export const sampleWithPartialData: IAuditoriaRespuesta = {
  id: 5516,
  valorNuevo: 1,
  timestampCambio: dayjs('2025-05-31T12:55'),
  motivoCambio: 'extent',
};

export const sampleWithFullData: IAuditoriaRespuesta = {
  id: 24642,
  valorAnterior: 0,
  valorNuevo: 9,
  timestampCambio: dayjs('2025-05-31T17:58'),
  motivoCambio: 'before coal',
};

export const sampleWithNewData: NewAuditoriaRespuesta = {
  valorNuevo: 3,
  timestampCambio: dayjs('2025-05-31T12:10'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
