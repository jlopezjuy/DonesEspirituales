import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IConfiguracionSistema } from '../configuracion-sistema.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../configuracion-sistema.test-samples';

import { ConfiguracionSistemaService, RestConfiguracionSistema } from './configuracion-sistema.service';

const requireRestSample: RestConfiguracionSistema = {
  ...sampleWithRequiredData,
  fechaActualizacion: sampleWithRequiredData.fechaActualizacion?.toJSON(),
};

describe('ConfiguracionSistema Service', () => {
  let service: ConfiguracionSistemaService;
  let httpMock: HttpTestingController;
  let expectedResult: IConfiguracionSistema | IConfiguracionSistema[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ConfiguracionSistemaService);
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

    it('should create a ConfiguracionSistema', () => {
      const configuracionSistema = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(configuracionSistema).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConfiguracionSistema', () => {
      const configuracionSistema = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(configuracionSistema).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConfiguracionSistema', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConfiguracionSistema', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ConfiguracionSistema', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addConfiguracionSistemaToCollectionIfMissing', () => {
      it('should add a ConfiguracionSistema to an empty array', () => {
        const configuracionSistema: IConfiguracionSistema = sampleWithRequiredData;
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing([], configuracionSistema);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configuracionSistema);
      });

      it('should not add a ConfiguracionSistema to an array that contains it', () => {
        const configuracionSistema: IConfiguracionSistema = sampleWithRequiredData;
        const configuracionSistemaCollection: IConfiguracionSistema[] = [
          {
            ...configuracionSistema,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing(configuracionSistemaCollection, configuracionSistema);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConfiguracionSistema to an array that doesn't contain it", () => {
        const configuracionSistema: IConfiguracionSistema = sampleWithRequiredData;
        const configuracionSistemaCollection: IConfiguracionSistema[] = [sampleWithPartialData];
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing(configuracionSistemaCollection, configuracionSistema);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configuracionSistema);
      });

      it('should add only unique ConfiguracionSistema to an array', () => {
        const configuracionSistemaArray: IConfiguracionSistema[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const configuracionSistemaCollection: IConfiguracionSistema[] = [sampleWithRequiredData];
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing(configuracionSistemaCollection, ...configuracionSistemaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const configuracionSistema: IConfiguracionSistema = sampleWithRequiredData;
        const configuracionSistema2: IConfiguracionSistema = sampleWithPartialData;
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing([], configuracionSistema, configuracionSistema2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(configuracionSistema);
        expect(expectedResult).toContain(configuracionSistema2);
      });

      it('should accept null and undefined values', () => {
        const configuracionSistema: IConfiguracionSistema = sampleWithRequiredData;
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing([], null, configuracionSistema, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(configuracionSistema);
      });

      it('should return initial array if no ConfiguracionSistema is added', () => {
        const configuracionSistemaCollection: IConfiguracionSistema[] = [sampleWithRequiredData];
        expectedResult = service.addConfiguracionSistemaToCollectionIfMissing(configuracionSistemaCollection, undefined, null);
        expect(expectedResult).toEqual(configuracionSistemaCollection);
      });
    });

    describe('compareConfiguracionSistema', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareConfiguracionSistema(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 12945 };
        const entity2 = null;

        const compareResult1 = service.compareConfiguracionSistema(entity1, entity2);
        const compareResult2 = service.compareConfiguracionSistema(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 12945 };
        const entity2 = { id: 32692 };

        const compareResult1 = service.compareConfiguracionSistema(entity1, entity2);
        const compareResult2 = service.compareConfiguracionSistema(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 12945 };
        const entity2 = { id: 12945 };

        const compareResult1 = service.compareConfiguracionSistema(entity1, entity2);
        const compareResult2 = service.compareConfiguracionSistema(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
