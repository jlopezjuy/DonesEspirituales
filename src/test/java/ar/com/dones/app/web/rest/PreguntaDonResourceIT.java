package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.PreguntaDonAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.domain.PreguntaDon;
import ar.com.dones.app.repository.PreguntaDonRepository;
import ar.com.dones.app.service.dto.PreguntaDonDTO;
import ar.com.dones.app.service.mapper.PreguntaDonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PreguntaDonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreguntaDonResourceIT {

  private static final Integer DEFAULT_PESO = 1;
  private static final Integer UPDATED_PESO = 2;

  private static final Boolean DEFAULT_ACTIVA = false;
  private static final Boolean UPDATED_ACTIVA = true;

  private static final String ENTITY_API_URL = "/api/pregunta-dons";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private PreguntaDonRepository preguntaDonRepository;

  @Autowired
  private PreguntaDonMapper preguntaDonMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restPreguntaDonMockMvc;

  private PreguntaDon preguntaDon;

  private PreguntaDon insertedPreguntaDon;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static PreguntaDon createEntity(EntityManager em) {
    PreguntaDon preguntaDon = new PreguntaDon().peso(DEFAULT_PESO).activa(DEFAULT_ACTIVA);
    // Add required entity
    Pregunta pregunta;
    if (TestUtil.findAll(em, Pregunta.class).isEmpty()) {
      pregunta = PreguntaResourceIT.createEntity(em);
      em.persist(pregunta);
      em.flush();
    } else {
      pregunta = TestUtil.findAll(em, Pregunta.class).get(0);
    }
    preguntaDon.setPregunta(pregunta);
    // Add required entity
    DonEspiritual donEspiritual;
    if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
      donEspiritual = DonEspiritualResourceIT.createEntity();
      em.persist(donEspiritual);
      em.flush();
    } else {
      donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
    }
    preguntaDon.setDonEspiritual(donEspiritual);
    return preguntaDon;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static PreguntaDon createUpdatedEntity(EntityManager em) {
    PreguntaDon updatedPreguntaDon = new PreguntaDon().peso(UPDATED_PESO).activa(UPDATED_ACTIVA);
    // Add required entity
    Pregunta pregunta;
    if (TestUtil.findAll(em, Pregunta.class).isEmpty()) {
      pregunta = PreguntaResourceIT.createUpdatedEntity(em);
      em.persist(pregunta);
      em.flush();
    } else {
      pregunta = TestUtil.findAll(em, Pregunta.class).get(0);
    }
    updatedPreguntaDon.setPregunta(pregunta);
    // Add required entity
    DonEspiritual donEspiritual;
    if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
      donEspiritual = DonEspiritualResourceIT.createUpdatedEntity();
      em.persist(donEspiritual);
      em.flush();
    } else {
      donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
    }
    updatedPreguntaDon.setDonEspiritual(donEspiritual);
    return updatedPreguntaDon;
  }

  @BeforeEach
  void initTest() {
    preguntaDon = createEntity(em);
  }

  @AfterEach
  void cleanup() {
    if (insertedPreguntaDon != null) {
      preguntaDonRepository.delete(insertedPreguntaDon);
      insertedPreguntaDon = null;
    }
  }

  @Test
  @Transactional
  void createPreguntaDon() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);
    var returnedPreguntaDonDTO = om.readValue(
      restPreguntaDonMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      PreguntaDonDTO.class
    );

    // Validate the PreguntaDon in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedPreguntaDon = preguntaDonMapper.toEntity(returnedPreguntaDonDTO);
    assertPreguntaDonUpdatableFieldsEquals(returnedPreguntaDon, getPersistedPreguntaDon(returnedPreguntaDon));

    insertedPreguntaDon = returnedPreguntaDon;
  }

  @Test
  @Transactional
  void createPreguntaDonWithExistingId() throws Exception {
    // Create the PreguntaDon with an existing ID
    preguntaDon.setId(1L);
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restPreguntaDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO)))
      .andExpect(status().isBadRequest());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkPesoIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    preguntaDon.setPeso(null);

    // Create the PreguntaDon, which fails.
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    restPreguntaDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkActivaIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    preguntaDon.setActiva(null);

    // Create the PreguntaDon, which fails.
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    restPreguntaDonMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllPreguntaDons() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    // Get all the preguntaDonList
    restPreguntaDonMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(preguntaDon.getId().intValue())))
      .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)))
      .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA)));
  }

  @Test
  @Transactional
  void getPreguntaDon() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    // Get the preguntaDon
    restPreguntaDonMockMvc
      .perform(get(ENTITY_API_URL_ID, preguntaDon.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(preguntaDon.getId().intValue()))
      .andExpect(jsonPath("$.peso").value(DEFAULT_PESO))
      .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA));
  }

  @Test
  @Transactional
  void getNonExistingPreguntaDon() throws Exception {
    // Get the preguntaDon
    restPreguntaDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingPreguntaDon() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the preguntaDon
    PreguntaDon updatedPreguntaDon = preguntaDonRepository.findById(preguntaDon.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedPreguntaDon are not directly saved in db
    em.detach(updatedPreguntaDon);
    updatedPreguntaDon.peso(UPDATED_PESO).activa(UPDATED_ACTIVA);
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(updatedPreguntaDon);

    restPreguntaDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, preguntaDonDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO))
      )
      .andExpect(status().isOk());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedPreguntaDonToMatchAllProperties(updatedPreguntaDon);
  }

  @Test
  @Transactional
  void putNonExistingPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, preguntaDonDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(preguntaDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDonDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdatePreguntaDonWithPatch() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the preguntaDon using partial update
    PreguntaDon partialUpdatedPreguntaDon = new PreguntaDon();
    partialUpdatedPreguntaDon.setId(preguntaDon.getId());

    partialUpdatedPreguntaDon.peso(UPDATED_PESO).activa(UPDATED_ACTIVA);

    restPreguntaDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPreguntaDon.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedPreguntaDon))
      )
      .andExpect(status().isOk());

    // Validate the PreguntaDon in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPreguntaDonUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedPreguntaDon, preguntaDon),
      getPersistedPreguntaDon(preguntaDon)
    );
  }

  @Test
  @Transactional
  void fullUpdatePreguntaDonWithPatch() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the preguntaDon using partial update
    PreguntaDon partialUpdatedPreguntaDon = new PreguntaDon();
    partialUpdatedPreguntaDon.setId(preguntaDon.getId());

    partialUpdatedPreguntaDon.peso(UPDATED_PESO).activa(UPDATED_ACTIVA);

    restPreguntaDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPreguntaDon.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedPreguntaDon))
      )
      .andExpect(status().isOk());

    // Validate the PreguntaDon in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPreguntaDonUpdatableFieldsEquals(partialUpdatedPreguntaDon, getPersistedPreguntaDon(partialUpdatedPreguntaDon));
  }

  @Test
  @Transactional
  void patchNonExistingPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, preguntaDonDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(preguntaDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(preguntaDonDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamPreguntaDon() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    preguntaDon.setId(longCount.incrementAndGet());

    // Create the PreguntaDon
    PreguntaDonDTO preguntaDonDTO = preguntaDonMapper.toDto(preguntaDon);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaDonMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(preguntaDonDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the PreguntaDon in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deletePreguntaDon() throws Exception {
    // Initialize the database
    insertedPreguntaDon = preguntaDonRepository.saveAndFlush(preguntaDon);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the preguntaDon
    restPreguntaDonMockMvc
      .perform(delete(ENTITY_API_URL_ID, preguntaDon.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return preguntaDonRepository.count();
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

  protected PreguntaDon getPersistedPreguntaDon(PreguntaDon preguntaDon) {
    return preguntaDonRepository.findById(preguntaDon.getId()).orElseThrow();
  }

  protected void assertPersistedPreguntaDonToMatchAllProperties(PreguntaDon expectedPreguntaDon) {
    assertPreguntaDonAllPropertiesEquals(expectedPreguntaDon, getPersistedPreguntaDon(expectedPreguntaDon));
  }

  protected void assertPersistedPreguntaDonToMatchUpdatableProperties(PreguntaDon expectedPreguntaDon) {
    assertPreguntaDonAllUpdatablePropertiesEquals(expectedPreguntaDon, getPersistedPreguntaDon(expectedPreguntaDon));
  }
}
