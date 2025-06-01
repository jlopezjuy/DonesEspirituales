export interface IEscalaRespuesta {
  id: number;
  valor?: number | null;
  etiqueta?: string | null;
  descripcion?: string | null;
  orden?: number | null;
}

export type NewEscalaRespuesta = Omit<IEscalaRespuesta, 'id'> & { id: null };
