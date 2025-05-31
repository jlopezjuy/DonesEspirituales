import { IResultadoDon, NewResultadoDon } from './resultado-don.model';

export const sampleWithRequiredData: IResultadoDon = {
  id: 10352,
  puntuacionTotal: 15522,
  porcentaje: 68.47,
  rankingPosicion: 15518,
  esDonPrincipal: false,
};

export const sampleWithPartialData: IResultadoDon = {
  id: 8815,
  puntuacionTotal: 30366,
  porcentaje: 34.11,
  rankingPosicion: 226,
  esDonPrincipal: false,
};

export const sampleWithFullData: IResultadoDon = {
  id: 9674,
  puntuacionTotal: 332,
  porcentaje: 85.71,
  rankingPosicion: 17291,
  esDonPrincipal: false,
};

export const sampleWithNewData: NewResultadoDon = {
  puntuacionTotal: 15065,
  porcentaje: 95.74,
  rankingPosicion: 25376,
  esDonPrincipal: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
