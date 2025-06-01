import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../interpretacion.test-samples';

import { InterpretacionFormService } from './interpretacion-form.service';

describe('Interpretacion Form Service', () => {
  let service: InterpretacionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterpretacionFormService);
  });

  describe('Service methods', () => {
    describe('createInterpretacionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInterpretacionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntuacionMinima: expect.any(Object),
            puntuacionMaxima: expect.any(Object),
            nivel: expect.any(Object),
            descripcionNivel: expect.any(Object),
            recomendaciones: expect.any(Object),
            areasServicio: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });

      it('passing IInterpretacion should create a new form with FormGroup', () => {
        const formGroup = service.createInterpretacionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntuacionMinima: expect.any(Object),
            puntuacionMaxima: expect.any(Object),
            nivel: expect.any(Object),
            descripcionNivel: expect.any(Object),
            recomendaciones: expect.any(Object),
            areasServicio: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });
    });

    describe('getInterpretacion', () => {
      it('should return NewInterpretacion for default Interpretacion initial value', () => {
        const formGroup = service.createInterpretacionFormGroup(sampleWithNewData);

        const interpretacion = service.getInterpretacion(formGroup) as any;

        expect(interpretacion).toMatchObject(sampleWithNewData);
      });

      it('should return NewInterpretacion for empty Interpretacion initial value', () => {
        const formGroup = service.createInterpretacionFormGroup();

        const interpretacion = service.getInterpretacion(formGroup) as any;

        expect(interpretacion).toMatchObject({});
      });

      it('should return IInterpretacion', () => {
        const formGroup = service.createInterpretacionFormGroup(sampleWithRequiredData);

        const interpretacion = service.getInterpretacion(formGroup) as any;

        expect(interpretacion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInterpretacion should not enable id FormControl', () => {
        const formGroup = service.createInterpretacionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInterpretacion should disable id FormControl', () => {
        const formGroup = service.createInterpretacionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
