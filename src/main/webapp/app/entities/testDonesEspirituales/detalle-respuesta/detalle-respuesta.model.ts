import dayjs from 'dayjs/esm';
import { IEscalaRespuesta } from 'app/entities/testDonesEspirituales/escala-respuesta/escala-respuesta.model';
import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';

export interface IDetalleRespuesta {
  id: number;
  valorRespuesta?: number | null;
  timestampRespuesta?: dayjs.Dayjs | null;
  tiempoPreguntaSegundos?: number | null;
  escalaRespuesta?: Pick<IEscalaRespuesta, 'id'> | null;
  pregunta?: Pick<IPregunta, 'id'> | null;
  respuestaUsuario?: Pick<IRespuestaUsuario, 'id'> | null;
}

export type NewDetalleRespuesta = Omit<IDetalleRespuesta, 'id'> & { id: null };
