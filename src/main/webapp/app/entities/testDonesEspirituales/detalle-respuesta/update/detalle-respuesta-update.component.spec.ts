import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEscalaRespuesta } from 'app/entities/testDonesEspirituales/escala-respuesta/escala-respuesta.model';
import { EscalaRespuestaService } from 'app/entities/testDonesEspirituales/escala-respuesta/service/escala-respuesta.service';
import { IPregunta } from 'app/entities/testDonesEspirituales/pregunta/pregunta.model';
import { PreguntaService } from 'app/entities/testDonesEspirituales/pregunta/service/pregunta.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { IDetalleRespuesta } from '../detalle-respuesta.model';
import { DetalleRespuestaService } from '../service/detalle-respuesta.service';
import { DetalleRespuestaFormService } from './detalle-respuesta-form.service';

import { DetalleRespuestaUpdateComponent } from './detalle-respuesta-update.component';

describe('DetalleRespuesta Management Update Component', () => {
  let comp: DetalleRespuestaUpdateComponent;
  let fixture: ComponentFixture<DetalleRespuestaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detalleRespuestaFormService: DetalleRespuestaFormService;
  let detalleRespuestaService: DetalleRespuestaService;
  let escalaRespuestaService: EscalaRespuestaService;
  let preguntaService: PreguntaService;
  let respuestaUsuarioService: RespuestaUsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DetalleRespuestaUpdateComponent],
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
      .overrideTemplate(DetalleRespuestaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetalleRespuestaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detalleRespuestaFormService = TestBed.inject(DetalleRespuestaFormService);
    detalleRespuestaService = TestBed.inject(DetalleRespuestaService);
    escalaRespuestaService = TestBed.inject(EscalaRespuestaService);
    preguntaService = TestBed.inject(PreguntaService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call EscalaRespuesta query and add missing value', () => {
      const detalleRespuesta: IDetalleRespuesta = { id: 2437 };
      const escalaRespuesta: IEscalaRespuesta = { id: 12954 };
      detalleRespuesta.escalaRespuesta = escalaRespuesta;

      const escalaRespuestaCollection: IEscalaRespuesta[] = [{ id: 12954 }];
      jest.spyOn(escalaRespuestaService, 'query').mockReturnValue(of(new HttpResponse({ body: escalaRespuestaCollection })));
      const additionalEscalaRespuestas = [escalaRespuesta];
      const expectedCollection: IEscalaRespuesta[] = [...additionalEscalaRespuestas, ...escalaRespuestaCollection];
      jest.spyOn(escalaRespuestaService, 'addEscalaRespuestaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      expect(escalaRespuestaService.query).toHaveBeenCalled();
      expect(escalaRespuestaService.addEscalaRespuestaToCollectionIfMissing).toHaveBeenCalledWith(
        escalaRespuestaCollection,
        ...additionalEscalaRespuestas.map(expect.objectContaining),
      );
      expect(comp.escalaRespuestasSharedCollection).toEqual(expectedCollection);
    });

    it('should call Pregunta query and add missing value', () => {
      const detalleRespuesta: IDetalleRespuesta = { id: 2437 };
      const pregunta: IPregunta = { id: 28174 };
      detalleRespuesta.pregunta = pregunta;

      const preguntaCollection: IPregunta[] = [{ id: 28174 }];
      jest.spyOn(preguntaService, 'query').mockReturnValue(of(new HttpResponse({ body: preguntaCollection })));
      const additionalPreguntas = [pregunta];
      const expectedCollection: IPregunta[] = [...additionalPreguntas, ...preguntaCollection];
      jest.spyOn(preguntaService, 'addPreguntaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      expect(preguntaService.query).toHaveBeenCalled();
      expect(preguntaService.addPreguntaToCollectionIfMissing).toHaveBeenCalledWith(
        preguntaCollection,
        ...additionalPreguntas.map(expect.objectContaining),
      );
      expect(comp.preguntasSharedCollection).toEqual(expectedCollection);
    });

    it('should call RespuestaUsuario query and add missing value', () => {
      const detalleRespuesta: IDetalleRespuesta = { id: 2437 };
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      detalleRespuesta.respuestaUsuario = respuestaUsuario;

      const respuestaUsuarioCollection: IRespuestaUsuario[] = [{ id: 22901 }];
      jest.spyOn(respuestaUsuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: respuestaUsuarioCollection })));
      const additionalRespuestaUsuarios = [respuestaUsuario];
      const expectedCollection: IRespuestaUsuario[] = [...additionalRespuestaUsuarios, ...respuestaUsuarioCollection];
      jest.spyOn(respuestaUsuarioService, 'addRespuestaUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      expect(respuestaUsuarioService.query).toHaveBeenCalled();
      expect(respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        respuestaUsuarioCollection,
        ...additionalRespuestaUsuarios.map(expect.objectContaining),
      );
      expect(comp.respuestaUsuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const detalleRespuesta: IDetalleRespuesta = { id: 2437 };
      const escalaRespuesta: IEscalaRespuesta = { id: 12954 };
      detalleRespuesta.escalaRespuesta = escalaRespuesta;
      const pregunta: IPregunta = { id: 28174 };
      detalleRespuesta.pregunta = pregunta;
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      detalleRespuesta.respuestaUsuario = respuestaUsuario;

      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      expect(comp.escalaRespuestasSharedCollection).toContainEqual(escalaRespuesta);
      expect(comp.preguntasSharedCollection).toContainEqual(pregunta);
      expect(comp.respuestaUsuariosSharedCollection).toContainEqual(respuestaUsuario);
      expect(comp.detalleRespuesta).toEqual(detalleRespuesta);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleRespuesta>>();
      const detalleRespuesta = { id: 18708 };
      jest.spyOn(detalleRespuestaFormService, 'getDetalleRespuesta').mockReturnValue(detalleRespuesta);
      jest.spyOn(detalleRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleRespuesta }));
      saveSubject.complete();

      // THEN
      expect(detalleRespuestaFormService.getDetalleRespuesta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detalleRespuestaService.update).toHaveBeenCalledWith(expect.objectContaining(detalleRespuesta));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleRespuesta>>();
      const detalleRespuesta = { id: 18708 };
      jest.spyOn(detalleRespuestaFormService, 'getDetalleRespuesta').mockReturnValue({ id: null });
      jest.spyOn(detalleRespuestaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleRespuesta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalleRespuesta }));
      saveSubject.complete();

      // THEN
      expect(detalleRespuestaFormService.getDetalleRespuesta).toHaveBeenCalled();
      expect(detalleRespuestaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalleRespuesta>>();
      const detalleRespuesta = { id: 18708 };
      jest.spyOn(detalleRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalleRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detalleRespuestaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEscalaRespuesta', () => {
      it('should forward to escalaRespuestaService', () => {
        const entity = { id: 12954 };
        const entity2 = { id: 18820 };
        jest.spyOn(escalaRespuestaService, 'compareEscalaRespuesta');
        comp.compareEscalaRespuesta(entity, entity2);
        expect(escalaRespuestaService.compareEscalaRespuesta).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePregunta', () => {
      it('should forward to preguntaService', () => {
        const entity = { id: 28174 };
        const entity2 = { id: 11889 };
        jest.spyOn(preguntaService, 'comparePregunta');
        comp.comparePregunta(entity, entity2);
        expect(preguntaService.comparePregunta).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRespuestaUsuario', () => {
      it('should forward to respuestaUsuarioService', () => {
        const entity = { id: 22901 };
        const entity2 = { id: 15546 };
        jest.spyOn(respuestaUsuarioService, 'compareRespuestaUsuario');
        comp.compareRespuestaUsuario(entity, entity2);
        expect(respuestaUsuarioService.compareRespuestaUsuario).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
