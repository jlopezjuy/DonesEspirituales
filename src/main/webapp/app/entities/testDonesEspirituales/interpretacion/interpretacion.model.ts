import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { NivelInterpretacion } from 'app/entities/enumerations/nivel-interpretacion.model';

export interface IInterpretacion {
  id: number;
  puntuacionMinima?: number | null;
  puntuacionMaxima?: number | null;
  nivel?: keyof typeof NivelInterpretacion | null;
  descripcionNivel?: string | null;
  recomendaciones?: string | null;
  areasServicio?: string | null;
  donEspiritual?: Pick<IDonEspiritual, 'id'> | null;
}

export type NewInterpretacion = Omit<IInterpretacion, 'id'> & { id: null };
