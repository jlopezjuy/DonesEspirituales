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
import { TipoDato } from 'app/entities/enumerations/tipo-dato.model';
import { ConfiguracionSistemaService } from '../service/configuracion-sistema.service';
import { IConfiguracionSistema } from '../configuracion-sistema.model';
import { ConfiguracionSistemaFormGroup, ConfiguracionSistemaFormService } from './configuracion-sistema-form.service';

@Component({
  selector: 'jhi-configuracion-sistema-update',
  templateUrl: './configuracion-sistema-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ConfiguracionSistemaUpdateComponent implements OnInit {
  isSaving = false;
  configuracionSistema: IConfiguracionSistema | null = null;
  tipoDatoValues = Object.keys(TipoDato);

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected configuracionSistemaService = inject(ConfiguracionSistemaService);
  protected configuracionSistemaFormService = inject(ConfiguracionSistemaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ConfiguracionSistemaFormGroup = this.configuracionSistemaFormService.createConfiguracionSistemaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configuracionSistema }) => {
      this.configuracionSistema = configuracionSistema;
      if (configuracionSistema) {
        this.updateForm(configuracionSistema);
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
    const configuracionSistema = this.configuracionSistemaFormService.getConfiguracionSistema(this.editForm);
    if (configuracionSistema.id !== null) {
      this.subscribeToSaveResponse(this.configuracionSistemaService.update(configuracionSistema));
    } else {
      this.subscribeToSaveResponse(this.configuracionSistemaService.create(configuracionSistema));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfiguracionSistema>>): void {
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

  protected updateForm(configuracionSistema: IConfiguracionSistema): void {
    this.configuracionSistema = configuracionSistema;
    this.configuracionSistemaFormService.resetForm(this.editForm, configuracionSistema);
  }
}
