package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.PreguntaAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.repository.PreguntaRepository;
import ar.com.dones.app.service.dto.PreguntaDTO;
import ar.com.dones.app.service.mapper.PreguntaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PreguntaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreguntaResourceIT {

  private static final Integer DEFAULT_NUMERO_PREGUNTA = 1;
  private static final Integer UPDATED_NUMERO_PREGUNTA = 2;

  private static final String DEFAULT_TEXTO_PREGUNTA = "AAAAAAAAAA";
  private static final String UPDATED_TEXTO_PREGUNTA = "BBBBBBBBBB";

  private static final Boolean DEFAULT_OBLIGATORIA = false;
  private static final Boolean UPDATED_OBLIGATORIA = true;

  private static final Boolean DEFAULT_ACTIVA = false;
  private static final Boolean UPDATED_ACTIVA = true;

  private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final String ENTITY_API_URL = "/api/preguntas";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private PreguntaRepository preguntaRepository;

  @Autowired
  private PreguntaMapper preguntaMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restPreguntaMockMvc;

  private Pregunta pregunta;

  private Pregunta insertedPregunta;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Pregunta createEntity(EntityManager em) {
    Pregunta pregunta = new Pregunta()
      .numeroPregunta(DEFAULT_NUMERO_PREGUNTA)
      .textoPregunta(DEFAULT_TEXTO_PREGUNTA)
      .obligatoria(DEFAULT_OBLIGATORIA)
      .activa(DEFAULT_ACTIVA)
      .fechaCreacion(DEFAULT_FECHA_CREACION);
    // Add required entity
    Cuestionario cuestionario;
    if (TestUtil.findAll(em, Cuestionario.class).isEmpty()) {
      cuestionario = CuestionarioResourceIT.createEntity();
      em.persist(cuestionario);
      em.flush();
    } else {
      cuestionario = TestUtil.findAll(em, Cuestionario.class).get(0);
    }
    pregunta.setCuestionario(cuestionario);
    return pregunta;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Pregunta createUpdatedEntity(EntityManager em) {
    Pregunta updatedPregunta = new Pregunta()
      .numeroPregunta(UPDATED_NUMERO_PREGUNTA)
      .textoPregunta(UPDATED_TEXTO_PREGUNTA)
      .obligatoria(UPDATED_OBLIGATORIA)
      .activa(UPDATED_ACTIVA)
      .fechaCreacion(UPDATED_FECHA_CREACION);
    // Add required entity
    Cuestionario cuestionario;
    if (TestUtil.findAll(em, Cuestionario.class).isEmpty()) {
      cuestionario = CuestionarioResourceIT.createUpdatedEntity();
      em.persist(cuestionario);
      em.flush();
    } else {
      cuestionario = TestUtil.findAll(em, Cuestionario.class).get(0);
    }
    updatedPregunta.setCuestionario(cuestionario);
    return updatedPregunta;
  }

  @BeforeEach
  void initTest() {
    pregunta = createEntity(em);
  }

  @AfterEach
  void cleanup() {
    if (insertedPregunta != null) {
      preguntaRepository.delete(insertedPregunta);
      insertedPregunta = null;
    }
  }

  @Test
  @Transactional
  void createPregunta() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);
    var returnedPreguntaDTO = om.readValue(
      restPreguntaMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      PreguntaDTO.class
    );

    // Validate the Pregunta in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedPregunta = preguntaMapper.toEntity(returnedPreguntaDTO);
    assertPreguntaUpdatableFieldsEquals(returnedPregunta, getPersistedPregunta(returnedPregunta));

    insertedPregunta = returnedPregunta;
  }

  @Test
  @Transactional
  void createPreguntaWithExistingId() throws Exception {
    // Create the Pregunta with an existing ID
    pregunta.setId(1L);
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restPreguntaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isBadRequest());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkNumeroPreguntaIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    pregunta.setNumeroPregunta(null);

    // Create the Pregunta, which fails.
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    restPreguntaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkObligatoriaIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    pregunta.setObligatoria(null);

    // Create the Pregunta, which fails.
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    restPreguntaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkActivaIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    pregunta.setActiva(null);

    // Create the Pregunta, which fails.
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    restPreguntaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFechaCreacionIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    pregunta.setFechaCreacion(null);

    // Create the Pregunta, which fails.
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    restPreguntaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllPreguntas() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    // Get all the preguntaList
    restPreguntaMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(pregunta.getId().intValue())))
      .andExpect(jsonPath("$.[*].numeroPregunta").value(hasItem(DEFAULT_NUMERO_PREGUNTA)))
      .andExpect(jsonPath("$.[*].textoPregunta").value(hasItem(DEFAULT_TEXTO_PREGUNTA)))
      .andExpect(jsonPath("$.[*].obligatoria").value(hasItem(DEFAULT_OBLIGATORIA)))
      .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA)))
      .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())));
  }

  @Test
  @Transactional
  void getPregunta() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    // Get the pregunta
    restPreguntaMockMvc
      .perform(get(ENTITY_API_URL_ID, pregunta.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(pregunta.getId().intValue()))
      .andExpect(jsonPath("$.numeroPregunta").value(DEFAULT_NUMERO_PREGUNTA))
      .andExpect(jsonPath("$.textoPregunta").value(DEFAULT_TEXTO_PREGUNTA))
      .andExpect(jsonPath("$.obligatoria").value(DEFAULT_OBLIGATORIA))
      .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA))
      .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()));
  }

  @Test
  @Transactional
  void getNonExistingPregunta() throws Exception {
    // Get the pregunta
    restPreguntaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingPregunta() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the pregunta
    Pregunta updatedPregunta = preguntaRepository.findById(pregunta.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedPregunta are not directly saved in db
    em.detach(updatedPregunta);
    updatedPregunta
      .numeroPregunta(UPDATED_NUMERO_PREGUNTA)
      .textoPregunta(UPDATED_TEXTO_PREGUNTA)
      .obligatoria(UPDATED_OBLIGATORIA)
      .activa(UPDATED_ACTIVA)
      .fechaCreacion(UPDATED_FECHA_CREACION);
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(updatedPregunta);

    restPreguntaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, preguntaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO))
      )
      .andExpect(status().isOk());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedPreguntaToMatchAllProperties(updatedPregunta);
  }

  @Test
  @Transactional
  void putNonExistingPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, preguntaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(preguntaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdatePreguntaWithPatch() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the pregunta using partial update
    Pregunta partialUpdatedPregunta = new Pregunta();
    partialUpdatedPregunta.setId(pregunta.getId());

    partialUpdatedPregunta.numeroPregunta(UPDATED_NUMERO_PREGUNTA).textoPregunta(UPDATED_TEXTO_PREGUNTA);

    restPreguntaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPregunta.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedPregunta))
      )
      .andExpect(status().isOk());

    // Validate the Pregunta in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPreguntaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPregunta, pregunta), getPersistedPregunta(pregunta));
  }

  @Test
  @Transactional
  void fullUpdatePreguntaWithPatch() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the pregunta using partial update
    Pregunta partialUpdatedPregunta = new Pregunta();
    partialUpdatedPregunta.setId(pregunta.getId());

    partialUpdatedPregunta
      .numeroPregunta(UPDATED_NUMERO_PREGUNTA)
      .textoPregunta(UPDATED_TEXTO_PREGUNTA)
      .obligatoria(UPDATED_OBLIGATORIA)
      .activa(UPDATED_ACTIVA)
      .fechaCreacion(UPDATED_FECHA_CREACION);

    restPreguntaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedPregunta.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedPregunta))
      )
      .andExpect(status().isOk());

    // Validate the Pregunta in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPreguntaUpdatableFieldsEquals(partialUpdatedPregunta, getPersistedPregunta(partialUpdatedPregunta));
  }

  @Test
  @Transactional
  void patchNonExistingPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, preguntaDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(preguntaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(preguntaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamPregunta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    pregunta.setId(longCount.incrementAndGet());

    // Create the Pregunta
    PreguntaDTO preguntaDTO = preguntaMapper.toDto(pregunta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restPreguntaMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(preguntaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Pregunta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deletePregunta() throws Exception {
    // Initialize the database
    insertedPregunta = preguntaRepository.saveAndFlush(pregunta);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the pregunta
    restPreguntaMockMvc
      .perform(delete(ENTITY_API_URL_ID, pregunta.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return preguntaRepository.count();
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

  protected Pregunta getPersistedPregunta(Pregunta pregunta) {
    return preguntaRepository.findById(pregunta.getId()).orElseThrow();
  }

  protected void assertPersistedPreguntaToMatchAllProperties(Pregunta expectedPregunta) {
    assertPreguntaAllPropertiesEquals(expectedPregunta, getPersistedPregunta(expectedPregunta));
  }

  protected void assertPersistedPreguntaToMatchUpdatableProperties(Pregunta expectedPregunta) {
    assertPreguntaAllUpdatablePropertiesEquals(expectedPregunta, getPersistedPregunta(expectedPregunta));
  }
}
