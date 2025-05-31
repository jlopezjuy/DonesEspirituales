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
import { IUsuario } from 'app/entities/testDonesEspirituales/usuario/usuario.model';
import { UsuarioService } from 'app/entities/testDonesEspirituales/usuario/service/usuario.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { SesionUsuarioService } from '../service/sesion-usuario.service';
import { ISesionUsuario } from '../sesion-usuario.model';
import { SesionUsuarioFormGroup, SesionUsuarioFormService } from './sesion-usuario-form.service';

@Component({
  selector: 'jhi-sesion-usuario-update',
  templateUrl: './sesion-usuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SesionUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  sesionUsuario: ISesionUsuario | null = null;

  usuariosSharedCollection: IUsuario[] = [];
  respuestaUsuariosSharedCollection: IRespuestaUsuario[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected sesionUsuarioService = inject(SesionUsuarioService);
  protected sesionUsuarioFormService = inject(SesionUsuarioFormService);
  protected usuarioService = inject(UsuarioService);
  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SesionUsuarioFormGroup = this.sesionUsuarioFormService.createSesionUsuarioFormGroup();

  compareUsuario = (o1: IUsuario | null, o2: IUsuario | null): boolean => this.usuarioService.compareUsuario(o1, o2);

  compareRespuestaUsuario = (o1: IRespuestaUsuario | null, o2: IRespuestaUsuario | null): boolean =>
    this.respuestaUsuarioService.compareRespuestaUsuario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sesionUsuario }) => {
      this.sesionUsuario = sesionUsuario;
      if (sesionUsuario) {
        this.updateForm(sesionUsuario);
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
    const sesionUsuario = this.sesionUsuarioFormService.getSesionUsuario(this.editForm);
    if (sesionUsuario.id !== null) {
      this.subscribeToSaveResponse(this.sesionUsuarioService.update(sesionUsuario));
    } else {
      this.subscribeToSaveResponse(this.sesionUsuarioService.create(sesionUsuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISesionUsuario>>): void {
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

  protected updateForm(sesionUsuario: ISesionUsuario): void {
    this.sesionUsuario = sesionUsuario;
    this.sesionUsuarioFormService.resetForm(this.editForm, sesionUsuario);

    this.usuariosSharedCollection = this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(
      this.usuariosSharedCollection,
      sesionUsuario.usuario,
    );
    this.respuestaUsuariosSharedCollection = this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
      this.respuestaUsuariosSharedCollection,
      sesionUsuario.respuestaUsuario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.usuarioService
      .query()
      .pipe(map((res: HttpResponse<IUsuario[]>) => res.body ?? []))
      .pipe(
        map((usuarios: IUsuario[]) => this.usuarioService.addUsuarioToCollectionIfMissing<IUsuario>(usuarios, this.sesionUsuario?.usuario)),
      )
      .subscribe((usuarios: IUsuario[]) => (this.usuariosSharedCollection = usuarios));

    this.respuestaUsuarioService
      .query()
      .pipe(map((res: HttpResponse<IRespuestaUsuario[]>) => res.body ?? []))
      .pipe(
        map((respuestaUsuarios: IRespuestaUsuario[]) =>
          this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
            respuestaUsuarios,
            this.sesionUsuario?.respuestaUsuario,
          ),
        ),
      )
      .subscribe((respuestaUsuarios: IRespuestaUsuario[]) => (this.respuestaUsuariosSharedCollection = respuestaUsuarios));
  }
}
