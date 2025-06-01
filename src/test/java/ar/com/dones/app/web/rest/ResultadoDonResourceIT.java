package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.ResultadoDonAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static ar.com.dones.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.ResultadoDon;
import ar.com.dones.app.repository.ResultadoDonRepository;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
import ar.com.dones.app.service.mapper.ResultadoDonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResultadoDonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResultadoDonResourceIT {

  private static final Integer DEFAULT_PUNTUACION_TOTAL = 0;
  private static final Integer UPDATED_PUNTUACION_TOTAL = 1;

  private static final BigDecimal DEFAULT_PORCENTAJE = new BigDecimal(0);
  private static final BigDecimal UPDATED_PORCENTAJE = new BigDecimal(1);

  private static final Integer DEFAULT_RANKING_POSICION = 1;
  private static final Integer UPDATED_RANKING_POSICION = 2;

  private static final Boolean DEFAULT_ES_DON_PRINCIPAL = false;
  private static final Boolean UPDATED_ES_DON_PRINCIPAL = true;

  private static final String ENTITY_API_URL = "/api/resultado-dons";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private ResultadoDonRepository resultadoDonRepository;

  @Autowired
  private ResultadoDonMapper resultadoDonMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restResultadoDonMockMvc;

  private ResultadoDon resultadoDon;

  private ResultadoDon insertedResultadoDon;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ResultadoDon createEntity(EntityManager em) {
    ResultadoDon resultadoDon = new ResultadoDon()
      .puntuacionTotal(DEFAULT_PUNTUACION_TOTAL)
      .porcentaje(DEFAULT_PORCENTAJE)
      .rankingPosicion(DEFAULT_RANKING_POSICION)
      .esDonPrincipal(DEFAULT_ES_DON_PRINCIPAL);
    // Add required entity
    RespuestaUsuario respuestaUsuario;
    if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
      respuestaUsuario = RespuestaUsuarioResourceIT.createEntity(em);
      em.persist(respuestaUsuario);
      em.flush();
    } else {
      respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
    }
    resultadoDon.setRespuestaUsuario(respuestaUsuario);
    // Add required entity
    DonEspiritual donEspiritual;
    if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
      donEspiritual = DonEspiritualResourceIT.createEntity();
      em.persist(donEspiritual);
      em.flush();
    } else {
      donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
    }
    resultadoDon.setDonEspiritual(donEspiritual);
    return resultadoDon;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ResultadoDon createUpdatedEntity(EntityManager em) {
    ResultadoDon updatedResultadoDon = new ResultadoDon()
      .puntuacionTotal(UPDATED_PUNTUACION_TOTAL)
      .porcentaje(UPDATED_PORCENTAJE)
      .rankingPosicion(UPDATED_RANKING_POSICION)
      .esDonPrincipal(UPDATED_ES_DON_PRINCIPAL);
    // Add required entity
    RespuestaUsuario respuestaUsuario;
    if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
      respuestaUsuario = RespuestaUsuarioResourceIT.createUpdatedEntity(em);
      em.persist(respuestaUsuario);
      em.flush();
    } else {
      respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
    }
    updatedResultadoDon.setRespuestaUsuario(respuestaUsuario);
    // Add required entity
    DonEspiritual donEspiritual;
    if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
      donEspiritual = DonEspiritualResourceIT.createUpdatedEntity();
      em.persist(donEspiritual);
      em.flush();
    } else {
      donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
    }
    updatedResultadoDon.setDonEspiritual(donEspiritual);
    return updatedResultadoDon;
  }

  @BeforeEach
  void initTest() {
    resultadoDon = createEntity(em);
  }

  @AfterEach
  void cleanup() {
    if (insertedResultadoDon != null) {
      resultadoDonRepository.delete(insertedResultadoDon);
      insertedResultadoDon = null;
    }
  }

  @Test
  @Transactional
  void createResultadoDon() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);
    var returnedResultadoDonDTO = om.readValue(
      restResultadoDonMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      ResultadoDonDTO.class
    );

    // Validate the ResultadoDon in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedResultadoDon = resultadoDonMapper.toEntity(returnedResultadoDonDTO);
    assertResultadoDonUpdatableFieldsEquals(returnedResultadoDon, getPersistedResultadoDon(returnedResultadoDon));

    insertedResultadoDon = returnedResultadoDon;
  }

  @Test
  @Transactional
  void createResultadoDonWithExistingId() throws Exception {
    // Create the ResultadoDon with an existing ID
    resultadoDon.setId(1L);
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restResultadoDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isBadRequest());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkPuntuacionTotalIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    resultadoDon.setPuntuacionTotal(null);

    // Create the ResultadoDon, which fails.
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    restResultadoDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkPorcentajeIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    resultadoDon.setPorcentaje(null);

    // Create the ResultadoDon, which fails.
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    restResultadoDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkRankingPosicionIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    resultadoDon.setRankingPosicion(null);

    // Create the ResultadoDon, which fails.
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    restResultadoDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkEsDonPrincipalIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    resultadoDon.setEsDonPrincipal(null);

    // Create the ResultadoDon, which fails.
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    restResultadoDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllResultadoDons() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    // Get all the resultadoDonList
    restResultadoDonMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoDon.getId().intValue())))
      .andExpect(jsonPath("$.[*].puntuacionTotal").value(hasItem(DEFAULT_PUNTUACION_TOTAL)))
      .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(sameNumber(DEFAULT_PORCENTAJE))))
      .andExpect(jsonPath("$.[*].rankingPosicion").value(hasItem(DEFAULT_RANKING_POSICION)))
      .andExpect(jsonPath("$.[*].esDonPrincipal").value(hasItem(DEFAULT_ES_DON_PRINCIPAL)));
  }

  @Test
  @Transactional
  void getResultadoDon() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    // Get the resultadoDon
    restResultadoDonMockMvc
      .perform(get(ENTITY_API_URL_ID, resultadoDon.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(resultadoDon.getId().intValue()))
      .andExpect(jsonPath("$.puntuacionTotal").value(DEFAULT_PUNTUACION_TOTAL))
      .andExpect(jsonPath("$.porcentaje").value(sameNumber(DEFAULT_PORCENTAJE)))
      .andExpect(jsonPath("$.rankingPosicion").value(DEFAULT_RANKING_POSICION))
      .andExpect(jsonPath("$.esDonPrincipal").value(DEFAULT_ES_DON_PRINCIPAL));
  }

  @Test
  @Transactional
  void getNonExistingResultadoDon() throws Exception {
    // Get the resultadoDon
    restResultadoDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingResultadoDon() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the resultadoDon
    ResultadoDon updatedResultadoDon = resultadoDonRepository.findById(resultadoDon.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedResultadoDon are not directly saved in db
    em.detach(updatedResultadoDon);
    updatedResultadoDon
      .puntuacionTotal(UPDATED_PUNTUACION_TOTAL)
      .porcentaje(UPDATED_PORCENTAJE)
      .rankingPosicion(UPDATED_RANKING_POSICION)
      .esDonPrincipal(UPDATED_ES_DON_PRINCIPAL);
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(updatedResultadoDon);

    restResultadoDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, resultadoDonDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(resultadoDonDTO))
      )
      .andExpect(status().isOk());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedResultadoDonToMatchAllProperties(updatedResultadoDon);
  }

  @Test
  @Transactional
  void putNonExistingResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, resultadoDonDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(resultadoDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(resultadoDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateResultadoDonWithPatch() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the resultadoDon using partial update
    ResultadoDon partialUpdatedResultadoDon = new ResultadoDon();
    partialUpdatedResultadoDon.setId(resultadoDon.getId());

    restResultadoDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedResultadoDon.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedResultadoDon))
      )
      .andExpect(status().isOk());

    // Validate the ResultadoDon in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertResultadoDonUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedResultadoDon, resultadoDon),
      getPersistedResultadoDon(resultadoDon)
    );
  }

  @Test
  @Transactional
  void fullUpdateResultadoDonWithPatch() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the resultadoDon using partial update
    ResultadoDon partialUpdatedResultadoDon = new ResultadoDon();
    partialUpdatedResultadoDon.setId(resultadoDon.getId());

    partialUpdatedResultadoDon
      .puntuacionTotal(UPDATED_PUNTUACION_TOTAL)
      .porcentaje(UPDATED_PORCENTAJE)
      .rankingPosicion(UPDATED_RANKING_POSICION)
      .esDonPrincipal(UPDATED_ES_DON_PRINCIPAL);

    restResultadoDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedResultadoDon.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedResultadoDon))
      )
      .andExpect(status().isOk());

    // Validate the ResultadoDon in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertResultadoDonUpdatableFieldsEquals(partialUpdatedResultadoDon, getPersistedResultadoDon(partialUpdatedResultadoDon));
  }

  @Test
  @Transactional
  void patchNonExistingResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, resultadoDonDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(resultadoDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(resultadoDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamResultadoDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    resultadoDon.setId(longCount.incrementAndGet());

    // Create the ResultadoDon
    ResultadoDonDTO resultadoDonDTO = resultadoDonMapper.toDto(resultadoDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restResultadoDonMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(resultadoDonDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ResultadoDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteResultadoDon() throws Exception {
    // Initialize the database
    insertedResultadoDon = resultadoDonRepository.saveAndFlush(resultadoDon);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the resultadoDon
    restResultadoDonMockMvc
      .perform(delete(ENTITY_API_URL_ID, resultadoDon.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return resultadoDonRepository.count();
  }

  protected void assertIncrementedRepositoryCount(long countBefore) {
    assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
  }

  protected void assertDecrementedRepositoryCount(long countBefore) {
    assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
  }

  protected void assertSameRepositoryCount(long countBefore) {
    assertThat(countBefore).isEqualTo(getRepositoryCount());
  }

  protected ResultadoDon getPersistedResultadoDon(ResultadoDon resultadoDon) {
    return resultadoDonRepository.findById(resultadoDon.getId()).orElseThrow();
  }

  protected void assertPersistedResultadoDonToMatchAllProperties(ResultadoDon expectedResultadoDon) {
    assertResultadoDonAllPropertiesEquals(expectedResultadoDon, getPersistedResultadoDon(expectedResultadoDon));
  }

  protected void assertPersistedResultadoDonToMatchUpdatableProperties(ResultadoDon expectedResultadoDon) {
    assertResultadoDonAllUpdatablePropertiesEquals(expectedResultadoDon, getPersistedResultadoDon(expectedResultadoDon));
  }
}
