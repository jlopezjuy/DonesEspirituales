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

describe('SesionUsuario e2e test', () => {
  const sesionUsuarioPageUrl = '/sesion-usuario';
  const sesionUsuarioPageUrlPattern = new RegExp('/sesion-usuario(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sesionUsuarioSample = { fechaCreacion: '2025-05-31T23:40:31.742Z', fechaExpiracion: '2025-05-31T08:16:29.160Z', completada: false };

  let sesionUsuario;
  let usuario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/usuarios',
      body: {
        nombre: 'continually even',
        apellido: 'see',
        email: '(.nn@bLY+2.ac?>\\/',
        telefono: 'er governance provid',
        fechaNacimiento: '2025-05-31',
        genero: 'FEMENINO',
        iglesia: 'ah regarding continually',
        denominacion: 'mortally naturally geez',
        fechaRegistro: '2025-05-31T23:23:18.740Z',
        ultimaActividad: '2025-05-31T17:27:12.691Z',
        activo: true,
      },
    }).then(({ body }) => {
      usuario = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/sesion-usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/sesion-usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/sesion-usuarios/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/usuarios', {
      statusCode: 200,
      body: [usuario],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/respuesta-usuarios', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (sesionUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/sesion-usuarios/${sesionUsuario.id}`,
      }).then(() => {
        sesionUsuario = undefined;
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
  });

  it('SesionUsuarios menu should load SesionUsuarios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sesion-usuario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SesionUsuario').should('exist');
    cy.url().should('match', sesionUsuarioPageUrlPattern);
  });

  describe('SesionUsuario page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sesionUsuarioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SesionUsuario page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sesion-usuario/new$'));
        cy.getEntityCreateUpdateHeading('SesionUsuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', sesionUsuarioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/sesion-usuarios',
          body: {
            ...sesionUsuarioSample,
            usuario,
          },
        }).then(({ body }) => {
          sesionUsuario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/sesion-usuarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sesionUsuario],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(sesionUsuarioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SesionUsuario page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sesionUsuario');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', sesionUsuarioPageUrlPattern);
      });

      it('edit button click should load edit SesionUsuario page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SesionUsuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', sesionUsuarioPageUrlPattern);
      });

      it('edit button click should load edit SesionUsuario page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SesionUsuario');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', sesionUsuarioPageUrlPattern);
      });

      it('last delete button click should delete instance of SesionUsuario', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('sesionUsuario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', sesionUsuarioPageUrlPattern);

        sesionUsuario = undefined;
      });
    });
  });

  describe('new SesionUsuario page', () => {
    beforeEach(() => {
      cy.visit(`${sesionUsuarioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SesionUsuario');
    });

    it('should create an instance of SesionUsuario', () => {
      cy.get(`[data-cy="respuestasTemporales"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="respuestasTemporales"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="fechaCreacion"]`).type('2025-05-31T02:49');
      cy.get(`[data-cy="fechaCreacion"]`).blur();
      cy.get(`[data-cy="fechaCreacion"]`).should('have.value', '2025-05-31T02:49');

      cy.get(`[data-cy="fechaExpiracion"]`).type('2025-05-31T08:10');
      cy.get(`[data-cy="fechaExpiracion"]`).blur();
      cy.get(`[data-cy="fechaExpiracion"]`).should('have.value', '2025-05-31T08:10');

      cy.get(`[data-cy="completada"]`).should('not.be.checked');
      cy.get(`[data-cy="completada"]`).click();
      cy.get(`[data-cy="completada"]`).should('be.checked');

      cy.get(`[data-cy="usuario"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        sesionUsuario = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', sesionUsuarioPageUrlPattern);
    });
  });
});
