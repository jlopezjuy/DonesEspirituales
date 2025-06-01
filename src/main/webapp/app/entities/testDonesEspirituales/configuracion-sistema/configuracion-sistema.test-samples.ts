import dayjs from 'dayjs/esm';

import { IConfiguracionSistema, NewConfiguracionSistema } from './configuracion-sistema.model';

export const sampleWithRequiredData: IConfiguracionSistema = {
  id: 22834,
  clave: 'acceptable unique',
  valor: 'light',
  tipoDato: 'BOOLEAN',
  fechaActualizacion: dayjs('2025-05-31T09:39'),
};

export const sampleWithPartialData: IConfiguracionSistema = {
  id: 3671,
  clave: 'uh-huh',
  valor: 'um',
  descripcion: '../fake-data/blob/hipster.txt',
  tipoDato: 'BOOLEAN',
  fechaActualizacion: dayjs('2025-05-31T10:59'),
};

export const sampleWithFullData: IConfiguracionSistema = {
  id: 12078,
  clave: 'however',
  valor: 'well',
  descripcion: '../fake-data/blob/hipster.txt',
  tipoDato: 'DECIMAL',
  fechaActualizacion: dayjs('2025-05-31T12:31'),
};

export const sampleWithNewData: NewConfiguracionSistema = {
  clave: 'gosh geez retrospectivity',
  valor: 'unnaturally',
  tipoDato: 'DECIMAL',
  fechaActualizacion: dayjs('2025-05-31T00:58'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
