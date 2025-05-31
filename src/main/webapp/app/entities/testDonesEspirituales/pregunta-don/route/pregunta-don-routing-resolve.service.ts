import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPreguntaDon } from '../pregunta-don.model';
import { PreguntaDonService } from '../service/pregunta-don.service';

const preguntaDonResolve = (route: ActivatedRouteSnapshot): Observable<null | IPreguntaDon> => {
  const id = route.params.id;
  if (id) {
    return inject(PreguntaDonService)
      .find(id)
      .pipe(
        mergeMap((preguntaDon: HttpResponse<IPreguntaDon>) => {
          if (preguntaDon.body) {
            return of(preguntaDon.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default preguntaDonResolve;
