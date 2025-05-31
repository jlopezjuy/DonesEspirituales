import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { IDetalleRespuesta } from 'app/entities/testDonesEspirituales/detalle-respuesta/detalle-respuesta.model';
import { DetalleRespuestaService } from 'app/entities/testDonesEspirituales/detalle-respuesta/service/detalle-respuesta.service';
import { AuditoriaRespuestaService } from '../service/auditoria-respuesta.service';
import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { AuditoriaRespuestaFormGroup, AuditoriaRespuestaFormService } from './auditoria-respuesta-form.service';

@Component({
  selector: 'jhi-auditoria-respuesta-update',
  templateUrl: './auditoria-respuesta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AuditoriaRespuestaUpdateComponent implements OnInit {
  isSaving = false;
  auditoriaRespuesta: IAuditoriaRespuesta | null = null;

  respuestaUsuariosSharedCollection: IRespuestaUsuario[] = [];
  detalleRespuestasSharedCollection: IDetalleRespuesta[] = [];

  protected auditoriaRespuestaService = inject(AuditoriaRespuestaService);
  protected auditoriaRespuestaFormService = inject(AuditoriaRespuestaFormService);
  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected detalleRespuestaService = inject(DetalleRespuestaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AuditoriaRespuestaFormGroup = this.auditoriaRespuestaFormService.createAuditoriaRespuestaFormGroup();

  compareRespuestaUsuario = (o1: IRespuestaUsuario | null, o2: IRespuestaUsuario | null): boolean =>
    this.respuestaUsuarioService.compareRespuestaUsuario(o1, o2);

  compareDetalleRespuesta = (o1: IDetalleRespuesta | null, o2: IDetalleRespuesta | null): boolean =>
    this.detalleRespuestaService.compareDetalleRespuesta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditoriaRespuesta }) => {
      this.auditoriaRespuesta = auditoriaRespuesta;
      if (auditoriaRespuesta) {
        this.updateForm(auditoriaRespuesta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditoriaRespuesta = this.auditoriaRespuestaFormService.getAuditoriaRespuesta(this.editForm);
    if (auditoriaRespuesta.id !== null) {
      this.subscribeToSaveResponse(this.auditoriaRespuestaService.update(auditoriaRespuesta));
    } else {
      this.subscribeToSaveResponse(this.auditoriaRespuestaService.create(auditoriaRespuesta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditoriaRespuesta>>): void {
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

  protected updateForm(auditoriaRespuesta: IAuditoriaRespuesta): void {
    this.auditoriaRespuesta = auditoriaRespuesta;
    this.auditoriaRespuestaFormService.resetForm(this.editForm, auditoriaRespuesta);

    this.respuestaUsuariosSharedCollection = this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
      this.respuestaUsuariosSharedCollection,
      auditoriaRespuesta.respuestaUsuario,
    );
    this.detalleRespuestasSharedCollection = this.detalleRespuestaService.addDetalleRespuestaToCollectionIfMissing<IDetalleRespuesta>(
      this.detalleRespuestasSharedCollection,
      auditoriaRespuesta.detalleRespuesta,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.respuestaUsuarioService
      .query()
      .pipe(map((res: HttpResponse<IRespuestaUsuario[]>) => res.body ?? []))
      .pipe(
        map((respuestaUsuarios: IRespuestaUsuario[]) =>
          this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
            respuestaUsuarios,
            this.auditoriaRespuesta?.respuestaUsuario,
          ),
        ),
      )
      .subscribe((respuestaUsuarios: IRespuestaUsuario[]) => (this.respuestaUsuariosSharedCollection = respuestaUsuarios));

    this.detalleRespuestaService
      .query()
      .pipe(map((res: HttpResponse<IDetalleRespuesta[]>) => res.body ?? []))
      .pipe(
        map((detalleRespuestas: IDetalleRespuesta[]) =>
          this.detalleRespuestaService.addDetalleRespuestaToCollectionIfMissing<IDetalleRespuesta>(
            detalleRespuestas,
            this.auditoriaRespuesta?.detalleRespuesta,
          ),
        ),
      )
      .subscribe((detalleRespuestas: IDetalleRespuesta[]) => (this.detalleRespuestasSharedCollection = detalleRespuestas));
  }
}
