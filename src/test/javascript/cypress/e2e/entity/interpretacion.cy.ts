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

describe('Interpretacion e2e test', () => {
  const interpretacionPageUrl = '/interpretacion';
  const interpretacionPageUrlPattern = new RegExp('/interpretacion(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const interpretacionSample = {
    puntuacionMinima: 18594,
    puntuacionMaxima: 7151,
    nivel: 'ALTO',
    descripcionNivel: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
  };

  let interpretacion;
  let donEspiritual;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/don-espirituals',
      body: {
        nombre: 'hypothesize',
        nombreCorto: 'serpentine handsome guard',
        descripcion: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        caracteristicas: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        versiculosBiblicos: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        activo: true,
        ordenPresentacion: 2868,
      },
    }).then(({ body }) => {
      donEspiritual = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/interpretacions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/interpretacions').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/interpretacions/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/don-espirituals', {
      statusCode: 200,
      body: [donEspiritual],
    });
  });

  afterEach(() => {
    if (interpretacion) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/interpretacions/${interpretacion.id}`,
      }).then(() => {
        interpretacion = undefined;
      });
    }
  });

  afterEach(() => {
    if (donEspiritual) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/don-espirituals/${donEspiritual.id}`,
      }).then(() => {
        donEspiritual = undefined;
      });
    }
  });

  it('Interpretacions menu should load Interpretacions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('interpretacion');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Interpretacion').should('exist');
    cy.url().should('match', interpretacionPageUrlPattern);
  });

  describe('Interpretacion page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(interpretacionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Interpretacion page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/interpretacion/new$'));
        cy.getEntityCreateUpdateHeading('Interpretacion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', interpretacionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/interpretacions',
          body: {
            ...interpretacionSample,
            donEspiritual,
          },
        }).then(({ body }) => {
          interpretacion = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/interpretacions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [interpretacion],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(interpretacionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Interpretacion page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('interpretacion');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', interpretacionPageUrlPattern);
      });

      it('edit button click should load edit Interpretacion page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Interpretacion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', interpretacionPageUrlPattern);
      });

      it('edit button click should load edit Interpretacion page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Interpretacion');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', interpretacionPageUrlPattern);
      });

      it('last delete button click should delete instance of Interpretacion', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('interpretacion').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', interpretacionPageUrlPattern);

        interpretacion = undefined;
      });
    });
  });

  describe('new Interpretacion page', () => {
    beforeEach(() => {
      cy.visit(`${interpretacionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Interpretacion');
    });

    it('should create an instance of Interpretacion', () => {
      cy.get(`[data-cy="puntuacionMinima"]`).type('22515');
      cy.get(`[data-cy="puntuacionMinima"]`).should('have.value', '22515');

      cy.get(`[data-cy="puntuacionMaxima"]`).type('24756');
      cy.get(`[data-cy="puntuacionMaxima"]`).should('have.value', '24756');

      cy.get(`[data-cy="nivel"]`).select('MUY_BAJO');

      cy.get(`[data-cy="descripcionNivel"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="descripcionNivel"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="recomendaciones"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="recomendaciones"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="areasServicio"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="areasServicio"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="donEspiritual"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        interpretacion = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', interpretacionPageUrlPattern);
    });
  });
});
