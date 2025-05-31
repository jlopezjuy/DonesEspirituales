import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pregunta-don.test-samples';

import { PreguntaDonFormService } from './pregunta-don-form.service';

describe('PreguntaDon Form Service', () => {
  let service: PreguntaDonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PreguntaDonFormService);
  });

  describe('Service methods', () => {
    describe('createPreguntaDonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPreguntaDonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            peso: expect.any(Object),
            activa: expect.any(Object),
            pregunta: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });

      it('passing IPreguntaDon should create a new form with FormGroup', () => {
        const formGroup = service.createPreguntaDonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            peso: expect.any(Object),
            activa: expect.any(Object),
            pregunta: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });
    });

    describe('getPreguntaDon', () => {
      it('should return NewPreguntaDon for default PreguntaDon initial value', () => {
        const formGroup = service.createPreguntaDonFormGroup(sampleWithNewData);

        const preguntaDon = service.getPreguntaDon(formGroup) as any;

        expect(preguntaDon).toMatchObject(sampleWithNewData);
      });

      it('should return NewPreguntaDon for empty PreguntaDon initial value', () => {
        const formGroup = service.createPreguntaDonFormGroup();

        const preguntaDon = service.getPreguntaDon(formGroup) as any;

        expect(preguntaDon).toMatchObject({});
      });

      it('should return IPreguntaDon', () => {
        const formGroup = service.createPreguntaDonFormGroup(sampleWithRequiredData);

        const preguntaDon = service.getPreguntaDon(formGroup) as any;

        expect(preguntaDon).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPreguntaDon should not enable id FormControl', () => {
        const formGroup = service.createPreguntaDonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPreguntaDon should disable id FormControl', () => {
        const formGroup = service.createPreguntaDonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
