import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import InterpretacionResolve from './route/interpretacion-routing-resolve.service';

const interpretacionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/interpretacion.component').then(m => m.InterpretacionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/interpretacion-detail.component').then(m => m.InterpretacionDetailComponent),
    resolve: {
      interpretacion: InterpretacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/interpretacion-update.component').then(m => m.InterpretacionUpdateComponent),
    resolve: {
      interpretacion: InterpretacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/interpretacion-update.component').then(m => m.InterpretacionUpdateComponent),
    resolve: {
      interpretacion: InterpretacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default interpretacionRoute;
