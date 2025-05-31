import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ConfiguracionSistemaResolve from './route/configuracion-sistema-routing-resolve.service';

const configuracionSistemaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/configuracion-sistema.component').then(m => m.ConfiguracionSistemaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/configuracion-sistema-detail.component').then(m => m.ConfiguracionSistemaDetailComponent),
    resolve: {
      configuracionSistema: ConfiguracionSistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/configuracion-sistema-update.component').then(m => m.ConfiguracionSistemaUpdateComponent),
    resolve: {
      configuracionSistema: ConfiguracionSistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/configuracion-sistema-update.component').then(m => m.ConfiguracionSistemaUpdateComponent),
    resolve: {
      configuracionSistema: ConfiguracionSistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default configuracionSistemaRoute;
