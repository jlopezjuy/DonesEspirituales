import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';
import { CuestionarioService } from 'app/entities/testDonesEspirituales/cuestionario/service/cuestionario.service';
import { EstadoRespuesta } from 'app/entities/enumerations/estado-respuesta.model';
import { RespuestaUsuarioService } from '../service/respuesta-usuario.service';
import { IRespuestaUsuario } from '../respuesta-usuario.model';
import { RespuestaUsuarioFormGroup, RespuestaUsuarioFormService } from './respuesta-usuario-form.service';

@Component({
  selector: 'jhi-respuesta-usuario-update',
  templateUrl: './respuesta-usuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RespuestaUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  respuestaUsuario: IRespuestaUsuario | null = null;
  estadoRespuestaValues = Object.keys(EstadoRespuesta);

  usersSharedCollection: IUser[] = [];
  cuestionariosSharedCollection: ICuestionario[] = [];

  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected respuestaUsuarioFormService = inject(RespuestaUsuarioFormService);
  protected userService = inject(UserService);
  protected cuestionarioService = inject(CuestionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RespuestaUsuarioFormGroup = this.respuestaUsuarioFormService.createRespuestaUsuarioFormGroup();

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareCuestionario = (o1: ICuestionario | null, o2: ICuestionario | null): boolean =>
    this.cuestionarioService.compareCuestionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ respuestaUsuario }) => {
      this.respuestaUsuario = respuestaUsuario;
      if (respuestaUsuario) {
        this.updateForm(respuestaUsuario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const respuestaUsuario = this.respuestaUsuarioFormService.getRespuestaUsuario(this.editForm);
    if (respuestaUsuario.id !== null) {
      this.subscribeToSaveResponse(this.respuestaUsuarioService.update(respuestaUsuario));
    } else {
      this.subscribeToSaveResponse(this.respuestaUsuarioService.create(respuestaUsuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRespuestaUsuario>>): void {
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

  protected updateForm(respuestaUsuario: IRespuestaUsuario): void {
    this.respuestaUsuario = respuestaUsuario;
    this.respuestaUsuarioFormService.resetForm(this.editForm, respuestaUsuario);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, respuestaUsuario.user);
    this.cuestionariosSharedCollection = this.cuestionarioService.addCuestionarioToCollectionIfMissing<ICuestionario>(
      this.cuestionariosSharedCollection,
      respuestaUsuario.cuestionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.respuestaUsuario?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.cuestionarioService
      .query()
      .pipe(map((res: HttpResponse<ICuestionario[]>) => res.body ?? []))
      .pipe(
        map((cuestionarios: ICuestionario[]) =>
          this.cuestionarioService.addCuestionarioToCollectionIfMissing<ICuestionario>(cuestionarios, this.respuestaUsuario?.cuestionario),
        ),
      )
      .subscribe((cuestionarios: ICuestionario[]) => (this.cuestionariosSharedCollection = cuestionarios));
  }
}
