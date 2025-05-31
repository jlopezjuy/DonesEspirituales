import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RespuestaUsuarioResolve from './route/respuesta-usuario-routing-resolve.service';

const respuestaUsuarioRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/respuesta-usuario.component').then(m => m.RespuestaUsuarioComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/respuesta-usuario-detail.component').then(m => m.RespuestaUsuarioDetailComponent),
    resolve: {
      respuestaUsuario: RespuestaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/respuesta-usuario-update.component').then(m => m.RespuestaUsuarioUpdateComponent),
    resolve: {
      respuestaUsuario: RespuestaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/respuesta-usuario-update.component').then(m => m.RespuestaUsuarioUpdateComponent),
    resolve: {
      respuestaUsuario: RespuestaUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default respuestaUsuarioRoute;
