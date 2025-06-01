import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DetalleRespuestaResolve from './route/detalle-respuesta-routing-resolve.service';

const detalleRespuestaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/detalle-respuesta.component').then(m => m.DetalleRespuestaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/detalle-respuesta-detail.component').then(m => m.DetalleRespuestaDetailComponent),
    resolve: {
      detalleRespuesta: DetalleRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/detalle-respuesta-update.component').then(m => m.DetalleRespuestaUpdateComponent),
    resolve: {
      detalleRespuesta: DetalleRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/detalle-respuesta-update.component').then(m => m.DetalleRespuestaUpdateComponent),
    resolve: {
      detalleRespuesta: DetalleRespuestaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default detalleRespuestaRoute;
