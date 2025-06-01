import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { PreguntaService } from 'app/entities/testDonesEspirituales/pregunta/service/pregunta.service';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { IPreguntaDon } from '../pregunta-don.model';
import { PreguntaDonService } from '../service/pregunta-don.service';
import { PreguntaDonFormService } from './pregunta-don-form.service';

import { PreguntaDonUpdateComponent } from './pregunta-don-update.component';

describe('PreguntaDon Management Update Component', () => {
  let comp: PreguntaDonUpdateComponent;
  let fixture: ComponentFixture<PreguntaDonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let preguntaDonFormService: PreguntaDonFormService;
  let preguntaDonService: PreguntaDonService;
  let preguntaService: PreguntaService;
  let donEspiritualService: DonEspiritualService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PreguntaDonUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PreguntaDonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PreguntaDonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    preguntaDonFormService = TestBed.inject(PreguntaDonFormService);
    preguntaDonService = TestBed.inject(PreguntaDonService);
    preguntaService = TestBed.inject(PreguntaService);
    donEspiritualService = TestBed.inject(DonEspiritualService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Pregunta query and add missing value', () => {
      const preguntaDon: IPreguntaDon = { id: 2072 };
      const pregunta: IPregunta = { id: 28174 };
      preguntaDon.pregunta = pregunta;

      const preguntaCollection: IPregunta[] = [{ id: 28174 }];
      jest.spyOn(preguntaService, 'query').mockReturnValue(of(new HttpResponse({ body: preguntaCollection })));
      const additionalPreguntas = [pregunta];
      const expectedCollection: IPregunta[] = [...additionalPreguntas, ...preguntaCollection];
      jest.spyOn(preguntaService, 'addPreguntaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ preguntaDon });
      comp.ngOnInit();

      expect(preguntaService.query).toHaveBeenCalled();
      expect(preguntaService.addPreguntaToCollectionIfMissing).toHaveBeenCalledWith(
        preguntaCollection,
        ...additionalPreguntas.map(expect.objectContaining),
      );
      expect(comp.preguntasSharedCollection).toEqual(expectedCollection);
    });

    it('should call DonEspiritual query and add missing value', () => {
      const preguntaDon: IPreguntaDon = { id: 2072 };
      const donEspiritual: IDonEspiritual = { id: 2824 };
      preguntaDon.donEspiritual = donEspiritual;

      const donEspiritualCollection: IDonEspiritual[] = [{ id: 2824 }];
      jest.spyOn(donEspiritualService, 'query').mockReturnValue(of(new HttpResponse({ body: donEspiritualCollection })));
      const additionalDonEspirituals = [donEspiritual];
      const expectedCollection: IDonEspiritual[] = [...additionalDonEspirituals, ...donEspiritualCollection];
      jest.spyOn(donEspiritualService, 'addDonEspiritualToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ preguntaDon });
      comp.ngOnInit();

      expect(donEspiritualService.query).toHaveBeenCalled();
      expect(donEspiritualService.addDonEspiritualToCollectionIfMissing).toHaveBeenCalledWith(
        donEspiritualCollection,
        ...additionalDonEspirituals.map(expect.objectContaining),
      );
      expect(comp.donEspiritualsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const preguntaDon: IPreguntaDon = { id: 2072 };
      const pregunta: IPregunta = { id: 28174 };
      preguntaDon.pregunta = pregunta;
      const donEspiritual: IDonEspiritual = { id: 2824 };
      preguntaDon.donEspiritual = donEspiritual;

      activatedRoute.data = of({ preguntaDon });
      comp.ngOnInit();

      expect(comp.preguntasSharedCollection).toContainEqual(pregunta);
      expect(comp.donEspiritualsSharedCollection).toContainEqual(donEspiritual);
      expect(comp.preguntaDon).toEqual(preguntaDon);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreguntaDon>>();
      const preguntaDon = { id: 25443 };
      jest.spyOn(preguntaDonFormService, 'getPreguntaDon').mockReturnValue(preguntaDon);
      jest.spyOn(preguntaDonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preguntaDon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: preguntaDon }));
      saveSubject.complete();

      // THEN
      expect(preguntaDonFormService.getPreguntaDon).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(preguntaDonService.update).toHaveBeenCalledWith(expect.objectContaining(preguntaDon));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreguntaDon>>();
      const preguntaDon = { id: 25443 };
      jest.spyOn(preguntaDonFormService, 'getPreguntaDon').mockReturnValue({ id: null });
      jest.spyOn(preguntaDonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preguntaDon: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: preguntaDon }));
      saveSubject.complete();

      // THEN
      expect(preguntaDonFormService.getPreguntaDon).toHaveBeenCalled();
      expect(preguntaDonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPreguntaDon>>();
      const preguntaDon = { id: 25443 };
      jest.spyOn(preguntaDonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ preguntaDon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(preguntaDonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePregunta', () => {
      it('should forward to preguntaService', () => {
        const entity = { id: 28174 };
        const entity2 = { id: 11889 };
        jest.spyOn(preguntaService, 'comparePregunta');
        comp.comparePregunta(entity, entity2);
        expect(preguntaService.comparePregunta).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDonEspiritual', () => {
      it('should forward to donEspiritualService', () => {
        const entity = { id: 2824 };
        const entity2 = { id: 697 };
        jest.spyOn(donEspiritualService, 'compareDonEspiritual');
        comp.compareDonEspiritual(entity, entity2);
        expect(donEspiritualService.compareDonEspiritual).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
