import dayjs from 'dayjs/esm';
import { IUsuario } from 'app/entities/testDonesEspirituales/usuario/usuario.model';
import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';
import { EstadoRespuesta } from 'app/entities/enumerations/estado-respuesta.model';

export interface IRespuestaUsuario {
  id: number;
  fechaInicio?: dayjs.Dayjs | null;
  fechaCompletado?: dayjs.Dayjs | null;
  estado?: keyof typeof EstadoRespuesta | null;
  tiempoTotalSegundos?: number | null;
  ipAddress?: string | null;
  userAgent?: string | null;
  usuario?: Pick<IUsuario, 'id'> | null;
  cuestionario?: Pick<ICuestionario, 'id'> | null;
}

export type NewRespuestaUsuario = Omit<IRespuestaUsuario, 'id'> & { id: null };
