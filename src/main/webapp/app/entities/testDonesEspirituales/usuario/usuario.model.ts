import dayjs from 'dayjs/esm';
import { Genero } from 'app/entities/enumerations/genero.model';

export interface IUsuario {
  id: number;
  nombre?: string | null;
  apellido?: string | null;
  email?: string | null;
  telefono?: string | null;
  fechaNacimiento?: dayjs.Dayjs | null;
  genero?: keyof typeof Genero | null;
  iglesia?: string | null;
  denominacion?: string | null;
  fechaRegistro?: dayjs.Dayjs | null;
  ultimaActividad?: dayjs.Dayjs | null;
  activo?: boolean | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
