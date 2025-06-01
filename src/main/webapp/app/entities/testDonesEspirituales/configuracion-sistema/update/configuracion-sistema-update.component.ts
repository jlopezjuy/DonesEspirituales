import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoDato } from 'app/entities/enumerations/tipo-dato.model';
import { IConfiguracionSistema } from '../configuracion-sistema.model';
import { ConfiguracionSistemaService } from '../service/configuracion-sistema.service';
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
