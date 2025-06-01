import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEscalaRespuesta } from '../escala-respuesta.model';
import { EscalaRespuestaService } from '../service/escala-respuesta.service';

const escalaRespuestaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEscalaRespuesta> => {
  const id = route.params.id;
  if (id) {
    return inject(EscalaRespuestaService)
      .find(id)
      .pipe(
        mergeMap((escalaRespuesta: HttpResponse<IEscalaRespuesta>) => {
          if (escalaRespuesta.body) {
            return of(escalaRespuesta.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default escalaRespuestaResolve;
