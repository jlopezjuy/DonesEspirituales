import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInterpretacion } from '../interpretacion.model';
import { InterpretacionService } from '../service/interpretacion.service';

@Component({
  templateUrl: './interpretacion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InterpretacionDeleteDialogComponent {
  interpretacion?: IInterpretacion;

  protected interpretacionService = inject(InterpretacionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interpretacionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
