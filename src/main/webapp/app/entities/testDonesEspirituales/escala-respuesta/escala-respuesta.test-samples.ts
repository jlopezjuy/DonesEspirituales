import { IEscalaRespuesta, NewEscalaRespuesta } from './escala-respuesta.model';

export const sampleWithRequiredData: IEscalaRespuesta = {
  id: 29587,
  valor: 5,
  etiqueta: 'alongside miserable',
  orden: 9244,
};

export const sampleWithPartialData: IEscalaRespuesta = {
  id: 20985,
  valor: 10,
  etiqueta: 'what defrag whoever',
  orden: 30330,
};

export const sampleWithFullData: IEscalaRespuesta = {
  id: 6241,
  valor: 0,
  etiqueta: 'consequently',
  descripcion: '../fake-data/blob/hipster.txt',
  orden: 8363,
};

export const sampleWithNewData: NewEscalaRespuesta = {
  valor: 4,
  etiqueta: 'accidentally which',
  orden: 16767,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
