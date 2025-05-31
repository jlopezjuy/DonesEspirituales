import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../cuestionario.test-samples';

import { CuestionarioFormService } from './cuestionario-form.service';

describe('Cuestionario Form Service', () => {
  let service: CuestionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuestionarioFormService);
  });

  describe('Service methods', () => {
    describe('createCuestionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCuestionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descripcion: expect.any(Object),
            instrucciones: expect.any(Object),
            totalPreguntas: expect.any(Object),
            activo: expect.any(Object),
            fechaCreacion: expect.any(Object),
            fechaActualizacion: expect.any(Object),
            version: expect.any(Object),
          }),
        );
      });

      it('passing ICuestionario should create a new form with FormGroup', () => {
        const formGroup = service.createCuestionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descripcion: expect.any(Object),
            instrucciones: expect.any(Object),
            totalPreguntas: expect.any(Object),
            activo: expect.any(Object),
            fechaCreacion: expect.any(Object),
            fechaActualizacion: expect.any(Object),
            version: expect.any(Object),
          }),
        );
      });
    });

    describe('getCuestionario', () => {
      it('should return NewCuestionario for default Cuestionario initial value', () => {
        const formGroup = service.createCuestionarioFormGroup(sampleWithNewData);

        const cuestionario = service.getCuestionario(formGroup) as any;

        expect(cuestionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewCuestionario for empty Cuestionario initial value', () => {
        const formGroup = service.createCuestionarioFormGroup();

        const cuestionario = service.getCuestionario(formGroup) as any;

        expect(cuestionario).toMatchObject({});
      });

      it('should return ICuestionario', () => {
        const formGroup = service.createCuestionarioFormGroup(sampleWithRequiredData);

        const cuestionario = service.getCuestionario(formGroup) as any;

        expect(cuestionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICuestionario should not enable id FormControl', () => {
        const formGroup = service.createCuestionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCuestionario should disable id FormControl', () => {
        const formGroup = service.createCuestionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
