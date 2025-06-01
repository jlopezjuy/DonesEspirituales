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

describe('RespuestaUsuario e2e test', () => {
  const respuestaUsuarioPageUrl = '/respuesta-usuario';
  const respuestaUsuarioPageUrlPattern = new RegExp('/respuesta-usuario(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const respuestaUsuarioSample = { fechaInicio: '2025-05-31T22:23:08.336Z', estado: 'INICIADA' };

  let respuestaUsuario;
  let cuestionario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/cuestionarios',
      body: {
        titulo: 'rotating to',
        descripcion: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        instrucciones: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        totalPreguntas: 724,
        activo: true,
        fechaCreacion: '2025-05-31T02:33:41.335Z',
        fechaActualizacion: '2025-05-31T07:22:57.734Z',
        version: 6513,
      },
    }).then(({ body }) => {
      cuestionario = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/respuesta-usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/respuesta-usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/api/respuesta-usuarios/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/detalle-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/resultado-dons', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/sesion-usuarios', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/auditoria-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/users', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/cuestionarios', {
      statusCode: 200,
      body: [cuestionario],
    });
  });

  afterEach(() => {
    if (respuestaUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/respuesta-usuarios/${respuestaUsuario.id}`,
      }).then(() => {
        respuestaUsuario = undefined;
      });
    }
  });

  afterEach(() => {
    if (cuestionario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cuestionarios/${cuestionario.id}`,
      }).then(() => {
        cuestionario = undefined;
      });
    }
  });

  it('RespuestaUsuarios menu should load RespuestaUsuarios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('respuesta-usuario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RespuestaUsuario').should('exist');
    cy.url().should('match', respuestaUsuarioPageUrlPattern);
  });

  describe('RespuestaUsuario page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(respuestaUsuarioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RespuestaUsuario page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/respuesta-usuario/new$'));
        cy.getEntityCreateUpdateHeading('RespuestaUsuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', respuestaUsuarioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/respuesta-usuarios',
          body: {
            ...respuestaUsuarioSample,
            cuestionario,
          },
        }).then(({ body }) => {
          respuestaUsuario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/respuesta-usuarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/respuesta-usuarios?page=0&size=20>; rel="last",<http://localhost/api/respuesta-usuarios?page=0&size=20>; rel="first"',
              },
              body: [respuestaUsuario],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(respuestaUsuarioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RespuestaUsuario page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('respuestaUsuario');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', respuestaUsuarioPageUrlPattern);
      });

      it('edit button click should load edit RespuestaUsuario page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RespuestaUsuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', respuestaUsuarioPageUrlPattern);
      });

      it('edit button click should load edit RespuestaUsuario page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RespuestaUsuario');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', respuestaUsuarioPageUrlPattern);
      });

      it('last delete button click should delete instance of RespuestaUsuario', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('respuestaUsuario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', respuestaUsuarioPageUrlPattern);

        respuestaUsuario = undefined;
      });
    });
  });

  describe('new RespuestaUsuario page', () => {
    beforeEach(() => {
      cy.visit(`${respuestaUsuarioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RespuestaUsuario');
    });

    it('should create an instance of RespuestaUsuario', () => {
      cy.get(`[data-cy="fechaInicio"]`).type('2025-05-31T14:45');
      cy.get(`[data-cy="fechaInicio"]`).blur();
      cy.get(`[data-cy="fechaInicio"]`).should('have.value', '2025-05-31T14:45');

      cy.get(`[data-cy="fechaCompletado"]`).type('2025-05-31T10:38');
      cy.get(`[data-cy="fechaCompletado"]`).blur();
      cy.get(`[data-cy="fechaCompletado"]`).should('have.value', '2025-05-31T10:38');

      cy.get(`[data-cy="estado"]`).select('EN_PROGRESO');

      cy.get(`[data-cy="tiempoTotalSegundos"]`).type('17424');
      cy.get(`[data-cy="tiempoTotalSegundos"]`).should('have.value', '17424');

      cy.get(`[data-cy="ipAddress"]`).type('legislature');
      cy.get(`[data-cy="ipAddress"]`).should('have.value', 'legislature');

      cy.get(`[data-cy="userAgent"]`).type('sheepishly');
      cy.get(`[data-cy="userAgent"]`).should('have.value', 'sheepishly');

      cy.get(`[data-cy="cuestionario"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        respuestaUsuario = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', respuestaUsuarioPageUrlPattern);
    });
  });
});
