import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPregunta, NewPregunta } from '../pregunta.model';

export type PartialUpdatePregunta = Partial<IPregunta> & Pick<IPregunta, 'id'>;

type RestOf<T extends IPregunta | NewPregunta> = Omit<T, 'fechaCreacion'> & {
  fechaCreacion?: string | null;
};

export type RestPregunta = RestOf<IPregunta>;

export type NewRestPregunta = RestOf<NewPregunta>;

export type PartialUpdateRestPregunta = RestOf<PartialUpdatePregunta>;

export type EntityResponseType = HttpResponse<IPregunta>;
export type EntityArrayResponseType = HttpResponse<IPregunta[]>;

@Injectable({ providedIn: 'root' })
export class PreguntaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/preguntas');

  create(pregunta: NewPregunta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pregunta);
    return this.http
      .post<RestPregunta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pregunta: IPregunta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pregunta);
    return this.http
      .put<RestPregunta>(`${this.resourceUrl}/${this.getPreguntaIdentifier(pregunta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pregunta: PartialUpdatePregunta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pregunta);
    return this.http
      .patch<RestPregunta>(`${this.resourceUrl}/${this.getPreguntaIdentifier(pregunta)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPregunta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPregunta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPreguntaIdentifier(pregunta: Pick<IPregunta, 'id'>): number {
    return pregunta.id;
  }

  comparePregunta(o1: Pick<IPregunta, 'id'> | null, o2: Pick<IPregunta, 'id'> | null): boolean {
    return o1 && o2 ? this.getPreguntaIdentifier(o1) === this.getPreguntaIdentifier(o2) : o1 === o2;
  }

  addPreguntaToCollectionIfMissing<Type extends Pick<IPregunta, 'id'>>(
    preguntaCollection: Type[],
    ...preguntasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const preguntas: Type[] = preguntasToCheck.filter(isPresent);
    if (preguntas.length > 0) {
      const preguntaCollectionIdentifiers = preguntaCollection.map(preguntaItem => this.getPreguntaIdentifier(preguntaItem));
      const preguntasToAdd = preguntas.filter(preguntaItem => {
        const preguntaIdentifier = this.getPreguntaIdentifier(preguntaItem);
        if (preguntaCollectionIdentifiers.includes(preguntaIdentifier)) {
          return false;
        }
        preguntaCollectionIdentifiers.push(preguntaIdentifier);
        return true;
      });
      return [...preguntasToAdd, ...preguntaCollection];
    }
    return preguntaCollection;
  }

  protected convertDateFromClient<T extends IPregunta | NewPregunta | PartialUpdatePregunta>(pregunta: T): RestOf<T> {
    return {
      ...pregunta,
      fechaCreacion: pregunta.fechaCreacion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPregunta: RestPregunta): IPregunta {
    return {
      ...restPregunta,
      fechaCreacion: restPregunta.fechaCreacion ? dayjs(restPregunta.fechaCreacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPregunta>): HttpResponse<IPregunta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPregunta[]>): HttpResponse<IPregunta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
