import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IResultadoDon } from '../resultado-don.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../resultado-don.test-samples';

import { ResultadoDonService } from './resultado-don.service';

const requireRestSample: IResultadoDon = {
  ...sampleWithRequiredData,
};

describe('ResultadoDon Service', () => {
  let service: ResultadoDonService;
  let httpMock: HttpTestingController;
  let expectedResult: IResultadoDon | IResultadoDon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ResultadoDonService);
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

    it('should create a ResultadoDon', () => {
      const resultadoDon = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(resultadoDon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResultadoDon', () => {
      const resultadoDon = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(resultadoDon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResultadoDon', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResultadoDon', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResultadoDon', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResultadoDonToCollectionIfMissing', () => {
      it('should add a ResultadoDon to an empty array', () => {
        const resultadoDon: IResultadoDon = sampleWithRequiredData;
        expectedResult = service.addResultadoDonToCollectionIfMissing([], resultadoDon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultadoDon);
      });

      it('should not add a ResultadoDon to an array that contains it', () => {
        const resultadoDon: IResultadoDon = sampleWithRequiredData;
        const resultadoDonCollection: IResultadoDon[] = [
          {
            ...resultadoDon,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResultadoDonToCollectionIfMissing(resultadoDonCollection, resultadoDon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResultadoDon to an array that doesn't contain it", () => {
        const resultadoDon: IResultadoDon = sampleWithRequiredData;
        const resultadoDonCollection: IResultadoDon[] = [sampleWithPartialData];
        expectedResult = service.addResultadoDonToCollectionIfMissing(resultadoDonCollection, resultadoDon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultadoDon);
      });

      it('should add only unique ResultadoDon to an array', () => {
        const resultadoDonArray: IResultadoDon[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const resultadoDonCollection: IResultadoDon[] = [sampleWithRequiredData];
        expectedResult = service.addResultadoDonToCollectionIfMissing(resultadoDonCollection, ...resultadoDonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const resultadoDon: IResultadoDon = sampleWithRequiredData;
        const resultadoDon2: IResultadoDon = sampleWithPartialData;
        expectedResult = service.addResultadoDonToCollectionIfMissing([], resultadoDon, resultadoDon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(resultadoDon);
        expect(expectedResult).toContain(resultadoDon2);
      });

      it('should accept null and undefined values', () => {
        const resultadoDon: IResultadoDon = sampleWithRequiredData;
        expectedResult = service.addResultadoDonToCollectionIfMissing([], null, resultadoDon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(resultadoDon);
      });

      it('should return initial array if no ResultadoDon is added', () => {
        const resultadoDonCollection: IResultadoDon[] = [sampleWithRequiredData];
        expectedResult = service.addResultadoDonToCollectionIfMissing(resultadoDonCollection, undefined, null);
        expect(expectedResult).toEqual(resultadoDonCollection);
      });
    });

    describe('compareResultadoDon', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResultadoDon(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 9168 };
        const entity2 = null;

        const compareResult1 = service.compareResultadoDon(entity1, entity2);
        const compareResult2 = service.compareResultadoDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 9168 };
        const entity2 = { id: 32157 };

        const compareResult1 = service.compareResultadoDon(entity1, entity2);
        const compareResult2 = service.compareResultadoDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 9168 };
        const entity2 = { id: 9168 };

        const compareResult1 = service.compareResultadoDon(entity1, entity2);
        const compareResult2 = service.compareResultadoDon(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
