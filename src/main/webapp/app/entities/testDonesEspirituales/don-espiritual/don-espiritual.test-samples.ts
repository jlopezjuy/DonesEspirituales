import { IDonEspiritual, NewDonEspiritual } from './don-espiritual.model';

export const sampleWithRequiredData: IDonEspiritual = {
  id: 11933,
  nombre: 'chiffonier',
  nombreCorto: 'between gosh pish',
  activo: false,
};

export const sampleWithPartialData: IDonEspiritual = {
  id: 8606,
  nombre: 'finally affiliate',
  nombreCorto: 'mortally good thin',
  versiculosBiblicos: 'scarcely honestly',
  activo: true,
  ordenPresentacion: 17626,
};

export const sampleWithFullData: IDonEspiritual = {
  id: 10443,
  nombre: 'evince but until',
  nombreCorto: 'jubilant handful',
  descripcion: 'dandelion',
  caracteristicas: 'empty via',
  versiculosBiblicos: 'off besides lampoon',
  activo: false,
  ordenPresentacion: 15385,
};

export const sampleWithNewData: NewDonEspiritual = {
  nombre: 'reasonable psst',
  nombreCorto: 'onto',
  activo: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
