import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../resultado-don.test-samples';

import { ResultadoDonFormService } from './resultado-don-form.service';

describe('ResultadoDon Form Service', () => {
  let service: ResultadoDonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResultadoDonFormService);
  });

  describe('Service methods', () => {
    describe('createResultadoDonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResultadoDonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntuacionTotal: expect.any(Object),
            porcentaje: expect.any(Object),
            rankingPosicion: expect.any(Object),
            esDonPrincipal: expect.any(Object),
            interpretacion: expect.any(Object),
            respuestaUsuario: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });

      it('passing IResultadoDon should create a new form with FormGroup', () => {
        const formGroup = service.createResultadoDonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntuacionTotal: expect.any(Object),
            porcentaje: expect.any(Object),
            rankingPosicion: expect.any(Object),
            esDonPrincipal: expect.any(Object),
            interpretacion: expect.any(Object),
            respuestaUsuario: expect.any(Object),
            donEspiritual: expect.any(Object),
          }),
        );
      });
    });

    describe('getResultadoDon', () => {
      it('should return NewResultadoDon for default ResultadoDon initial value', () => {
        const formGroup = service.createResultadoDonFormGroup(sampleWithNewData);

        const resultadoDon = service.getResultadoDon(formGroup) as any;

        expect(resultadoDon).toMatchObject(sampleWithNewData);
      });

      it('should return NewResultadoDon for empty ResultadoDon initial value', () => {
        const formGroup = service.createResultadoDonFormGroup();

        const resultadoDon = service.getResultadoDon(formGroup) as any;

        expect(resultadoDon).toMatchObject({});
      });

      it('should return IResultadoDon', () => {
        const formGroup = service.createResultadoDonFormGroup(sampleWithRequiredData);

        const resultadoDon = service.getResultadoDon(formGroup) as any;

        expect(resultadoDon).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResultadoDon should not enable id FormControl', () => {
        const formGroup = service.createResultadoDonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResultadoDon should disable id FormControl', () => {
        const formGroup = service.createResultadoDonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
