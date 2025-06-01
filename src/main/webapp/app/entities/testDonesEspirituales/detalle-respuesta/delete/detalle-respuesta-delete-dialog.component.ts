import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDetalleRespuesta } from '../detalle-respuesta.model';
import { DetalleRespuestaService } from '../service/detalle-respuesta.service';

@Component({
  templateUrl: './detalle-respuesta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DetalleRespuestaDeleteDialogComponent {
  detalleRespuesta?: IDetalleRespuesta;

  protected detalleRespuestaService = inject(DetalleRespuestaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalleRespuestaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
