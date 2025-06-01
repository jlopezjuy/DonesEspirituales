import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPregunta } from '../pregunta.model';
import { PreguntaService } from '../service/pregunta.service';

const preguntaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPregunta> => {
  const id = route.params.id;
  if (id) {
    return inject(PreguntaService)
      .find(id)
      .pipe(
        mergeMap((pregunta: HttpResponse<IPregunta>) => {
          if (pregunta.body) {
            return of(pregunta.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default preguntaResolve;
