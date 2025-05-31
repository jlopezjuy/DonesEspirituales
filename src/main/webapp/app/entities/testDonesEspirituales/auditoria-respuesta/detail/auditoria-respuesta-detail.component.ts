import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';

@Component({
  selector: 'jhi-auditoria-respuesta-detail',
  templateUrl: './auditoria-respuesta-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class AuditoriaRespuestaDetailComponent {
  auditoriaRespuesta = input<IAuditoriaRespuesta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
