import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISesionUsuario } from '../sesion-usuario.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../sesion-usuario.test-samples';

import { RestSesionUsuario, SesionUsuarioService } from './sesion-usuario.service';

const requireRestSample: RestSesionUsuario = {
  ...sampleWithRequiredData,
  fechaCreacion: sampleWithRequiredData.fechaCreacion?.toJSON(),
  fechaExpiracion: sampleWithRequiredData.fechaExpiracion?.toJSON(),
};

describe('SesionUsuario Service', () => {
  let service: SesionUsuarioService;
  let httpMock: HttpTestingController;
  let expectedResult: ISesionUsuario | ISesionUsuario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SesionUsuarioService);
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

    it('should create a SesionUsuario', () => {
      const sesionUsuario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sesionUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SesionUsuario', () => {
      const sesionUsuario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sesionUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SesionUsuario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SesionUsuario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SesionUsuario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSesionUsuarioToCollectionIfMissing', () => {
      it('should add a SesionUsuario to an empty array', () => {
        const sesionUsuario: ISesionUsuario = sampleWithRequiredData;
        expectedResult = service.addSesionUsuarioToCollectionIfMissing([], sesionUsuario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sesionUsuario);
      });

      it('should not add a SesionUsuario to an array that contains it', () => {
        const sesionUsuario: ISesionUsuario = sampleWithRequiredData;
        const sesionUsuarioCollection: ISesionUsuario[] = [
          {
            ...sesionUsuario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSesionUsuarioToCollectionIfMissing(sesionUsuarioCollection, sesionUsuario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SesionUsuario to an array that doesn't contain it", () => {
        const sesionUsuario: ISesionUsuario = sampleWithRequiredData;
        const sesionUsuarioCollection: ISesionUsuario[] = [sampleWithPartialData];
        expectedResult = service.addSesionUsuarioToCollectionIfMissing(sesionUsuarioCollection, sesionUsuario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sesionUsuario);
      });

      it('should add only unique SesionUsuario to an array', () => {
        const sesionUsuarioArray: ISesionUsuario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sesionUsuarioCollection: ISesionUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addSesionUsuarioToCollectionIfMissing(sesionUsuarioCollection, ...sesionUsuarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sesionUsuario: ISesionUsuario = sampleWithRequiredData;
        const sesionUsuario2: ISesionUsuario = sampleWithPartialData;
        expectedResult = service.addSesionUsuarioToCollectionIfMissing([], sesionUsuario, sesionUsuario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sesionUsuario);
        expect(expectedResult).toContain(sesionUsuario2);
      });

      it('should accept null and undefined values', () => {
        const sesionUsuario: ISesionUsuario = sampleWithRequiredData;
        expectedResult = service.addSesionUsuarioToCollectionIfMissing([], null, sesionUsuario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sesionUsuario);
      });

      it('should return initial array if no SesionUsuario is added', () => {
        const sesionUsuarioCollection: ISesionUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addSesionUsuarioToCollectionIfMissing(sesionUsuarioCollection, undefined, null);
        expect(expectedResult).toEqual(sesionUsuarioCollection);
      });
    });

    describe('compareSesionUsuario', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSesionUsuario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 5645 };
        const entity2 = null;

        const compareResult1 = service.compareSesionUsuario(entity1, entity2);
        const compareResult2 = service.compareSesionUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 5645 };
        const entity2 = { id: 18656 };

        const compareResult1 = service.compareSesionUsuario(entity1, entity2);
        const compareResult2 = service.compareSesionUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 5645 };
        const entity2 = { id: 5645 };

        const compareResult1 = service.compareSesionUsuario(entity1, entity2);
        const compareResult2 = service.compareSesionUsuario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
