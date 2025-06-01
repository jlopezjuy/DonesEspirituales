import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ConfiguracionSistemaDetailComponent } from './configuracion-sistema-detail.component';

describe('ConfiguracionSistema Management Detail Component', () => {
  let comp: ConfiguracionSistemaDetailComponent;
  let fixture: ComponentFixture<ConfiguracionSistemaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfiguracionSistemaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./configuracion-sistema-detail.component').then(m => m.ConfiguracionSistemaDetailComponent),
              resolve: { configuracionSistema: () => of({ id: 12945 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ConfiguracionSistemaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfiguracionSistemaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load configuracionSistema on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ConfiguracionSistemaDetailComponent);

      // THEN
      expect(instance.configuracionSistema()).toEqual(expect.objectContaining({ id: 12945 }));
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
