import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDonEspiritual } from '../don-espiritual.model';
import { DonEspiritualService } from '../service/don-espiritual.service';

@Component({
  templateUrl: './don-espiritual-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DonEspiritualDeleteDialogComponent {
  donEspiritual?: IDonEspiritual;

  protected donEspiritualService = inject(DonEspiritualService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.donEspiritualService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
