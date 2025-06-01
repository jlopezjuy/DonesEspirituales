import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EscalaRespuestaDetailComponent } from './escala-respuesta-detail.component';

describe('EscalaRespuesta Management Detail Component', () => {
  let comp: EscalaRespuestaDetailComponent;
  let fixture: ComponentFixture<EscalaRespuestaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscalaRespuestaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./escala-respuesta-detail.component').then(m => m.EscalaRespuestaDetailComponent),
              resolve: { escalaRespuesta: () => of({ id: 12954 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EscalaRespuestaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EscalaRespuestaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load escalaRespuesta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EscalaRespuestaDetailComponent);

      // THEN
      expect(instance.escalaRespuesta()).toEqual(expect.objectContaining({ id: 12954 }));
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
