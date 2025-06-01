import dayjs from 'dayjs/esm';

import { IRespuestaUsuario, NewRespuestaUsuario } from './respuesta-usuario.model';

export const sampleWithRequiredData: IRespuestaUsuario = {
  id: 5552,
  fechaInicio: dayjs('2025-05-31T22:53'),
  estado: 'COMPLETADA',
};

export const sampleWithPartialData: IRespuestaUsuario = {
  id: 31627,
  fechaInicio: dayjs('2025-05-31T01:40'),
  estado: 'ABANDONADA',
  tiempoTotalSegundos: 9016,
  ipAddress: 'but ugh because',
};

export const sampleWithFullData: IRespuestaUsuario = {
  id: 28311,
  fechaInicio: dayjs('2025-05-31T22:38'),
  fechaCompletado: dayjs('2025-05-31T00:52'),
  estado: 'EN_PROGRESO',
  tiempoTotalSegundos: 6558,
  ipAddress: 'along unused',
  userAgent: 'yet',
};

export const sampleWithNewData: NewRespuestaUsuario = {
  fechaInicio: dayjs('2025-05-31T15:12'),
  estado: 'INICIADA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
