import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISesionUsuario, NewSesionUsuario } from '../sesion-usuario.model';

export type PartialUpdateSesionUsuario = Partial<ISesionUsuario> & Pick<ISesionUsuario, 'id'>;

type RestOf<T extends ISesionUsuario | NewSesionUsuario> = Omit<T, 'fechaCreacion' | 'fechaExpiracion'> & {
  fechaCreacion?: string | null;
  fechaExpiracion?: string | null;
};

export type RestSesionUsuario = RestOf<ISesionUsuario>;

export type NewRestSesionUsuario = RestOf<NewSesionUsuario>;

export type PartialUpdateRestSesionUsuario = RestOf<PartialUpdateSesionUsuario>;

export type EntityResponseType = HttpResponse<ISesionUsuario>;
export type EntityArrayResponseType = HttpResponse<ISesionUsuario[]>;

@Injectable({ providedIn: 'root' })
export class SesionUsuarioService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sesion-usuarios');

  create(sesionUsuario: NewSesionUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesionUsuario);
    return this.http
      .post<RestSesionUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sesionUsuario: ISesionUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesionUsuario);
    return this.http
      .put<RestSesionUsuario>(`${this.resourceUrl}/${this.getSesionUsuarioIdentifier(sesionUsuario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sesionUsuario: PartialUpdateSesionUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sesionUsuario);
    return this.http
      .patch<RestSesionUsuario>(`${this.resourceUrl}/${this.getSesionUsuarioIdentifier(sesionUsuario)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSesionUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSesionUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSesionUsuarioIdentifier(sesionUsuario: Pick<ISesionUsuario, 'id'>): number {
    return sesionUsuario.id;
  }

  compareSesionUsuario(o1: Pick<ISesionUsuario, 'id'> | null, o2: Pick<ISesionUsuario, 'id'> | null): boolean {
    return o1 && o2 ? this.getSesionUsuarioIdentifier(o1) === this.getSesionUsuarioIdentifier(o2) : o1 === o2;
  }

  addSesionUsuarioToCollectionIfMissing<Type extends Pick<ISesionUsuario, 'id'>>(
    sesionUsuarioCollection: Type[],
    ...sesionUsuariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sesionUsuarios: Type[] = sesionUsuariosToCheck.filter(isPresent);
    if (sesionUsuarios.length > 0) {
      const sesionUsuarioCollectionIdentifiers = sesionUsuarioCollection.map(sesionUsuarioItem =>
        this.getSesionUsuarioIdentifier(sesionUsuarioItem),
      );
      const sesionUsuariosToAdd = sesionUsuarios.filter(sesionUsuarioItem => {
        const sesionUsuarioIdentifier = this.getSesionUsuarioIdentifier(sesionUsuarioItem);
        if (sesionUsuarioCollectionIdentifiers.includes(sesionUsuarioIdentifier)) {
          return false;
        }
        sesionUsuarioCollectionIdentifiers.push(sesionUsuarioIdentifier);
        return true;
      });
      return [...sesionUsuariosToAdd, ...sesionUsuarioCollection];
    }
    return sesionUsuarioCollection;
  }

  protected convertDateFromClient<T extends ISesionUsuario | NewSesionUsuario | PartialUpdateSesionUsuario>(sesionUsuario: T): RestOf<T> {
    return {
      ...sesionUsuario,
      fechaCreacion: sesionUsuario.fechaCreacion?.toJSON() ?? null,
      fechaExpiracion: sesionUsuario.fechaExpiracion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSesionUsuario: RestSesionUsuario): ISesionUsuario {
    return {
      ...restSesionUsuario,
      fechaCreacion: restSesionUsuario.fechaCreacion ? dayjs(restSesionUsuario.fechaCreacion) : undefined,
      fechaExpiracion: restSesionUsuario.fechaExpiracion ? dayjs(restSesionUsuario.fechaExpiracion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSesionUsuario>): HttpResponse<ISesionUsuario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSesionUsuario[]>): HttpResponse<ISesionUsuario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
