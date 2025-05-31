import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { DonEspiritualService } from '../service/don-espiritual.service';
import { IDonEspiritual } from '../don-espiritual.model';
import { DonEspiritualFormService } from './don-espiritual-form.service';

import { DonEspiritualUpdateComponent } from './don-espiritual-update.component';

describe('DonEspiritual Management Update Component', () => {
  let comp: DonEspiritualUpdateComponent;
  let fixture: ComponentFixture<DonEspiritualUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donEspiritualFormService: DonEspiritualFormService;
  let donEspiritualService: DonEspiritualService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DonEspiritualUpdateComponent],
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
      .overrideTemplate(DonEspiritualUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonEspiritualUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donEspiritualFormService = TestBed.inject(DonEspiritualFormService);
    donEspiritualService = TestBed.inject(DonEspiritualService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const donEspiritual: IDonEspiritual = { id: 697 };

      activatedRoute.data = of({ donEspiritual });
      comp.ngOnInit();

      expect(comp.donEspiritual).toEqual(donEspiritual);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonEspiritual>>();
      const donEspiritual = { id: 2824 };
      jest.spyOn(donEspiritualFormService, 'getDonEspiritual').mockReturnValue(donEspiritual);
      jest.spyOn(donEspiritualService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donEspiritual });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donEspiritual }));
      saveSubject.complete();

      // THEN
      expect(donEspiritualFormService.getDonEspiritual).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(donEspiritualService.update).toHaveBeenCalledWith(expect.objectContaining(donEspiritual));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonEspiritual>>();
      const donEspiritual = { id: 2824 };
      jest.spyOn(donEspiritualFormService, 'getDonEspiritual').mockReturnValue({ id: null });
      jest.spyOn(donEspiritualService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donEspiritual: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donEspiritual }));
      saveSubject.complete();

      // THEN
      expect(donEspiritualFormService.getDonEspiritual).toHaveBeenCalled();
      expect(donEspiritualService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDonEspiritual>>();
      const donEspiritual = { id: 2824 };
      jest.spyOn(donEspiritualService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donEspiritual });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donEspiritualService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
