import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetalleRespuesta } from '../detalle-respuesta.model';
import { DetalleRespuestaService } from '../service/detalle-respuesta.service';

const detalleRespuestaResolve = (route: ActivatedRouteSnapshot): Observable<null | IDetalleRespuesta> => {
  const id = route.params.id;
  if (id) {
    return inject(DetalleRespuestaService)
      .find(id)
      .pipe(
        mergeMap((detalleRespuesta: HttpResponse<IDetalleRespuesta>) => {
          if (detalleRespuesta.body) {
            return of(detalleRespuesta.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default detalleRespuestaResolve;
