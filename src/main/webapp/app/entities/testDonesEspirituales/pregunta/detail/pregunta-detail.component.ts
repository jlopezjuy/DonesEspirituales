import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IPregunta } from '../pregunta.model';

@Component({
  selector: 'jhi-pregunta-detail',
  templateUrl: './pregunta-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class PreguntaDetailComponent {
  pregunta = input<IPregunta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
