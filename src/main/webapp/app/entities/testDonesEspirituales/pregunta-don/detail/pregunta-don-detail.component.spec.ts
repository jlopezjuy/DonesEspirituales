import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PreguntaDonDetailComponent } from './pregunta-don-detail.component';

describe('PreguntaDon Management Detail Component', () => {
  let comp: PreguntaDonDetailComponent;
  let fixture: ComponentFixture<PreguntaDonDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreguntaDonDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./pregunta-don-detail.component').then(m => m.PreguntaDonDetailComponent),
              resolve: { preguntaDon: () => of({ id: 25443 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PreguntaDonDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PreguntaDonDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load preguntaDon on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PreguntaDonDetailComponent);

      // THEN
      expect(instance.preguntaDon()).toEqual(expect.objectContaining({ id: 25443 }));
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
