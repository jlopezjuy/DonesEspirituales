import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
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
  let userService: UserService;
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
    userService = TestBed.inject(UserService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call User query and add missing value', () => {
      const sesionUsuario: ISesionUsuario = { id: 18656 };
      const user: IUser = { id: 3944 };
      sesionUsuario.user = user;

      const userCollection: IUser[] = [{ id: 3944 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining),
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
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
      const user: IUser = { id: 3944 };
      sesionUsuario.user = user;
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      sesionUsuario.respuestaUsuario = respuestaUsuario;

      activatedRoute.data = of({ sesionUsuario });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContainEqual(user);
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
    describe('compareUser', () => {
      it('should forward to userService', () => {
        const entity = { id: 3944 };
        const entity2 = { id: 6275 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
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
