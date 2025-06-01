import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEscalaRespuesta } from '../escala-respuesta.model';

@Component({
  selector: 'jhi-escala-respuesta-detail',
  templateUrl: './escala-respuesta-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EscalaRespuestaDetailComponent {
  escalaRespuesta = input<IEscalaRespuesta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
