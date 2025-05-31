import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../detalle-respuesta.test-samples';

import { DetalleRespuestaFormService } from './detalle-respuesta-form.service';

describe('DetalleRespuesta Form Service', () => {
  let service: DetalleRespuestaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetalleRespuestaFormService);
  });

  describe('Service methods', () => {
    describe('createDetalleRespuestaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetalleRespuestaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorRespuesta: expect.any(Object),
            timestampRespuesta: expect.any(Object),
            tiempoPreguntaSegundos: expect.any(Object),
            escalaRespuesta: expect.any(Object),
            pregunta: expect.any(Object),
            respuestaUsuario: expect.any(Object),
          }),
        );
      });

      it('passing IDetalleRespuesta should create a new form with FormGroup', () => {
        const formGroup = service.createDetalleRespuestaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valorRespuesta: expect.any(Object),
            timestampRespuesta: expect.any(Object),
            tiempoPreguntaSegundos: expect.any(Object),
            escalaRespuesta: expect.any(Object),
            pregunta: expect.any(Object),
            respuestaUsuario: expect.any(Object),
          }),
        );
      });
    });

    describe('getDetalleRespuesta', () => {
      it('should return NewDetalleRespuesta for default DetalleRespuesta initial value', () => {
        const formGroup = service.createDetalleRespuestaFormGroup(sampleWithNewData);

        const detalleRespuesta = service.getDetalleRespuesta(formGroup) as any;

        expect(detalleRespuesta).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetalleRespuesta for empty DetalleRespuesta initial value', () => {
        const formGroup = service.createDetalleRespuestaFormGroup();

        const detalleRespuesta = service.getDetalleRespuesta(formGroup) as any;

        expect(detalleRespuesta).toMatchObject({});
      });

      it('should return IDetalleRespuesta', () => {
        const formGroup = service.createDetalleRespuestaFormGroup(sampleWithRequiredData);

        const detalleRespuesta = service.getDetalleRespuesta(formGroup) as any;

        expect(detalleRespuesta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetalleRespuesta should not enable id FormControl', () => {
        const formGroup = service.createDetalleRespuestaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetalleRespuesta should disable id FormControl', () => {
        const formGroup = service.createDetalleRespuestaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
