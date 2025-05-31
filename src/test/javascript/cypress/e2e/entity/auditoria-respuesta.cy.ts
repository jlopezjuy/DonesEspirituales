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

describe('AuditoriaRespuesta e2e test', () => {
  const auditoriaRespuestaPageUrl = '/auditoria-respuesta';
  const auditoriaRespuestaPageUrlPattern = new RegExp('/auditoria-respuesta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const auditoriaRespuestaSample = {"valorNuevo":10,"timestampCambio":"2025-05-31T13:30:10.766Z"};

  let auditoriaRespuesta;
  // let respuestaUsuario;
  // let detalleRespuesta;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/respuesta-usuarios',
      body: {"fechaInicio":"2025-05-31T11:21:50.346Z","fechaCompletado":"2025-05-31T19:15:47.110Z","estado":"EN_PROGRESO","tiempoTotalSegundos":6706,"ipAddress":"deflect painfully","userAgent":"over home"},
    }).then(({ body }) => {
      respuestaUsuario = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/detalle-respuestas',
      body: {"valorRespuesta":0,"timestampRespuesta":"2025-05-31T02:21:56.839Z","tiempoPreguntaSegundos":5308},
    }).then(({ body }) => {
      detalleRespuesta = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/auditoria-respuestas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/auditoria-respuestas').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/auditoria-respuestas/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/respuesta-usuarios', {
      statusCode: 200,
      body: [respuestaUsuario],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/detalle-respuestas', {
      statusCode: 200,
      body: [detalleRespuesta],
    });

  });
   */

  afterEach(() => {
    if (auditoriaRespuesta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/auditoria-respuestas/${auditoriaRespuesta.id}`,
      }).then(() => {
        auditoriaRespuesta = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (respuestaUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/respuesta-usuarios/${respuestaUsuario.id}`,
      }).then(() => {
        respuestaUsuario = undefined;
      });
    }
    if (detalleRespuesta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/detalle-respuestas/${detalleRespuesta.id}`,
      }).then(() => {
        detalleRespuesta = undefined;
      });
    }
  });
   */

  it('AuditoriaRespuestas menu should load AuditoriaRespuestas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('auditoria-respuesta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AuditoriaRespuesta').should('exist');
    cy.url().should('match', auditoriaRespuestaPageUrlPattern);
  });

  describe('AuditoriaRespuesta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(auditoriaRespuestaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AuditoriaRespuesta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/auditoria-respuesta/new$'));
        cy.getEntityCreateUpdateHeading('AuditoriaRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', auditoriaRespuestaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/auditoria-respuestas',
          body: {
            ...auditoriaRespuestaSample,
            respuestaUsuario: respuestaUsuario,
            detalleRespuesta: detalleRespuesta,
          },
        }).then(({ body }) => {
          auditoriaRespuesta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/auditoria-respuestas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/testdonesespirituales/api/auditoria-respuestas?page=0&size=20>; rel="last",<http://localhost/services/testdonesespirituales/api/auditoria-respuestas?page=0&size=20>; rel="first"',
              },
              body: [auditoriaRespuesta],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(auditoriaRespuestaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(auditoriaRespuestaPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details AuditoriaRespuesta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('auditoriaRespuesta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', auditoriaRespuestaPageUrlPattern);
      });

      it('edit button click should load edit AuditoriaRespuesta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AuditoriaRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', auditoriaRespuestaPageUrlPattern);
      });

      it('edit button click should load edit AuditoriaRespuesta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AuditoriaRespuesta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', auditoriaRespuestaPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of AuditoriaRespuesta', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('auditoriaRespuesta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', auditoriaRespuestaPageUrlPattern);

        auditoriaRespuesta = undefined;
      });
    });
  });

  describe('new AuditoriaRespuesta page', () => {
    beforeEach(() => {
      cy.visit(`${auditoriaRespuestaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AuditoriaRespuesta');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of AuditoriaRespuesta', () => {
      cy.get(`[data-cy="valorAnterior"]`).type('6');
      cy.get(`[data-cy="valorAnterior"]`).should('have.value', '6');

      cy.get(`[data-cy="valorNuevo"]`).type('8');
      cy.get(`[data-cy="valorNuevo"]`).should('have.value', '8');

      cy.get(`[data-cy="timestampCambio"]`).type('2025-05-31T07:50');
      cy.get(`[data-cy="timestampCambio"]`).blur();
      cy.get(`[data-cy="timestampCambio"]`).should('have.value', '2025-05-31T07:50');

      cy.get(`[data-cy="motivoCambio"]`).type('fooey polished frightened');
      cy.get(`[data-cy="motivoCambio"]`).should('have.value', 'fooey polished frightened');

      cy.get(`[data-cy="respuestaUsuario"]`).select(1);
      cy.get(`[data-cy="detalleRespuesta"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        auditoriaRespuesta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', auditoriaRespuestaPageUrlPattern);
    });
  });
});
