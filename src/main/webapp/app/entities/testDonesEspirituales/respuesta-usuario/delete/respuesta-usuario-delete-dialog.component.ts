import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRespuestaUsuario } from '../respuesta-usuario.model';
import { RespuestaUsuarioService } from '../service/respuesta-usuario.service';

@Component({
  templateUrl: './respuesta-usuario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RespuestaUsuarioDeleteDialogComponent {
  respuestaUsuario?: IRespuestaUsuario;

  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.respuestaUsuarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
