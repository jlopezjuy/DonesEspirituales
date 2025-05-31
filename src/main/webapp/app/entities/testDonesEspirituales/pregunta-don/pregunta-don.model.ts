import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';

export interface IPreguntaDon {
  id: number;
  peso?: number | null;
  activa?: boolean | null;
  pregunta?: Pick<IPregunta, 'id'> | null;
  donEspiritual?: Pick<IDonEspiritual, 'id'> | null;
}

export type NewPreguntaDon = Omit<IPreguntaDon, 'id'> & { id: null };
