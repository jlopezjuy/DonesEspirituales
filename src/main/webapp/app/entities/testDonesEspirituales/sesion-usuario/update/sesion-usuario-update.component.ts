import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
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

  usersSharedCollection: IUser[] = [];
  respuestaUsuariosSharedCollection: IRespuestaUsuario[] = [];

  protected sesionUsuarioService = inject(SesionUsuarioService);
  protected sesionUsuarioFormService = inject(SesionUsuarioFormService);
  protected userService = inject(UserService);
  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SesionUsuarioFormGroup = this.sesionUsuarioFormService.createSesionUsuarioFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

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

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, sesionUsuario.user);
    this.respuestaUsuariosSharedCollection = this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
      this.respuestaUsuariosSharedCollection,
      sesionUsuario.respuestaUsuario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.sesionUsuario?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

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
