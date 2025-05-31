import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISesionUsuario } from '../sesion-usuario.model';
import { SesionUsuarioService } from '../service/sesion-usuario.service';

@Component({
  templateUrl: './sesion-usuario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SesionUsuarioDeleteDialogComponent {
  sesionUsuario?: ISesionUsuario;

  protected sesionUsuarioService = inject(SesionUsuarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sesionUsuarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
