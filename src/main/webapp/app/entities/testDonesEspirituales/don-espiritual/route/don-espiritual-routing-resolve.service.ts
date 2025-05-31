import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDonEspiritual } from '../don-espiritual.model';
import { DonEspiritualService } from '../service/don-espiritual.service';

const donEspiritualResolve = (route: ActivatedRouteSnapshot): Observable<null | IDonEspiritual> => {
  const id = route.params.id;
  if (id) {
    return inject(DonEspiritualService)
      .find(id)
      .pipe(
        mergeMap((donEspiritual: HttpResponse<IDonEspiritual>) => {
          if (donEspiritual.body) {
            return of(donEspiritual.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default donEspiritualResolve;
