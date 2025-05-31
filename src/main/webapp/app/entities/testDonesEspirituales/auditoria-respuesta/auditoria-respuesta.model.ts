import dayjs from 'dayjs/esm';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { IDetalleRespuesta } from 'app/entities/testDonesEspirituales/detalle-respuesta/detalle-respuesta.model';

export interface IAuditoriaRespuesta {
  id: number;
  valorAnterior?: number | null;
  valorNuevo?: number | null;
  timestampCambio?: dayjs.Dayjs | null;
  motivoCambio?: string | null;
  respuestaUsuario?: Pick<IRespuestaUsuario, 'id'> | null;
  detalleRespuesta?: Pick<IDetalleRespuesta, 'id'> | null;
}

export type NewAuditoriaRespuesta = Omit<IAuditoriaRespuesta, 'id'> & { id: null };
