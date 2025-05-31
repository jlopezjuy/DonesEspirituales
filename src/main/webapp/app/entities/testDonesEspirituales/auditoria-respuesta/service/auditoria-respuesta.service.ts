import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuditoriaRespuesta, NewAuditoriaRespuesta } from '../auditoria-respuesta.model';

export type PartialUpdateAuditoriaRespuesta = Partial<IAuditoriaRespuesta> & Pick<IAuditoriaRespuesta, 'id'>;

type RestOf<T extends IAuditoriaRespuesta | NewAuditoriaRespuesta> = Omit<T, 'timestampCambio'> & {
  timestampCambio?: string | null;
};

export type RestAuditoriaRespuesta = RestOf<IAuditoriaRespuesta>;

export type NewRestAuditoriaRespuesta = RestOf<NewAuditoriaRespuesta>;

export type PartialUpdateRestAuditoriaRespuesta = RestOf<PartialUpdateAuditoriaRespuesta>;

export type EntityResponseType = HttpResponse<IAuditoriaRespuesta>;
export type EntityArrayResponseType = HttpResponse<IAuditoriaRespuesta[]>;

@Injectable({ providedIn: 'root' })
export class AuditoriaRespuestaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/auditoria-respuestas');

  create(auditoriaRespuesta: NewAuditoriaRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaRespuesta);
    return this.http
      .post<RestAuditoriaRespuesta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(auditoriaRespuesta: IAuditoriaRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaRespuesta);
    return this.http
      .put<RestAuditoriaRespuesta>(`${this.resourceUrl}/${this.getAuditoriaRespuestaIdentifier(auditoriaRespuesta)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(auditoriaRespuesta: PartialUpdateAuditoriaRespuesta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditoriaRespuesta);
    return this.http
      .patch<RestAuditoriaRespuesta>(`${this.resourceUrl}/${this.getAuditoriaRespuestaIdentifier(auditoriaRespuesta)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAuditoriaRespuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAuditoriaRespuesta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAuditoriaRespuestaIdentifier(auditoriaRespuesta: Pick<IAuditoriaRespuesta, 'id'>): number {
    return auditoriaRespuesta.id;
  }

  compareAuditoriaRespuesta(o1: Pick<IAuditoriaRespuesta, 'id'> | null, o2: Pick<IAuditoriaRespuesta, 'id'> | null): boolean {
    return o1 && o2 ? this.getAuditoriaRespuestaIdentifier(o1) === this.getAuditoriaRespuestaIdentifier(o2) : o1 === o2;
  }

  addAuditoriaRespuestaToCollectionIfMissing<Type extends Pick<IAuditoriaRespuesta, 'id'>>(
    auditoriaRespuestaCollection: Type[],
    ...auditoriaRespuestasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const auditoriaRespuestas: Type[] = auditoriaRespuestasToCheck.filter(isPresent);
    if (auditoriaRespuestas.length > 0) {
      const auditoriaRespuestaCollectionIdentifiers = auditoriaRespuestaCollection.map(auditoriaRespuestaItem =>
        this.getAuditoriaRespuestaIdentifier(auditoriaRespuestaItem),
      );
      const auditoriaRespuestasToAdd = auditoriaRespuestas.filter(auditoriaRespuestaItem => {
        const auditoriaRespuestaIdentifier = this.getAuditoriaRespuestaIdentifier(auditoriaRespuestaItem);
        if (auditoriaRespuestaCollectionIdentifiers.includes(auditoriaRespuestaIdentifier)) {
          return false;
        }
        auditoriaRespuestaCollectionIdentifiers.push(auditoriaRespuestaIdentifier);
        return true;
      });
      return [...auditoriaRespuestasToAdd, ...auditoriaRespuestaCollection];
    }
    return auditoriaRespuestaCollection;
  }

  protected convertDateFromClient<T extends IAuditoriaRespuesta | NewAuditoriaRespuesta | PartialUpdateAuditoriaRespuesta>(
    auditoriaRespuesta: T,
  ): RestOf<T> {
    return {
      ...auditoriaRespuesta,
      timestampCambio: auditoriaRespuesta.timestampCambio?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAuditoriaRespuesta: RestAuditoriaRespuesta): IAuditoriaRespuesta {
    return {
      ...restAuditoriaRespuesta,
      timestampCambio: restAuditoriaRespuesta.timestampCambio ? dayjs(restAuditoriaRespuesta.timestampCambio) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAuditoriaRespuesta>): HttpResponse<IAuditoriaRespuesta> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAuditoriaRespuesta[]>): HttpResponse<IAuditoriaRespuesta[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
