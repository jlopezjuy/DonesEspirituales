import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDonEspiritual } from '../don-espiritual.model';

@Component({
  selector: 'jhi-don-espiritual-detail',
  templateUrl: './don-espiritual-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class DonEspiritualDetailComponent {
  donEspiritual = input<IDonEspiritual | null>(null);

  previousState(): void {
    window.history.back();
  }
}
