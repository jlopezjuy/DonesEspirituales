import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../auditoria-respuesta.test-samples';

import { AuditoriaRespuestaFormService } from './auditoria-respuesta-form.service';

describe('AuditoriaRespuesta Form Service', () => {
  let service: AuditoriaRespuestaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuditoriaRespuestaFormService);
  });

  describe('Service methods', () => {
    describe('createAuditoriaRespuestaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorAnterior: expect.any(Object),
            valorNuevo: expect.any(Object),
            timestampCambio: expect.any(Object),
            motivoCambio: expect.any(Object),
            respuestaUsuario: expect.any(Object),
            detalleRespuesta: expect.any(Object),
          }),
        );
      });

      it('passing IAuditoriaRespuesta should create a new form with FormGroup', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorAnterior: expect.any(Object),
            valorNuevo: expect.any(Object),
            timestampCambio: expect.any(Object),
            motivoCambio: expect.any(Object),
            respuestaUsuario: expect.any(Object),
            detalleRespuesta: expect.any(Object),
          }),
        );
      });
    });

    describe('getAuditoriaRespuesta', () => {
      it('should return NewAuditoriaRespuesta for default AuditoriaRespuesta initial value', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup(sampleWithNewData);

        const auditoriaRespuesta = service.getAuditoriaRespuesta(formGroup) as any;

        expect(auditoriaRespuesta).toMatchObject(sampleWithNewData);
      });

      it('should return NewAuditoriaRespuesta for empty AuditoriaRespuesta initial value', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup();

        const auditoriaRespuesta = service.getAuditoriaRespuesta(formGroup) as any;

        expect(auditoriaRespuesta).toMatchObject({});
      });

      it('should return IAuditoriaRespuesta', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup(sampleWithRequiredData);

        const auditoriaRespuesta = service.getAuditoriaRespuesta(formGroup) as any;

        expect(auditoriaRespuesta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAuditoriaRespuesta should not enable id FormControl', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAuditoriaRespuesta should disable id FormControl', () => {
        const formGroup = service.createAuditoriaRespuestaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
