import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
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
  user?: Pick<IUser, 'id' | 'login'> | null;
  cuestionario?: Pick<ICuestionario, 'id'> | null;
}

export type NewRespuestaUsuario = Omit<IRespuestaUsuario, 'id'> & { id: null };
