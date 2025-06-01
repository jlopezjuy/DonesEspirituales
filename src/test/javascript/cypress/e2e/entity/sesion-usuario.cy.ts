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
  const sesionUsuarioSample = { fechaCreacion: '2025-05-31T08:19:38.126Z', fechaExpiracion: '2025-05-31T13:25:38.958Z', completada: true };

  let sesionUsuario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sesion-usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sesion-usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sesion-usuarios/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sesionUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sesion-usuarios/${sesionUsuario.id}`,
      }).then(() => {
        sesionUsuario = undefined;
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
          url: '/api/sesion-usuarios',
          body: sesionUsuarioSample,
        }).then(({ body }) => {
          sesionUsuario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sesion-usuarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/sesion-usuarios?page=0&size=20>; rel="last",<http://localhost/api/sesion-usuarios?page=0&size=20>; rel="first"',
              },
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

      cy.get(`[data-cy="fechaCreacion"]`).type('2025-05-31T07:51');
      cy.get(`[data-cy="fechaCreacion"]`).blur();
      cy.get(`[data-cy="fechaCreacion"]`).should('have.value', '2025-05-31T07:51');

      cy.get(`[data-cy="fechaExpiracion"]`).type('2025-05-31T15:37');
      cy.get(`[data-cy="fechaExpiracion"]`).blur();
      cy.get(`[data-cy="fechaExpiracion"]`).should('have.value', '2025-05-31T15:37');

      cy.get(`[data-cy="completada"]`).should('not.be.checked');
      cy.get(`[data-cy="completada"]`).click();
      cy.get(`[data-cy="completada"]`).should('be.checked');

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
