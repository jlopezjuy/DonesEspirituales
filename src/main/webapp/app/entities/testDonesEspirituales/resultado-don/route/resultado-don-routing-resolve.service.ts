import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultadoDon } from '../resultado-don.model';
import { ResultadoDonService } from '../service/resultado-don.service';

const resultadoDonResolve = (route: ActivatedRouteSnapshot): Observable<null | IResultadoDon> => {
  const id = route.params.id;
  if (id) {
    return inject(ResultadoDonService)
      .find(id)
      .pipe(
        mergeMap((resultadoDon: HttpResponse<IResultadoDon>) => {
          if (resultadoDon.body) {
            return of(resultadoDon.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default resultadoDonResolve;
