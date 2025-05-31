import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ConfiguracionSistemaService } from '../service/configuracion-sistema.service';
import { IConfiguracionSistema } from '../configuracion-sistema.model';
import { ConfiguracionSistemaFormService } from './configuracion-sistema-form.service';

import { ConfiguracionSistemaUpdateComponent } from './configuracion-sistema-update.component';

describe('ConfiguracionSistema Management Update Component', () => {
  let comp: ConfiguracionSistemaUpdateComponent;
  let fixture: ComponentFixture<ConfiguracionSistemaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let configuracionSistemaFormService: ConfiguracionSistemaFormService;
  let configuracionSistemaService: ConfiguracionSistemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ConfiguracionSistemaUpdateComponent],
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
      .overrideTemplate(ConfiguracionSistemaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConfiguracionSistemaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    configuracionSistemaFormService = TestBed.inject(ConfiguracionSistemaFormService);
    configuracionSistemaService = TestBed.inject(ConfiguracionSistemaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const configuracionSistema: IConfiguracionSistema = { id: 32692 };

      activatedRoute.data = of({ configuracionSistema });
      comp.ngOnInit();

      expect(comp.configuracionSistema).toEqual(configuracionSistema);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfiguracionSistema>>();
      const configuracionSistema = { id: 12945 };
      jest.spyOn(configuracionSistemaFormService, 'getConfiguracionSistema').mockReturnValue(configuracionSistema);
      jest.spyOn(configuracionSistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configuracionSistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configuracionSistema }));
      saveSubject.complete();

      // THEN
      expect(configuracionSistemaFormService.getConfiguracionSistema).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(configuracionSistemaService.update).toHaveBeenCalledWith(expect.objectContaining(configuracionSistema));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfiguracionSistema>>();
      const configuracionSistema = { id: 12945 };
      jest.spyOn(configuracionSistemaFormService, 'getConfiguracionSistema').mockReturnValue({ id: null });
      jest.spyOn(configuracionSistemaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configuracionSistema: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: configuracionSistema }));
      saveSubject.complete();

      // THEN
      expect(configuracionSistemaFormService.getConfiguracionSistema).toHaveBeenCalled();
      expect(configuracionSistemaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IConfiguracionSistema>>();
      const configuracionSistema = { id: 12945 };
      jest.spyOn(configuracionSistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ configuracionSistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(configuracionSistemaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
