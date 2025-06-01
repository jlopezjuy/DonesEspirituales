package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.ConfiguracionSistemaAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.ConfiguracionSistema;
import ar.com.dones.app.domain.enumeration.TipoDato;
import ar.com.dones.app.repository.ConfiguracionSistemaRepository;
import ar.com.dones.app.service.dto.ConfiguracionSistemaDTO;
import ar.com.dones.app.service.mapper.ConfiguracionSistemaMapper;
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
 * Integration tests for the {@link ConfiguracionSistemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfiguracionSistemaResourceIT {

  private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
  private static final String UPDATED_CLAVE = "BBBBBBBBBB";

  private static final String DEFAULT_VALOR = "AAAAAAAAAA";
  private static final String UPDATED_VALOR = "BBBBBBBBBB";

  private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
  private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

  private static final TipoDato DEFAULT_TIPO_DATO = TipoDato.STRING;
  private static final TipoDato UPDATED_TIPO_DATO = TipoDato.INTEGER;

  private static final Instant DEFAULT_FECHA_ACTUALIZACION = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_ACTUALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final String ENTITY_API_URL = "/api/configuracion-sistemas";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private ConfiguracionSistemaRepository configuracionSistemaRepository;

  @Autowired
  private ConfiguracionSistemaMapper configuracionSistemaMapper;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restConfiguracionSistemaMockMvc;

  private ConfiguracionSistema configuracionSistema;

  private ConfiguracionSistema insertedConfiguracionSistema;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ConfiguracionSistema createEntity() {
    return new ConfiguracionSistema()
      .clave(DEFAULT_CLAVE)
      .valor(DEFAULT_VALOR)
      .descripcion(DEFAULT_DESCRIPCION)
      .tipoDato(DEFAULT_TIPO_DATO)
      .fechaActualizacion(DEFAULT_FECHA_ACTUALIZACION);
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ConfiguracionSistema createUpdatedEntity() {
    return new ConfiguracionSistema()
      .clave(UPDATED_CLAVE)
      .valor(UPDATED_VALOR)
      .descripcion(UPDATED_DESCRIPCION)
      .tipoDato(UPDATED_TIPO_DATO)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION);
  }

  @BeforeEach
  void initTest() {
    configuracionSistema = createEntity();
  }

  @AfterEach
  void cleanup() {
    if (insertedConfiguracionSistema != null) {
      configuracionSistemaRepository.delete(insertedConfiguracionSistema);
      insertedConfiguracionSistema = null;
    }
  }

  @Test
  @Transactional
  void createConfiguracionSistema() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);
    var returnedConfiguracionSistemaDTO = om.readValue(
      restConfiguracionSistemaMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      ConfiguracionSistemaDTO.class
    );

    // Validate the ConfiguracionSistema in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedConfiguracionSistema = configuracionSistemaMapper.toEntity(returnedConfiguracionSistemaDTO);
    assertConfiguracionSistemaUpdatableFieldsEquals(
      returnedConfiguracionSistema,
      getPersistedConfiguracionSistema(returnedConfiguracionSistema)
    );

    insertedConfiguracionSistema = returnedConfiguracionSistema;
  }

  @Test
  @Transactional
  void createConfiguracionSistemaWithExistingId() throws Exception {
    // Create the ConfiguracionSistema with an existing ID
    configuracionSistema.setId(1L);
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restConfiguracionSistemaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isBadRequest());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkClaveIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    configuracionSistema.setClave(null);

    // Create the ConfiguracionSistema, which fails.
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    restConfiguracionSistemaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkValorIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    configuracionSistema.setValor(null);

    // Create the ConfiguracionSistema, which fails.
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    restConfiguracionSistemaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkTipoDatoIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    configuracionSistema.setTipoDato(null);

    // Create the ConfiguracionSistema, which fails.
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    restConfiguracionSistemaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkFechaActualizacionIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    configuracionSistema.setFechaActualizacion(null);

    // Create the ConfiguracionSistema, which fails.
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    restConfiguracionSistemaMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllConfiguracionSistemas() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    // Get all the configuracionSistemaList
    restConfiguracionSistemaMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(configuracionSistema.getId().intValue())))
      .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
      .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
      .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
      .andExpect(jsonPath("$.[*].tipoDato").value(hasItem(DEFAULT_TIPO_DATO.toString())))
      .andExpect(jsonPath("$.[*].fechaActualizacion").value(hasItem(DEFAULT_FECHA_ACTUALIZACION.toString())));
  }

  @Test
  @Transactional
  void getConfiguracionSistema() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    // Get the configuracionSistema
    restConfiguracionSistemaMockMvc
      .perform(get(ENTITY_API_URL_ID, configuracionSistema.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(configuracionSistema.getId().intValue()))
      .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
      .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
      .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
      .andExpect(jsonPath("$.tipoDato").value(DEFAULT_TIPO_DATO.toString()))
      .andExpect(jsonPath("$.fechaActualizacion").value(DEFAULT_FECHA_ACTUALIZACION.toString()));
  }

  @Test
  @Transactional
  void getNonExistingConfiguracionSistema() throws Exception {
    // Get the configuracionSistema
    restConfiguracionSistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingConfiguracionSistema() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the configuracionSistema
    ConfiguracionSistema updatedConfiguracionSistema = configuracionSistemaRepository.findById(configuracionSistema.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedConfiguracionSistema are not directly saved in db
    em.detach(updatedConfiguracionSistema);
    updatedConfiguracionSistema
      .clave(UPDATED_CLAVE)
      .valor(UPDATED_VALOR)
      .descripcion(UPDATED_DESCRIPCION)
      .tipoDato(UPDATED_TIPO_DATO)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION);
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(updatedConfiguracionSistema);

    restConfiguracionSistemaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, configuracionSistemaDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(configuracionSistemaDTO))
      )
      .andExpect(status().isOk());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedConfiguracionSistemaToMatchAllProperties(updatedConfiguracionSistema);
  }

  @Test
  @Transactional
  void putNonExistingConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, configuracionSistemaDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(configuracionSistemaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(configuracionSistemaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateConfiguracionSistemaWithPatch() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the configuracionSistema using partial update
    ConfiguracionSistema partialUpdatedConfiguracionSistema = new ConfiguracionSistema();
    partialUpdatedConfiguracionSistema.setId(configuracionSistema.getId());

    partialUpdatedConfiguracionSistema
      .clave(UPDATED_CLAVE)
      .valor(UPDATED_VALOR)
      .tipoDato(UPDATED_TIPO_DATO)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION);

    restConfiguracionSistemaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedConfiguracionSistema.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedConfiguracionSistema))
      )
      .andExpect(status().isOk());

    // Validate the ConfiguracionSistema in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertConfiguracionSistemaUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedConfiguracionSistema, configuracionSistema),
      getPersistedConfiguracionSistema(configuracionSistema)
    );
  }

  @Test
  @Transactional
  void fullUpdateConfiguracionSistemaWithPatch() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the configuracionSistema using partial update
    ConfiguracionSistema partialUpdatedConfiguracionSistema = new ConfiguracionSistema();
    partialUpdatedConfiguracionSistema.setId(configuracionSistema.getId());

    partialUpdatedConfiguracionSistema
      .clave(UPDATED_CLAVE)
      .valor(UPDATED_VALOR)
      .descripcion(UPDATED_DESCRIPCION)
      .tipoDato(UPDATED_TIPO_DATO)
      .fechaActualizacion(UPDATED_FECHA_ACTUALIZACION);

    restConfiguracionSistemaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedConfiguracionSistema.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedConfiguracionSistema))
      )
      .andExpect(status().isOk());

    // Validate the ConfiguracionSistema in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertConfiguracionSistemaUpdatableFieldsEquals(
      partialUpdatedConfiguracionSistema,
      getPersistedConfiguracionSistema(partialUpdatedConfiguracionSistema)
    );
  }

  @Test
  @Transactional
  void patchNonExistingConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, configuracionSistemaDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(configuracionSistemaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(configuracionSistemaDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamConfiguracionSistema() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    configuracionSistema.setId(longCount.incrementAndGet());

    // Create the ConfiguracionSistema
    ConfiguracionSistemaDTO configuracionSistemaDTO = configuracionSistemaMapper.toDto(configuracionSistema);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restConfiguracionSistemaMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(configuracionSistemaDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ConfiguracionSistema in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteConfiguracionSistema() throws Exception {
    // Initialize the database
    insertedConfiguracionSistema = configuracionSistemaRepository.saveAndFlush(configuracionSistema);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the configuracionSistema
    restConfiguracionSistemaMockMvc
      .perform(delete(ENTITY_API_URL_ID, configuracionSistema.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return configuracionSistemaRepository.count();
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

  protected ConfiguracionSistema getPersistedConfiguracionSistema(ConfiguracionSistema configuracionSistema) {
    return configuracionSistemaRepository.findById(configuracionSistema.getId()).orElseThrow();
  }

  protected void assertPersistedConfiguracionSistemaToMatchAllProperties(ConfiguracionSistema expectedConfiguracionSistema) {
    assertConfiguracionSistemaAllPropertiesEquals(
      expectedConfiguracionSistema,
      getPersistedConfiguracionSistema(expectedConfiguracionSistema)
    );
  }

  protected void assertPersistedConfiguracionSistemaToMatchUpdatableProperties(ConfiguracionSistema expectedConfiguracionSistema) {
    assertConfiguracionSistemaAllUpdatablePropertiesEquals(
      expectedConfiguracionSistema,
      getPersistedConfiguracionSistema(expectedConfiguracionSistema)
    );
  }
}
