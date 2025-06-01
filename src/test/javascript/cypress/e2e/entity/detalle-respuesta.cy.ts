import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('DetalleRespuesta e2e test', () => {
  const detalleRespuestaPageUrl = '/detalle-respuesta';
  const detalleRespuestaPageUrlPattern = new RegExp('/detalle-respuesta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const detalleRespuestaSample = {"valorRespuesta":1,"timestampRespuesta":"2025-05-31T01:58:37.321Z"};

  let detalleRespuesta;
  // let escalaRespuesta;
  // let pregunta;
  // let respuestaUsuario;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/escala-respuestas',
      body: {"valor":2,"etiqueta":"limply","descripcion":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","orden":25957},
    }).then(({ body }) => {
      escalaRespuesta = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/preguntas',
      body: {"numeroPregunta":27790,"textoPregunta":"beep","obligatoria":false,"activa":false,"fechaCreacion":"2025-05-31T01:22:04.512Z"},
    }).then(({ body }) => {
      pregunta = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/respuesta-usuarios',
      body: {"fechaInicio":"2025-05-31T13:50:40.425Z","fechaCompletado":"2025-05-31T15:17:37.057Z","estado":"COMPLETADA","tiempoTotalSegundos":23337,"ipAddress":"throbbing zowie","userAgent":"forgather mortise"},
    }).then(({ body }) => {
      respuestaUsuario = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/detalle-respuestas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/detalle-respuestas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/detalle-respuestas/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/auditoria-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/escala-respuestas', {
      statusCode: 200,
      body: [escalaRespuesta],
    });

    cy.intercept('GET', '/api/preguntas', {
      statusCode: 200,
      body: [pregunta],
    });

    cy.intercept('GET', '/api/respuesta-usuarios', {
      statusCode: 200,
      body: [respuestaUsuario],
    });

  });
   */

  afterEach(() => {
    if (detalleRespuesta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/detalle-respuestas/${detalleRespuesta.id}`,
      }).then(() => {
        detalleRespuesta = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (escalaRespuesta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/escala-respuestas/${escalaRespuesta.id}`,
      }).then(() => {
        escalaRespuesta = undefined;
      });
    }
    if (pregunta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/preguntas/${pregunta.id}`,
      }).then(() => {
        pregunta = undefined;
      });
    }
    if (respuestaUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/respuesta-usuarios/${respuestaUsuario.id}`,
      }).then(() => {
        respuestaUsuario = undefined;
      });
    }
  });
   */

  it('DetalleRespuestas menu should load DetalleRespuestas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('detalle-respuesta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DetalleRespuesta').should('exist');
    cy.url().should('match', detalleRespuestaPageUrlPattern);
  });

  describe('DetalleRespuesta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(detalleRespuestaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DetalleRespuesta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/detalle-respuesta/new$'));
        cy.getEntityCreateUpdateHeading('DetalleRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', detalleRespuestaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/detalle-respuestas',
          body: {
            ...detalleRespuestaSample,
            escalaRespuesta: escalaRespuesta,
            pregunta: pregunta,
            respuestaUsuario: respuestaUsuario,
          },
        }).then(({ body }) => {
          detalleRespuesta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/detalle-respuestas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/detalle-respuestas?page=0&size=20>; rel="last",<http://localhost/api/detalle-respuestas?page=0&size=20>; rel="first"',
              },
              body: [detalleRespuesta],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(detalleRespuestaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(detalleRespuestaPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details DetalleRespuesta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('detalleRespuesta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', detalleRespuestaPageUrlPattern);
      });

      it('edit button click should load edit DetalleRespuesta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DetalleRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', detalleRespuestaPageUrlPattern);
      });

      it('edit button click should load edit DetalleRespuesta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DetalleRespuesta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', detalleRespuestaPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of DetalleRespuesta', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('detalleRespuesta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', detalleRespuestaPageUrlPattern);

        detalleRespuesta = undefined;
      });
    });
  });

  describe('new DetalleRespuesta page', () => {
    beforeEach(() => {
      cy.visit(`${detalleRespuestaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DetalleRespuesta');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of DetalleRespuesta', () => {
      cy.get(`[data-cy="valorRespuesta"]`).type('1');
      cy.get(`[data-cy="valorRespuesta"]`).should('have.value', '1');

      cy.get(`[data-cy="timestampRespuesta"]`).type('2025-05-31T02:26');
      cy.get(`[data-cy="timestampRespuesta"]`).blur();
      cy.get(`[data-cy="timestampRespuesta"]`).should('have.value', '2025-05-31T02:26');

      cy.get(`[data-cy="tiempoPreguntaSegundos"]`).type('15528');
      cy.get(`[data-cy="tiempoPreguntaSegundos"]`).should('have.value', '15528');

      cy.get(`[data-cy="escalaRespuesta"]`).select(1);
      cy.get(`[data-cy="pregunta"]`).select(1);
      cy.get(`[data-cy="respuestaUsuario"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        detalleRespuesta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', detalleRespuestaPageUrlPattern);
    });
  });
});
