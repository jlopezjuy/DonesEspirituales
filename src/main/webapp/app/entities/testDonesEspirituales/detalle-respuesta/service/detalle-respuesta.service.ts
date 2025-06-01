import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetalleRespuesta, NewDetalleRespuesta } from '../detalle-respuesta.model';

export type PartialUpdateDetalleRespuesta = Partial<IDetalleRespuesta> & Pick<IDetalleRespuesta, 'id'>;

type RestOf<T extends IDetalleRespuesta | NewDetalleRespuesta> = Omit<T, 'timestampRespuesta'> & {
  timestampRespuesta?: string | null;
};

export type RestDetalleRespuesta = RestOf<IDetalleRespuesta>;

export type NewRestDetalleRespuesta = RestOf<NewDetalleRespuesta>;

export type PartialUpdateRestDetalleRespuesta = RestOf<PartialUpdateDetalleRespuesta>;

export type EntityResponseType = HttpResponse<IDetalleRespuesta>;
export type EntityArrayResponseType = HttpResponse<IDetalleRespuesta[]>;

@Injectable({ providedIn: 'root' })
export class DetalleRespuestaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detalle-respuestas');

  create(detalleRespuesta: NewDetalleRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleRespuesta);
    return this.http
      .post<RestDetalleRespuesta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(detalleRespuesta: IDetalleRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleRespuesta);
    return this.http
      .put<RestDetalleRespuesta>(`${this.resourceUrl}/${this.getDetalleRespuestaIdentifier(detalleRespuesta)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(detalleRespuesta: PartialUpdateDetalleRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(detalleRespuesta);
    return this.http
      .patch<RestDetalleRespuesta>(`${this.resourceUrl}/${this.getDetalleRespuestaIdentifier(detalleRespuesta)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDetalleRespuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDetalleRespuesta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetalleRespuestaIdentifier(detalleRespuesta: Pick<IDetalleRespuesta, 'id'>): number {
    return detalleRespuesta.id;
  }

  compareDetalleRespuesta(o1: Pick<IDetalleRespuesta, 'id'> | null, o2: Pick<IDetalleRespuesta, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetalleRespuestaIdentifier(o1) === this.getDetalleRespuestaIdentifier(o2) : o1 === o2;
  }

  addDetalleRespuestaToCollectionIfMissing<Type extends Pick<IDetalleRespuesta, 'id'>>(
    detalleRespuestaCollection: Type[],
    ...detalleRespuestasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detalleRespuestas: Type[] = detalleRespuestasToCheck.filter(isPresent);
    if (detalleRespuestas.length > 0) {
      const detalleRespuestaCollectionIdentifiers = detalleRespuestaCollection.map(detalleRespuestaItem =>
        this.getDetalleRespuestaIdentifier(detalleRespuestaItem),
      );
      const detalleRespuestasToAdd = detalleRespuestas.filter(detalleRespuestaItem => {
        const detalleRespuestaIdentifier = this.getDetalleRespuestaIdentifier(detalleRespuestaItem);
        if (detalleRespuestaCollectionIdentifiers.includes(detalleRespuestaIdentifier)) {
          return false;
        }
        detalleRespuestaCollectionIdentifiers.push(detalleRespuestaIdentifier);
        return true;
      });
      return [...detalleRespuestasToAdd, ...detalleRespuestaCollection];
    }
    return detalleRespuestaCollection;
  }

  protected convertDateFromClient<T extends IDetalleRespuesta | NewDetalleRespuesta | PartialUpdateDetalleRespuesta>(
    detalleRespuesta: T,
  ): RestOf<T> {
    return {
      ...detalleRespuesta,
      timestampRespuesta: detalleRespuesta.timestampRespuesta?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDetalleRespuesta: RestDetalleRespuesta): IDetalleRespuesta {
    return {
      ...restDetalleRespuesta,
      timestampRespuesta: restDetalleRespuesta.timestampRespuesta ? dayjs(restDetalleRespuesta.timestampRespuesta) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDetalleRespuesta>): HttpResponse<IDetalleRespuesta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDetalleRespuesta[]>): HttpResponse<IDetalleRespuesta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
