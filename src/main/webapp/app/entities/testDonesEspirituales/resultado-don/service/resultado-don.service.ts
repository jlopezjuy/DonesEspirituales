import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResultadoDon, NewResultadoDon } from '../resultado-don.model';

export type PartialUpdateResultadoDon = Partial<IResultadoDon> & Pick<IResultadoDon, 'id'>;

export type EntityResponseType = HttpResponse<IResultadoDon>;
export type EntityArrayResponseType = HttpResponse<IResultadoDon[]>;

@Injectable({ providedIn: 'root' })
export class ResultadoDonService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resultado-dons');

  create(resultadoDon: NewResultadoDon): Observable<EntityResponseType> {
    return this.http.post<IResultadoDon>(this.resourceUrl, resultadoDon, { observe: 'response' });
  }

  update(resultadoDon: IResultadoDon): Observable<EntityResponseType> {
    return this.http.put<IResultadoDon>(`${this.resourceUrl}/${this.getResultadoDonIdentifier(resultadoDon)}`, resultadoDon, {
      observe: 'response',
    });
  }

  partialUpdate(resultadoDon: PartialUpdateResultadoDon): Observable<EntityResponseType> {
    return this.http.patch<IResultadoDon>(`${this.resourceUrl}/${this.getResultadoDonIdentifier(resultadoDon)}`, resultadoDon, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultadoDon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultadoDon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResultadoDonIdentifier(resultadoDon: Pick<IResultadoDon, 'id'>): number {
    return resultadoDon.id;
  }

  compareResultadoDon(o1: Pick<IResultadoDon, 'id'> | null, o2: Pick<IResultadoDon, 'id'> | null): boolean {
    return o1 && o2 ? this.getResultadoDonIdentifier(o1) === this.getResultadoDonIdentifier(o2) : o1 === o2;
  }

  addResultadoDonToCollectionIfMissing<Type extends Pick<IResultadoDon, 'id'>>(
    resultadoDonCollection: Type[],
    ...resultadoDonsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resultadoDons: Type[] = resultadoDonsToCheck.filter(isPresent);
    if (resultadoDons.length > 0) {
      const resultadoDonCollectionIdentifiers = resultadoDonCollection.map(resultadoDonItem =>
        this.getResultadoDonIdentifier(resultadoDonItem),
      );
      const resultadoDonsToAdd = resultadoDons.filter(resultadoDonItem => {
        const resultadoDonIdentifier = this.getResultadoDonIdentifier(resultadoDonItem);
        if (resultadoDonCollectionIdentifiers.includes(resultadoDonIdentifier)) {
          return false;
        }
        resultadoDonCollectionIdentifiers.push(resultadoDonIdentifier);
        return true;
      });
      return [...resultadoDonsToAdd, ...resultadoDonCollection];
    }
    return resultadoDonCollection;
  }
}
