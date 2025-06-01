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

describe('PreguntaDon e2e test', () => {
  const preguntaDonPageUrl = '/pregunta-don';
  const preguntaDonPageUrlPattern = new RegExp('/pregunta-don(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const preguntaDonSample = {"peso":5,"activa":false};

  let preguntaDon;
  // let pregunta;
  // let donEspiritual;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/preguntas',
      body: {"numeroPregunta":22879,"textoPregunta":"gnaw orange","obligatoria":true,"activa":true,"fechaCreacion":"2025-05-31T18:32:41.241Z"},
    }).then(({ body }) => {
      pregunta = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/don-espirituals',
      body: {"nombre":"convalesce whether yum","nombreCorto":"ick how frankly","descripcion":"woefully till","caracteristicas":"now","versiculosBiblicos":"coincide nab","activo":false,"ordenPresentacion":180},
    }).then(({ body }) => {
      donEspiritual = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/pregunta-dons+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pregunta-dons').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pregunta-dons/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/preguntas', {
      statusCode: 200,
      body: [pregunta],
    });

    cy.intercept('GET', '/api/don-espirituals', {
      statusCode: 200,
      body: [donEspiritual],
    });

  });
   */

  afterEach(() => {
    if (preguntaDon) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pregunta-dons/${preguntaDon.id}`,
      }).then(() => {
        preguntaDon = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (pregunta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/preguntas/${pregunta.id}`,
      }).then(() => {
        pregunta = undefined;
      });
    }
    if (donEspiritual) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/don-espirituals/${donEspiritual.id}`,
      }).then(() => {
        donEspiritual = undefined;
      });
    }
  });
   */

  it('PreguntaDons menu should load PreguntaDons page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pregunta-don');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PreguntaDon').should('exist');
    cy.url().should('match', preguntaDonPageUrlPattern);
  });

  describe('PreguntaDon page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(preguntaDonPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PreguntaDon page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pregunta-don/new$'));
        cy.getEntityCreateUpdateHeading('PreguntaDon');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaDonPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pregunta-dons',
          body: {
            ...preguntaDonSample,
            pregunta: pregunta,
            donEspiritual: donEspiritual,
          },
        }).then(({ body }) => {
          preguntaDon = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pregunta-dons+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/pregunta-dons?page=0&size=20>; rel="last",<http://localhost/api/pregunta-dons?page=0&size=20>; rel="first"',
              },
              body: [preguntaDon],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(preguntaDonPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(preguntaDonPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details PreguntaDon page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('preguntaDon');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaDonPageUrlPattern);
      });

      it('edit button click should load edit PreguntaDon page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreguntaDon');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaDonPageUrlPattern);
      });

      it('edit button click should load edit PreguntaDon page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreguntaDon');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaDonPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of PreguntaDon', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('preguntaDon').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', preguntaDonPageUrlPattern);

        preguntaDon = undefined;
      });
    });
  });

  describe('new PreguntaDon page', () => {
    beforeEach(() => {
      cy.visit(`${preguntaDonPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PreguntaDon');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of PreguntaDon', () => {
      cy.get(`[data-cy="peso"]`).type('1');
      cy.get(`[data-cy="peso"]`).should('have.value', '1');

      cy.get(`[data-cy="activa"]`).should('not.be.checked');
      cy.get(`[data-cy="activa"]`).click();
      cy.get(`[data-cy="activa"]`).should('be.checked');

      cy.get(`[data-cy="pregunta"]`).select(1);
      cy.get(`[data-cy="donEspiritual"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        preguntaDon = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', preguntaDonPageUrlPattern);
    });
  });
});
