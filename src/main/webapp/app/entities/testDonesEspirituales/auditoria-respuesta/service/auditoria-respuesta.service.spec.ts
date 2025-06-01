import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../auditoria-respuesta.test-samples';

import { AuditoriaRespuestaService, RestAuditoriaRespuesta } from './auditoria-respuesta.service';

const requireRestSample: RestAuditoriaRespuesta = {
  ...sampleWithRequiredData,
  timestampCambio: sampleWithRequiredData.timestampCambio?.toJSON(),
};

describe('AuditoriaRespuesta Service', () => {
  let service: AuditoriaRespuestaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAuditoriaRespuesta | IAuditoriaRespuesta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AuditoriaRespuestaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AuditoriaRespuesta', () => {
      const auditoriaRespuesta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(auditoriaRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuditoriaRespuesta', () => {
      const auditoriaRespuesta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(auditoriaRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuditoriaRespuesta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuditoriaRespuesta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AuditoriaRespuesta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAuditoriaRespuestaToCollectionIfMissing', () => {
      it('should add a AuditoriaRespuesta to an empty array', () => {
        const auditoriaRespuesta: IAuditoriaRespuesta = sampleWithRequiredData;
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing([], auditoriaRespuesta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditoriaRespuesta);
      });

      it('should not add a AuditoriaRespuesta to an array that contains it', () => {
        const auditoriaRespuesta: IAuditoriaRespuesta = sampleWithRequiredData;
        const auditoriaRespuestaCollection: IAuditoriaRespuesta[] = [
          {
            ...auditoriaRespuesta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing(auditoriaRespuestaCollection, auditoriaRespuesta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuditoriaRespuesta to an array that doesn't contain it", () => {
        const auditoriaRespuesta: IAuditoriaRespuesta = sampleWithRequiredData;
        const auditoriaRespuestaCollection: IAuditoriaRespuesta[] = [sampleWithPartialData];
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing(auditoriaRespuestaCollection, auditoriaRespuesta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditoriaRespuesta);
      });

      it('should add only unique AuditoriaRespuesta to an array', () => {
        const auditoriaRespuestaArray: IAuditoriaRespuesta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const auditoriaRespuestaCollection: IAuditoriaRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing(auditoriaRespuestaCollection, ...auditoriaRespuestaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const auditoriaRespuesta: IAuditoriaRespuesta = sampleWithRequiredData;
        const auditoriaRespuesta2: IAuditoriaRespuesta = sampleWithPartialData;
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing([], auditoriaRespuesta, auditoriaRespuesta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(auditoriaRespuesta);
        expect(expectedResult).toContain(auditoriaRespuesta2);
      });

      it('should accept null and undefined values', () => {
        const auditoriaRespuesta: IAuditoriaRespuesta = sampleWithRequiredData;
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing([], null, auditoriaRespuesta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(auditoriaRespuesta);
      });

      it('should return initial array if no AuditoriaRespuesta is added', () => {
        const auditoriaRespuestaCollection: IAuditoriaRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addAuditoriaRespuestaToCollectionIfMissing(auditoriaRespuestaCollection, undefined, null);
        expect(expectedResult).toEqual(auditoriaRespuestaCollection);
      });
    });

    describe('compareAuditoriaRespuesta', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAuditoriaRespuesta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 28590 };
        const entity2 = null;

        const compareResult1 = service.compareAuditoriaRespuesta(entity1, entity2);
        const compareResult2 = service.compareAuditoriaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 28590 };
        const entity2 = { id: 18851 };

        const compareResult1 = service.compareAuditoriaRespuesta(entity1, entity2);
        const compareResult2 = service.compareAuditoriaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 28590 };
        const entity2 = { id: 28590 };

        const compareResult1 = service.compareAuditoriaRespuesta(entity1, entity2);
        const compareResult2 = service.compareAuditoriaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
