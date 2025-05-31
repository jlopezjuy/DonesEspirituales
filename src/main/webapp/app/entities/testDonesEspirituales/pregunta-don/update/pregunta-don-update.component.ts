import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { PreguntaService } from 'app/entities/testDonesEspirituales/pregunta/service/pregunta.service';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { PreguntaDonService } from '../service/pregunta-don.service';
import { IPreguntaDon } from '../pregunta-don.model';
import { PreguntaDonFormGroup, PreguntaDonFormService } from './pregunta-don-form.service';

@Component({
  selector: 'jhi-pregunta-don-update',
  templateUrl: './pregunta-don-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PreguntaDonUpdateComponent implements OnInit {
  isSaving = false;
  preguntaDon: IPreguntaDon | null = null;

  preguntasSharedCollection: IPregunta[] = [];
  donEspiritualsSharedCollection: IDonEspiritual[] = [];

  protected preguntaDonService = inject(PreguntaDonService);
  protected preguntaDonFormService = inject(PreguntaDonFormService);
  protected preguntaService = inject(PreguntaService);
  protected donEspiritualService = inject(DonEspiritualService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PreguntaDonFormGroup = this.preguntaDonFormService.createPreguntaDonFormGroup();

  comparePregunta = (o1: IPregunta | null, o2: IPregunta | null): boolean => this.preguntaService.comparePregunta(o1, o2);

  compareDonEspiritual = (o1: IDonEspiritual | null, o2: IDonEspiritual | null): boolean =>
    this.donEspiritualService.compareDonEspiritual(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ preguntaDon }) => {
      this.preguntaDon = preguntaDon;
      if (preguntaDon) {
        this.updateForm(preguntaDon);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const preguntaDon = this.preguntaDonFormService.getPreguntaDon(this.editForm);
    if (preguntaDon.id !== null) {
      this.subscribeToSaveResponse(this.preguntaDonService.update(preguntaDon));
    } else {
      this.subscribeToSaveResponse(this.preguntaDonService.create(preguntaDon));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreguntaDon>>): void {
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

  protected updateForm(preguntaDon: IPreguntaDon): void {
    this.preguntaDon = preguntaDon;
    this.preguntaDonFormService.resetForm(this.editForm, preguntaDon);

    this.preguntasSharedCollection = this.preguntaService.addPreguntaToCollectionIfMissing<IPregunta>(
      this.preguntasSharedCollection,
      preguntaDon.pregunta,
    );
    this.donEspiritualsSharedCollection = this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(
      this.donEspiritualsSharedCollection,
      preguntaDon.donEspiritual,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.preguntaService
      .query()
      .pipe(map((res: HttpResponse<IPregunta[]>) => res.body ?? []))
      .pipe(
        map((preguntas: IPregunta[]) =>
          this.preguntaService.addPreguntaToCollectionIfMissing<IPregunta>(preguntas, this.preguntaDon?.pregunta),
        ),
      )
      .subscribe((preguntas: IPregunta[]) => (this.preguntasSharedCollection = preguntas));

    this.donEspiritualService
      .query()
      .pipe(map((res: HttpResponse<IDonEspiritual[]>) => res.body ?? []))
      .pipe(
        map((donEspirituals: IDonEspiritual[]) =>
          this.donEspiritualService.addDonEspiritualToCollectionIfMissing<IDonEspiritual>(donEspirituals, this.preguntaDon?.donEspiritual),
        ),
      )
      .subscribe((donEspirituals: IDonEspiritual[]) => (this.donEspiritualsSharedCollection = donEspirituals));
  }
}
