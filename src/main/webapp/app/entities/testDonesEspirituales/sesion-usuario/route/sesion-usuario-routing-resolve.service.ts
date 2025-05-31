import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISesionUsuario } from '../sesion-usuario.model';
import { SesionUsuarioService } from '../service/sesion-usuario.service';

const sesionUsuarioResolve = (route: ActivatedRouteSnapshot): Observable<null | ISesionUsuario> => {
  const id = route.params.id;
  if (id) {
    return inject(SesionUsuarioService)
      .find(id)
      .pipe(
        mergeMap((sesionUsuario: HttpResponse<ISesionUsuario>) => {
          if (sesionUsuario.body) {
            return of(sesionUsuario.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sesionUsuarioResolve;
