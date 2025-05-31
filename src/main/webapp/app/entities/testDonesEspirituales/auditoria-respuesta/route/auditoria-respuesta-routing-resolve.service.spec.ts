import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAuditoriaRespuesta } from '../auditoria-respuesta.model';
import { AuditoriaRespuestaService } from '../service/auditoria-respuesta.service';

import auditoriaRespuestaResolve from './auditoria-respuesta-routing-resolve.service';

describe('AuditoriaRespuesta routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AuditoriaRespuestaService;
  let resultAuditoriaRespuesta: IAuditoriaRespuesta | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(AuditoriaRespuestaService);
    resultAuditoriaRespuesta = undefined;
  });

  describe('resolve', () => {
    it('should return IAuditoriaRespuesta returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        auditoriaRespuestaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuditoriaRespuesta = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAuditoriaRespuesta).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        auditoriaRespuestaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuditoriaRespuesta = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAuditoriaRespuesta).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAuditoriaRespuesta>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        auditoriaRespuestaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAuditoriaRespuesta = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAuditoriaRespuesta).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
