import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPreguntaDon, NewPreguntaDon } from '../pregunta-don.model';

export type PartialUpdatePreguntaDon = Partial<IPreguntaDon> & Pick<IPreguntaDon, 'id'>;

export type EntityResponseType = HttpResponse<IPreguntaDon>;
export type EntityArrayResponseType = HttpResponse<IPreguntaDon[]>;

@Injectable({ providedIn: 'root' })
export class PreguntaDonService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pregunta-dons');

  create(preguntaDon: NewPreguntaDon): Observable<EntityResponseType> {
    return this.http.post<IPreguntaDon>(this.resourceUrl, preguntaDon, { observe: 'response' });
  }

  update(preguntaDon: IPreguntaDon): Observable<EntityResponseType> {
    return this.http.put<IPreguntaDon>(`${this.resourceUrl}/${this.getPreguntaDonIdentifier(preguntaDon)}`, preguntaDon, {
      observe: 'response',
    });
  }

  partialUpdate(preguntaDon: PartialUpdatePreguntaDon): Observable<EntityResponseType> {
    return this.http.patch<IPreguntaDon>(`${this.resourceUrl}/${this.getPreguntaDonIdentifier(preguntaDon)}`, preguntaDon, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreguntaDon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreguntaDon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPreguntaDonIdentifier(preguntaDon: Pick<IPreguntaDon, 'id'>): number {
    return preguntaDon.id;
  }

  comparePreguntaDon(o1: Pick<IPreguntaDon, 'id'> | null, o2: Pick<IPreguntaDon, 'id'> | null): boolean {
    return o1 && o2 ? this.getPreguntaDonIdentifier(o1) === this.getPreguntaDonIdentifier(o2) : o1 === o2;
  }

  addPreguntaDonToCollectionIfMissing<Type extends Pick<IPreguntaDon, 'id'>>(
    preguntaDonCollection: Type[],
    ...preguntaDonsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const preguntaDons: Type[] = preguntaDonsToCheck.filter(isPresent);
    if (preguntaDons.length > 0) {
      const preguntaDonCollectionIdentifiers = preguntaDonCollection.map(preguntaDonItem => this.getPreguntaDonIdentifier(preguntaDonItem));
      const preguntaDonsToAdd = preguntaDons.filter(preguntaDonItem => {
        const preguntaDonIdentifier = this.getPreguntaDonIdentifier(preguntaDonItem);
        if (preguntaDonCollectionIdentifiers.includes(preguntaDonIdentifier)) {
          return false;
        }
        preguntaDonCollectionIdentifiers.push(preguntaDonIdentifier);
        return true;
      });
      return [...preguntaDonsToAdd, ...preguntaDonCollection];
    }
    return preguntaDonCollection;
  }
}
