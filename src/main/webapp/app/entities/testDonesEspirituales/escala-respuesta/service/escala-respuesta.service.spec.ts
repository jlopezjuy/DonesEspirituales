import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEscalaRespuesta } from '../escala-respuesta.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../escala-respuesta.test-samples';

import { EscalaRespuestaService } from './escala-respuesta.service';

const requireRestSample: IEscalaRespuesta = {
  ...sampleWithRequiredData,
};

describe('EscalaRespuesta Service', () => {
  let service: EscalaRespuestaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEscalaRespuesta | IEscalaRespuesta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EscalaRespuestaService);
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

    it('should create a EscalaRespuesta', () => {
      const escalaRespuesta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(escalaRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EscalaRespuesta', () => {
      const escalaRespuesta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(escalaRespuesta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EscalaRespuesta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EscalaRespuesta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EscalaRespuesta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEscalaRespuestaToCollectionIfMissing', () => {
      it('should add a EscalaRespuesta to an empty array', () => {
        const escalaRespuesta: IEscalaRespuesta = sampleWithRequiredData;
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing([], escalaRespuesta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escalaRespuesta);
      });

      it('should not add a EscalaRespuesta to an array that contains it', () => {
        const escalaRespuesta: IEscalaRespuesta = sampleWithRequiredData;
        const escalaRespuestaCollection: IEscalaRespuesta[] = [
          {
            ...escalaRespuesta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing(escalaRespuestaCollection, escalaRespuesta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EscalaRespuesta to an array that doesn't contain it", () => {
        const escalaRespuesta: IEscalaRespuesta = sampleWithRequiredData;
        const escalaRespuestaCollection: IEscalaRespuesta[] = [sampleWithPartialData];
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing(escalaRespuestaCollection, escalaRespuesta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escalaRespuesta);
      });

      it('should add only unique EscalaRespuesta to an array', () => {
        const escalaRespuestaArray: IEscalaRespuesta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const escalaRespuestaCollection: IEscalaRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing(escalaRespuestaCollection, ...escalaRespuestaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const escalaRespuesta: IEscalaRespuesta = sampleWithRequiredData;
        const escalaRespuesta2: IEscalaRespuesta = sampleWithPartialData;
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing([], escalaRespuesta, escalaRespuesta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(escalaRespuesta);
        expect(expectedResult).toContain(escalaRespuesta2);
      });

      it('should accept null and undefined values', () => {
        const escalaRespuesta: IEscalaRespuesta = sampleWithRequiredData;
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing([], null, escalaRespuesta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(escalaRespuesta);
      });

      it('should return initial array if no EscalaRespuesta is added', () => {
        const escalaRespuestaCollection: IEscalaRespuesta[] = [sampleWithRequiredData];
        expectedResult = service.addEscalaRespuestaToCollectionIfMissing(escalaRespuestaCollection, undefined, null);
        expect(expectedResult).toEqual(escalaRespuestaCollection);
      });
    });

    describe('compareEscalaRespuesta', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEscalaRespuesta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 12954 };
        const entity2 = null;

        const compareResult1 = service.compareEscalaRespuesta(entity1, entity2);
        const compareResult2 = service.compareEscalaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 12954 };
        const entity2 = { id: 18820 };

        const compareResult1 = service.compareEscalaRespuesta(entity1, entity2);
        const compareResult2 = service.compareEscalaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 12954 };
        const entity2 = { id: 12954 };

        const compareResult1 = service.compareEscalaRespuesta(entity1, entity2);
        const compareResult2 = service.compareEscalaRespuesta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
