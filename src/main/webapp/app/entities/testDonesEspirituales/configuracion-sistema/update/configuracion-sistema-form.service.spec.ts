import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../configuracion-sistema.test-samples';

import { ConfiguracionSistemaFormService } from './configuracion-sistema-form.service';

describe('ConfiguracionSistema Form Service', () => {
  let service: ConfiguracionSistemaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfiguracionSistemaFormService);
  });

  describe('Service methods', () => {
    describe('createConfiguracionSistemaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            clave: expect.any(Object),
            valor: expect.any(Object),
            descripcion: expect.any(Object),
            tipoDato: expect.any(Object),
            fechaActualizacion: expect.any(Object),
          }),
        );
      });

      it('passing IConfiguracionSistema should create a new form with FormGroup', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            clave: expect.any(Object),
            valor: expect.any(Object),
            descripcion: expect.any(Object),
            tipoDato: expect.any(Object),
            fechaActualizacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getConfiguracionSistema', () => {
      it('should return NewConfiguracionSistema for default ConfiguracionSistema initial value', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup(sampleWithNewData);

        const configuracionSistema = service.getConfiguracionSistema(formGroup) as any;

        expect(configuracionSistema).toMatchObject(sampleWithNewData);
      });

      it('should return NewConfiguracionSistema for empty ConfiguracionSistema initial value', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup();

        const configuracionSistema = service.getConfiguracionSistema(formGroup) as any;

        expect(configuracionSistema).toMatchObject({});
      });

      it('should return IConfiguracionSistema', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup(sampleWithRequiredData);

        const configuracionSistema = service.getConfiguracionSistema(formGroup) as any;

        expect(configuracionSistema).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IConfiguracionSistema should not enable id FormControl', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewConfiguracionSistema should disable id FormControl', () => {
        const formGroup = service.createConfiguracionSistemaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
