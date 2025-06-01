import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IDetalleRespuesta } from '../detalle-respuesta.model';

@Component({
  selector: 'jhi-detalle-respuesta-detail',
  templateUrl: './detalle-respuesta-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class DetalleRespuestaDetailComponent {
  detalleRespuesta = input<IDetalleRespuesta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
