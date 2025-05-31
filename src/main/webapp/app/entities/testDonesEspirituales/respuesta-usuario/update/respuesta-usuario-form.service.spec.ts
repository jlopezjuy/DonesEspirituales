import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../respuesta-usuario.test-samples';

import { RespuestaUsuarioFormService } from './respuesta-usuario-form.service';

describe('RespuestaUsuario Form Service', () => {
  let service: RespuestaUsuarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RespuestaUsuarioFormService);
  });

  describe('Service methods', () => {
    describe('createRespuestaUsuarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaCompletado: expect.any(Object),
            estado: expect.any(Object),
            tiempoTotalSegundos: expect.any(Object),
            ipAddress: expect.any(Object),
            userAgent: expect.any(Object),
            usuario: expect.any(Object),
            cuestionario: expect.any(Object),
          }),
        );
      });

      it('passing IRespuestaUsuario should create a new form with FormGroup', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaCompletado: expect.any(Object),
            estado: expect.any(Object),
            tiempoTotalSegundos: expect.any(Object),
            ipAddress: expect.any(Object),
            userAgent: expect.any(Object),
            usuario: expect.any(Object),
            cuestionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getRespuestaUsuario', () => {
      it('should return NewRespuestaUsuario for default RespuestaUsuario initial value', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup(sampleWithNewData);

        const respuestaUsuario = service.getRespuestaUsuario(formGroup) as any;

        expect(respuestaUsuario).toMatchObject(sampleWithNewData);
      });

      it('should return NewRespuestaUsuario for empty RespuestaUsuario initial value', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup();

        const respuestaUsuario = service.getRespuestaUsuario(formGroup) as any;

        expect(respuestaUsuario).toMatchObject({});
      });

      it('should return IRespuestaUsuario', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup(sampleWithRequiredData);

        const respuestaUsuario = service.getRespuestaUsuario(formGroup) as any;

        expect(respuestaUsuario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRespuestaUsuario should not enable id FormControl', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRespuestaUsuario should disable id FormControl', () => {
        const formGroup = service.createRespuestaUsuarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
