import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DonEspiritualResolve from './route/don-espiritual-routing-resolve.service';

const donEspiritualRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/don-espiritual.component').then(m => m.DonEspiritualComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/don-espiritual-detail.component').then(m => m.DonEspiritualDetailComponent),
    resolve: {
      donEspiritual: DonEspiritualResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/don-espiritual-update.component').then(m => m.DonEspiritualUpdateComponent),
    resolve: {
      donEspiritual: DonEspiritualResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/don-espiritual-update.component').then(m => m.DonEspiritualUpdateComponent),
    resolve: {
      donEspiritual: DonEspiritualResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default donEspiritualRoute;
