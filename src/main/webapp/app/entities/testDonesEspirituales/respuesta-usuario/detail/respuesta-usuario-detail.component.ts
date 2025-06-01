import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IRespuestaUsuario } from '../respuesta-usuario.model';

@Component({
  selector: 'jhi-respuesta-usuario-detail',
  templateUrl: './respuesta-usuario-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class RespuestaUsuarioDetailComponent {
  respuestaUsuario = input<IRespuestaUsuario | null>(null);

  previousState(): void {
    window.history.back();
  }
}
