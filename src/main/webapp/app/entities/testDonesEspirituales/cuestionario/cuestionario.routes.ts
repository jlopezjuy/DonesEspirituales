import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CuestionarioResolve from './route/cuestionario-routing-resolve.service';

const cuestionarioRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/cuestionario.component').then(m => m.CuestionarioComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/cuestionario-detail.component').then(m => m.CuestionarioDetailComponent),
    resolve: {
      cuestionario: CuestionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/cuestionario-update.component').then(m => m.CuestionarioUpdateComponent),
    resolve: {
      cuestionario: CuestionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/cuestionario-update.component').then(m => m.CuestionarioUpdateComponent),
    resolve: {
      cuestionario: CuestionarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cuestionarioRoute;
