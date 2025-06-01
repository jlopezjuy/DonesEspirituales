import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IConfiguracionSistema } from '../configuracion-sistema.model';
import { ConfiguracionSistemaService } from '../service/configuracion-sistema.service';

@Component({
  templateUrl: './configuracion-sistema-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ConfiguracionSistemaDeleteDialogComponent {
  configuracionSistema?: IConfiguracionSistema;

  protected configuracionSistemaService = inject(ConfiguracionSistemaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.configuracionSistemaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
