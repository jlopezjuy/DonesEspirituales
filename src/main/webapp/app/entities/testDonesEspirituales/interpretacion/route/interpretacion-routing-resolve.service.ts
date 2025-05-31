import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInterpretacion } from '../interpretacion.model';
import { InterpretacionService } from '../service/interpretacion.service';

const interpretacionResolve = (route: ActivatedRouteSnapshot): Observable<null | IInterpretacion> => {
  const id = route.params.id;
  if (id) {
    return inject(InterpretacionService)
      .find(id)
      .pipe(
        mergeMap((interpretacion: HttpResponse<IInterpretacion>) => {
          if (interpretacion.body) {
            return of(interpretacion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default interpretacionResolve;
