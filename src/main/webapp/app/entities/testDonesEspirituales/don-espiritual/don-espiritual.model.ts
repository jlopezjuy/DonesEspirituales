export interface IDonEspiritual {
  id: number;
  nombre?: string | null;
  nombreCorto?: string | null;
  descripcion?: string | null;
  caracteristicas?: string | null;
  versiculosBiblicos?: string | null;
  activo?: boolean | null;
  ordenPresentacion?: number | null;
}

export type NewDonEspiritual = Omit<IDonEspiritual, 'id'> & { id: null };
