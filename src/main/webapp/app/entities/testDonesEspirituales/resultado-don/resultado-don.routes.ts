import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ResultadoDonResolve from './route/resultado-don-routing-resolve.service';

const resultadoDonRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/resultado-don.component').then(m => m.ResultadoDonComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/resultado-don-detail.component').then(m => m.ResultadoDonDetailComponent),
    resolve: {
      resultadoDon: ResultadoDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/resultado-don-update.component').then(m => m.ResultadoDonUpdateComponent),
    resolve: {
      resultadoDon: ResultadoDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/resultado-don-update.component').then(m => m.ResultadoDonUpdateComponent),
    resolve: {
      resultadoDon: ResultadoDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default resultadoDonRoute;
