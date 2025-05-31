import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetalleRespuestaDetailComponent } from './detalle-respuesta-detail.component';

describe('DetalleRespuesta Management Detail Component', () => {
  let comp: DetalleRespuestaDetailComponent;
  let fixture: ComponentFixture<DetalleRespuestaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleRespuestaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./detalle-respuesta-detail.component').then(m => m.DetalleRespuestaDetailComponent),
              resolve: { detalleRespuesta: () => of({ id: 18708 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DetalleRespuestaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleRespuestaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load detalleRespuesta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DetalleRespuestaDetailComponent);

      // THEN
      expect(instance.detalleRespuesta()).toEqual(expect.objectContaining({ id: 18708 }));
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
