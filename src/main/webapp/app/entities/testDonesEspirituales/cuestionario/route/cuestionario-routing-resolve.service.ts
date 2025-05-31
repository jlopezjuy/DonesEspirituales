import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICuestionario } from '../cuestionario.model';
import { CuestionarioService } from '../service/cuestionario.service';

const cuestionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | ICuestionario> => {
  const id = route.params.id;
  if (id) {
    return inject(CuestionarioService)
      .find(id)
      .pipe(
        mergeMap((cuestionario: HttpResponse<ICuestionario>) => {
          if (cuestionario.body) {
            return of(cuestionario.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cuestionarioResolve;
