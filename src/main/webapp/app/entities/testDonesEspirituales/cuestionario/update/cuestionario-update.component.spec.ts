import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CuestionarioService } from '../service/cuestionario.service';
import { ICuestionario } from '../cuestionario.model';
import { CuestionarioFormService } from './cuestionario-form.service';

import { CuestionarioUpdateComponent } from './cuestionario-update.component';

describe('Cuestionario Management Update Component', () => {
  let comp: CuestionarioUpdateComponent;
  let fixture: ComponentFixture<CuestionarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cuestionarioFormService: CuestionarioFormService;
  let cuestionarioService: CuestionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CuestionarioUpdateComponent],
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
      .overrideTemplate(CuestionarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CuestionarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cuestionarioFormService = TestBed.inject(CuestionarioFormService);
    cuestionarioService = TestBed.inject(CuestionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cuestionario: ICuestionario = { id: 29113 };

      activatedRoute.data = of({ cuestionario });
      comp.ngOnInit();

      expect(comp.cuestionario).toEqual(cuestionario);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuestionario>>();
      const cuestionario = { id: 24961 };
      jest.spyOn(cuestionarioFormService, 'getCuestionario').mockReturnValue(cuestionario);
      jest.spyOn(cuestionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuestionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuestionario }));
      saveSubject.complete();

      // THEN
      expect(cuestionarioFormService.getCuestionario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cuestionarioService.update).toHaveBeenCalledWith(expect.objectContaining(cuestionario));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuestionario>>();
      const cuestionario = { id: 24961 };
      jest.spyOn(cuestionarioFormService, 'getCuestionario').mockReturnValue({ id: null });
      jest.spyOn(cuestionarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuestionario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuestionario }));
      saveSubject.complete();

      // THEN
      expect(cuestionarioFormService.getCuestionario).toHaveBeenCalled();
      expect(cuestionarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuestionario>>();
      const cuestionario = { id: 24961 };
      jest.spyOn(cuestionarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuestionario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cuestionarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
