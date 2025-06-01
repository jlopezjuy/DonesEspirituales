import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEscalaRespuesta } from '../escala-respuesta.model';
import { EscalaRespuestaService } from '../service/escala-respuesta.service';

@Component({
  templateUrl: './escala-respuesta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EscalaRespuestaDeleteDialogComponent {
  escalaRespuesta?: IEscalaRespuesta;

  protected escalaRespuestaService = inject(EscalaRespuestaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.escalaRespuestaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
