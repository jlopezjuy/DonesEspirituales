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

describe('Usuario e2e test', () => {
  const usuarioPageUrl = '/usuario';
  const usuarioPageUrlPattern = new RegExp('/usuario(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const usuarioSample = {
    nombre: 'whether',
    apellido: 'dramatic',
    email: "7[12@u'%\\B.G]",
    fechaRegistro: '2025-05-31T13:58:33.528Z',
    activo: true,
  };

  let usuario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/usuarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/usuarios').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/usuarios/*').as('deleteEntityRequest');
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

  it('Usuarios menu should load Usuarios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('usuario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Usuario').should('exist');
    cy.url().should('match', usuarioPageUrlPattern);
  });

  describe('Usuario page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(usuarioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Usuario page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/usuario/new$'));
        cy.getEntityCreateUpdateHeading('Usuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', usuarioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/usuarios',
          body: usuarioSample,
        }).then(({ body }) => {
          usuario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/usuarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/testdonesespirituales/api/usuarios?page=0&size=20>; rel="last",<http://localhost/services/testdonesespirituales/api/usuarios?page=0&size=20>; rel="first"',
              },
              body: [usuario],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(usuarioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Usuario page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('usuario');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', usuarioPageUrlPattern);
      });

      it('edit button click should load edit Usuario page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Usuario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', usuarioPageUrlPattern);
      });

      it('edit button click should load edit Usuario page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Usuario');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', usuarioPageUrlPattern);
      });

      it('last delete button click should delete instance of Usuario', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('usuario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', usuarioPageUrlPattern);

        usuario = undefined;
      });
    });
  });

  describe('new Usuario page', () => {
    beforeEach(() => {
      cy.visit(`${usuarioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Usuario');
    });

    it('should create an instance of Usuario', () => {
      cy.get(`[data-cy="nombre"]`).type('integer');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'integer');

      cy.get(`[data-cy="apellido"]`).type('brr queasy so');
      cy.get(`[data-cy="apellido"]`).should('have.value', 'brr queasy so');

      cy.get(`[data-cy="email"]`).type('4]c@$z^.R]_>T,');
      cy.get(`[data-cy="email"]`).should('have.value', '4]c@$z^.R]_>T,');

      cy.get(`[data-cy="telefono"]`).type('provided outlaw circ');
      cy.get(`[data-cy="telefono"]`).should('have.value', 'provided outlaw circ');

      cy.get(`[data-cy="fechaNacimiento"]`).type('2025-05-31');
      cy.get(`[data-cy="fechaNacimiento"]`).blur();
      cy.get(`[data-cy="fechaNacimiento"]`).should('have.value', '2025-05-31');

      cy.get(`[data-cy="genero"]`).select('PREFIERO_NO_DECIR');

      cy.get(`[data-cy="iglesia"]`).type('for');
      cy.get(`[data-cy="iglesia"]`).should('have.value', 'for');

      cy.get(`[data-cy="denominacion"]`).type('ouch hmph');
      cy.get(`[data-cy="denominacion"]`).should('have.value', 'ouch hmph');

      cy.get(`[data-cy="fechaRegistro"]`).type('2025-05-31T03:11');
      cy.get(`[data-cy="fechaRegistro"]`).blur();
      cy.get(`[data-cy="fechaRegistro"]`).should('have.value', '2025-05-31T03:11');

      cy.get(`[data-cy="ultimaActividad"]`).type('2025-05-31T03:15');
      cy.get(`[data-cy="ultimaActividad"]`).blur();
      cy.get(`[data-cy="ultimaActividad"]`).should('have.value', '2025-05-31T03:15');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click();
      cy.get(`[data-cy="activo"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        usuario = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', usuarioPageUrlPattern);
    });
  });
});
