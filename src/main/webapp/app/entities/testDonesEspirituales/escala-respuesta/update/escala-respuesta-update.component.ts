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
import { EscalaRespuestaService } from '../service/escala-respuesta.service';
import { IEscalaRespuesta } from '../escala-respuesta.model';
import { EscalaRespuestaFormGroup, EscalaRespuestaFormService } from './escala-respuesta-form.service';

@Component({
  selector: 'jhi-escala-respuesta-update',
  templateUrl: './escala-respuesta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EscalaRespuestaUpdateComponent implements OnInit {
  isSaving = false;
  escalaRespuesta: IEscalaRespuesta | null = null;

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected escalaRespuestaService = inject(EscalaRespuestaService);
  protected escalaRespuestaFormService = inject(EscalaRespuestaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EscalaRespuestaFormGroup = this.escalaRespuestaFormService.createEscalaRespuestaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ escalaRespuesta }) => {
      this.escalaRespuesta = escalaRespuesta;
      if (escalaRespuesta) {
        this.updateForm(escalaRespuesta);
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
    const escalaRespuesta = this.escalaRespuestaFormService.getEscalaRespuesta(this.editForm);
    if (escalaRespuesta.id !== null) {
      this.subscribeToSaveResponse(this.escalaRespuestaService.update(escalaRespuesta));
    } else {
      this.subscribeToSaveResponse(this.escalaRespuestaService.create(escalaRespuesta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEscalaRespuesta>>): void {
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

  protected updateForm(escalaRespuesta: IEscalaRespuesta): void {
    this.escalaRespuesta = escalaRespuesta;
    this.escalaRespuestaFormService.resetForm(this.editForm, escalaRespuesta);
  }
}
