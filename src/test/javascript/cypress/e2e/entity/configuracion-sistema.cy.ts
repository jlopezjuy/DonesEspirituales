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

describe('ConfiguracionSistema e2e test', () => {
  const configuracionSistemaPageUrl = '/configuracion-sistema';
  const configuracionSistemaPageUrlPattern = new RegExp('/configuracion-sistema(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const configuracionSistemaSample = {
    clave: 'after at superb',
    valor: 'capsize whereas upright',
    tipoDato: 'DATE',
    fechaActualizacion: '2025-05-31T15:35:43.412Z',
  };

  let configuracionSistema;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/configuracion-sistemas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/configuracion-sistemas').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/configuracion-sistemas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (configuracionSistema) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/configuracion-sistemas/${configuracionSistema.id}`,
      }).then(() => {
        configuracionSistema = undefined;
      });
    }
  });

  it('ConfiguracionSistemas menu should load ConfiguracionSistemas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('configuracion-sistema');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ConfiguracionSistema').should('exist');
    cy.url().should('match', configuracionSistemaPageUrlPattern);
  });

  describe('ConfiguracionSistema page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(configuracionSistemaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ConfiguracionSistema page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/configuracion-sistema/new$'));
        cy.getEntityCreateUpdateHeading('ConfiguracionSistema');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', configuracionSistemaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/configuracion-sistemas',
          body: configuracionSistemaSample,
        }).then(({ body }) => {
          configuracionSistema = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/configuracion-sistemas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [configuracionSistema],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(configuracionSistemaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ConfiguracionSistema page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('configuracionSistema');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', configuracionSistemaPageUrlPattern);
      });

      it('edit button click should load edit ConfiguracionSistema page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ConfiguracionSistema');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', configuracionSistemaPageUrlPattern);
      });

      it('edit button click should load edit ConfiguracionSistema page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ConfiguracionSistema');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', configuracionSistemaPageUrlPattern);
      });

      it('last delete button click should delete instance of ConfiguracionSistema', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('configuracionSistema').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', configuracionSistemaPageUrlPattern);

        configuracionSistema = undefined;
      });
    });
  });

  describe('new ConfiguracionSistema page', () => {
    beforeEach(() => {
      cy.visit(`${configuracionSistemaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ConfiguracionSistema');
    });

    it('should create an instance of ConfiguracionSistema', () => {
      cy.get(`[data-cy="clave"]`).type('close forenenst');
      cy.get(`[data-cy="clave"]`).should('have.value', 'close forenenst');

      cy.get(`[data-cy="valor"]`).type('joyful blah');
      cy.get(`[data-cy="valor"]`).should('have.value', 'joyful blah');

      cy.get(`[data-cy="descripcion"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="descripcion"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="tipoDato"]`).select('STRING');

      cy.get(`[data-cy="fechaActualizacion"]`).type('2025-05-31T11:00');
      cy.get(`[data-cy="fechaActualizacion"]`).blur();
      cy.get(`[data-cy="fechaActualizacion"]`).should('have.value', '2025-05-31T11:00');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        configuracionSistema = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', configuracionSistemaPageUrlPattern);
    });
  });
});
