import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPreguntaDon } from '../pregunta-don.model';

@Component({
  selector: 'jhi-pregunta-don-detail',
  templateUrl: './pregunta-don-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PreguntaDonDetailComponent {
  preguntaDon = input<IPreguntaDon | null>(null);

  previousState(): void {
    window.history.back();
  }
}
