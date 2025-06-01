import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDetalleRespuesta } from '../detalle-respuesta.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../detalle-respuesta.test-samples';

import { DetalleRespuestaService, RestDetalleRespuesta } from './detalle-respuesta.service';

const requireRestSample: RestDetalleRespuesta = {
  ...sampleWithRequiredData,
  timestampRespuesta: sampleWithRequiredData.timestampRespuesta?.toJSON(),
};

describe('DetalleRespuesta Service', () => {
  let service: DetalleRespuestaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetalleRespuesta | IDetalleRespuesta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DetalleRespuestaService);
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

    it('should create a DetalleRespuesta', () => {
      const detalleRespuesta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detalleRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetalleRespuesta', () => {
      const detalleRespuesta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detalleRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetalleRespuesta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetalleRespuesta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetalleRespuesta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetalleRespuestaToCollectionIfMissing', () => {
      it('should add a DetalleRespuesta to an empty array', () => {
        const detalleRespuesta: IDetalleRespuesta = sampleWithRequiredData;
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing([], detalleRespuesta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleRespuesta);
      });

      it('should not add a DetalleRespuesta to an array that contains it', () => {
        const detalleRespuesta: IDetalleRespuesta = sampleWithRequiredData;
        const detalleRespuestaCollection: IDetalleRespuesta[] = [
          {
            ...detalleRespuesta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing(detalleRespuestaCollection, detalleRespuesta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetalleRespuesta to an array that doesn't contain it", () => {
        const detalleRespuesta: IDetalleRespuesta = sampleWithRequiredData;
        const detalleRespuestaCollection: IDetalleRespuesta[] = [sampleWithPartialData];
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing(detalleRespuestaCollection, detalleRespuesta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleRespuesta);
      });

      it('should add only unique DetalleRespuesta to an array', () => {
        const detalleRespuestaArray: IDetalleRespuesta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detalleRespuestaCollection: IDetalleRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing(detalleRespuestaCollection, ...detalleRespuestaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detalleRespuesta: IDetalleRespuesta = sampleWithRequiredData;
        const detalleRespuesta2: IDetalleRespuesta = sampleWithPartialData;
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing([], detalleRespuesta, detalleRespuesta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalleRespuesta);
        expect(expectedResult).toContain(detalleRespuesta2);
      });

      it('should accept null and undefined values', () => {
        const detalleRespuesta: IDetalleRespuesta = sampleWithRequiredData;
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing([], null, detalleRespuesta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalleRespuesta);
      });

      it('should return initial array if no DetalleRespuesta is added', () => {
        const detalleRespuestaCollection: IDetalleRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addDetalleRespuestaToCollectionIfMissing(detalleRespuestaCollection, undefined, null);
        expect(expectedResult).toEqual(detalleRespuestaCollection);
      });
    });

    describe('compareDetalleRespuesta', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetalleRespuesta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18708 };
        const entity2 = null;

        const compareResult1 = service.compareDetalleRespuesta(entity1, entity2);
        const compareResult2 = service.compareDetalleRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18708 };
        const entity2 = { id: 2437 };

        const compareResult1 = service.compareDetalleRespuesta(entity1, entity2);
        const compareResult2 = service.compareDetalleRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18708 };
        const entity2 = { id: 18708 };

        const compareResult1 = service.compareDetalleRespuesta(entity1, entity2);
        const compareResult2 = service.compareDetalleRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
