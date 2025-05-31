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
  versiculosBiblicos: '../fake-data/blob/hipster.txt',
  activo: true,
  ordenPresentacion: 2037,
};

export const sampleWithFullData: IDonEspiritual = {
  id: 10443,
  nombre: 'evince but until',
  nombreCorto: 'jubilant handful',
  descripcion: '../fake-data/blob/hipster.txt',
  caracteristicas: '../fake-data/blob/hipster.txt',
  versiculosBiblicos: '../fake-data/blob/hipster.txt',
  activo: true,
  ordenPresentacion: 2415,
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
