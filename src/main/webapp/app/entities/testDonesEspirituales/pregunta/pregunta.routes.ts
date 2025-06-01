import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PreguntaResolve from './route/pregunta-routing-resolve.service';

const preguntaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pregunta.component').then(m => m.PreguntaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pregunta-detail.component').then(m => m.PreguntaDetailComponent),
    resolve: {
      pregunta: PreguntaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pregunta-update.component').then(m => m.PreguntaUpdateComponent),
    resolve: {
      pregunta: PreguntaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pregunta-update.component').then(m => m.PreguntaUpdateComponent),
    resolve: {
      pregunta: PreguntaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default preguntaRoute;
