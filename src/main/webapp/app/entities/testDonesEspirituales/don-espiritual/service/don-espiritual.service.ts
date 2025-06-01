import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDonEspiritual, NewDonEspiritual } from '../don-espiritual.model';

export type PartialUpdateDonEspiritual = Partial<IDonEspiritual> & Pick<IDonEspiritual, 'id'>;

export type EntityResponseType = HttpResponse<IDonEspiritual>;
export type EntityArrayResponseType = HttpResponse<IDonEspiritual[]>;

@Injectable({ providedIn: 'root' })
export class DonEspiritualService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/don-espirituals');

  create(donEspiritual: NewDonEspiritual): Observable<EntityResponseType> {
    return this.http.post<IDonEspiritual>(this.resourceUrl, donEspiritual, { observe: 'response' });
  }

  update(donEspiritual: IDonEspiritual): Observable<EntityResponseType> {
    return this.http.put<IDonEspiritual>(`${this.resourceUrl}/${this.getDonEspiritualIdentifier(donEspiritual)}`, donEspiritual, {
      observe: 'response',
    });
  }

  partialUpdate(donEspiritual: PartialUpdateDonEspiritual): Observable<EntityResponseType> {
    return this.http.patch<IDonEspiritual>(`${this.resourceUrl}/${this.getDonEspiritualIdentifier(donEspiritual)}`, donEspiritual, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDonEspiritual>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDonEspiritual[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDonEspiritualIdentifier(donEspiritual: Pick<IDonEspiritual, 'id'>): number {
    return donEspiritual.id;
  }

  compareDonEspiritual(o1: Pick<IDonEspiritual, 'id'> | null, o2: Pick<IDonEspiritual, 'id'> | null): boolean {
    return o1 && o2 ? this.getDonEspiritualIdentifier(o1) === this.getDonEspiritualIdentifier(o2) : o1 === o2;
  }

  addDonEspiritualToCollectionIfMissing<Type extends Pick<IDonEspiritual, 'id'>>(
    donEspiritualCollection: Type[],
    ...donEspiritualsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const donEspirituals: Type[] = donEspiritualsToCheck.filter(isPresent);
    if (donEspirituals.length > 0) {
      const donEspiritualCollectionIdentifiers = donEspiritualCollection.map(donEspiritualItem =>
        this.getDonEspiritualIdentifier(donEspiritualItem),
      );
      const donEspiritualsToAdd = donEspirituals.filter(donEspiritualItem => {
        const donEspiritualIdentifier = this.getDonEspiritualIdentifier(donEspiritualItem);
        if (donEspiritualCollectionIdentifiers.includes(donEspiritualIdentifier)) {
          return false;
        }
        donEspiritualCollectionIdentifiers.push(donEspiritualIdentifier);
        return true;
      });
      return [...donEspiritualsToAdd, ...donEspiritualCollection];
    }
    return donEspiritualCollection;
  }
}
