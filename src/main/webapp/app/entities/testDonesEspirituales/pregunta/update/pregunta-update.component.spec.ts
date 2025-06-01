import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';
import { CuestionarioService } from 'app/entities/testDonesEspirituales/cuestionario/service/cuestionario.service';
import { PreguntaService } from '../service/pregunta.service';
import { IPregunta } from '../pregunta.model';
import { PreguntaFormService } from './pregunta-form.service';

import { PreguntaUpdateComponent } from './pregunta-update.component';

describe('Pregunta Management Update Component', () => {
  let comp: PreguntaUpdateComponent;
  let fixture: ComponentFixture<PreguntaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let preguntaFormService: PreguntaFormService;
  let preguntaService: PreguntaService;
  let cuestionarioService: CuestionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PreguntaUpdateComponent],
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
      .overrideTemplate(PreguntaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PreguntaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    preguntaFormService = TestBed.inject(PreguntaFormService);
    preguntaService = TestBed.inject(PreguntaService);
    cuestionarioService = TestBed.inject(CuestionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Cuestionario query and add missing value', () => {
      const pregunta: IPregunta = { id: 11889 };
      const cuestionario: ICuestionario = { id: 24961 };
      pregunta.cuestionario = cuestionario;

      const cuestionarioCollection: ICuestionario[] = [{ id: 24961 }];
      jest.spyOn(cuestionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: cuestionarioCollection })));
      const additionalCuestionarios = [cuestionario];
      const expectedCollection: ICuestionario[] = [...additionalCuestionarios, ...cuestionarioCollection];
      jest.spyOn(cuestionarioService, 'addCuestionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pregunta });
      comp.ngOnInit();

      expect(cuestionarioService.query).toHaveBeenCalled();
      expect(cuestionarioService.addCuestionarioToCollectionIfMissing).toHaveBeenCalledWith(
        cuestionarioCollection,
        ...additionalCuestionarios.map(expect.objectContaining),
      );
      expect(comp.cuestionariosSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const pregunta: IPregunta = { id: 11889 };
      const cuestionario: ICuestionario = { id: 24961 };
      pregunta.cuestionario = cuestionario;

      activatedRoute.data = of({ pregunta });
      comp.ngOnInit();

      expect(comp.cuestionariosSharedCollection).toContainEqual(cuestionario);
      expect(comp.pregunta).toEqual(pregunta);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPregunta>>();
      const pregunta = { id: 28174 };
      jest.spyOn(preguntaFormService, 'getPregunta').mockReturnValue(pregunta);
      jest.spyOn(preguntaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pregunta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pregunta }));
      saveSubject.complete();

      // THEN
      expect(preguntaFormService.getPregunta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(preguntaService.update).toHaveBeenCalledWith(expect.objectContaining(pregunta));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPregunta>>();
      const pregunta = { id: 28174 };
      jest.spyOn(preguntaFormService, 'getPregunta').mockReturnValue({ id: null });
      jest.spyOn(preguntaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pregunta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pregunta }));
      saveSubject.complete();

      // THEN
      expect(preguntaFormService.getPregunta).toHaveBeenCalled();
      expect(preguntaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPregunta>>();
      const pregunta = { id: 28174 };
      jest.spyOn(preguntaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pregunta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(preguntaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCuestionario', () => {
      it('should forward to cuestionarioService', () => {
        const entity = { id: 24961 };
        const entity2 = { id: 29113 };
        jest.spyOn(cuestionarioService, 'compareCuestionario');
        comp.compareCuestionario(entity, entity2);
        expect(cuestionarioService.compareCuestionario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
