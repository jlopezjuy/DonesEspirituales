import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SesionUsuarioDetailComponent } from './sesion-usuario-detail.component';

describe('SesionUsuario Management Detail Component', () => {
  let comp: SesionUsuarioDetailComponent;
  let fixture: ComponentFixture<SesionUsuarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SesionUsuarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sesion-usuario-detail.component').then(m => m.SesionUsuarioDetailComponent),
              resolve: { sesionUsuario: () => of({ id: 5645 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SesionUsuarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SesionUsuarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load sesionUsuario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SesionUsuarioDetailComponent);

      // THEN
      expect(instance.sesionUsuario()).toEqual(expect.objectContaining({ id: 5645 }));
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
