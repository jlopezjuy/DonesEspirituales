import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pregunta.test-samples';

import { PreguntaFormService } from './pregunta-form.service';

describe('Pregunta Form Service', () => {
  let service: PreguntaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PreguntaFormService);
  });

  describe('Service methods', () => {
    describe('createPreguntaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPreguntaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroPregunta: expect.any(Object),
            textoPregunta: expect.any(Object),
            obligatoria: expect.any(Object),
            activa: expect.any(Object),
            fechaCreacion: expect.any(Object),
            cuestionario: expect.any(Object),
          }),
        );
      });

      it('passing IPregunta should create a new form with FormGroup', () => {
        const formGroup = service.createPreguntaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroPregunta: expect.any(Object),
            textoPregunta: expect.any(Object),
            obligatoria: expect.any(Object),
            activa: expect.any(Object),
            fechaCreacion: expect.any(Object),
            cuestionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getPregunta', () => {
      it('should return NewPregunta for default Pregunta initial value', () => {
        const formGroup = service.createPreguntaFormGroup(sampleWithNewData);

        const pregunta = service.getPregunta(formGroup) as any;

        expect(pregunta).toMatchObject(sampleWithNewData);
      });

      it('should return NewPregunta for empty Pregunta initial value', () => {
        const formGroup = service.createPreguntaFormGroup();

        const pregunta = service.getPregunta(formGroup) as any;

        expect(pregunta).toMatchObject({});
      });

      it('should return IPregunta', () => {
        const formGroup = service.createPreguntaFormGroup(sampleWithRequiredData);

        const pregunta = service.getPregunta(formGroup) as any;

        expect(pregunta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPregunta should not enable id FormControl', () => {
        const formGroup = service.createPreguntaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPregunta should disable id FormControl', () => {
        const formGroup = service.createPreguntaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
