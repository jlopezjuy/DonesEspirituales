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
import { CuestionarioService } from '../service/cuestionario.service';
import { ICuestionario } from '../cuestionario.model';
import { CuestionarioFormGroup, CuestionarioFormService } from './cuestionario-form.service';

@Component({
  selector: 'jhi-cuestionario-update',
  templateUrl: './cuestionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CuestionarioUpdateComponent implements OnInit {
  isSaving = false;
  cuestionario: ICuestionario | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected cuestionarioService = inject(CuestionarioService);
  protected cuestionarioFormService = inject(CuestionarioFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CuestionarioFormGroup = this.cuestionarioFormService.createCuestionarioFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuestionario }) => {
      this.cuestionario = cuestionario;
      if (cuestionario) {
        this.updateForm(cuestionario);
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
    const cuestionario = this.cuestionarioFormService.getCuestionario(this.editForm);
    if (cuestionario.id !== null) {
      this.subscribeToSaveResponse(this.cuestionarioService.update(cuestionario));
    } else {
      this.subscribeToSaveResponse(this.cuestionarioService.create(cuestionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuestionario>>): void {
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

  protected updateForm(cuestionario: ICuestionario): void {
    this.cuestionario = cuestionario;
    this.cuestionarioFormService.resetForm(this.editForm, cuestionario);
  }
}
