import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuditoriaRespuestaDetailComponent } from './auditoria-respuesta-detail.component';

describe('AuditoriaRespuesta Management Detail Component', () => {
  let comp: AuditoriaRespuestaDetailComponent;
  let fixture: ComponentFixture<AuditoriaRespuestaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuditoriaRespuestaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./auditoria-respuesta-detail.component').then(m => m.AuditoriaRespuestaDetailComponent),
              resolve: { auditoriaRespuesta: () => of({ id: 28590 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AuditoriaRespuestaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditoriaRespuestaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load auditoriaRespuesta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AuditoriaRespuestaDetailComponent);

      // THEN
      expect(instance.auditoriaRespuesta()).toEqual(expect.objectContaining({ id: 28590 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
