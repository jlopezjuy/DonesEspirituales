import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IResultadoDon } from '../resultado-don.model';

@Component({
  selector: 'jhi-resultado-don-detail',
  templateUrl: './resultado-don-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ResultadoDonDetailComponent {
  resultadoDon = input<IResultadoDon | null>(null);

  previousState(): void {
    window.history.back();
  }
}
