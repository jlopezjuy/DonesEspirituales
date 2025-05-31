import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IInterpretacion } from 'app/entities/testDonesEspirituales/interpretacion/interpretacion.model';
import { InterpretacionService } from 'app/entities/testDonesEspirituales/interpretacion/service/interpretacion.service';
import { IRespuestaUsuario } from 'app/entities/testDonesEspirituales/respuesta-usuario/respuesta-usuario.model';
import { RespuestaUsuarioService } from 'app/entities/testDonesEspirituales/respuesta-usuario/service/respuesta-usuario.service';
import { IDonEspiritual } from 'app/entities/testDonesEspirituales/don-espiritual/don-espiritual.model';
import { DonEspiritualService } from 'app/entities/testDonesEspirituales/don-espiritual/service/don-espiritual.service';
import { IResultadoDon } from '../resultado-don.model';
import { ResultadoDonService } from '../service/resultado-don.service';
import { ResultadoDonFormService } from './resultado-don-form.service';

import { ResultadoDonUpdateComponent } from './resultado-don-update.component';

describe('ResultadoDon Management Update Component', () => {
  let comp: ResultadoDonUpdateComponent;
  let fixture: ComponentFixture<ResultadoDonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resultadoDonFormService: ResultadoDonFormService;
  let resultadoDonService: ResultadoDonService;
  let interpretacionService: InterpretacionService;
  let respuestaUsuarioService: RespuestaUsuarioService;
  let donEspiritualService: DonEspiritualService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ResultadoDonUpdateComponent],
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
      .overrideTemplate(ResultadoDonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResultadoDonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resultadoDonFormService = TestBed.inject(ResultadoDonFormService);
    resultadoDonService = TestBed.inject(ResultadoDonService);
    interpretacionService = TestBed.inject(InterpretacionService);
    respuestaUsuarioService = TestBed.inject(RespuestaUsuarioService);
    donEspiritualService = TestBed.inject(DonEspiritualService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Interpretacion query and add missing value', () => {
      const resultadoDon: IResultadoDon = { id: 32157 };
      const interpretacion: IInterpretacion = { id: 1334 };
      resultadoDon.interpretacion = interpretacion;

      const interpretacionCollection: IInterpretacion[] = [{ id: 1334 }];
      jest.spyOn(interpretacionService, 'query').mockReturnValue(of(new HttpResponse({ body: interpretacionCollection })));
      const additionalInterpretacions = [interpretacion];
      const expectedCollection: IInterpretacion[] = [...additionalInterpretacions, ...interpretacionCollection];
      jest.spyOn(interpretacionService, 'addInterpretacionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      expect(interpretacionService.query).toHaveBeenCalled();
      expect(interpretacionService.addInterpretacionToCollectionIfMissing).toHaveBeenCalledWith(
        interpretacionCollection,
        ...additionalInterpretacions.map(expect.objectContaining),
      );
      expect(comp.interpretacionsSharedCollection).toEqual(expectedCollection);
    });

    it('should call RespuestaUsuario query and add missing value', () => {
      const resultadoDon: IResultadoDon = { id: 32157 };
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      resultadoDon.respuestaUsuario = respuestaUsuario;

      const respuestaUsuarioCollection: IRespuestaUsuario[] = [{ id: 22901 }];
      jest.spyOn(respuestaUsuarioService, 'query').mockReturnValue(of(new HttpResponse({ body: respuestaUsuarioCollection })));
      const additionalRespuestaUsuarios = [respuestaUsuario];
      const expectedCollection: IRespuestaUsuario[] = [...additionalRespuestaUsuarios, ...respuestaUsuarioCollection];
      jest.spyOn(respuestaUsuarioService, 'addRespuestaUsuarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      expect(respuestaUsuarioService.query).toHaveBeenCalled();
      expect(respuestaUsuarioService.addRespuestaUsuarioToCollectionIfMissing).toHaveBeenCalledWith(
        respuestaUsuarioCollection,
        ...additionalRespuestaUsuarios.map(expect.objectContaining),
      );
      expect(comp.respuestaUsuariosSharedCollection).toEqual(expectedCollection);
    });

    it('should call DonEspiritual query and add missing value', () => {
      const resultadoDon: IResultadoDon = { id: 32157 };
      const donEspiritual: IDonEspiritual = { id: 2824 };
      resultadoDon.donEspiritual = donEspiritual;

      const donEspiritualCollection: IDonEspiritual[] = [{ id: 2824 }];
      jest.spyOn(donEspiritualService, 'query').mockReturnValue(of(new HttpResponse({ body: donEspiritualCollection })));
      const additionalDonEspirituals = [donEspiritual];
      const expectedCollection: IDonEspiritual[] = [...additionalDonEspirituals, ...donEspiritualCollection];
      jest.spyOn(donEspiritualService, 'addDonEspiritualToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      expect(donEspiritualService.query).toHaveBeenCalled();
      expect(donEspiritualService.addDonEspiritualToCollectionIfMissing).toHaveBeenCalledWith(
        donEspiritualCollection,
        ...additionalDonEspirituals.map(expect.objectContaining),
      );
      expect(comp.donEspiritualsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const resultadoDon: IResultadoDon = { id: 32157 };
      const interpretacion: IInterpretacion = { id: 1334 };
      resultadoDon.interpretacion = interpretacion;
      const respuestaUsuario: IRespuestaUsuario = { id: 22901 };
      resultadoDon.respuestaUsuario = respuestaUsuario;
      const donEspiritual: IDonEspiritual = { id: 2824 };
      resultadoDon.donEspiritual = donEspiritual;

      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      expect(comp.interpretacionsSharedCollection).toContainEqual(interpretacion);
      expect(comp.respuestaUsuariosSharedCollection).toContainEqual(respuestaUsuario);
      expect(comp.donEspiritualsSharedCollection).toContainEqual(donEspiritual);
      expect(comp.resultadoDon).toEqual(resultadoDon);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultadoDon>>();
      const resultadoDon = { id: 9168 };
      jest.spyOn(resultadoDonFormService, 'getResultadoDon').mockReturnValue(resultadoDon);
      jest.spyOn(resultadoDonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultadoDon }));
      saveSubject.complete();

      // THEN
      expect(resultadoDonFormService.getResultadoDon).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resultadoDonService.update).toHaveBeenCalledWith(expect.objectContaining(resultadoDon));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultadoDon>>();
      const resultadoDon = { id: 9168 };
      jest.spyOn(resultadoDonFormService, 'getResultadoDon').mockReturnValue({ id: null });
      jest.spyOn(resultadoDonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultadoDon: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resultadoDon }));
      saveSubject.complete();

      // THEN
      expect(resultadoDonFormService.getResultadoDon).toHaveBeenCalled();
      expect(resultadoDonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResultadoDon>>();
      const resultadoDon = { id: 9168 };
      jest.spyOn(resultadoDonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resultadoDon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resultadoDonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInterpretacion', () => {
      it('should forward to interpretacionService', () => {
        const entity = { id: 1334 };
        const entity2 = { id: 32306 };
        jest.spyOn(interpretacionService, 'compareInterpretacion');
        comp.compareInterpretacion(entity, entity2);
        expect(interpretacionService.compareInterpretacion).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDonEspiritual', () => {
      it('should forward to donEspiritualService', () => {
        const entity = { id: 2824 };
        const entity2 = { id: 697 };
        jest.spyOn(donEspiritualService, 'compareDonEspiritual');
        comp.compareDonEspiritual(entity, entity2);
        expect(donEspiritualService.compareDonEspiritual).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
