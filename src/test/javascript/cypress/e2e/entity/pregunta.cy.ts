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

describe('Pregunta e2e test', () => {
  const preguntaPageUrl = '/pregunta';
  const preguntaPageUrlPattern = new RegExp('/pregunta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const preguntaSample = {
    numeroPregunta: 20908,
    textoPregunta: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
    obligatoria: true,
    activa: false,
    fechaCreacion: '2025-05-31T20:27:15.850Z',
  };

  let pregunta;
  let cuestionario;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/cuestionarios',
      body: {
        titulo: 'after likewise murky',
        descripcion: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        instrucciones: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        totalPreguntas: 368,
        activo: false,
        fechaCreacion: '2025-05-31T04:39:20.887Z',
        fechaActualizacion: '2025-05-31T18:34:24.753Z',
        version: 28632,
      },
    }).then(({ body }) => {
      cuestionario = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/preguntas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/preguntas').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/preguntas/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/pregunta-dons', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/detalle-respuestas', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/cuestionarios', {
      statusCode: 200,
      body: [cuestionario],
    });
  });

  afterEach(() => {
    if (pregunta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/preguntas/${pregunta.id}`,
      }).then(() => {
        pregunta = undefined;
      });
    }
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

  it('Preguntas menu should load Preguntas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pregunta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Pregunta').should('exist');
    cy.url().should('match', preguntaPageUrlPattern);
  });

  describe('Pregunta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(preguntaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Pregunta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pregunta/new$'));
        cy.getEntityCreateUpdateHeading('Pregunta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/preguntas',
          body: {
            ...preguntaSample,
            cuestionario,
          },
        }).then(({ body }) => {
          pregunta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/preguntas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pregunta],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(preguntaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Pregunta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pregunta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaPageUrlPattern);
      });

      it('edit button click should load edit Pregunta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Pregunta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaPageUrlPattern);
      });

      it('edit button click should load edit Pregunta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Pregunta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaPageUrlPattern);
      });

      it('last delete button click should delete instance of Pregunta', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pregunta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaPageUrlPattern);

        pregunta = undefined;
      });
    });
  });

  describe('new Pregunta page', () => {
    beforeEach(() => {
      cy.visit(`${preguntaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Pregunta');
    });

    it('should create an instance of Pregunta', () => {
      cy.get(`[data-cy="numeroPregunta"]`).type('28810');
      cy.get(`[data-cy="numeroPregunta"]`).should('have.value', '28810');

      cy.get(`[data-cy="textoPregunta"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="textoPregunta"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="obligatoria"]`).should('not.be.checked');
      cy.get(`[data-cy="obligatoria"]`).click();
      cy.get(`[data-cy="obligatoria"]`).should('be.checked');

      cy.get(`[data-cy="activa"]`).should('not.be.checked');
      cy.get(`[data-cy="activa"]`).click();
      cy.get(`[data-cy="activa"]`).should('be.checked');

      cy.get(`[data-cy="fechaCreacion"]`).type('2025-05-31T11:54');
      cy.get(`[data-cy="fechaCreacion"]`).blur();
      cy.get(`[data-cy="fechaCreacion"]`).should('have.value', '2025-05-31T11:54');

      cy.get(`[data-cy="cuestionario"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        pregunta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', preguntaPageUrlPattern);
    });
  });
});
