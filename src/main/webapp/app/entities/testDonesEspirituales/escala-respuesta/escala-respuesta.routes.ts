import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EscalaRespuestaResolve from './route/escala-respuesta-routing-resolve.service';

const escalaRespuestaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/escala-respuesta.component').then(m => m.EscalaRespuestaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/escala-respuesta-detail.component').then(m => m.EscalaRespuestaDetailComponent),
    resolve: {
      escalaRespuesta: EscalaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/escala-respuesta-update.component').then(m => m.EscalaRespuestaUpdateComponent),
    resolve: {
      escalaRespuesta: EscalaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/escala-respuesta-update.component').then(m => m.EscalaRespuestaUpdateComponent),
    resolve: {
      escalaRespuesta: EscalaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default escalaRespuestaRoute;
