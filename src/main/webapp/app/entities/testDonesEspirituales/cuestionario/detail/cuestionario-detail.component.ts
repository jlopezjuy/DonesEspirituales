import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { ICuestionario } from '../cuestionario.model';

@Component({
  selector: 'jhi-cuestionario-detail',
  templateUrl: './cuestionario-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class CuestionarioDetailComponent {
  cuestionario = input<ICuestionario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
