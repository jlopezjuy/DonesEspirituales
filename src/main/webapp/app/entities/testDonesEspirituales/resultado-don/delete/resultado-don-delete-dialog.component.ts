import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IResultadoDon } from '../resultado-don.model';
import { ResultadoDonService } from '../service/resultado-don.service';

@Component({
  templateUrl: './resultado-don-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ResultadoDonDeleteDialogComponent {
  resultadoDon?: IResultadoDon;

  protected resultadoDonService = inject(ResultadoDonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultadoDonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
