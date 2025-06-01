import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICuestionario } from '../cuestionario.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../cuestionario.test-samples';

import { CuestionarioService, RestCuestionario } from './cuestionario.service';

const requireRestSample: RestCuestionario = {
  ...sampleWithRequiredData,
  fechaCreacion: sampleWithRequiredData.fechaCreacion?.toJSON(),
  fechaActualizacion: sampleWithRequiredData.fechaActualizacion?.toJSON(),
};

describe('Cuestionario Service', () => {
  let service: CuestionarioService;
  let httpMock: HttpTestingController;
  let expectedResult: ICuestionario | ICuestionario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CuestionarioService);
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

    it('should create a Cuestionario', () => {
      const cuestionario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cuestionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cuestionario', () => {
      const cuestionario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cuestionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cuestionario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cuestionario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cuestionario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCuestionarioToCollectionIfMissing', () => {
      it('should add a Cuestionario to an empty array', () => {
        const cuestionario: ICuestionario = sampleWithRequiredData;
        expectedResult = service.addCuestionarioToCollectionIfMissing([], cuestionario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuestionario);
      });

      it('should not add a Cuestionario to an array that contains it', () => {
        const cuestionario: ICuestionario = sampleWithRequiredData;
        const cuestionarioCollection: ICuestionario[] = [
          {
            ...cuestionario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCuestionarioToCollectionIfMissing(cuestionarioCollection, cuestionario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cuestionario to an array that doesn't contain it", () => {
        const cuestionario: ICuestionario = sampleWithRequiredData;
        const cuestionarioCollection: ICuestionario[] = [sampleWithPartialData];
        expectedResult = service.addCuestionarioToCollectionIfMissing(cuestionarioCollection, cuestionario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuestionario);
      });

      it('should add only unique Cuestionario to an array', () => {
        const cuestionarioArray: ICuestionario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cuestionarioCollection: ICuestionario[] = [sampleWithRequiredData];
        expectedResult = service.addCuestionarioToCollectionIfMissing(cuestionarioCollection, ...cuestionarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cuestionario: ICuestionario = sampleWithRequiredData;
        const cuestionario2: ICuestionario = sampleWithPartialData;
        expectedResult = service.addCuestionarioToCollectionIfMissing([], cuestionario, cuestionario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuestionario);
        expect(expectedResult).toContain(cuestionario2);
      });

      it('should accept null and undefined values', () => {
        const cuestionario: ICuestionario = sampleWithRequiredData;
        expectedResult = service.addCuestionarioToCollectionIfMissing([], null, cuestionario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuestionario);
      });

      it('should return initial array if no Cuestionario is added', () => {
        const cuestionarioCollection: ICuestionario[] = [sampleWithRequiredData];
        expectedResult = service.addCuestionarioToCollectionIfMissing(cuestionarioCollection, undefined, null);
        expect(expectedResult).toEqual(cuestionarioCollection);
      });
    });

    describe('compareCuestionario', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCuestionario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 24961 };
        const entity2 = null;

        const compareResult1 = service.compareCuestionario(entity1, entity2);
        const compareResult2 = service.compareCuestionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 24961 };
        const entity2 = { id: 29113 };

        const compareResult1 = service.compareCuestionario(entity1, entity2);
        const compareResult2 = service.compareCuestionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 24961 };
        const entity2 = { id: 24961 };

        const compareResult1 = service.compareCuestionario(entity1, entity2);
        const compareResult2 = service.compareCuestionario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
