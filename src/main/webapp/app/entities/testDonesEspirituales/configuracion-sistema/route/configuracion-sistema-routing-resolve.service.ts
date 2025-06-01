import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConfiguracionSistema } from '../configuracion-sistema.model';
import { ConfiguracionSistemaService } from '../service/configuracion-sistema.service';

const configuracionSistemaResolve = (route: ActivatedRouteSnapshot): Observable<null | IConfiguracionSistema> => {
  const id = route.params.id;
  if (id) {
    return inject(ConfiguracionSistemaService)
      .find(id)
      .pipe(
        mergeMap((configuracionSistema: HttpResponse<IConfiguracionSistema>) => {
          if (configuracionSistema.body) {
            return of(configuracionSistema.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default configuracionSistemaResolve;
