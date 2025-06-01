import dayjs from 'dayjs/esm';

import { IPregunta, NewPregunta } from './pregunta.model';

export const sampleWithRequiredData: IPregunta = {
  id: 13882,
  numeroPregunta: 1237,
  textoPregunta: '../fake-data/blob/hipster.txt',
  obligatoria: true,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T17:02'),
};

export const sampleWithPartialData: IPregunta = {
  id: 9088,
  numeroPregunta: 24155,
  textoPregunta: '../fake-data/blob/hipster.txt',
  obligatoria: true,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T14:00'),
};

export const sampleWithFullData: IPregunta = {
  id: 11237,
  numeroPregunta: 19011,
  textoPregunta: '../fake-data/blob/hipster.txt',
  obligatoria: false,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T14:07'),
};

export const sampleWithNewData: NewPregunta = {
  numeroPregunta: 27364,
  textoPregunta: '../fake-data/blob/hipster.txt',
  obligatoria: true,
  activa: false,
  fechaCreacion: dayjs('2025-05-31T07:06'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
