import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IInterpretacion } from 'app/entities/testDonesEspirituales/interpretacion/interpretacion.model';
import { InterpretacionService } from 'app/entities/testDonesEspirituales/interpretacion/service/interpretacion.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { ResultadoDonService } from '../service/resultado-don.service';
import { IResultadoDon } from '../resultado-don.model';
import { ResultadoDonFormGroup, ResultadoDonFormService } from './resultado-don-form.service';

@Component({
  selector: 'jhi-resultado-don-update',
  templateUrl: './resultado-don-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ResultadoDonUpdateComponent implements OnInit {
  isSaving = false;
  resultadoDon: IResultadoDon | null = null;

  interpretacionsSharedCollection: IInterpretacion[] = [];
  respuestaUsuariosSharedCollection: IRespuestaUsuario[] = [];
  donEspiritualsSharedCollection: IDonEspiritual[] = [];

  protected resultadoDonService = inject(ResultadoDonService);
  protected resultadoDonFormService = inject(ResultadoDonFormService);
  protected interpretacionService = inject(InterpretacionService);
  protected respuestaUsuarioService = inject(RespuestaUsuarioService);
  protected donEspiritualService = inject(DonEspiritualService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ResultadoDonFormGroup = this.resultadoDonFormService.createResultadoDonFormGroup();

  compareInterpretacion = (o1: IInterpretacion | null, o2: IInterpretacion | null): boolean =>
    this.interpretacionService.compareInterpretacion(o1, o2);

  compareRespuestaUsuario = (o1: IRespuestaUsuario | null, o2: IRespuestaUsuario | null): boolean =>
    this.respuestaUsuarioService.compareRespuestaUsuario(o1, o2);

  compareDonEspiritual = (o1: IDonEspiritual | null, o2: IDonEspiritual | null): boolean =>
    this.donEspiritualService.compareDonEspiritual(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultadoDon }) => {
      this.resultadoDon = resultadoDon;
      if (resultadoDon) {
        this.updateForm(resultadoDon);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultadoDon = this.resultadoDonFormService.getResultadoDon(this.editForm);
    if (resultadoDon.id !== null) {
      this.subscribeToSaveResponse(this.resultadoDonService.update(resultadoDon));
    } else {
      this.subscribeToSaveResponse(this.resultadoDonService.create(resultadoDon));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadoDon>>): void {
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

  protected updateForm(resultadoDon: IResultadoDon): void {
    this.resultadoDon = resultadoDon;
    this.resultadoDonFormService.resetForm(this.editForm, resultadoDon);

    this.interpretacionsSharedCollection = this.interpretacionService.addInterpretacionToCollectionIfMissing<IInterpretacion>(
      this.interpretacionsSharedCollection,
      resultadoDon.interpretacion,
    );
    this.respuestaUsuariosSharedCollection = this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
      this.respuestaUsuariosSharedCollection,
      resultadoDon.respuestaUsuario,
    );
    this.donEspiritualsSharedCollection = this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(
      this.donEspiritualsSharedCollection,
      resultadoDon.donEspiritual,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.interpretacionService
      .query()
      .pipe(map((res: HttpResponse<IInterpretacion[]>) => res.body ?? []))
      .pipe(
        map((interpretacions: IInterpretacion[]) =>
          this.interpretacionService.addInterpretacionToCollectionIfMissing<IInterpretacion>(
            interpretacions,
            this.resultadoDon?.interpretacion,
          ),
        ),
      )
      .subscribe((interpretacions: IInterpretacion[]) => (this.interpretacionsSharedCollection = interpretacions));

    this.respuestaUsuarioService
      .query()
      .pipe(map((res: HttpResponse<IRespuestaUsuario[]>) => res.body ?? []))
      .pipe(
        map((respuestaUsuarios: IRespuestaUsuario[]) =>
          this.respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing<IRespuestaUsuario>(
            respuestaUsuarios,
            this.resultadoDon?.respuestaUsuario,
          ),
        ),
      )
      .subscribe((respuestaUsuarios: IRespuestaUsuario[]) => (this.respuestaUsuariosSharedCollection = respuestaUsuarios));

    this.donEspiritualService
      .query()
      .pipe(map((res: HttpResponse<IDonEspiritual[]>) => res.body ?? []))
      .pipe(
        map((donEspirituals: IDonEspiritual[]) =>
          this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(donEspirituals, this.resultadoDon?.donEspiritual),
        ),
      )
      .subscribe((donEspirituals: IDonEspiritual[]) => (this.donEspiritualsSharedCollection = donEspirituals));
  }
}
