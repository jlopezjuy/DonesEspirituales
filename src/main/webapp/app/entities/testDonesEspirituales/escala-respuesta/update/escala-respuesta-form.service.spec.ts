import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../escala-respuesta.test-samples';

import { EscalaRespuestaFormService } from './escala-respuesta-form.service';

describe('EscalaRespuesta Form Service', () => {
  let service: EscalaRespuestaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EscalaRespuestaFormService);
  });

  describe('Service methods', () => {
    describe('createEscalaRespuestaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEscalaRespuestaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            etiqueta: expect.any(Object),
            descripcion: expect.any(Object),
            orden: expect.any(Object),
          }),
        );
      });

      it('passing IEscalaRespuesta should create a new form with FormGroup', () => {
        const formGroup = service.createEscalaRespuestaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            etiqueta: expect.any(Object),
            descripcion: expect.any(Object),
            orden: expect.any(Object),
          }),
        );
      });
    });

    describe('getEscalaRespuesta', () => {
      it('should return NewEscalaRespuesta for default EscalaRespuesta initial value', () => {
        const formGroup = service.createEscalaRespuestaFormGroup(sampleWithNewData);

        const escalaRespuesta = service.getEscalaRespuesta(formGroup) as any;

        expect(escalaRespuesta).toMatchObject(sampleWithNewData);
      });

      it('should return NewEscalaRespuesta for empty EscalaRespuesta initial value', () => {
        const formGroup = service.createEscalaRespuestaFormGroup();

        const escalaRespuesta = service.getEscalaRespuesta(formGroup) as any;

        expect(escalaRespuesta).toMatchObject({});
      });

      it('should return IEscalaRespuesta', () => {
        const formGroup = service.createEscalaRespuestaFormGroup(sampleWithRequiredData);

        const escalaRespuesta = service.getEscalaRespuesta(formGroup) as any;

        expect(escalaRespuesta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEscalaRespuesta should not enable id FormControl', () => {
        const formGroup = service.createEscalaRespuestaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEscalaRespuesta should disable id FormControl', () => {
        const formGroup = service.createEscalaRespuestaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
