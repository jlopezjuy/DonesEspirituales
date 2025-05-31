import dayjs from 'dayjs/esm';

import { ICuestionario, NewCuestionario } from './cuestionario.model';

export const sampleWithRequiredData: ICuestionario = {
  id: 12268,
  titulo: 'voluminous grave',
  instrucciones: '../fake-data/blob/hipster.txt',
  totalPreguntas: 562,
  activo: true,
  fechaCreacion: dayjs('2025-05-31T19:53'),
  version: 30952,
};

export const sampleWithPartialData: ICuestionario = {
  id: 32630,
  titulo: 'bustling drat coolly',
  instrucciones: '../fake-data/blob/hipster.txt',
  totalPreguntas: 587,
  activo: false,
  fechaCreacion: dayjs('2025-05-31T20:49'),
  version: 8421,
};

export const sampleWithFullData: ICuestionario = {
  id: 11937,
  titulo: 'quicker every gee',
  descripcion: '../fake-data/blob/hipster.txt',
  instrucciones: '../fake-data/blob/hipster.txt',
  totalPreguntas: 606,
  activo: true,
  fechaCreacion: dayjs('2025-05-31T07:50'),
  fechaActualizacion: dayjs('2025-05-31T00:12'),
  version: 9600,
};

export const sampleWithNewData: NewCuestionario = {
  titulo: 'stormy',
  instrucciones: '../fake-data/blob/hipster.txt',
  totalPreguntas: 577,
  activo: false,
  fechaCreacion: dayjs('2025-05-31T11:26'),
  version: 15327,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
