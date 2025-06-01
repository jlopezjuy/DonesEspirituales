import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { InterpretacionDetailComponent } from './interpretacion-detail.component';

describe('Interpretacion Management Detail Component', () => {
  let comp: InterpretacionDetailComponent;
  let fixture: ComponentFixture<InterpretacionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InterpretacionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./interpretacion-detail.component').then(m => m.InterpretacionDetailComponent),
              resolve: { interpretacion: () => of({ id: 1334 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InterpretacionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InterpretacionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load interpretacion on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InterpretacionDetailComponent);

      // THEN
      expect(instance.interpretacion()).toEqual(expect.objectContaining({ id: 1334 }));
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
