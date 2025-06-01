import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICuestionario, NewCuestionario } from '../cuestionario.model';

export type PartialUpdateCuestionario = Partial<ICuestionario> & Pick<ICuestionario, 'id'>;

type RestOf<T extends ICuestionario | NewCuestionario> = Omit<T, 'fechaCreacion' | 'fechaActualizacion'> & {
  fechaCreacion?: string | null;
  fechaActualizacion?: string | null;
};

export type RestCuestionario = RestOf<ICuestionario>;

export type NewRestCuestionario = RestOf<NewCuestionario>;

export type PartialUpdateRestCuestionario = RestOf<PartialUpdateCuestionario>;

export type EntityResponseType = HttpResponse<ICuestionario>;
export type EntityArrayResponseType = HttpResponse<ICuestionario[]>;

@Injectable({ providedIn: 'root' })
export class CuestionarioService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cuestionarios');

  create(cuestionario: NewCuestionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuestionario);
    return this.http
      .post<RestCuestionario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cuestionario: ICuestionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuestionario);
    return this.http
      .put<RestCuestionario>(`${this.resourceUrl}/${this.getCuestionarioIdentifier(cuestionario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cuestionario: PartialUpdateCuestionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuestionario);
    return this.http
      .patch<RestCuestionario>(`${this.resourceUrl}/${this.getCuestionarioIdentifier(cuestionario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCuestionario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCuestionario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCuestionarioIdentifier(cuestionario: Pick<ICuestionario, 'id'>): number {
    return cuestionario.id;
  }

  compareCuestionario(o1: Pick<ICuestionario, 'id'> | null, o2: Pick<ICuestionario, 'id'> | null): boolean {
    return o1 && o2 ? this.getCuestionarioIdentifier(o1) === this.getCuestionarioIdentifier(o2) : o1 === o2;
  }

  addCuestionarioToCollectionIfMissing<Type extends Pick<ICuestionario, 'id'>>(
    cuestionarioCollection: Type[],
    ...cuestionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cuestionarios: Type[] = cuestionariosToCheck.filter(isPresent);
    if (cuestionarios.length > 0) {
      const cuestionarioCollectionIdentifiers = cuestionarioCollection.map(cuestionarioItem =>
        this.getCuestionarioIdentifier(cuestionarioItem),
      );
      const cuestionariosToAdd = cuestionarios.filter(cuestionarioItem => {
        const cuestionarioIdentifier = this.getCuestionarioIdentifier(cuestionarioItem);
        if (cuestionarioCollectionIdentifiers.includes(cuestionarioIdentifier)) {
          return false;
        }
        cuestionarioCollectionIdentifiers.push(cuestionarioIdentifier);
        return true;
      });
      return [...cuestionariosToAdd, ...cuestionarioCollection];
    }
    return cuestionarioCollection;
  }

  protected convertDateFromClient<T extends ICuestionario | NewCuestionario | PartialUpdateCuestionario>(cuestionario: T): RestOf<T> {
    return {
      ...cuestionario,
      fechaCreacion: cuestionario.fechaCreacion?.toJSON() ?? null,
      fechaActualizacion: cuestionario.fechaActualizacion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCuestionario: RestCuestionario): ICuestionario {
    return {
      ...restCuestionario,
      fechaCreacion: restCuestionario.fechaCreacion ? dayjs(restCuestionario.fechaCreacion) : undefined,
      fechaActualizacion: restCuestionario.fechaActualizacion ? dayjs(restCuestionario.fechaActualizacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCuestionario>): HttpResponse<ICuestionario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCuestionario[]>): HttpResponse<ICuestionario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
