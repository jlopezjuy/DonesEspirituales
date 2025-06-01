import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRespuestaUsuario, NewRespuestaUsuario } from '../respuesta-usuario.model';

export type PartialUpdateRespuestaUsuario = Partial<IRespuestaUsuario> & Pick<IRespuestaUsuario, 'id'>;

type RestOf<T extends IRespuestaUsuario | NewRespuestaUsuario> = Omit<T, 'fechaInicio' | 'fechaCompletado'> & {
  fechaInicio?: string | null;
  fechaCompletado?: string | null;
};

export type RestRespuestaUsuario = RestOf<IRespuestaUsuario>;

export type NewRestRespuestaUsuario = RestOf<NewRespuestaUsuario>;

export type PartialUpdateRestRespuestaUsuario = RestOf<PartialUpdateRespuestaUsuario>;

export type EntityResponseType = HttpResponse<IRespuestaUsuario>;
export type EntityArrayResponseType = HttpResponse<IRespuestaUsuario[]>;

@Injectable({ providedIn: 'root' })
export class RespuestaUsuarioService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/respuesta-usuarios');

  create(respuestaUsuario: NewRespuestaUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(respuestaUsuario);
    return this.http
      .post<RestRespuestaUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(respuestaUsuario: IRespuestaUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(respuestaUsuario);
    return this.http
      .put<RestRespuestaUsuario>(`${this.resourceUrl}/${this.getRespuestaUsuarioIdentifier(respuestaUsuario)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(respuestaUsuario: PartialUpdateRespuestaUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(respuestaUsuario);
    return this.http
      .patch<RestRespuestaUsuario>(`${this.resourceUrl}/${this.getRespuestaUsuarioIdentifier(respuestaUsuario)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRespuestaUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRespuestaUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRespuestaUsuarioIdentifier(respuestaUsuario: Pick<IRespuestaUsuario, 'id'>): number {
    return respuestaUsuario.id;
  }

  compareRespuestaUsuario(o1: Pick<IRespuestaUsuario, 'id'> | null, o2: Pick<IRespuestaUsuario, 'id'> | null): boolean {
    return o1 && o2 ? this.getRespuestaUsuarioIdentifier(o1) === this.getRespuestaUsuarioIdentifier(o2) : o1 === o2;
  }

  addRespuestaUsuarioToCollectionIfMissing<Type extends Pick<IRespuestaUsuario, 'id'>>(
    respuestaUsuarioCollection: Type[],
    ...respuestaUsuariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const respuestaUsuarios: Type[] = respuestaUsuariosToCheck.filter(isPresent);
    if (respuestaUsuarios.length > 0) {
      const respuestaUsuarioCollectionIdentifiers = respuestaUsuarioCollection.map(respuestaUsuarioItem =>
        this.getRespuestaUsuarioIdentifier(respuestaUsuarioItem),
      );
      const respuestaUsuariosToAdd = respuestaUsuarios.filter(respuestaUsuarioItem => {
        const respuestaUsuarioIdentifier = this.getRespuestaUsuarioIdentifier(respuestaUsuarioItem);
        if (respuestaUsuarioCollectionIdentifiers.includes(respuestaUsuarioIdentifier)) {
          return false;
        }
        respuestaUsuarioCollectionIdentifiers.push(respuestaUsuarioIdentifier);
        return true;
      });
      return [...respuestaUsuariosToAdd, ...respuestaUsuarioCollection];
    }
    return respuestaUsuarioCollection;
  }

  protected convertDateFromClient<T extends IRespuestaUsuario | NewRespuestaUsuario | PartialUpdateRespuestaUsuario>(
    respuestaUsuario: T,
  ): RestOf<T> {
    return {
      ...respuestaUsuario,
      fechaInicio: respuestaUsuario.fechaInicio?.toJSON() ?? null,
      fechaCompletado: respuestaUsuario.fechaCompletado?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRespuestaUsuario: RestRespuestaUsuario): IRespuestaUsuario {
    return {
      ...restRespuestaUsuario,
      fechaInicio: restRespuestaUsuario.fechaInicio ? dayjs(restRespuestaUsuario.fechaInicio) : undefined,
      fechaCompletado: restRespuestaUsuario.fechaCompletado ? dayjs(restRespuestaUsuario.fechaCompletado) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRespuestaUsuario>): HttpResponse<IRespuestaUsuario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRespuestaUsuario[]>): HttpResponse<IRespuestaUsuario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
