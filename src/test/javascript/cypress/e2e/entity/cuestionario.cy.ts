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

describe('Cuestionario e2e test', () => {
  const cuestionarioPageUrl = '/cuestionario';
  const cuestionarioPageUrlPattern = new RegExp('/cuestionario(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cuestionarioSample = {
    titulo: 'ick beyond for',
    instrucciones: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    totalPreguntas: 740,
    activo: false,
    fechaCreacion: '2025-05-31T02:30:45.725Z',
    version: 13848,
  };

  let cuestionario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/cuestionarios+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/cuestionarios').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/cuestionarios/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cuestionario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/cuestionarios/${cuestionario.id}`,
      }).then(() => {
        cuestionario = undefined;
      });
    }
  });

  it('Cuestionarios menu should load Cuestionarios page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cuestionario');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Cuestionario').should('exist');
    cy.url().should('match', cuestionarioPageUrlPattern);
  });

  describe('Cuestionario page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cuestionarioPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Cuestionario page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cuestionario/new$'));
        cy.getEntityCreateUpdateHeading('Cuestionario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cuestionarioPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/cuestionarios',
          body: cuestionarioSample,
        }).then(({ body }) => {
          cuestionario = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/cuestionarios+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [cuestionario],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cuestionarioPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Cuestionario page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cuestionario');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cuestionarioPageUrlPattern);
      });

      it('edit button click should load edit Cuestionario page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Cuestionario');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cuestionarioPageUrlPattern);
      });

      it('edit button click should load edit Cuestionario page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Cuestionario');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cuestionarioPageUrlPattern);
      });

      it('last delete button click should delete instance of Cuestionario', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cuestionario').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cuestionarioPageUrlPattern);

        cuestionario = undefined;
      });
    });
  });

  describe('new Cuestionario page', () => {
    beforeEach(() => {
      cy.visit(`${cuestionarioPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Cuestionario');
    });

    it('should create an instance of Cuestionario', () => {
      cy.get(`[data-cy="titulo"]`).type('throbbing intend impeccable');
      cy.get(`[data-cy="titulo"]`).should('have.value', 'throbbing intend impeccable');

      cy.get(`[data-cy="descripcion"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="descripcion"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="instrucciones"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="instrucciones"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="totalPreguntas"]`).type('589');
      cy.get(`[data-cy="totalPreguntas"]`).should('have.value', '589');

      cy.get(`[data-cy="activo"]`).should('not.be.checked');
      cy.get(`[data-cy="activo"]`).click();
      cy.get(`[data-cy="activo"]`).should('be.checked');

      cy.get(`[data-cy="fechaCreacion"]`).type('2025-05-31T18:34');
      cy.get(`[data-cy="fechaCreacion"]`).blur();
      cy.get(`[data-cy="fechaCreacion"]`).should('have.value', '2025-05-31T18:34');

      cy.get(`[data-cy="fechaActualizacion"]`).type('2025-05-31T20:58');
      cy.get(`[data-cy="fechaActualizacion"]`).blur();
      cy.get(`[data-cy="fechaActualizacion"]`).should('have.value', '2025-05-31T20:58');

      cy.get(`[data-cy="version"]`).type('19281');
      cy.get(`[data-cy="version"]`).should('have.value', '19281');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cuestionario = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cuestionarioPageUrlPattern);
    });
  });
});
