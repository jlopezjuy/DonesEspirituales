import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../don-espiritual.test-samples';

import { DonEspiritualFormService } from './don-espiritual-form.service';

describe('DonEspiritual Form Service', () => {
  let service: DonEspiritualFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DonEspiritualFormService);
  });

  describe('Service methods', () => {
    describe('createDonEspiritualFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDonEspiritualFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            nombreCorto: expect.any(Object),
            descripcion: expect.any(Object),
            caracteristicas: expect.any(Object),
            versiculosBiblicos: expect.any(Object),
            activo: expect.any(Object),
            ordenPresentacion: expect.any(Object),
          }),
        );
      });

      it('passing IDonEspiritual should create a new form with FormGroup', () => {
        const formGroup = service.createDonEspiritualFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
            nombreCorto: expect.any(Object),
            descripcion: expect.any(Object),
            caracteristicas: expect.any(Object),
            versiculosBiblicos: expect.any(Object),
            activo: expect.any(Object),
            ordenPresentacion: expect.any(Object),
          }),
        );
      });
    });

    describe('getDonEspiritual', () => {
      it('should return NewDonEspiritual for default DonEspiritual initial value', () => {
        const formGroup = service.createDonEspiritualFormGroup(sampleWithNewData);

        const donEspiritual = service.getDonEspiritual(formGroup) as any;

        expect(donEspiritual).toMatchObject(sampleWithNewData);
      });

      it('should return NewDonEspiritual for empty DonEspiritual initial value', () => {
        const formGroup = service.createDonEspiritualFormGroup();

        const donEspiritual = service.getDonEspiritual(formGroup) as any;

        expect(donEspiritual).toMatchObject({});
      });

      it('should return IDonEspiritual', () => {
        const formGroup = service.createDonEspiritualFormGroup(sampleWithRequiredData);

        const donEspiritual = service.getDonEspiritual(formGroup) as any;

        expect(donEspiritual).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDonEspiritual should not enable id FormControl', () => {
        const formGroup = service.createDonEspiritualFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDonEspiritual should disable id FormControl', () => {
        const formGroup = service.createDonEspiritualFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
