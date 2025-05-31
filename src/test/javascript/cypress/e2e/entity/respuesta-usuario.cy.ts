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
  const respuestaUsuarioSample = { fechaInicio: '2025-05-31T01:47:49.072Z', estado: 'COMPLETADA' };

  let respuestaUsuario;
  let usuario;
  let cuestionario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/usuarios',
      body: {
        nombre: 'honestly across joshingly',
        apellido: 'crooked along cruelty',
        email: 'hb&py@).5,}&Hd',
        telefono: 'underplay',
        fechaNacimiento: '2025-05-31',
        genero: 'OTRO',
        iglesia: 'nor righteously given',
        denominacion: 'accomplished past beside',
        fechaRegistro: '2025-05-31T02:10:54.585Z',
        ultimaActividad: '2025-05-31T21:45:25.383Z',
        activo: true,
      },
    }).then(({ body }) => {
      usuario = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/cuestionarios',
      body: {
        titulo: 'culminate impractical populist',
        descripcion: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        instrucciones: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        totalPreguntas: 126,
        activo: true,
        fechaCreacion: '2025-05-31T00:04:59.776Z',
        fechaActualizacion: '2025-05-31T09:24:12.029Z',
        version: 29850,
      },
    }).then(({ body }) => {
      cuestionario = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/respuesta-usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/respuesta-usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/respuesta-usuarios/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/detalle-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/resultado-dons', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/sesion-usuarios', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/auditoria-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/usuarios', {
      statusCode: 200,
      body: [usuario],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/cuestionarios', {
      statusCode: 200,
      body: [cuestionario],
    });
  });

  afterEach(() => {
    if (respuestaUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/respuesta-usuarios/${respuestaUsuario.id}`,
      }).then(() => {
        respuestaUsuario = undefined;
      });
    }
  });

  afterEach(() => {
    if (usuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/usuarios/${usuario.id}`,
      }).then(() => {
        usuario = undefined;
      });
    }
    if (cuestionario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/cuestionarios/${cuestionario.id}`,
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
          url: '/services/testdonesespirituales/api/respuesta-usuarios',
          body: {
            ...respuestaUsuarioSample,
            usuario,
            cuestionario,
          },
        }).then(({ body }) => {
          respuestaUsuario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/respuesta-usuarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/testdonesespirituales/api/respuesta-usuarios?page=0&size=20>; rel="last",<http://localhost/services/testdonesespirituales/api/respuesta-usuarios?page=0&size=20>; rel="first"',
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
      cy.get(`[data-cy="fechaInicio"]`).type('2025-05-31T02:40');
      cy.get(`[data-cy="fechaInicio"]`).blur();
      cy.get(`[data-cy="fechaInicio"]`).should('have.value', '2025-05-31T02:40');

      cy.get(`[data-cy="fechaCompletado"]`).type('2025-05-31T08:26');
      cy.get(`[data-cy="fechaCompletado"]`).blur();
      cy.get(`[data-cy="fechaCompletado"]`).should('have.value', '2025-05-31T08:26');

      cy.get(`[data-cy="estado"]`).select('EN_PROGRESO');

      cy.get(`[data-cy="tiempoTotalSegundos"]`).type('26088');
      cy.get(`[data-cy="tiempoTotalSegundos"]`).should('have.value', '26088');

      cy.get(`[data-cy="ipAddress"]`).type('consequently in when');
      cy.get(`[data-cy="ipAddress"]`).should('have.value', 'consequently in when');

      cy.get(`[data-cy="userAgent"]`).type('tackle ack enchanted');
      cy.get(`[data-cy="userAgent"]`).should('have.value', 'tackle ack enchanted');

      cy.get(`[data-cy="usuario"]`).select(1);
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
