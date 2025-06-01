import dayjs from 'dayjs/esm';

import { IPregunta, NewPregunta } from './pregunta.model';

export const sampleWithRequiredData: IPregunta = {
  id: 13882,
  numeroPregunta: 1237,
  textoPregunta: 'stark meanwhile',
  obligatoria: true,
  activa: true,
  fechaCreacion: dayjs('2025-05-31T09:50'),
};

export const sampleWithPartialData: IPregunta = {
  id: 9088,
  numeroPregunta: 24155,
  textoPregunta: 'rudely',
  obligatoria: false,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T04:11'),
};

export const sampleWithFullData: IPregunta = {
  id: 11237,
  numeroPregunta: 19011,
  textoPregunta: 'robust far among',
  obligatoria: false,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T13:18'),
};

export const sampleWithNewData: NewPregunta = {
  numeroPregunta: 27364,
  textoPregunta: 'practical pretty',
  obligatoria: true,
  activa: true,
  fechaCreacion: dayjs('2025-05-31T07:53'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
