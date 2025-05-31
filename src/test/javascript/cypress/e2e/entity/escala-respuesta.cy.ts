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

describe('EscalaRespuesta e2e test', () => {
  const escalaRespuestaPageUrl = '/escala-respuesta';
  const escalaRespuestaPageUrlPattern = new RegExp('/escala-respuesta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const escalaRespuestaSample = { valor: 5, etiqueta: 'wherever secrecy', orden: 17929 };

  let escalaRespuesta;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/testdonesespirituales/api/escala-respuestas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/testdonesespirituales/api/escala-respuestas').as('postEntityRequest');
    cy.intercept('DELETE', '/services/testdonesespirituales/api/escala-respuestas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (escalaRespuesta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/testdonesespirituales/api/escala-respuestas/${escalaRespuesta.id}`,
      }).then(() => {
        escalaRespuesta = undefined;
      });
    }
  });

  it('EscalaRespuestas menu should load EscalaRespuestas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('escala-respuesta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EscalaRespuesta').should('exist');
    cy.url().should('match', escalaRespuestaPageUrlPattern);
  });

  describe('EscalaRespuesta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(escalaRespuestaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EscalaRespuesta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/escala-respuesta/new$'));
        cy.getEntityCreateUpdateHeading('EscalaRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', escalaRespuestaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/testdonesespirituales/api/escala-respuestas',
          body: escalaRespuestaSample,
        }).then(({ body }) => {
          escalaRespuesta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/testdonesespirituales/api/escala-respuestas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [escalaRespuesta],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(escalaRespuestaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EscalaRespuesta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('escalaRespuesta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', escalaRespuestaPageUrlPattern);
      });

      it('edit button click should load edit EscalaRespuesta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EscalaRespuesta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', escalaRespuestaPageUrlPattern);
      });

      it('edit button click should load edit EscalaRespuesta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EscalaRespuesta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', escalaRespuestaPageUrlPattern);
      });

      it('last delete button click should delete instance of EscalaRespuesta', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('escalaRespuesta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', escalaRespuestaPageUrlPattern);

        escalaRespuesta = undefined;
      });
    });
  });

  describe('new EscalaRespuesta page', () => {
    beforeEach(() => {
      cy.visit(`${escalaRespuestaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EscalaRespuesta');
    });

    it('should create an instance of EscalaRespuesta', () => {
      cy.get(`[data-cy="valor"]`).type('0');
      cy.get(`[data-cy="valor"]`).should('have.value', '0');

      cy.get(`[data-cy="etiqueta"]`).type('geez fatally');
      cy.get(`[data-cy="etiqueta"]`).should('have.value', 'geez fatally');

      cy.get(`[data-cy="descripcion"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="descripcion"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="orden"]`).type('22398');
      cy.get(`[data-cy="orden"]`).should('have.value', '22398');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        escalaRespuesta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', escalaRespuestaPageUrlPattern);
    });
  });
});
