import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { AuditoriaRespuestaService } from '../service/auditoria-respuesta.service';

@Component({
  templateUrl: './auditoria-respuesta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AuditoriaRespuestaDeleteDialogComponent {
  auditoriaRespuesta?: IAuditoriaRespuesta;

  protected auditoriaRespuestaService = inject(AuditoriaRespuestaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditoriaRespuestaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
