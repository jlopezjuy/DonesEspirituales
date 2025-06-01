import dayjs from 'dayjs/esm';

import { IDetalleRespuesta, NewDetalleRespuesta } from './detalle-respuesta.model';

export const sampleWithRequiredData: IDetalleRespuesta = {
  id: 17442,
  valorRespuesta: 9,
  timestampRespuesta: dayjs('2025-05-31T11:49'),
};

export const sampleWithPartialData: IDetalleRespuesta = {
  id: 19730,
  valorRespuesta: 5,
  timestampRespuesta: dayjs('2025-05-31T23:19'),
};

export const sampleWithFullData: IDetalleRespuesta = {
  id: 5483,
  valorRespuesta: 1,
  timestampRespuesta: dayjs('2025-05-31T02:10'),
  tiempoPreguntaSegundos: 20976,
};

export const sampleWithNewData: NewDetalleRespuesta = {
  valorRespuesta: 1,
  timestampRespuesta: dayjs('2025-05-31T20:46'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
