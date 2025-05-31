import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AuditoriaRespuestaResolve from './route/auditoria-respuesta-routing-resolve.service';

const auditoriaRespuestaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/auditoria-respuesta.component').then(m => m.AuditoriaRespuestaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/auditoria-respuesta-detail.component').then(m => m.AuditoriaRespuestaDetailComponent),
    resolve: {
      auditoriaRespuesta: AuditoriaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/auditoria-respuesta-update.component').then(m => m.AuditoriaRespuestaUpdateComponent),
    resolve: {
      auditoriaRespuesta: AuditoriaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/auditoria-respuesta-update.component').then(m => m.AuditoriaRespuestaUpdateComponent),
    resolve: {
      auditoriaRespuesta: AuditoriaRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default auditoriaRespuestaRoute;
