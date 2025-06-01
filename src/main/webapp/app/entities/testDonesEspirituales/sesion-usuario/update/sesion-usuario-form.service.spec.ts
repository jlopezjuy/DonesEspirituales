import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sesion-usuario.test-samples';

import { SesionUsuarioFormService } from './sesion-usuario-form.service';

describe('SesionUsuario Form Service', () => {
  let service: SesionUsuarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SesionUsuarioFormService);
  });

  describe('Service methods', () => {
    describe('createSesionUsuarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSesionUsuarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            respuestasTemporales: expect.any(Object),
            fechaCreacion: expect.any(Object),
            fechaExpiracion: expect.any(Object),
            completada: expect.any(Object),
            user: expect.any(Object),
            respuestaUsuario: expect.any(Object),
          }),
        );
      });

      it('passing ISesionUsuario should create a new form with FormGroup', () => {
        const formGroup = service.createSesionUsuarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            respuestasTemporales: expect.any(Object),
            fechaCreacion: expect.any(Object),
            fechaExpiracion: expect.any(Object),
            completada: expect.any(Object),
            user: expect.any(Object),
            respuestaUsuario: expect.any(Object),
          }),
        );
      });
    });

    describe('getSesionUsuario', () => {
      it('should return NewSesionUsuario for default SesionUsuario initial value', () => {
        const formGroup = service.createSesionUsuarioFormGroup(sampleWithNewData);

        const sesionUsuario = service.getSesionUsuario(formGroup) as any;

        expect(sesionUsuario).toMatchObject(sampleWithNewData);
      });

      it('should return NewSesionUsuario for empty SesionUsuario initial value', () => {
        const formGroup = service.createSesionUsuarioFormGroup();

        const sesionUsuario = service.getSesionUsuario(formGroup) as any;

        expect(sesionUsuario).toMatchObject({});
      });

      it('should return ISesionUsuario', () => {
        const formGroup = service.createSesionUsuarioFormGroup(sampleWithRequiredData);

        const sesionUsuario = service.getSesionUsuario(formGroup) as any;

        expect(sesionUsuario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISesionUsuario should not enable id FormControl', () => {
        const formGroup = service.createSesionUsuarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSesionUsuario should disable id FormControl', () => {
        const formGroup = service.createSesionUsuarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
