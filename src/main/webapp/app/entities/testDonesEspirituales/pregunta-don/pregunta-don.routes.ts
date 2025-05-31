import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PreguntaDonResolve from './route/pregunta-don-routing-resolve.service';

const preguntaDonRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pregunta-don.component').then(m => m.PreguntaDonComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pregunta-don-detail.component').then(m => m.PreguntaDonDetailComponent),
    resolve: {
      preguntaDon: PreguntaDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pregunta-don-update.component').then(m => m.PreguntaDonUpdateComponent),
    resolve: {
      preguntaDon: PreguntaDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pregunta-don-update.component').then(m => m.PreguntaDonUpdateComponent),
    resolve: {
      preguntaDon: PreguntaDonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default preguntaDonRoute;
