package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.AuditoriaRespuestaAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.AuditoriaRespuesta;
import ar.com.dones.app.domain.DetalleRespuesta;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.repository.AuditoriaRespuestaRepository;
import ar.com.dones.app.service.dto.AuditoriaRespuestaDTO;
import ar.com.dones.app.service.mapper.AuditoriaRespuestaMapper;
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
 * Integration tests for the {@link AuditoriaRespuestaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditoriaRespuestaResourceIT {

  private static final Integer DEFAULT_VALOR_ANTERIOR = 0;
  private static final Integer UPDATED_VALOR_ANTERIOR = 1;

  private static final Integer DEFAULT_VALOR_NUEVO = 0;
  private static final Integer UPDATED_VALOR_NUEVO = 1;

  private static final Instant DEFAULT_TIMESTAMP_CAMBIO = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_TIMESTAMP_CAMBIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final String DEFAULT_MOTIVO_CAMBIO = "AAAAAAAAAA";
  private static final String UPDATED_MOTIVO_CAMBIO = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/auditoria-respuestas";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private AuditoriaRespuestaRepository auditoriaRespuestaRepository;

  @Autowired
  private AuditoriaRespuestaMapper auditoriaRespuestaMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restAuditoriaRespuestaMockMvc;

  private AuditoriaRespuesta auditoriaRespuesta;

  private AuditoriaRespuesta insertedAuditoriaRespuesta;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AuditoriaRespuesta createEntity(EntityManager em) {
    AuditoriaRespuesta auditoriaRespuesta = new AuditoriaRespuesta()
      .valorAnterior(DEFAULT_VALOR_ANTERIOR)
      .valorNuevo(DEFAULT_VALOR_NUEVO)
      .timestampCambio(DEFAULT_TIMESTAMP_CAMBIO)
      .motivoCambio(DEFAULT_MOTIVO_CAMBIO);
    // Add required entity
    RespuestaUsuario respuestaUsuario;
    if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
      respuestaUsuario = RespuestaUsuarioResourceIT.createEntity(em);
      em.persist(respuestaUsuario);
      em.flush();
    } else {
      respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
    }
    auditoriaRespuesta.setRespuestaUsuario(respuestaUsuario);
    // Add required entity
    DetalleRespuesta detalleRespuesta;
    if (TestUtil.findAll(em, DetalleRespuesta.class).isEmpty()) {
      detalleRespuesta = DetalleRespuestaResourceIT.createEntity(em);
      em.persist(detalleRespuesta);
      em.flush();
    } else {
      detalleRespuesta = TestUtil.findAll(em, DetalleRespuesta.class).get(0);
    }
    auditoriaRespuesta.setDetalleRespuesta(detalleRespuesta);
    return auditoriaRespuesta;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static AuditoriaRespuesta createUpdatedEntity(EntityManager em) {
    AuditoriaRespuesta updatedAuditoriaRespuesta = new AuditoriaRespuesta()
      .valorAnterior(UPDATED_VALOR_ANTERIOR)
      .valorNuevo(UPDATED_VALOR_NUEVO)
      .timestampCambio(UPDATED_TIMESTAMP_CAMBIO)
      .motivoCambio(UPDATED_MOTIVO_CAMBIO);
    // Add required entity
    RespuestaUsuario respuestaUsuario;
    if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
      respuestaUsuario = RespuestaUsuarioResourceIT.createUpdatedEntity(em);
      em.persist(respuestaUsuario);
      em.flush();
    } else {
      respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
    }
    updatedAuditoriaRespuesta.setRespuestaUsuario(respuestaUsuario);
    // Add required entity
    DetalleRespuesta detalleRespuesta;
    if (TestUtil.findAll(em, DetalleRespuesta.class).isEmpty()) {
      detalleRespuesta = DetalleRespuestaResourceIT.createUpdatedEntity(em);
      em.persist(detalleRespuesta);
      em.flush();
    } else {
      detalleRespuesta = TestUtil.findAll(em, DetalleRespuesta.class).get(0);
    }
    updatedAuditoriaRespuesta.setDetalleRespuesta(detalleRespuesta);
    return updatedAuditoriaRespuesta;
  }

  @BeforeEach
  void initTest() {
    auditoriaRespuesta = createEntity(em);
  }

  @AfterEach
  void cleanup() {
    if (insertedAuditoriaRespuesta != null) {
      auditoriaRespuestaRepository.delete(insertedAuditoriaRespuesta);
      insertedAuditoriaRespuesta = null;
    }
  }

  @Test
  @Transactional
  void createAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);
    var returnedAuditoriaRespuestaDTO = om.readValue(
      restAuditoriaRespuestaMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      AuditoriaRespuestaDTO.class
    );

    // Validate the AuditoriaRespuesta in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedAuditoriaRespuesta = auditoriaRespuestaMapper.toEntity(returnedAuditoriaRespuestaDTO);
    assertAuditoriaRespuestaUpdatableFieldsEquals(returnedAuditoriaRespuesta, getPersistedAuditoriaRespuesta(returnedAuditoriaRespuesta));

    insertedAuditoriaRespuesta = returnedAuditoriaRespuesta;
  }

  @Test
  @Transactional
  void createAuditoriaRespuestaWithExistingId() throws Exception {
    // Create the AuditoriaRespuesta with an existing ID
    auditoriaRespuesta.setId(1L);
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restAuditoriaRespuestaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
      .andExpect(status().isBadRequest());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkValorNuevoIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    auditoriaRespuesta.setValorNuevo(null);

    // Create the AuditoriaRespuesta, which fails.
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    restAuditoriaRespuestaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkTimestampCambioIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    auditoriaRespuesta.setTimestampCambio(null);

    // Create the AuditoriaRespuesta, which fails.
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    restAuditoriaRespuestaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllAuditoriaRespuestas() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    // Get all the auditoriaRespuestaList
    restAuditoriaRespuestaMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(auditoriaRespuesta.getId().intValue())))
      .andExpect(jsonPath("$.[*].valorAnterior").value(hasItem(DEFAULT_VALOR_ANTERIOR)))
      .andExpect(jsonPath("$.[*].valorNuevo").value(hasItem(DEFAULT_VALOR_NUEVO)))
      .andExpect(jsonPath("$.[*].timestampCambio").value(hasItem(DEFAULT_TIMESTAMP_CAMBIO.toString())))
      .andExpect(jsonPath("$.[*].motivoCambio").value(hasItem(DEFAULT_MOTIVO_CAMBIO)));
  }

  @Test
  @Transactional
  void getAuditoriaRespuesta() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    // Get the auditoriaRespuesta
    restAuditoriaRespuestaMockMvc
      .perform(get(ENTITY_API_URL_ID, auditoriaRespuesta.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(auditoriaRespuesta.getId().intValue()))
      .andExpect(jsonPath("$.valorAnterior").value(DEFAULT_VALOR_ANTERIOR))
      .andExpect(jsonPath("$.valorNuevo").value(DEFAULT_VALOR_NUEVO))
      .andExpect(jsonPath("$.timestampCambio").value(DEFAULT_TIMESTAMP_CAMBIO.toString()))
      .andExpect(jsonPath("$.motivoCambio").value(DEFAULT_MOTIVO_CAMBIO));
  }

  @Test
  @Transactional
  void getNonExistingAuditoriaRespuesta() throws Exception {
    // Get the auditoriaRespuesta
    restAuditoriaRespuestaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingAuditoriaRespuesta() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the auditoriaRespuesta
    AuditoriaRespuesta updatedAuditoriaRespuesta = auditoriaRespuestaRepository.findById(auditoriaRespuesta.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedAuditoriaRespuesta are not directly saved in db
    em.detach(updatedAuditoriaRespuesta);
    updatedAuditoriaRespuesta
      .valorAnterior(UPDATED_VALOR_ANTERIOR)
      .valorNuevo(UPDATED_VALOR_NUEVO)
      .timestampCambio(UPDATED_TIMESTAMP_CAMBIO)
      .motivoCambio(UPDATED_MOTIVO_CAMBIO);
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(updatedAuditoriaRespuesta);

    restAuditoriaRespuestaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, auditoriaRespuestaDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(auditoriaRespuestaDTO))
      )
      .andExpect(status().isOk());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedAuditoriaRespuestaToMatchAllProperties(updatedAuditoriaRespuesta);
  }

  @Test
  @Transactional
  void putNonExistingAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, auditoriaRespuestaDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(auditoriaRespuestaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(auditoriaRespuestaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateAuditoriaRespuestaWithPatch() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the auditoriaRespuesta using partial update
    AuditoriaRespuesta partialUpdatedAuditoriaRespuesta = new AuditoriaRespuesta();
    partialUpdatedAuditoriaRespuesta.setId(auditoriaRespuesta.getId());

    partialUpdatedAuditoriaRespuesta.valorAnterior(UPDATED_VALOR_ANTERIOR).timestampCambio(UPDATED_TIMESTAMP_CAMBIO);

    restAuditoriaRespuestaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAuditoriaRespuesta.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedAuditoriaRespuesta))
      )
      .andExpect(status().isOk());

    // Validate the AuditoriaRespuesta in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertAuditoriaRespuestaUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedAuditoriaRespuesta, auditoriaRespuesta),
      getPersistedAuditoriaRespuesta(auditoriaRespuesta)
    );
  }

  @Test
  @Transactional
  void fullUpdateAuditoriaRespuestaWithPatch() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the auditoriaRespuesta using partial update
    AuditoriaRespuesta partialUpdatedAuditoriaRespuesta = new AuditoriaRespuesta();
    partialUpdatedAuditoriaRespuesta.setId(auditoriaRespuesta.getId());

    partialUpdatedAuditoriaRespuesta
      .valorAnterior(UPDATED_VALOR_ANTERIOR)
      .valorNuevo(UPDATED_VALOR_NUEVO)
      .timestampCambio(UPDATED_TIMESTAMP_CAMBIO)
      .motivoCambio(UPDATED_MOTIVO_CAMBIO);

    restAuditoriaRespuestaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedAuditoriaRespuesta.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedAuditoriaRespuesta))
      )
      .andExpect(status().isOk());

    // Validate the AuditoriaRespuesta in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertAuditoriaRespuestaUpdatableFieldsEquals(
      partialUpdatedAuditoriaRespuesta,
      getPersistedAuditoriaRespuesta(partialUpdatedAuditoriaRespuesta)
    );
  }

  @Test
  @Transactional
  void patchNonExistingAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, auditoriaRespuestaDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(auditoriaRespuestaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(auditoriaRespuestaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamAuditoriaRespuesta() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    auditoriaRespuesta.setId(longCount.incrementAndGet());

    // Create the AuditoriaRespuesta
    AuditoriaRespuestaDTO auditoriaRespuestaDTO = auditoriaRespuestaMapper.toDto(auditoriaRespuesta);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restAuditoriaRespuestaMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(auditoriaRespuestaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the AuditoriaRespuesta in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteAuditoriaRespuesta() throws Exception {
    // Initialize the database
    insertedAuditoriaRespuesta = auditoriaRespuestaRepository.saveAndFlush(auditoriaRespuesta);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the auditoriaRespuesta
    restAuditoriaRespuestaMockMvc
      .perform(delete(ENTITY_API_URL_ID, auditoriaRespuesta.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return auditoriaRespuestaRepository.count();
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

  protected AuditoriaRespuesta getPersistedAuditoriaRespuesta(AuditoriaRespuesta auditoriaRespuesta) {
    return auditoriaRespuestaRepository.findById(auditoriaRespuesta.getId()).orElseThrow();
  }

  protected void assertPersistedAuditoriaRespuestaToMatchAllProperties(AuditoriaRespuesta expectedAuditoriaRespuesta) {
    assertAuditoriaRespuestaAllPropertiesEquals(expectedAuditoriaRespuesta, getPersistedAuditoriaRespuesta(expectedAuditoriaRespuesta));
  }

  protected void assertPersistedAuditoriaRespuestaToMatchUpdatableProperties(AuditoriaRespuesta expectedAuditoriaRespuesta) {
    assertAuditoriaRespuestaAllUpdatablePropertiesEquals(
      expectedAuditoriaRespuesta,
      getPersistedAuditoriaRespuesta(expectedAuditoriaRespuesta)
    );
  }
}
