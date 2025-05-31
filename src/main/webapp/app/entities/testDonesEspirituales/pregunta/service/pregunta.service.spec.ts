import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPregunta } from '../pregunta.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../pregunta.test-samples';

import { PreguntaService, RestPregunta } from './pregunta.service';

const requireRestSample: RestPregunta = {
  ...sampleWithRequiredData,
  fechaCreacion: sampleWithRequiredData.fechaCreacion?.toJSON(),
};

describe('Pregunta Service', () => {
  let service: PreguntaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPregunta | IPregunta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PreguntaService);
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

    it('should create a Pregunta', () => {
      const pregunta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pregunta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pregunta', () => {
      const pregunta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pregunta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pregunta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pregunta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pregunta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPreguntaToCollectionIfMissing', () => {
      it('should add a Pregunta to an empty array', () => {
        const pregunta: IPregunta = sampleWithRequiredData;
        expectedResult = service.addPreguntaToCollectionIfMissing([], pregunta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pregunta);
      });

      it('should not add a Pregunta to an array that contains it', () => {
        const pregunta: IPregunta = sampleWithRequiredData;
        const preguntaCollection: IPregunta[] = [
          {
            ...pregunta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPreguntaToCollectionIfMissing(preguntaCollection, pregunta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pregunta to an array that doesn't contain it", () => {
        const pregunta: IPregunta = sampleWithRequiredData;
        const preguntaCollection: IPregunta[] = [sampleWithPartialData];
        expectedResult = service.addPreguntaToCollectionIfMissing(preguntaCollection, pregunta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pregunta);
      });

      it('should add only unique Pregunta to an array', () => {
        const preguntaArray: IPregunta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const preguntaCollection: IPregunta[] = [sampleWithRequiredData];
        expectedResult = service.addPreguntaToCollectionIfMissing(preguntaCollection, ...preguntaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pregunta: IPregunta = sampleWithRequiredData;
        const pregunta2: IPregunta = sampleWithPartialData;
        expectedResult = service.addPreguntaToCollectionIfMissing([], pregunta, pregunta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pregunta);
        expect(expectedResult).toContain(pregunta2);
      });

      it('should accept null and undefined values', () => {
        const pregunta: IPregunta = sampleWithRequiredData;
        expectedResult = service.addPreguntaToCollectionIfMissing([], null, pregunta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pregunta);
      });

      it('should return initial array if no Pregunta is added', () => {
        const preguntaCollection: IPregunta[] = [sampleWithRequiredData];
        expectedResult = service.addPreguntaToCollectionIfMissing(preguntaCollection, undefined, null);
        expect(expectedResult).toEqual(preguntaCollection);
      });
    });

    describe('comparePregunta', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePregunta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 28174 };
        const entity2 = null;

        const compareResult1 = service.comparePregunta(entity1, entity2);
        const compareResult2 = service.comparePregunta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 28174 };
        const entity2 = { id: 11889 };

        const compareResult1 = service.comparePregunta(entity1, entity2);
        const compareResult2 = service.comparePregunta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 28174 };
        const entity2 = { id: 28174 };

        const compareResult1 = service.comparePregunta(entity1, entity2);
        const compareResult2 = service.comparePregunta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
