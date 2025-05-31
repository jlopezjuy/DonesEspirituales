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

describe('ResultadoDon e2e test', () => {
  const resultadoDonPageUrl = '/resultado-don';
  const resultadoDonPageUrlPattern = new RegExp('/resultado-don(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const resultadoDonSample = {"puntuacionTotal":29732,"porcentaje":28.23,"rankingPosicion":16454,"esDonPrincipal":true};

  let resultadoDon;
  // let respuestaUsuario;
  // let donEspiritual;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/respuesta-usuarios',
      body: {"fechaInicio":"2025-05-31T04:47:58.521Z","fechaCompletado":"2025-05-31T19:36:26.158Z","estado":"EN_PROGRESO","tiempoTotalSegundos":11359,"ipAddress":"yieldingly joyfully","userAgent":"curiously aha insidious"},
    }).then(({ body }) => {
      respuestaUsuario = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/testdonesespirituales/api/don-espirituals',
      body: {"nombre":"lid definitive","nombreCorto":"swanling thoroughly intrigue","descripcion":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","caracteristicas":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","versiculosBiblicos":"Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=","activo":true,"ordenPresentacion":24825},
    }).then(({ body }) => {
      donEspiritual = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/resultado-dons+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/resultado-dons').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/resultado-dons/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/testdonesespirituales/api/interpretacions', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/respuesta-usuarios', {
      statusCode: 200,
      body: [respuestaUsuario],
    });

    cy.intercept('GET', '/services/testdonesespirituales/api/don-espirituals', {
      statusCode: 200,
      body: [donEspiritual],
    });

  });
   */

  afterEach(() => {
    if (resultadoDon) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/resultado-dons/${resultadoDon.id}`,
      }).then(() => {
        resultadoDon = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (respuestaUsuario) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/respuesta-usuarios/${respuestaUsuario.id}`,
      }).then(() => {
        respuestaUsuario = undefined;
      });
    }
    if (donEspiritual) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/don-espirituals/${donEspiritual.id}`,
      }).then(() => {
        donEspiritual = undefined;
      });
    }
  });
   */

  it('ResultadoDons menu should load ResultadoDons page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('resultado-don');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ResultadoDon').should('exist');
    cy.url().should('match', resultadoDonPageUrlPattern);
  });

  describe('ResultadoDon page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(resultadoDonPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ResultadoDon page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/resultado-don/new$'));
        cy.getEntityCreateUpdateHeading('ResultadoDon');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', resultadoDonPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/resultado-dons',
          body: {
            ...resultadoDonSample,
            respuestaUsuario: respuestaUsuario,
            donEspiritual: donEspiritual,
          },
        }).then(({ body }) => {
          resultadoDon = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/resultado-dons+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/testdonesespirituales/api/resultado-dons?page=0&size=20>; rel="last",<http://localhost/services/testdonesespirituales/api/resultado-dons?page=0&size=20>; rel="first"',
              },
              body: [resultadoDon],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(resultadoDonPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(resultadoDonPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details ResultadoDon page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('resultadoDon');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', resultadoDonPageUrlPattern);
      });

      it('edit button click should load edit ResultadoDon page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ResultadoDon');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', resultadoDonPageUrlPattern);
      });

      it('edit button click should load edit ResultadoDon page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ResultadoDon');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', resultadoDonPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of ResultadoDon', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('resultadoDon').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', resultadoDonPageUrlPattern);

        resultadoDon = undefined;
      });
    });
  });

  describe('new ResultadoDon page', () => {
    beforeEach(() => {
      cy.visit(`${resultadoDonPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ResultadoDon');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of ResultadoDon', () => {
      cy.get(`[data-cy="puntuacionTotal"]`).type('26727');
      cy.get(`[data-cy="puntuacionTotal"]`).should('have.value', '26727');

      cy.get(`[data-cy="porcentaje"]`).type('10.3');
      cy.get(`[data-cy="porcentaje"]`).should('have.value', '10.3');

      cy.get(`[data-cy="rankingPosicion"]`).type('20990');
      cy.get(`[data-cy="rankingPosicion"]`).should('have.value', '20990');

      cy.get(`[data-cy="esDonPrincipal"]`).should('not.be.checked');
      cy.get(`[data-cy="esDonPrincipal"]`).click();
      cy.get(`[data-cy="esDonPrincipal"]`).should('be.checked');

      cy.get(`[data-cy="respuestaUsuario"]`).select(1);
      cy.get(`[data-cy="donEspiritual"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        resultadoDon = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', resultadoDonPageUrlPattern);
    });
  });
});
