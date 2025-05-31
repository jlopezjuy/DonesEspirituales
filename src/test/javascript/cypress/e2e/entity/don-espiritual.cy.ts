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

describe('DonEspiritual e2e test', () => {
  const donEspiritualPageUrl = '/don-espiritual';
  const donEspiritualPageUrlPattern = new RegExp('/don-espiritual(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const donEspiritualSample = { nombre: 'quixotic welcome buzzing', nombreCorto: 'ouch meh yet', activo: true };

  let donEspiritual;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/don-espirituals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/don-espirituals').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/don-espirituals/*').as('deleteEntityRequest');
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

  it('DonEspirituals menu should load DonEspirituals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('don-espiritual');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DonEspiritual').should('exist');
    cy.url().should('match', donEspiritualPageUrlPattern);
  });

  describe('DonEspiritual page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(donEspiritualPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DonEspiritual page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/don-espiritual/new$'));
        cy.getEntityCreateUpdateHeading('DonEspiritual');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', donEspiritualPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/don-espirituals',
          body: donEspiritualSample,
        }).then(({ body }) => {
          donEspiritual = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/don-espirituals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [donEspiritual],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(donEspiritualPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DonEspiritual page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('donEspiritual');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', donEspiritualPageUrlPattern);
      });

      it('edit button click should load edit DonEspiritual page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DonEspiritual');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', donEspiritualPageUrlPattern);
      });

      it('edit button click should load edit DonEspiritual page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DonEspiritual');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', donEspiritualPageUrlPattern);
      });

      it('last delete button click should delete instance of DonEspiritual', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('donEspiritual').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', donEspiritualPageUrlPattern);

        donEspiritual = undefined;
      });
    });
  });

  describe('new DonEspiritual page', () => {
    beforeEach(() => {
      cy.visit(`${donEspiritualPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DonEspiritual');
    });

    it('should create an instance of DonEspiritual', () => {
      cy.get(`[data-cy="nombre"]`).type('soon before');
      cy.get(`[data-cy="nombre"]`).should('have.value', 'soon before');

      cy.get(`[data-cy="nombreCorto"]`).type('eek shout');
      cy.get(`[data-cy="nombreCorto"]`).should('have.value', 'eek shout');

      cy.get(`[data-cy="descripcion"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="descripcion"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="caracteristicas"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="caracteristicas"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="versiculosBiblicos"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="versiculosBiblicos"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click();
      cy.get(`[data-cy="activo"]`).should('be.checked');

      cy.get(`[data-cy="ordenPresentacion"]`).type('32313');
      cy.get(`[data-cy="ordenPresentacion"]`).should('have.value', '32313');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        donEspiritual = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', donEspiritualPageUrlPattern);
    });
  });
});
