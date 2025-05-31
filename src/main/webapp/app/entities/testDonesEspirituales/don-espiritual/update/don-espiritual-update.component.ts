import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { DonEspiritualService } from '../service/don-espiritual.service';
import { IDonEspiritual } from '../don-espiritual.model';
import { DonEspiritualFormGroup, DonEspiritualFormService } from './don-espiritual-form.service';

@Component({
  selector: 'jhi-don-espiritual-update',
  templateUrl: './don-espiritual-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DonEspiritualUpdateComponent implements OnInit {
  isSaving = false;
  donEspiritual: IDonEspiritual | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected donEspiritualService = inject(DonEspiritualService);
  protected donEspiritualFormService = inject(DonEspiritualFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DonEspiritualFormGroup = this.donEspiritualFormService.createDonEspiritualFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donEspiritual }) => {
      this.donEspiritual = donEspiritual;
      if (donEspiritual) {
        this.updateForm(donEspiritual);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('donesEspiritualesApp.error', { ...err, key: `error.file.${err.key}` }),
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donEspiritual = this.donEspiritualFormService.getDonEspiritual(this.editForm);
    if (donEspiritual.id !== null) {
      this.subscribeToSaveResponse(this.donEspiritualService.update(donEspiritual));
    } else {
      this.subscribeToSaveResponse(this.donEspiritualService.create(donEspiritual));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonEspiritual>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(donEspiritual: IDonEspiritual): void {
    this.donEspiritual = donEspiritual;
    this.donEspiritualFormService.resetForm(this.editForm, donEspiritual);
  }
}
