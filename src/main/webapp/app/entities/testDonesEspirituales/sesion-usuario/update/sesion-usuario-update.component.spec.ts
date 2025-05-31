import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUsuario } from 'app/entities/testDonesEspirituales/usuario/usuario.model';
import { UsuarioService } from 'app/entities/testDonesEspirituales/usuario/service/usuario.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { ISesionUsuario } from '../sesion-usuario.model';
import { SesionUsuarioService } from '../service/sesion-usuario.service';
import { SesionUsuarioFormService } from './sesion-usuario-form.service';

import { SesionUsuarioUpdateComponent } from './sesion-usuario-update.component';

describe('SesionUsuario Management Update Component', () => {
  let comp: SesionUsuarioUpdateComponent;
  let fixture: ComponentFixture<SesionUsuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sesionUsuarioFormService: SesionUsuarioFormService;
  let sesionUsuarioService: SesionUsuarioService;
  let usuarioService: UsuarioService;
  let respuestaUsuarioService: RespuestaUsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SesionUsuarioUpdateComponent],
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
      .overrideTemplate(SesionUsuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SesionUsuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sesionUsuarioFormService = TestBed.inject(SesionUsuarioFormService);
    sesionUsuarioService = TestBed.inject(SesionUsuarioService);
    usuarioService = TestBed.inject(UsuarioService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Usuario query and add missing value', () => {
      const sesionUsuario: ISesionUsuario = { id: 18656 };
      const usuario: IUsuario = { id: 544 };
      sesionUsuario.usuario = usuario;

      const usuarioCollection: IUsuario[] = [{ id: 544 }];
      jest.spyOn(usuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioCollection })));
      const additionalUsuarios = [usuario];
      const expectedCollection: IUsuario[] = [...additionalUsuarios, ...usuarioCollection];
      jest.spyOn(usuarioService, 'addUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      expect(usuarioService.query).toHaveBeenCalled();
      expect(usuarioService.addUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioCollection,
        ...additionalUsuarios.map(expect.objectContaining),
      );
      expect(comp.usuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should call RespuestaUsuario query and add missing value', () => {
      const sesionUsuario: ISesionUsuario = { id: 18656 };
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      sesionUsuario.respuestaUsuario = respuestaUsuario;

      const respuestaUsuarioCollection: IRespuestaUsuario[] = [{ id: 22901 }];
      jest.spyOn(respuestaUsuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: respuestaUsuarioCollection })));
      const additionalRespuestaUsuarios = [respuestaUsuario];
      const expectedCollection: IRespuestaUsuario[] = [...additionalRespuestaUsuarios, ...respuestaUsuarioCollection];
      jest.spyOn(respuestaUsuarioService, 'addRespuestaUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      expect(respuestaUsuarioService.query).toHaveBeenCalled();
      expect(respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        respuestaUsuarioCollection,
        ...additionalRespuestaUsuarios.map(expect.objectContaining),
      );
      expect(comp.respuestaUsuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const sesionUsuario: ISesionUsuario = { id: 18656 };
      const usuario: IUsuario = { id: 544 };
      sesionUsuario.usuario = usuario;
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      sesionUsuario.respuestaUsuario = respuestaUsuario;

      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      expect(comp.usuariosSharedCollection).toContainEqual(usuario);
      expect(comp.respuestaUsuariosSharedCollection).toContainEqual(respuestaUsuario);
      expect(comp.sesionUsuario).toEqual(sesionUsuario);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesionUsuario>>();
      const sesionUsuario = { id: 5645 };
      jest.spyOn(sesionUsuarioFormService, 'getSesionUsuario').mockReturnValue(sesionUsuario);
      jest.spyOn(sesionUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sesionUsuario }));
      saveSubject.complete();

      // THEN
      expect(sesionUsuarioFormService.getSesionUsuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sesionUsuarioService.update).toHaveBeenCalledWith(expect.objectContaining(sesionUsuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesionUsuario>>();
      const sesionUsuario = { id: 5645 };
      jest.spyOn(sesionUsuarioFormService, 'getSesionUsuario').mockReturnValue({ id: null });
      jest.spyOn(sesionUsuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesionUsuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sesionUsuario }));
      saveSubject.complete();

      // THEN
      expect(sesionUsuarioFormService.getSesionUsuario).toHaveBeenCalled();
      expect(sesionUsuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISesionUsuario>>();
      const sesionUsuario = { id: 5645 };
      jest.spyOn(sesionUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sesionUsuarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUsuario', () => {
      it('should forward to usuarioService', () => {
        const entity = { id: 544 };
        const entity2 = { id: 13162 };
        jest.spyOn(usuarioService, 'compareUsuario');
        comp.compareUsuario(entity, entity2);
        expect(usuarioService.compareUsuario).toHaveBeenCalledWith(entity, entity2);
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
