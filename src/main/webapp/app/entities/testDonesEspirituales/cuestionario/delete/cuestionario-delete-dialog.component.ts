import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICuestionario } from '../cuestionario.model';
import { CuestionarioService } from '../service/cuestionario.service';

@Component({
  templateUrl: './cuestionario-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CuestionarioDeleteDialogComponent {
  cuestionario?: ICuestionario;

  protected cuestionarioService = inject(CuestionarioService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cuestionarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
