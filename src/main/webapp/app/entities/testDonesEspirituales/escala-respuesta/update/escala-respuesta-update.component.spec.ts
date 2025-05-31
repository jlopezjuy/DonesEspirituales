import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EscalaRespuestaService } from '../service/escala-respuesta.service';
import { IEscalaRespuesta } from '../escala-respuesta.model';
import { EscalaRespuestaFormService } from './escala-respuesta-form.service';

import { EscalaRespuestaUpdateComponent } from './escala-respuesta-update.component';

describe('EscalaRespuesta Management Update Component', () => {
  let comp: EscalaRespuestaUpdateComponent;
  let fixture: ComponentFixture<EscalaRespuestaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let escalaRespuestaFormService: EscalaRespuestaFormService;
  let escalaRespuestaService: EscalaRespuestaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EscalaRespuestaUpdateComponent],
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
      .overrideTemplate(EscalaRespuestaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EscalaRespuestaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    escalaRespuestaFormService = TestBed.inject(EscalaRespuestaFormService);
    escalaRespuestaService = TestBed.inject(EscalaRespuestaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const escalaRespuesta: IEscalaRespuesta = { id: 18820 };

      activatedRoute.data = of({ escalaRespuesta });
      comp.ngOnInit();

      expect(comp.escalaRespuesta).toEqual(escalaRespuesta);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscalaRespuesta>>();
      const escalaRespuesta = { id: 12954 };
      jest.spyOn(escalaRespuestaFormService, 'getEscalaRespuesta').mockReturnValue(escalaRespuesta);
      jest.spyOn(escalaRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escalaRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escalaRespuesta }));
      saveSubject.complete();

      // THEN
      expect(escalaRespuestaFormService.getEscalaRespuesta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(escalaRespuestaService.update).toHaveBeenCalledWith(expect.objectContaining(escalaRespuesta));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscalaRespuesta>>();
      const escalaRespuesta = { id: 12954 };
      jest.spyOn(escalaRespuestaFormService, 'getEscalaRespuesta').mockReturnValue({ id: null });
      jest.spyOn(escalaRespuestaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escalaRespuesta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: escalaRespuesta }));
      saveSubject.complete();

      // THEN
      expect(escalaRespuestaFormService.getEscalaRespuesta).toHaveBeenCalled();
      expect(escalaRespuestaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEscalaRespuesta>>();
      const escalaRespuesta = { id: 12954 };
      jest.spyOn(escalaRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ escalaRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(escalaRespuestaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
