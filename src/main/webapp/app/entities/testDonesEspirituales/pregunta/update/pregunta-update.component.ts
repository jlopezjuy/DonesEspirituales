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
import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';
import { CuestionarioService } from 'app/entities/testDonesEspirituales/cuestionario/service/cuestionario.service';
import { PreguntaService } from '../service/pregunta.service';
import { IPregunta } from '../pregunta.model';
import { PreguntaFormGroup, PreguntaFormService } from './pregunta-form.service';

@Component({
  selector: 'jhi-pregunta-update',
  templateUrl: './pregunta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PreguntaUpdateComponent implements OnInit {
  isSaving = false;
  pregunta: IPregunta | null = null;

  cuestionariosSharedCollection: ICuestionario[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected preguntaService = inject(PreguntaService);
  protected preguntaFormService = inject(PreguntaFormService);
  protected cuestionarioService = inject(CuestionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PreguntaFormGroup = this.preguntaFormService.createPreguntaFormGroup();

  compareCuestionario = (o1: ICuestionario | null, o2: ICuestionario | null): boolean =>
    this.cuestionarioService.compareCuestionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pregunta }) => {
      this.pregunta = pregunta;
      if (pregunta) {
        this.updateForm(pregunta);
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
    const pregunta = this.preguntaFormService.getPregunta(this.editForm);
    if (pregunta.id !== null) {
      this.subscribeToSaveResponse(this.preguntaService.update(pregunta));
    } else {
      this.subscribeToSaveResponse(this.preguntaService.create(pregunta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPregunta>>): void {
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

  protected updateForm(pregunta: IPregunta): void {
    this.pregunta = pregunta;
    this.preguntaFormService.resetForm(this.editForm, pregunta);

    this.cuestionariosSharedCollection = this.cuestionarioService.addCuestionarioToCollectionIfMissing<ICuestionario>(
      this.cuestionariosSharedCollection,
      pregunta.cuestionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cuestionarioService
      .query()
      .pipe(map((res: HttpResponse<ICuestionario[]>) => res.body ?? []))
      .pipe(
        map((cuestionarios: ICuestionario[]) =>
          this.cuestionarioService.addCuestionarioToCollectionIfMissing<ICuestionario>(cuestionarios, this.pregunta?.cuestionario),
        ),
      )
      .subscribe((cuestionarios: ICuestionario[]) => (this.cuestionariosSharedCollection = cuestionarios));
  }
}
