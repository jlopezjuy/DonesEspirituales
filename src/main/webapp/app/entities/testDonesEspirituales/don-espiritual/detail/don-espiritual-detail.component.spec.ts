import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DonEspiritualDetailComponent } from './don-espiritual-detail.component';

describe('DonEspiritual Management Detail Component', () => {
  let comp: DonEspiritualDetailComponent;
  let fixture: ComponentFixture<DonEspiritualDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DonEspiritualDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./don-espiritual-detail.component').then(m => m.DonEspiritualDetailComponent),
              resolve: { donEspiritual: () => of({ id: 2824 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DonEspiritualDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DonEspiritualDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load donEspiritual on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DonEspiritualDetailComponent);

      // THEN
      expect(instance.donEspiritual()).toEqual(expect.objectContaining({ id: 2824 }));
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
