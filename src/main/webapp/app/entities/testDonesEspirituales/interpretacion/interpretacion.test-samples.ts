import { IInterpretacion, NewInterpretacion } from './interpretacion.model';

export const sampleWithRequiredData: IInterpretacion = {
  id: 28324,
  puntuacionMinima: 8508,
  puntuacionMaxima: 15946,
  nivel: 'MUY_ALTO',
  descripcionNivel: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IInterpretacion = {
  id: 9331,
  puntuacionMinima: 31046,
  puntuacionMaxima: 27874,
  nivel: 'BAJO',
  descripcionNivel: '../fake-data/blob/hipster.txt',
  recomendaciones: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IInterpretacion = {
  id: 20531,
  puntuacionMinima: 23002,
  puntuacionMaxima: 1535,
  nivel: 'MEDIO',
  descripcionNivel: '../fake-data/blob/hipster.txt',
  recomendaciones: '../fake-data/blob/hipster.txt',
  areasServicio: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewInterpretacion = {
  puntuacionMinima: 26778,
  puntuacionMaxima: 27553,
  nivel: 'ALTO',
  descripcionNivel: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
