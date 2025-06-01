import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { InterpretacionService } from '../service/interpretacion.service';
import { IInterpretacion } from '../interpretacion.model';
import { InterpretacionFormService } from './interpretacion-form.service';

import { InterpretacionUpdateComponent } from './interpretacion-update.component';

describe('Interpretacion Management Update Component', () => {
  let comp: InterpretacionUpdateComponent;
  let fixture: ComponentFixture<InterpretacionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let interpretacionFormService: InterpretacionFormService;
  let interpretacionService: InterpretacionService;
  let donEspiritualService: DonEspiritualService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterpretacionUpdateComponent],
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
      .overrideTemplate(InterpretacionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InterpretacionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    interpretacionFormService = TestBed.inject(InterpretacionFormService);
    interpretacionService = TestBed.inject(InterpretacionService);
    donEspiritualService = TestBed.inject(DonEspiritualService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call DonEspiritual query and add missing value', () => {
      const interpretacion: IInterpretacion = { id: 32306 };
      const donEspiritual: IDonEspiritual = { id: 2824 };
      interpretacion.donEspiritual = donEspiritual;

      const donEspiritualCollection: IDonEspiritual[] = [{ id: 2824 }];
      jest.spyOn(donEspiritualService, 'query').mockReturnValue(of(new HttpResponse({ body: donEspiritualCollection })));
      const additionalDonEspirituals = [donEspiritual];
      const expectedCollection: IDonEspiritual[] = [...additionalDonEspirituals, ...donEspiritualCollection];
      jest.spyOn(donEspiritualService, 'addDonEspiritualToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ interpretacion });
      comp.ngOnInit();

      expect(donEspiritualService.query).toHaveBeenCalled();
      expect(donEspiritualService.addDonEspiritualToCollectionIfMissing).toHaveBeenCalledWith(
        donEspiritualCollection,
        ...additionalDonEspirituals.map(expect.objectContaining),
      );
      expect(comp.donEspiritualsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const interpretacion: IInterpretacion = { id: 32306 };
      const donEspiritual: IDonEspiritual = { id: 2824 };
      interpretacion.donEspiritual = donEspiritual;

      activatedRoute.data = of({ interpretacion });
      comp.ngOnInit();

      expect(comp.donEspiritualsSharedCollection).toContainEqual(donEspiritual);
      expect(comp.interpretacion).toEqual(interpretacion);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterpretacion>>();
      const interpretacion = { id: 1334 };
      jest.spyOn(interpretacionFormService, 'getInterpretacion').mockReturnValue(interpretacion);
      jest.spyOn(interpretacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interpretacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interpretacion }));
      saveSubject.complete();

      // THEN
      expect(interpretacionFormService.getInterpretacion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(interpretacionService.update).toHaveBeenCalledWith(expect.objectContaining(interpretacion));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterpretacion>>();
      const interpretacion = { id: 1334 };
      jest.spyOn(interpretacionFormService, 'getInterpretacion').mockReturnValue({ id: null });
      jest.spyOn(interpretacionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interpretacion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: interpretacion }));
      saveSubject.complete();

      // THEN
      expect(interpretacionFormService.getInterpretacion).toHaveBeenCalled();
      expect(interpretacionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInterpretacion>>();
      const interpretacion = { id: 1334 };
      jest.spyOn(interpretacionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ interpretacion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(interpretacionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
