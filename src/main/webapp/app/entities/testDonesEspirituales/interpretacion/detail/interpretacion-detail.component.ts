import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IInterpretacion } from '../interpretacion.model';

@Component({
  selector: 'jhi-interpretacion-detail',
  templateUrl: './interpretacion-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class InterpretacionDetailComponent {
  interpretacion = input<IInterpretacion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
