import dayjs from 'dayjs/esm';
import { TipoDato } from 'app/entities/enumerations/tipo-dato.model';

export interface IConfiguracionSistema {
  id: number;
  clave?: string | null;
  valor?: string | null;
  descripcion?: string | null;
  tipoDato?: keyof typeof TipoDato | null;
  fechaActualizacion?: dayjs.Dayjs | null;
}

export type NewConfiguracionSistema = Omit<IConfiguracionSistema, 'id'> & { id: null };
