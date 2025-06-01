import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RespuestaUsuarioDetailComponent } from './respuesta-usuario-detail.component';

describe('RespuestaUsuario Management Detail Component', () => {
  let comp: RespuestaUsuarioDetailComponent;
  let fixture: ComponentFixture<RespuestaUsuarioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RespuestaUsuarioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./respuesta-usuario-detail.component').then(m => m.RespuestaUsuarioDetailComponent),
              resolve: { respuestaUsuario: () => of({ id: 22901 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RespuestaUsuarioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RespuestaUsuarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load respuestaUsuario on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RespuestaUsuarioDetailComponent);

      // THEN
      expect(instance.respuestaUsuario()).toEqual(expect.objectContaining({ id: 22901 }));
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
