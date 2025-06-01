import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PreguntaDetailComponent } from './pregunta-detail.component';

describe('Pregunta Management Detail Component', () => {
  let comp: PreguntaDetailComponent;
  let fixture: ComponentFixture<PreguntaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreguntaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./pregunta-detail.component').then(m => m.PreguntaDetailComponent),
              resolve: { pregunta: () => of({ id: 28174 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PreguntaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreguntaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pregunta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PreguntaDetailComponent);

      // THEN
      expect(instance.pregunta()).toEqual(expect.objectContaining({ id: 28174 }));
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
