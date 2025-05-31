import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRespuestaUsuario } from '../respuesta-usuario.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../respuesta-usuario.test-samples';

import { RespuestaUsuarioService, RestRespuestaUsuario } from './respuesta-usuario.service';

const requireRestSample: RestRespuestaUsuario = {
  ...sampleWithRequiredData,
  fechaInicio: sampleWithRequiredData.fechaInicio?.toJSON(),
  fechaCompletado: sampleWithRequiredData.fechaCompletado?.toJSON(),
};

describe('RespuestaUsuario Service', () => {
  let service: RespuestaUsuarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IRespuestaUsuario | IRespuestaUsuario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RespuestaUsuarioService);
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

    it('should create a RespuestaUsuario', () => {
      const respuestaUsuario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(respuestaUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RespuestaUsuario', () => {
      const respuestaUsuario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(respuestaUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RespuestaUsuario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RespuestaUsuario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RespuestaUsuario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRespuestaUsuarioToCollectionIfMissing', () => {
      it('should add a RespuestaUsuario to an empty array', () => {
        const respuestaUsuario: IRespuestaUsuario = sampleWithRequiredData;
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing([], respuestaUsuario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(respuestaUsuario);
      });

      it('should not add a RespuestaUsuario to an array that contains it', () => {
        const respuestaUsuario: IRespuestaUsuario = sampleWithRequiredData;
        const respuestaUsuarioCollection: IRespuestaUsuario[] = [
          {
            ...respuestaUsuario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing(respuestaUsuarioCollection, respuestaUsuario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RespuestaUsuario to an array that doesn't contain it", () => {
        const respuestaUsuario: IRespuestaUsuario = sampleWithRequiredData;
        const respuestaUsuarioCollection: IRespuestaUsuario[] = [sampleWithPartialData];
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing(respuestaUsuarioCollection, respuestaUsuario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(respuestaUsuario);
      });

      it('should add only unique RespuestaUsuario to an array', () => {
        const respuestaUsuarioArray: IRespuestaUsuario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const respuestaUsuarioCollection: IRespuestaUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing(respuestaUsuarioCollection, ...respuestaUsuarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const respuestaUsuario: IRespuestaUsuario = sampleWithRequiredData;
        const respuestaUsuario2: IRespuestaUsuario = sampleWithPartialData;
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing([], respuestaUsuario, respuestaUsuario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(respuestaUsuario);
        expect(expectedResult).toContain(respuestaUsuario2);
      });

      it('should accept null and undefined values', () => {
        const respuestaUsuario: IRespuestaUsuario = sampleWithRequiredData;
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing([], null, respuestaUsuario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(respuestaUsuario);
      });

      it('should return initial array if no RespuestaUsuario is added', () => {
        const respuestaUsuarioCollection: IRespuestaUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addRespuestaUsuarioToCollectionIfMissing(respuestaUsuarioCollection, undefined, null);
        expect(expectedResult).toEqual(respuestaUsuarioCollection);
      });
    });

    describe('compareRespuestaUsuario', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRespuestaUsuario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 22901 };
        const entity2 = null;

        const compareResult1 = service.compareRespuestaUsuario(entity1, entity2);
        const compareResult2 = service.compareRespuestaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 22901 };
        const entity2 = { id: 15546 };

        const compareResult1 = service.compareRespuestaUsuario(entity1, entity2);
        const compareResult2 = service.compareRespuestaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 22901 };
        const entity2 = { id: 22901 };

        const compareResult1 = service.compareRespuestaUsuario(entity1, entity2);
        const compareResult2 = service.compareRespuestaUsuario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
