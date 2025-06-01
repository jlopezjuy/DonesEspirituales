import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { AuditoriaRespuestaService } from '../service/auditoria-respuesta.service';

const auditoriaRespuestaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAuditoriaRespuesta> => {
  const id = route.params.id;
  if (id) {
    return inject(AuditoriaRespuestaService)
      .find(id)
      .pipe(
        mergeMap((auditoriaRespuesta: HttpResponse<IAuditoriaRespuesta>) => {
          if (auditoriaRespuesta.body) {
            return of(auditoriaRespuesta.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default auditoriaRespuestaResolve;
