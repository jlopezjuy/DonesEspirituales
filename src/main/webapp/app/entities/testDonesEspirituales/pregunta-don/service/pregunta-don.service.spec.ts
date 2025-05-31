import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPreguntaDon } from '../pregunta-don.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pregunta-don.test-samples';

import { PreguntaDonService } from './pregunta-don.service';

const requireRestSample: IPreguntaDon = {
  ...sampleWithRequiredData,
};

describe('PreguntaDon Service', () => {
  let service: PreguntaDonService;
  let httpMock: HttpTestingController;
  let expectedResult: IPreguntaDon | IPreguntaDon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PreguntaDonService);
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

    it('should create a PreguntaDon', () => {
      const preguntaDon = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(preguntaDon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PreguntaDon', () => {
      const preguntaDon = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(preguntaDon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PreguntaDon', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PreguntaDon', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PreguntaDon', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPreguntaDonToCollectionIfMissing', () => {
      it('should add a PreguntaDon to an empty array', () => {
        const preguntaDon: IPreguntaDon = sampleWithRequiredData;
        expectedResult = service.addPreguntaDonToCollectionIfMissing([], preguntaDon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(preguntaDon);
      });

      it('should not add a PreguntaDon to an array that contains it', () => {
        const preguntaDon: IPreguntaDon = sampleWithRequiredData;
        const preguntaDonCollection: IPreguntaDon[] = [
          {
            ...preguntaDon,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPreguntaDonToCollectionIfMissing(preguntaDonCollection, preguntaDon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PreguntaDon to an array that doesn't contain it", () => {
        const preguntaDon: IPreguntaDon = sampleWithRequiredData;
        const preguntaDonCollection: IPreguntaDon[] = [sampleWithPartialData];
        expectedResult = service.addPreguntaDonToCollectionIfMissing(preguntaDonCollection, preguntaDon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(preguntaDon);
      });

      it('should add only unique PreguntaDon to an array', () => {
        const preguntaDonArray: IPreguntaDon[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const preguntaDonCollection: IPreguntaDon[] = [sampleWithRequiredData];
        expectedResult = service.addPreguntaDonToCollectionIfMissing(preguntaDonCollection, ...preguntaDonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const preguntaDon: IPreguntaDon = sampleWithRequiredData;
        const preguntaDon2: IPreguntaDon = sampleWithPartialData;
        expectedResult = service.addPreguntaDonToCollectionIfMissing([], preguntaDon, preguntaDon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(preguntaDon);
        expect(expectedResult).toContain(preguntaDon2);
      });

      it('should accept null and undefined values', () => {
        const preguntaDon: IPreguntaDon = sampleWithRequiredData;
        expectedResult = service.addPreguntaDonToCollectionIfMissing([], null, preguntaDon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(preguntaDon);
      });

      it('should return initial array if no PreguntaDon is added', () => {
        const preguntaDonCollection: IPreguntaDon[] = [sampleWithRequiredData];
        expectedResult = service.addPreguntaDonToCollectionIfMissing(preguntaDonCollection, undefined, null);
        expect(expectedResult).toEqual(preguntaDonCollection);
      });
    });

    describe('comparePreguntaDon', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePreguntaDon(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 25443 };
        const entity2 = null;

        const compareResult1 = service.comparePreguntaDon(entity1, entity2);
        const compareResult2 = service.comparePreguntaDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 25443 };
        const entity2 = { id: 2072 };

        const compareResult1 = service.comparePreguntaDon(entity1, entity2);
        const compareResult2 = service.comparePreguntaDon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 25443 };
        const entity2 = { id: 25443 };

        const compareResult1 = service.comparePreguntaDon(entity1, entity2);
        const compareResult2 = service.comparePreguntaDon(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
