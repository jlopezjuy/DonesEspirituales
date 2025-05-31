import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUsuario } from 'app/entities/testDonesEspirituales/usuario/usuario.model';
import { UsuarioService } from 'app/entities/testDonesEspirituales/usuario/service/usuario.service';
import { ICuestionario } from 'app/entities/testDonesEspirituales/cuestionario/cuestionario.model';
import { CuestionarioService } from 'app/entities/testDonesEspirituales/cuestionario/service/cuestionario.service';
import { IRespuestaUsuario } from '../respuesta-usuario.model';
import { RespuestaUsuarioService } from '../service/respuesta-usuario.service';
import { RespuestaUsuarioFormService } from './respuesta-usuario-form.service';

import { RespuestaUsuarioUpdateComponent } from './respuesta-usuario-update.component';

describe('RespuestaUsuario Management Update Component', () => {
  let comp: RespuestaUsuarioUpdateComponent;
  let fixture: ComponentFixture<RespuestaUsuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let respuestaUsuarioFormService: RespuestaUsuarioFormService;
  let respuestaUsuarioService: RespuestaUsuarioService;
  let usuarioService: UsuarioService;
  let cuestionarioService: CuestionarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RespuestaUsuarioUpdateComponent],
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
      .overrideTemplate(RespuestaUsuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RespuestaUsuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    respuestaUsuarioFormService = TestBed.inject(RespuestaUsuarioFormService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);
    usuarioService = TestBed.inject(UsuarioService);
    cuestionarioService = TestBed.inject(CuestionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Usuario query and add missing value', () => {
      const respuestaUsuario: IRespuestaUsuario = { id: 15546 };
      const usuario: IUsuario = { id: 544 };
      respuestaUsuario.usuario = usuario;

      const usuarioCollection: IUsuario[] = [{ id: 544 }];
      jest.spyOn(usuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioCollection })));
      const additionalUsuarios = [usuario];
      const expectedCollection: IUsuario[] = [...additionalUsuarios, ...usuarioCollection];
      jest.spyOn(usuarioService, 'addUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      expect(usuarioService.query).toHaveBeenCalled();
      expect(usuarioService.addUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioCollection,
        ...additionalUsuarios.map(expect.objectContaining),
      );
      expect(comp.usuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should call Cuestionario query and add missing value', () => {
      const respuestaUsuario: IRespuestaUsuario = { id: 15546 };
      const cuestionario: ICuestionario = { id: 24961 };
      respuestaUsuario.cuestionario = cuestionario;

      const cuestionarioCollection: ICuestionario[] = [{ id: 24961 }];
      jest.spyOn(cuestionarioService, 'query').mockReturnValue(of(new HttpResponse({ body: cuestionarioCollection })));
      const additionalCuestionarios = [cuestionario];
      const expectedCollection: ICuestionario[] = [...additionalCuestionarios, ...cuestionarioCollection];
      jest.spyOn(cuestionarioService, 'addCuestionarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      expect(cuestionarioService.query).toHaveBeenCalled();
      expect(cuestionarioService.addCuestionarioToCollectionIfMissing).toHaveBeenCalledWith(
        cuestionarioCollection,
        ...additionalCuestionarios.map(expect.objectContaining),
      );
      expect(comp.cuestionariosSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const respuestaUsuario: IRespuestaUsuario = { id: 15546 };
      const usuario: IUsuario = { id: 544 };
      respuestaUsuario.usuario = usuario;
      const cuestionario: ICuestionario = { id: 24961 };
      respuestaUsuario.cuestionario = cuestionario;

      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      expect(comp.usuariosSharedCollection).toContainEqual(usuario);
      expect(comp.cuestionariosSharedCollection).toContainEqual(cuestionario);
      expect(comp.respuestaUsuario).toEqual(respuestaUsuario);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRespuestaUsuario>>();
      const respuestaUsuario = { id: 22901 };
      jest.spyOn(respuestaUsuarioFormService, 'getRespuestaUsuario').mockReturnValue(respuestaUsuario);
      jest.spyOn(respuestaUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: respuestaUsuario }));
      saveSubject.complete();

      // THEN
      expect(respuestaUsuarioFormService.getRespuestaUsuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(respuestaUsuarioService.update).toHaveBeenCalledWith(expect.objectContaining(respuestaUsuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRespuestaUsuario>>();
      const respuestaUsuario = { id: 22901 };
      jest.spyOn(respuestaUsuarioFormService, 'getRespuestaUsuario').mockReturnValue({ id: null });
      jest.spyOn(respuestaUsuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ respuestaUsuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: respuestaUsuario }));
      saveSubject.complete();

      // THEN
      expect(respuestaUsuarioFormService.getRespuestaUsuario).toHaveBeenCalled();
      expect(respuestaUsuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRespuestaUsuario>>();
      const respuestaUsuario = { id: 22901 };
      jest.spyOn(respuestaUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(respuestaUsuarioService.update).toHaveBeenCalled();
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
