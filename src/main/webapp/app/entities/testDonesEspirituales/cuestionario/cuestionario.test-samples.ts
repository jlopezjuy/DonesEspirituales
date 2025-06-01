import dayjs from 'dayjs/esm';

import { ICuestionario, NewCuestionario } from './cuestionario.model';

export const sampleWithRequiredData: ICuestionario = {
  id: 12268,
  titulo: 'voluminous grave',
  instrucciones: 'so at',
  totalPreguntas: 638,
  activo: true,
  fechaCreacion: dayjs('2025-05-31T22:47'),
  version: 31238,
};

export const sampleWithPartialData: ICuestionario = {
  id: 32630,
  titulo: 'bustling drat coolly',
  instrucciones: 'failing near',
  totalPreguntas: 415,
  activo: false,
  fechaCreacion: dayjs('2025-05-31T03:58'),
  version: 6752,
};

export const sampleWithFullData: ICuestionario = {
  id: 11937,
  titulo: 'quicker every gee',
  descripcion: 'regarding inasmuch',
  instrucciones: 'oof',
  totalPreguntas: 435,
  activo: true,
  fechaCreacion: dayjs('2025-05-31T03:52'),
  fechaActualizacion: dayjs('2025-05-31T08:05'),
  version: 14545,
};

export const sampleWithNewData: NewCuestionario = {
  titulo: 'stormy',
  instrucciones: 'voluminous deplore',
  totalPreguntas: 849,
  activo: false,
  fechaCreacion: dayjs('2025-05-31T08:01'),
  version: 31899,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
