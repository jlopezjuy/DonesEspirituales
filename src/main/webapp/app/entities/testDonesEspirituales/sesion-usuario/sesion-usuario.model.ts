import dayjs from 'dayjs/esm';
import { IUsuario } from 'app/entities/testDonesEspirituales/usuario/usuario.model';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';

export interface ISesionUsuario {
  id: number;
  respuestasTemporales?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
  fechaExpiracion?: dayjs.Dayjs | null;
  completada?: boolean | null;
  usuario?: Pick<IUsuario, 'id'> | null;
  respuestaUsuario?: Pick<IRespuestaUsuario, 'id'> | null;
}

export type NewSesionUsuario = Omit<ISesionUsuario, 'id'> & { id: null };
