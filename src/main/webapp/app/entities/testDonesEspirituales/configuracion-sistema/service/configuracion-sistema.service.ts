import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConfiguracionSistema, NewConfiguracionSistema } from '../configuracion-sistema.model';

export type PartialUpdateConfiguracionSistema = Partial<IConfiguracionSistema> & Pick<IConfiguracionSistema, 'id'>;

type RestOf<T extends IConfiguracionSistema | NewConfiguracionSistema> = Omit<T, 'fechaActualizacion'> & {
  fechaActualizacion?: string | null;
};

export type RestConfiguracionSistema = RestOf<IConfiguracionSistema>;

export type NewRestConfiguracionSistema = RestOf<NewConfiguracionSistema>;

export type PartialUpdateRestConfiguracionSistema = RestOf<PartialUpdateConfiguracionSistema>;

export type EntityResponseType = HttpResponse<IConfiguracionSistema>;
export type EntityArrayResponseType = HttpResponse<IConfiguracionSistema[]>;

@Injectable({ providedIn: 'root' })
export class ConfiguracionSistemaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/configuracion-sistemas');

  create(configuracionSistema: NewConfiguracionSistema): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configuracionSistema);
    return this.http
      .post<RestConfiguracionSistema>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(configuracionSistema: IConfiguracionSistema): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configuracionSistema);
    return this.http
      .put<RestConfiguracionSistema>(`${this.resourceUrl}/${this.getConfiguracionSistemaIdentifier(configuracionSistema)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(configuracionSistema: PartialUpdateConfiguracionSistema): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(configuracionSistema);
    return this.http
      .patch<RestConfiguracionSistema>(`${this.resourceUrl}/${this.getConfiguracionSistemaIdentifier(configuracionSistema)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestConfiguracionSistema>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestConfiguracionSistema[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getConfiguracionSistemaIdentifier(configuracionSistema: Pick<IConfiguracionSistema, 'id'>): number {
    return configuracionSistema.id;
  }

  compareConfiguracionSistema(o1: Pick<IConfiguracionSistema, 'id'> | null, o2: Pick<IConfiguracionSistema, 'id'> | null): boolean {
    return o1 && o2 ? this.getConfiguracionSistemaIdentifier(o1) === this.getConfiguracionSistemaIdentifier(o2) : o1 === o2;
  }

  addConfiguracionSistemaToCollectionIfMissing<Type extends Pick<IConfiguracionSistema, 'id'>>(
    configuracionSistemaCollection: Type[],
    ...configuracionSistemasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const configuracionSistemas: Type[] = configuracionSistemasToCheck.filter(isPresent);
    if (configuracionSistemas.length > 0) {
      const configuracionSistemaCollectionIdentifiers = configuracionSistemaCollection.map(configuracionSistemaItem =>
        this.getConfiguracionSistemaIdentifier(configuracionSistemaItem),
      );
      const configuracionSistemasToAdd = configuracionSistemas.filter(configuracionSistemaItem => {
        const configuracionSistemaIdentifier = this.getConfiguracionSistemaIdentifier(configuracionSistemaItem);
        if (configuracionSistemaCollectionIdentifiers.includes(configuracionSistemaIdentifier)) {
          return false;
        }
        configuracionSistemaCollectionIdentifiers.push(configuracionSistemaIdentifier);
        return true;
      });
      return [...configuracionSistemasToAdd, ...configuracionSistemaCollection];
    }
    return configuracionSistemaCollection;
  }

  protected convertDateFromClient<T extends IConfiguracionSistema | NewConfiguracionSistema | PartialUpdateConfiguracionSistema>(
    configuracionSistema: T,
  ): RestOf<T> {
    return {
      ...configuracionSistema,
      fechaActualizacion: configuracionSistema.fechaActualizacion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restConfiguracionSistema: RestConfiguracionSistema): IConfiguracionSistema {
    return {
      ...restConfiguracionSistema,
      fechaActualizacion: restConfiguracionSistema.fechaActualizacion ? dayjs(restConfiguracionSistema.fechaActualizacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestConfiguracionSistema>): HttpResponse<IConfiguracionSistema> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestConfiguracionSistema[]>): HttpResponse<IConfiguracionSistema[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
