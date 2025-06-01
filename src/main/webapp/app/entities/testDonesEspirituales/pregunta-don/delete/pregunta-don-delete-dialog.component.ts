import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPreguntaDon } from '../pregunta-don.model';
import { PreguntaDonService } from '../service/pregunta-don.service';

@Component({
  templateUrl: './pregunta-don-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PreguntaDonDeleteDialogComponent {
  preguntaDon?: IPreguntaDon;

  protected preguntaDonService = inject(PreguntaDonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.preguntaDonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
