import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEscalaRespuesta } from 'app/entities/testDonesEspirituales/escala-respuesta/escala-respuesta.model';
import { EscalaRespuestaService } from 'app/entities/testDonesEspirituales/escala-respuesta/service/escala-respuesta.service';
import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { PreguntaService } from 'app/entities/testDonesEspirituales/pregunta/service/pregunta.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { DetalleRespuestaService } from '../service/detalle-respuesta.service';
import { IDetalleRespuesta } from '../detalle-respuesta.model';
import { DetalleRespuestaFormGroup, DetalleRespuestaFormService } from './detalle-respuesta-form.service';

@Component({
  selector: 'jhi-detalle-respuesta-update',
  templateUrl: './detalle-respuesta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DetalleRespuestaUpdateComponent implements OnInit {
  isSaving = false;
  detalleRespuesta: IDetalleRespuesta | null = null;

  escalaRespuestasSharedCollection: IEscalaRespuesta[] = [];
  preguntasSharedCollection: IPregunta[] = [];
  respuestaUsuariosSharedCollection: IRespuestaUsuario[] = [];

  protected detalleRespuestaService = inject(DetalleRespuestaService);
  protected detalleRespuestaFormService = inject(DetalleRespuestaFormService);
  protected escalaRespuestaService = inject(EscalaRespuestaService);
  protected preguntaService = inject(PreguntaService);
  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DetalleRespuestaFormGroup = this.detalleRespuestaFormService.createDetalleRespuestaFormGroup();

  compareEscalaRespuesta = (o1: IEscalaRespuesta | null, o2: IEscalaRespuesta | null): boolean =>
    this.escalaRespuestaService.compareEscalaRespuesta(o1, o2);

  comparePregunta = (o1: IPregunta | null, o2: IPregunta | null): boolean => this.preguntaService.comparePregunta(o1, o2);

  compareRespuestaUsuario = (o1: IRespuestaUsuario | null, o2: IRespuestaUsuario | null): boolean =>
    this.respuestaUsuarioService.compareRespuestaUsuario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalleRespuesta }) => {
      this.detalleRespuesta = detalleRespuesta;
      if (detalleRespuesta) {
        this.updateForm(detalleRespuesta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalleRespuesta = this.detalleRespuestaFormService.getDetalleRespuesta(this.editForm);
    if (detalleRespuesta.id !== null) {
      this.subscribeToSaveResponse(this.detalleRespuestaService.update(detalleRespuesta));
    } else {
      this.subscribeToSaveResponse(this.detalleRespuestaService.create(detalleRespuesta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleRespuesta>>): void {
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

  protected updateForm(detalleRespuesta: IDetalleRespuesta): void {
    this.detalleRespuesta = detalleRespuesta;
    this.detalleRespuestaFormService.resetForm(this.editForm, detalleRespuesta);

    this.escalaRespuestasSharedCollection = this.escalaRespuestaService.addEscalaRespuestaToCollectionIfMissing<IEscalaRespuesta>(
      this.escalaRespuestasSharedCollection,
      detalleRespuesta.escalaRespuesta,
    );
    this.preguntasSharedCollection = this.preguntaService.addPreguntaToCollectionIfMissing<IPregunta>(
      this.preguntasSharedCollection,
      detalleRespuesta.pregunta,
    );
    this.respuestaUsuariosSharedCollection = this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
      this.respuestaUsuariosSharedCollection,
      detalleRespuesta.respuestaUsuario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.escalaRespuestaService
      .query()
      .pipe(map((res: HttpResponse<IEscalaRespuesta[]>) => res.body ?? []))
      .pipe(
        map((escalaRespuestas: IEscalaRespuesta[]) =>
          this.escalaRespuestaService.addEscalaRespuestaToCollectionIfMissing<IEscalaRespuesta>(
            escalaRespuestas,
            this.detalleRespuesta?.escalaRespuesta,
          ),
        ),
      )
      .subscribe((escalaRespuestas: IEscalaRespuesta[]) => (this.escalaRespuestasSharedCollection = escalaRespuestas));

    this.preguntaService
      .query()
      .pipe(map((res: HttpResponse<IPregunta[]>) => res.body ?? []))
      .pipe(
        map((preguntas: IPregunta[]) =>
          this.preguntaService.addPreguntaToCollectionIfMissing<IPregunta>(preguntas, this.detalleRespuesta?.pregunta),
        ),
      )
      .subscribe((preguntas: IPregunta[]) => (this.preguntasSharedCollection = preguntas));

    this.respuestaUsuarioService
      .query()
      .pipe(map((res: HttpResponse<IRespuestaUsuario[]>) => res.body ?? []))
      .pipe(
        map((respuestaUsuarios: IRespuestaUsuario[]) =>
          this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
            respuestaUsuarios,
            this.detalleRespuesta?.respuestaUsuario,
          ),
        ),
      )
      .subscribe((respuestaUsuarios: IRespuestaUsuario[]) => (this.respuestaUsuariosSharedCollection = respuestaUsuarios));
  }
}
