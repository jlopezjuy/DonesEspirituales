import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
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
  let userService: UserService;
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
    userService = TestBed.inject(UserService);
    cuestionarioService = TestBed.inject(CuestionarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call User query and add missing value', () => {
      const respuestaUsuario: IRespuestaUsuario = { id: 15546 };
      const user: IUser = { id: 3944 };
      respuestaUsuario.user = user;

      const userCollection: IUser[] = [{ id: 3944 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
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
      const user: IUser = { id: 3944 };
      respuestaUsuario.user = user;
      const cuestionario: ICuestionario = { id: 24961 };
      respuestaUsuario.cuestionario = cuestionario;

      activatedRoute.data = of({ respuestaUsuario });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContainEqual(user);
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
    describe('compareUser', () => {
      it('should forward to userService', () => {
        const entity = { id: 3944 };
        const entity2 = { id: 6275 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
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
