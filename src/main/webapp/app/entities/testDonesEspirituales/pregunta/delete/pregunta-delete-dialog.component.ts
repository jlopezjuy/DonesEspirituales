import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPregunta } from '../pregunta.model';
import { PreguntaService } from '../service/pregunta.service';

@Component({
  templateUrl: './pregunta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PreguntaDeleteDialogComponent {
  pregunta?: IPregunta;

  protected preguntaService = inject(PreguntaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.preguntaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
