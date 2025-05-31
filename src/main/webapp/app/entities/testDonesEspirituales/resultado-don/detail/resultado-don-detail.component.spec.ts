import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ResultadoDonDetailComponent } from './resultado-don-detail.component';

describe('ResultadoDon Management Detail Component', () => {
  let comp: ResultadoDonDetailComponent;
  let fixture: ComponentFixture<ResultadoDonDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResultadoDonDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./resultado-don-detail.component').then(m => m.ResultadoDonDetailComponent),
              resolve: { resultadoDon: () => of({ id: 9168 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ResultadoDonDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultadoDonDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load resultadoDon on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ResultadoDonDetailComponent);

      // THEN
      expect(instance.resultadoDon()).toEqual(expect.objectContaining({ id: 9168 }));
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
