import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IConfiguracionSistema } from '../configuracion-sistema.model';

@Component({
  selector: 'jhi-configuracion-sistema-detail',
  templateUrl: './configuracion-sistema-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class ConfiguracionSistemaDetailComponent {
  configuracionSistema = input<IConfiguracionSistema | null>(null);

  previousState(): void {
    window.history.back();
  }
}
