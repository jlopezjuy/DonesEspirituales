import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInterpretacion, NewInterpretacion } from '../interpretacion.model';

export type PartialUpdateInterpretacion = Partial<IInterpretacion> & Pick<IInterpretacion, 'id'>;

export type EntityResponseType = HttpResponse<IInterpretacion>;
export type EntityArrayResponseType = HttpResponse<IInterpretacion[]>;

@Injectable({ providedIn: 'root' })
export class InterpretacionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/interpretacions');

  create(interpretacion: NewInterpretacion): Observable<EntityResponseType> {
    return this.http.post<IInterpretacion>(this.resourceUrl, interpretacion, { observe: 'response' });
  }

  update(interpretacion: IInterpretacion): Observable<EntityResponseType> {
    return this.http.put<IInterpretacion>(`${this.resourceUrl}/${this.getInterpretacionIdentifier(interpretacion)}`, interpretacion, {
      observe: 'response',
    });
  }

  partialUpdate(interpretacion: PartialUpdateInterpretacion): Observable<EntityResponseType> {
    return this.http.patch<IInterpretacion>(`${this.resourceUrl}/${this.getInterpretacionIdentifier(interpretacion)}`, interpretacion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterpretacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterpretacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInterpretacionIdentifier(interpretacion: Pick<IInterpretacion, 'id'>): number {
    return interpretacion.id;
  }

  compareInterpretacion(o1: Pick<IInterpretacion, 'id'> | null, o2: Pick<IInterpretacion, 'id'> | null): boolean {
    return o1 && o2 ? this.getInterpretacionIdentifier(o1) === this.getInterpretacionIdentifier(o2) : o1 === o2;
  }

  addInterpretacionToCollectionIfMissing<Type extends Pick<IInterpretacion, 'id'>>(
    interpretacionCollection: Type[],
    ...interpretacionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const interpretacions: Type[] = interpretacionsToCheck.filter(isPresent);
    if (interpretacions.length > 0) {
      const interpretacionCollectionIdentifiers = interpretacionCollection.map(interpretacionItem =>
        this.getInterpretacionIdentifier(interpretacionItem),
      );
      const interpretacionsToAdd = interpretacions.filter(interpretacionItem => {
        const interpretacionIdentifier = this.getInterpretacionIdentifier(interpretacionItem);
        if (interpretacionCollectionIdentifiers.includes(interpretacionIdentifier)) {
          return false;
        }
        interpretacionCollectionIdentifiers.push(interpretacionIdentifier);
        return true;
      });
      return [...interpretacionsToAdd, ...interpretacionCollection];
    }
    return interpretacionCollection;
  }
}
