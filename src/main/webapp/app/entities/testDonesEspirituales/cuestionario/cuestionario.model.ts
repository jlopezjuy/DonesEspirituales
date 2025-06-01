import dayjs from 'dayjs/esm';

export interface ICuestionario {
  id: number;
  titulo?: string | null;
  descripcion?: string | null;
  instrucciones?: string | null;
  totalPreguntas?: number | null;
  activo?: boolean | null;
  fechaCreacion?: dayjs.Dayjs | null;
  fechaActualizacion?: dayjs.Dayjs | null;
  version?: number | null;
}

export type NewCuestionario = Omit<ICuestionario, 'id'> & { id: null };
