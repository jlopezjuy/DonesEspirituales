import dayjs from 'dayjs/esm';
import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';

export interface IPregunta {
  id: number;
  numeroPregunta?: number | null;
  textoPregunta?: string | null;
  obligatoria?: boolean | null;
  activa?: boolean | null;
  fechaCreacion?: dayjs.Dayjs | null;
  cuestionario?: Pick<ICuestionario, 'id'> | null;
}

export type NewPregunta = Omit<IPregunta, 'id'> & { id: null };
