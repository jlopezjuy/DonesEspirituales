package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.CuestionarioAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.repository.CuestionarioRepository;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.mapper.CuestionarioMapper;
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
 * Integration tests for the {@link CuestionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CuestionarioResourceIT {

  private static final String DEFAULT_TITULO = "AAAAAAAAAA";
  private static final String UPDATED_TITULO = "BBBBBBBBBB";

  private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
  private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

  private static final String DEFAULT_INSTRUCCIONES = "AAAAAAAAAA";
  private static final String UPDATED_INSTRUCCIONES = "BBBBBBBBBB";

  private static final Integer DEFAULT_TOTAL_PREGUNTAS = 1;
  private static final Integer UPDATED_TOTAL_PREGUNTAS = 2;

  private static final Boolean DEFAULT_ACTIVO = false;
  private static final Boolean UPDATED_ACTIVO = true;

  private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final Instant DEFAULT_FECHA_ACTUALIZACION = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_ACTUALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final Integer DEFAULT_VERSION = 1;
  private static final Integer UPDATED_VERSION = 2;

  private static final String ENTITY_API_URL = "/api/cuestionarios";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private CuestionarioRepository cuestionarioRepository;

  @Autowired
  private CuestionarioMapper cuestionarioMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restCuestionarioMockMvc;

  private Cuestionario cuestionario;

  private Cuestionario insertedCuestionario;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Cuestionario createEntity() {
    return new Cuestionario()
      .titulo(DEFAULT_TITULO)
      .descripcion(DEFAULT_DESCRIPCION)
      .instrucciones(DEFAULT_INSTRUCCIONES)
      .totalPreguntas(DEFAULT_TOTAL_PREGUNTAS)
      .activo(DEFAULT_ACTIVO)
      .fechaCreacion(DEFAULT_FECHA_CREACION)
      .fechaActualizacion(DEFAULT_FECHA_ACTUALIZACION)
      .version(DEFAULT_VERSION);
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Cuestionario createUpdatedEntity() {
    return new Cuestionario()
      .titulo(UPDATED_TITULO)
      .descripcion(UPDATED_DESCRIPCION)
      .instrucciones(UPDATED_INSTRUCCIONES)
      .totalPreguntas(UPDATED_TOTAL_PREGUNTAS)
      .activo(UPDATED_ACTIVO)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
      .version(UPDATED_VERSION);
  }

  @BeforeEach
  void initTest() {
    cuestionario = createEntity();
  }

  @AfterEach
  void cleanup() {
    if (insertedCuestionario != null) {
      cuestionarioRepository.delete(insertedCuestionario);
      insertedCuestionario = null;
    }
  }

  @Test
  @Transactional
  void createCuestionario() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);
    var returnedCuestionarioDTO = om.readValue(
      restCuestionarioMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      CuestionarioDTO.class
    );

    // Validate the Cuestionario in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedCuestionario = cuestionarioMapper.toEntity(returnedCuestionarioDTO);
    assertCuestionarioUpdatableFieldsEquals(returnedCuestionario, getPersistedCuestionario(returnedCuestionario));

    insertedCuestionario = returnedCuestionario;
  }

  @Test
  @Transactional
  void createCuestionarioWithExistingId() throws Exception {
    // Create the Cuestionario with an existing ID
    cuestionario.setId(1L);
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkTituloIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    cuestionario.setTitulo(null);

    // Create the Cuestionario, which fails.
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkTotalPreguntasIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    cuestionario.setTotalPreguntas(null);

    // Create the Cuestionario, which fails.
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkActivoIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    cuestionario.setActivo(null);

    // Create the Cuestionario, which fails.
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFechaCreacionIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    cuestionario.setFechaCreacion(null);

    // Create the Cuestionario, which fails.
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkVersionIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    cuestionario.setVersion(null);

    // Create the Cuestionario, which fails.
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    restCuestionarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllCuestionarios() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    // Get all the cuestionarioList
    restCuestionarioMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(cuestionario.getId().intValue())))
      .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
      .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
      .andExpect(jsonPath("$.[*].instrucciones").value(hasItem(DEFAULT_INSTRUCCIONES)))
      .andExpect(jsonPath("$.[*].totalPreguntas").value(hasItem(DEFAULT_TOTAL_PREGUNTAS)))
      .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)))
      .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
      .andExpect(jsonPath("$.[*].fechaActualizacion").value(hasItem(DEFAULT_FECHA_ACTUALIZACION.toString())))
      .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
  }

  @Test
  @Transactional
  void getCuestionario() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    // Get the cuestionario
    restCuestionarioMockMvc
      .perform(get(ENTITY_API_URL_ID, cuestionario.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(cuestionario.getId().intValue()))
      .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
      .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
      .andExpect(jsonPath("$.instrucciones").value(DEFAULT_INSTRUCCIONES))
      .andExpect(jsonPath("$.totalPreguntas").value(DEFAULT_TOTAL_PREGUNTAS))
      .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO))
      .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
      .andExpect(jsonPath("$.fechaActualizacion").value(DEFAULT_FECHA_ACTUALIZACION.toString()))
      .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
  }

  @Test
  @Transactional
  void getNonExistingCuestionario() throws Exception {
    // Get the cuestionario
    restCuestionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingCuestionario() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the cuestionario
    Cuestionario updatedCuestionario = cuestionarioRepository.findById(cuestionario.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedCuestionario are not directly saved in db
    em.detach(updatedCuestionario);
    updatedCuestionario
      .titulo(UPDATED_TITULO)
      .descripcion(UPDATED_DESCRIPCION)
      .instrucciones(UPDATED_INSTRUCCIONES)
      .totalPreguntas(UPDATED_TOTAL_PREGUNTAS)
      .activo(UPDATED_ACTIVO)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
      .version(UPDATED_VERSION);
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(updatedCuestionario);

    restCuestionarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, cuestionarioDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(cuestionarioDTO))
      )
      .andExpect(status().isOk());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedCuestionarioToMatchAllProperties(updatedCuestionario);
  }

  @Test
  @Transactional
  void putNonExistingCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, cuestionarioDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(cuestionarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(cuestionarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateCuestionarioWithPatch() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the cuestionario using partial update
    Cuestionario partialUpdatedCuestionario = new Cuestionario();
    partialUpdatedCuestionario.setId(cuestionario.getId());

    partialUpdatedCuestionario
      .descripcion(UPDATED_DESCRIPCION)
      .instrucciones(UPDATED_INSTRUCCIONES)
      .activo(UPDATED_ACTIVO)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
      .version(UPDATED_VERSION);

    restCuestionarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCuestionario.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedCuestionario))
      )
      .andExpect(status().isOk());

    // Validate the Cuestionario in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertCuestionarioUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedCuestionario, cuestionario),
      getPersistedCuestionario(cuestionario)
    );
  }

  @Test
  @Transactional
  void fullUpdateCuestionarioWithPatch() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the cuestionario using partial update
    Cuestionario partialUpdatedCuestionario = new Cuestionario();
    partialUpdatedCuestionario.setId(cuestionario.getId());

    partialUpdatedCuestionario
      .titulo(UPDATED_TITULO)
      .descripcion(UPDATED_DESCRIPCION)
      .instrucciones(UPDATED_INSTRUCCIONES)
      .totalPreguntas(UPDATED_TOTAL_PREGUNTAS)
      .activo(UPDATED_ACTIVO)
      .fechaCreacion(UPDATED_FECHA_CREACION)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION)
      .version(UPDATED_VERSION);

    restCuestionarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCuestionario.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedCuestionario))
      )
      .andExpect(status().isOk());

    // Validate the Cuestionario in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertCuestionarioUpdatableFieldsEquals(partialUpdatedCuestionario, getPersistedCuestionario(partialUpdatedCuestionario));
  }

  @Test
  @Transactional
  void patchNonExistingCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, cuestionarioDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(cuestionarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(cuestionarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamCuestionario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    cuestionario.setId(longCount.incrementAndGet());

    // Create the Cuestionario
    CuestionarioDTO cuestionarioDTO = cuestionarioMapper.toDto(cuestionario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCuestionarioMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cuestionarioDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Cuestionario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteCuestionario() throws Exception {
    // Initialize the database
    insertedCuestionario = cuestionarioRepository.saveAndFlush(cuestionario);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the cuestionario
    restCuestionarioMockMvc
      .perform(delete(ENTITY_API_URL_ID, cuestionario.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return cuestionarioRepository.count();
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

  protected Cuestionario getPersistedCuestionario(Cuestionario cuestionario) {
    return cuestionarioRepository.findById(cuestionario.getId()).orElseThrow();
  }

  protected void assertPersistedCuestionarioToMatchAllProperties(Cuestionario expectedCuestionario) {
    assertCuestionarioAllPropertiesEquals(expectedCuestionario, getPersistedCuestionario(expectedCuestionario));
  }

  protected void assertPersistedCuestionarioToMatchUpdatableProperties(Cuestionario expectedCuestionario) {
    assertCuestionarioAllUpdatablePropertiesEquals(expectedCuestionario, getPersistedCuestionario(expectedCuestionario));
  }
}
