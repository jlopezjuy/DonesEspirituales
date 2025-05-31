import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { IDetalleRespuesta } from 'app/entities/testDonesEspirituales/detalle-respuesta/detalle-respuesta.model';
import { DetalleRespuestaService } from 'app/entities/testDonesEspirituales/detalle-respuesta/service/detalle-respuesta.service';
import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { AuditoriaRespuestaService } from '../service/auditoria-respuesta.service';
import { AuditoriaRespuestaFormService } from './auditoria-respuesta-form.service';

import { AuditoriaRespuestaUpdateComponent } from './auditoria-respuesta-update.component';

describe('AuditoriaRespuesta Management Update Component', () => {
  let comp: AuditoriaRespuestaUpdateComponent;
  let fixture: ComponentFixture<AuditoriaRespuestaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let auditoriaRespuestaFormService: AuditoriaRespuestaFormService;
  let auditoriaRespuestaService: AuditoriaRespuestaService;
  let respuestaUsuarioService: RespuestaUsuarioService;
  let detalleRespuestaService: DetalleRespuestaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AuditoriaRespuestaUpdateComponent],
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
      .overrideTemplate(AuditoriaRespuestaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuditoriaRespuestaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    auditoriaRespuestaFormService = TestBed.inject(AuditoriaRespuestaFormService);
    auditoriaRespuestaService = TestBed.inject(AuditoriaRespuestaService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);
    detalleRespuestaService = TestBed.inject(DetalleRespuestaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call RespuestaUsuario query and add missing value', () => {
      const auditoriaRespuesta: IAuditoriaRespuesta = { id: 18851 };
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      auditoriaRespuesta.respuestaUsuario = respuestaUsuario;

      const respuestaUsuarioCollection: IRespuestaUsuario[] = [{ id: 22901 }];
      jest.spyOn(respuestaUsuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: respuestaUsuarioCollection })));
      const additionalRespuestaUsuarios = [respuestaUsuario];
      const expectedCollection: IRespuestaUsuario[] = [...additionalRespuestaUsuarios, ...respuestaUsuarioCollection];
      jest.spyOn(respuestaUsuarioService, 'addRespuestaUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ auditoriaRespuesta });
      comp.ngOnInit();

      expect(respuestaUsuarioService.query).toHaveBeenCalled();
      expect(respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        respuestaUsuarioCollection,
        ...additionalRespuestaUsuarios.map(expect.objectContaining),
      );
      expect(comp.respuestaUsuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should call DetalleRespuesta query and add missing value', () => {
      const auditoriaRespuesta: IAuditoriaRespuesta = { id: 18851 };
      const detalleRespuesta: IDetalleRespuesta = { id: 18708 };
      auditoriaRespuesta.detalleRespuesta = detalleRespuesta;

      const detalleRespuestaCollection: IDetalleRespuesta[] = [{ id: 18708 }];
      jest.spyOn(detalleRespuestaService, 'query').mockReturnValue(of(new HttpResponse({ body: detalleRespuestaCollection })));
      const additionalDetalleRespuestas = [detalleRespuesta];
      const expectedCollection: IDetalleRespuesta[] = [...additionalDetalleRespuestas, ...detalleRespuestaCollection];
      jest.spyOn(detalleRespuestaService, 'addDetalleRespuestaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ auditoriaRespuesta });
      comp.ngOnInit();

      expect(detalleRespuestaService.query).toHaveBeenCalled();
      expect(detalleRespuestaService.addDetalleRespuestaToCollectionIfMissing).toHaveBeenCalledWith(
        detalleRespuestaCollection,
        ...additionalDetalleRespuestas.map(expect.objectContaining),
      );
      expect(comp.detalleRespuestasSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const auditoriaRespuesta: IAuditoriaRespuesta = { id: 18851 };
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      auditoriaRespuesta.respuestaUsuario = respuestaUsuario;
      const detalleRespuesta: IDetalleRespuesta = { id: 18708 };
      auditoriaRespuesta.detalleRespuesta = detalleRespuesta;

      activatedRoute.data = of({ auditoriaRespuesta });
      comp.ngOnInit();

      expect(comp.respuestaUsuariosSharedCollection).toContainEqual(respuestaUsuario);
      expect(comp.detalleRespuestasSharedCollection).toContainEqual(detalleRespuesta);
      expect(comp.auditoriaRespuesta).toEqual(auditoriaRespuesta);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditoriaRespuesta>>();
      const auditoriaRespuesta = { id: 28590 };
      jest.spyOn(auditoriaRespuestaFormService, 'getAuditoriaRespuesta').mockReturnValue(auditoriaRespuesta);
      jest.spyOn(auditoriaRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditoriaRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditoriaRespuesta }));
      saveSubject.complete();

      // THEN
      expect(auditoriaRespuestaFormService.getAuditoriaRespuesta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(auditoriaRespuestaService.update).toHaveBeenCalledWith(expect.objectContaining(auditoriaRespuesta));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditoriaRespuesta>>();
      const auditoriaRespuesta = { id: 28590 };
      jest.spyOn(auditoriaRespuestaFormService, 'getAuditoriaRespuesta').mockReturnValue({ id: null });
      jest.spyOn(auditoriaRespuestaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditoriaRespuesta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: auditoriaRespuesta }));
      saveSubject.complete();

      // THEN
      expect(auditoriaRespuestaFormService.getAuditoriaRespuesta).toHaveBeenCalled();
      expect(auditoriaRespuestaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuditoriaRespuesta>>();
      const auditoriaRespuesta = { id: 28590 };
      jest.spyOn(auditoriaRespuestaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ auditoriaRespuesta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(auditoriaRespuestaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRespuestaUsuario', () => {
      it('should forward to respuestaUsuarioService', () => {
        const entity = { id: 22901 };
        const entity2 = { id: 15546 };
        jest.spyOn(respuestaUsuarioService, 'compareRespuestaUsuario');
        comp.compareRespuestaUsuario(entity, entity2);
        expect(respuestaUsuarioService.compareRespuestaUsuario).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDetalleRespuesta', () => {
      it('should forward to detalleRespuestaService', () => {
        const entity = { id: 18708 };
        const entity2 = { id: 2437 };
        jest.spyOn(detalleRespuestaService, 'compareDetalleRespuesta');
        comp.compareDetalleRespuesta(entity, entity2);
        expect(detalleRespuestaService.compareDetalleRespuesta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
