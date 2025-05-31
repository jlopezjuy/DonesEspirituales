import { IPreguntaDon, NewPreguntaDon } from './pregunta-don.model';

export const sampleWithRequiredData: IPreguntaDon = {
  id: 15716,
  peso: 3,
  activa: true,
};

export const sampleWithPartialData: IPreguntaDon = {
  id: 11119,
  peso: 1,
  activa: false,
};

export const sampleWithFullData: IPreguntaDon = {
  id: 12555,
  peso: 6,
  activa: false,
};

export const sampleWithNewData: NewPreguntaDon = {
  peso: 10,
  activa: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
