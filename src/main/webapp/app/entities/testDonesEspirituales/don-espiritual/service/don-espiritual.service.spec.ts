import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDonEspiritual } from '../don-espiritual.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../don-espiritual.test-samples';

import { DonEspiritualService } from './don-espiritual.service';

const requireRestSample: IDonEspiritual = {
  ...sampleWithRequiredData,
};

describe('DonEspiritual Service', () => {
  let service: DonEspiritualService;
  let httpMock: HttpTestingController;
  let expectedResult: IDonEspiritual | IDonEspiritual[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DonEspiritualService);
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

    it('should create a DonEspiritual', () => {
      const donEspiritual = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(donEspiritual).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonEspiritual', () => {
      const donEspiritual = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(donEspiritual).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DonEspiritual', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DonEspiritual', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DonEspiritual', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDonEspiritualToCollectionIfMissing', () => {
      it('should add a DonEspiritual to an empty array', () => {
        const donEspiritual: IDonEspiritual = sampleWithRequiredData;
        expectedResult = service.addDonEspiritualToCollectionIfMissing([], donEspiritual);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donEspiritual);
      });

      it('should not add a DonEspiritual to an array that contains it', () => {
        const donEspiritual: IDonEspiritual = sampleWithRequiredData;
        const donEspiritualCollection: IDonEspiritual[] = [
          {
            ...donEspiritual,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDonEspiritualToCollectionIfMissing(donEspiritualCollection, donEspiritual);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonEspiritual to an array that doesn't contain it", () => {
        const donEspiritual: IDonEspiritual = sampleWithRequiredData;
        const donEspiritualCollection: IDonEspiritual[] = [sampleWithPartialData];
        expectedResult = service.addDonEspiritualToCollectionIfMissing(donEspiritualCollection, donEspiritual);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donEspiritual);
      });

      it('should add only unique DonEspiritual to an array', () => {
        const donEspiritualArray: IDonEspiritual[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const donEspiritualCollection: IDonEspiritual[] = [sampleWithRequiredData];
        expectedResult = service.addDonEspiritualToCollectionIfMissing(donEspiritualCollection, ...donEspiritualArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donEspiritual: IDonEspiritual = sampleWithRequiredData;
        const donEspiritual2: IDonEspiritual = sampleWithPartialData;
        expectedResult = service.addDonEspiritualToCollectionIfMissing([], donEspiritual, donEspiritual2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donEspiritual);
        expect(expectedResult).toContain(donEspiritual2);
      });

      it('should accept null and undefined values', () => {
        const donEspiritual: IDonEspiritual = sampleWithRequiredData;
        expectedResult = service.addDonEspiritualToCollectionIfMissing([], null, donEspiritual, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donEspiritual);
      });

      it('should return initial array if no DonEspiritual is added', () => {
        const donEspiritualCollection: IDonEspiritual[] = [sampleWithRequiredData];
        expectedResult = service.addDonEspiritualToCollectionIfMissing(donEspiritualCollection, undefined, null);
        expect(expectedResult).toEqual(donEspiritualCollection);
      });
    });

    describe('compareDonEspiritual', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDonEspiritual(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 2824 };
        const entity2 = null;

        const compareResult1 = service.compareDonEspiritual(entity1, entity2);
        const compareResult2 = service.compareDonEspiritual(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 2824 };
        const entity2 = { id: 697 };

        const compareResult1 = service.compareDonEspiritual(entity1, entity2);
        const compareResult2 = service.compareDonEspiritual(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 2824 };
        const entity2 = { id: 2824 };

        const compareResult1 = service.compareDonEspiritual(entity1, entity2);
        const compareResult2 = service.compareDonEspiritual(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
