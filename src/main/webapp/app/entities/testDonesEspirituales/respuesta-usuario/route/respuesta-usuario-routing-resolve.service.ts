import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRespuestaUsuario } from '../respuesta-usuario.model';
import { RespuestaUsuarioService } from '../service/respuesta-usuario.service';

const respuestaUsuarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IRespuestaUsuario> => {
  const id = route.params.id;
  if (id) {
    return inject(RespuestaUsuarioService)
      .find(id)
      .pipe(
        mergeMap((respuestaUsuario: HttpResponse<IRespuestaUsuario>) => {
          if (respuestaUsuario.body) {
            return of(respuestaUsuario.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default respuestaUsuarioResolve;
