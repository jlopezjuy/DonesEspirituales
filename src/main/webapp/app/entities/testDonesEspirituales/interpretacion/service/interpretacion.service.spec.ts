import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IInterpretacion } from '../interpretacion.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../interpretacion.test-samples';

import { InterpretacionService } from './interpretacion.service';

const requireRestSample: IInterpretacion = {
  ...sampleWithRequiredData,
};

describe('Interpretacion Service', () => {
  let service: InterpretacionService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterpretacion | IInterpretacion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(InterpretacionService);
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

    it('should create a Interpretacion', () => {
      const interpretacion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interpretacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Interpretacion', () => {
      const interpretacion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interpretacion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Interpretacion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Interpretacion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Interpretacion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterpretacionToCollectionIfMissing', () => {
      it('should add a Interpretacion to an empty array', () => {
        const interpretacion: IInterpretacion = sampleWithRequiredData;
        expectedResult = service.addInterpretacionToCollectionIfMissing([], interpretacion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interpretacion);
      });

      it('should not add a Interpretacion to an array that contains it', () => {
        const interpretacion: IInterpretacion = sampleWithRequiredData;
        const interpretacionCollection: IInterpretacion[] = [
          {
            ...interpretacion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterpretacionToCollectionIfMissing(interpretacionCollection, interpretacion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Interpretacion to an array that doesn't contain it", () => {
        const interpretacion: IInterpretacion = sampleWithRequiredData;
        const interpretacionCollection: IInterpretacion[] = [sampleWithPartialData];
        expectedResult = service.addInterpretacionToCollectionIfMissing(interpretacionCollection, interpretacion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interpretacion);
      });

      it('should add only unique Interpretacion to an array', () => {
        const interpretacionArray: IInterpretacion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interpretacionCollection: IInterpretacion[] = [sampleWithRequiredData];
        expectedResult = service.addInterpretacionToCollectionIfMissing(interpretacionCollection, ...interpretacionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interpretacion: IInterpretacion = sampleWithRequiredData;
        const interpretacion2: IInterpretacion = sampleWithPartialData;
        expectedResult = service.addInterpretacionToCollectionIfMissing([], interpretacion, interpretacion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interpretacion);
        expect(expectedResult).toContain(interpretacion2);
      });

      it('should accept null and undefined values', () => {
        const interpretacion: IInterpretacion = sampleWithRequiredData;
        expectedResult = service.addInterpretacionToCollectionIfMissing([], null, interpretacion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interpretacion);
      });

      it('should return initial array if no Interpretacion is added', () => {
        const interpretacionCollection: IInterpretacion[] = [sampleWithRequiredData];
        expectedResult = service.addInterpretacionToCollectionIfMissing(interpretacionCollection, undefined, null);
        expect(expectedResult).toEqual(interpretacionCollection);
      });
    });

    describe('compareInterpretacion', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterpretacion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 1334 };
        const entity2 = null;

        const compareResult1 = service.compareInterpretacion(entity1, entity2);
        const compareResult2 = service.compareInterpretacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 1334 };
        const entity2 = { id: 32306 };

        const compareResult1 = service.compareInterpretacion(entity1, entity2);
        const compareResult2 = service.compareInterpretacion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 1334 };
        const entity2 = { id: 1334 };

        const compareResult1 = service.compareInterpretacion(entity1, entity2);
        const compareResult2 = service.compareInterpretacion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
