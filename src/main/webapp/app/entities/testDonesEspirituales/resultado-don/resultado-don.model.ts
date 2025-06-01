import { IInterpretacion } from 'app/entities/testDonesEspirituales/interpretacion/interpretacion.model';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';

export interface IResultadoDon {
  id: number;
  puntuacionTotal?: number | null;
  porcentaje?: number | null;
  rankingPosicion?: number | null;
  esDonPrincipal?: boolean | null;
  interpretacion?: Pick<IInterpretacion, 'id'> | null;
  respuestaUsuario?: Pick<IRespuestaUsuario, 'id'> | null;
  donEspiritual?: Pick<IDonEspiritual, 'id'> | null;
}

export type NewResultadoDon = Omit<IResultadoDon, 'id'> & { id: null };
