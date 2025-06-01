import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';

export interface ISesionUsuario {
  id: number;
  respuestasTemporales?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
  fechaExpiracion?: dayjs.Dayjs | null;
  completada?: boolean | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  respuestaUsuario?: Pick<IRespuestaUsuario, 'id'> | null;
}

export type NewSesionUsuario = Omit<ISesionUsuario, 'id'> & { id: null };
