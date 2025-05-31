import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { NivelInterpretacion } from 'app/entities/enumerations/nivel-interpretacion.model';
import { InterpretacionService } from '../service/interpretacion.service';
import { IInterpretacion } from '../interpretacion.model';
import { InterpretacionFormGroup, InterpretacionFormService } from './interpretacion-form.service';

@Component({
  selector: 'jhi-interpretacion-update',
  templateUrl: './interpretacion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InterpretacionUpdateComponent implements OnInit {
  isSaving = false;
  interpretacion: IInterpretacion | null = null;
  nivelInterpretacionValues = Object.keys(NivelInterpretacion);

  donEspiritualsSharedCollection: IDonEspiritual[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected interpretacionService = inject(InterpretacionService);
  protected interpretacionFormService = inject(InterpretacionFormService);
  protected donEspiritualService = inject(DonEspiritualService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: InterpretacionFormGroup = this.interpretacionFormService.createInterpretacionFormGroup();

  compareDonEspiritual = (o1: IDonEspiritual | null, o2: IDonEspiritual | null): boolean =>
    this.donEspiritualService.compareDonEspiritual(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interpretacion }) => {
      this.interpretacion = interpretacion;
      if (interpretacion) {
        this.updateForm(interpretacion);
      }

      this.loadRelationshipsOptions();
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
    const interpretacion = this.interpretacionFormService.getInterpretacion(this.editForm);
    if (interpretacion.id !== null) {
      this.subscribeToSaveResponse(this.interpretacionService.update(interpretacion));
    } else {
      this.subscribeToSaveResponse(this.interpretacionService.create(interpretacion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterpretacion>>): void {
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

  protected updateForm(interpretacion: IInterpretacion): void {
    this.interpretacion = interpretacion;
    this.interpretacionFormService.resetForm(this.editForm, interpretacion);

    this.donEspiritualsSharedCollection = this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(
      this.donEspiritualsSharedCollection,
      interpretacion.donEspiritual,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.donEspiritualService
      .query()
      .pipe(map((res: HttpResponse<IDonEspiritual[]>) => res.body ?? []))
      .pipe(
        map((donEspirituals: IDonEspiritual[]) =>
          this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(
            donEspirituals,
            this.interpretacion?.donEspiritual,
          ),
        ),
      )
      .subscribe((donEspirituals: IDonEspiritual[]) => (this.donEspiritualsSharedCollection = donEspirituals));
  }
}
