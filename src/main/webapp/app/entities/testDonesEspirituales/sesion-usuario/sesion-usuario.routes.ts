import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SesionUsuarioResolve from './route/sesion-usuario-routing-resolve.service';

const sesionUsuarioRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sesion-usuario.component').then(m => m.SesionUsuarioComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sesion-usuario-detail.component').then(m => m.SesionUsuarioDetailComponent),
    resolve: {
      sesionUsuario: SesionUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sesion-usuario-update.component').then(m => m.SesionUsuarioUpdateComponent),
    resolve: {
      sesionUsuario: SesionUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sesion-usuario-update.component').then(m => m.SesionUsuarioUpdateComponent),
    resolve: {
      sesionUsuario: SesionUsuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sesionUsuarioRoute;
