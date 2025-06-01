import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEscalaRespuesta, NewEscalaRespuesta } from '../escala-respuesta.model';

export type PartialUpdateEscalaRespuesta = Partial<IEscalaRespuesta> & Pick<IEscalaRespuesta, 'id'>;

export type EntityResponseType = HttpResponse<IEscalaRespuesta>;
export type EntityArrayResponseType = HttpResponse<IEscalaRespuesta[]>;

@Injectable({ providedIn: 'root' })
export class EscalaRespuestaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/escala-respuestas');

  create(escalaRespuesta: NewEscalaRespuesta): Observable<EntityResponseType> {
    return this.http.post<IEscalaRespuesta>(this.resourceUrl, escalaRespuesta, { observe: 'response' });
  }

  update(escalaRespuesta: IEscalaRespuesta): Observable<EntityResponseType> {
    return this.http.put<IEscalaRespuesta>(`${this.resourceUrl}/${this.getEscalaRespuestaIdentifier(escalaRespuesta)}`, escalaRespuesta, {
      observe: 'response',
    });
  }

  partialUpdate(escalaRespuesta: PartialUpdateEscalaRespuesta): Observable<EntityResponseType> {
    return this.http.patch<IEscalaRespuesta>(`${this.resourceUrl}/${this.getEscalaRespuestaIdentifier(escalaRespuesta)}`, escalaRespuesta, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscalaRespuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscalaRespuesta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEscalaRespuestaIdentifier(escalaRespuesta: Pick<IEscalaRespuesta, 'id'>): number {
    return escalaRespuesta.id;
  }

  compareEscalaRespuesta(o1: Pick<IEscalaRespuesta, 'id'> | null, o2: Pick<IEscalaRespuesta, 'id'> | null): boolean {
    return o1 && o2 ? this.getEscalaRespuestaIdentifier(o1) === this.getEscalaRespuestaIdentifier(o2) : o1 === o2;
  }

  addEscalaRespuestaToCollectionIfMissing<Type extends Pick<IEscalaRespuesta, 'id'>>(
    escalaRespuestaCollection: Type[],
    ...escalaRespuestasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const escalaRespuestas: Type[] = escalaRespuestasToCheck.filter(isPresent);
    if (escalaRespuestas.length > 0) {
      const escalaRespuestaCollectionIdentifiers = escalaRespuestaCollection.map(escalaRespuestaItem =>
        this.getEscalaRespuestaIdentifier(escalaRespuestaItem),
      );
      const escalaRespuestasToAdd = escalaRespuestas.filter(escalaRespuestaItem => {
        const escalaRespuestaIdentifier = this.getEscalaRespuestaIdentifier(escalaRespuestaItem);
        if (escalaRespuestaCollectionIdentifiers.includes(escalaRespuestaIdentifier)) {
          return false;
        }
        escalaRespuestaCollectionIdentifiers.push(escalaRespuestaIdentifier);
        return true;
      });
      return [...escalaRespuestasToAdd, ...escalaRespuestaCollection];
    }
    return escalaRespuestaCollection;
  }
}
