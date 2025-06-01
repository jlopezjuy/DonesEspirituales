import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'donesEspiritualesApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'cuestionario',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesCuestionario.home.title' },
    loadChildren: () => import('./testDonesEspirituales/cuestionario/cuestionario.routes'),
  },
  {
    path: 'escala-respuesta',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesEscalaRespuesta.home.title' },
    loadChildren: () => import('./testDonesEspirituales/escala-respuesta/escala-respuesta.routes'),
  },
  {
    path: 'don-espiritual',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesDonEspiritual.home.title' },
    loadChildren: () => import('./testDonesEspirituales/don-espiritual/don-espiritual.routes'),
  },
  {
    path: 'pregunta',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesPregunta.home.title' },
    loadChildren: () => import('./testDonesEspirituales/pregunta/pregunta.routes'),
  },
  {
    path: 'pregunta-don',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesPreguntaDon.home.title' },
    loadChildren: () => import('./testDonesEspirituales/pregunta-don/pregunta-don.routes'),
  },
  {
    path: 'respuesta-usuario',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesRespuestaUsuario.home.title' },
    loadChildren: () => import('./testDonesEspirituales/respuesta-usuario/respuesta-usuario.routes'),
  },
  {
    path: 'detalle-respuesta',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesDetalleRespuesta.home.title' },
    loadChildren: () => import('./testDonesEspirituales/detalle-respuesta/detalle-respuesta.routes'),
  },
  {
    path: 'resultado-don',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesResultadoDon.home.title' },
    loadChildren: () => import('./testDonesEspirituales/resultado-don/resultado-don.routes'),
  },
  {
    path: 'interpretacion',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesInterpretacion.home.title' },
    loadChildren: () => import('./testDonesEspirituales/interpretacion/interpretacion.routes'),
  },
  {
    path: 'sesion-usuario',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesSesionUsuario.home.title' },
    loadChildren: () => import('./testDonesEspirituales/sesion-usuario/sesion-usuario.routes'),
  },
  {
    path: 'auditoria-respuesta',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesAuditoriaRespuesta.home.title' },
    loadChildren: () => import('./testDonesEspirituales/auditoria-respuesta/auditoria-respuesta.routes'),
  },
  {
    path: 'configuracion-sistema',
    data: { pageTitle: 'donesEspiritualesApp.testDonesEspiritualesConfiguracionSistema.home.title' },
    loadChildren: () => import('./testDonesEspirituales/configuracion-sistema/configuracion-sistema.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
