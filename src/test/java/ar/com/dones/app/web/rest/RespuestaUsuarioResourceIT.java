package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.RespuestaUsuarioAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.enumeration.EstadoRespuesta;
import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.repository.UserRepository;
import ar.com.dones.app.service.RespuestaUsuarioService;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.mapper.RespuestaUsuarioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RespuestaUsuarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RespuestaUsuarioResourceIT {

  private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final Instant DEFAULT_FECHA_COMPLETADO = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_FECHA_COMPLETADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final EstadoRespuesta DEFAULT_ESTADO = EstadoRespuesta.INICIADA;
  private static final EstadoRespuesta UPDATED_ESTADO = EstadoRespuesta.EN_PROGRESO;

  private static final Integer DEFAULT_TIEMPO_TOTAL_SEGUNDOS = 0;
  private static final Integer UPDATED_TIEMPO_TOTAL_SEGUNDOS = 1;

  private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
  private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

  private static final String DEFAULT_USER_AGENT = "AAAAAAAAAA";
  private static final String UPDATED_USER_AGENT = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/respuesta-usuarios";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ObjectMapper om;

  @Autowired
  private RespuestaUsuarioRepository respuestaUsuarioRepository;

  @Autowired
  private UserRepository userRepository;

  @Mock
  private RespuestaUsuarioRepository respuestaUsuarioRepositoryMock;

  @Autowired
  private RespuestaUsuarioMapper respuestaUsuarioMapper;

  @Mock
  private RespuestaUsuarioService respuestaUsuarioServiceMock;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restRespuestaUsuarioMockMvc;

  private RespuestaUsuario respuestaUsuario;

  private RespuestaUsuario insertedRespuestaUsuario;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RespuestaUsuario createEntity(EntityManager em) {
    RespuestaUsuario respuestaUsuario = new RespuestaUsuario()
      .fechaInicio(DEFAULT_FECHA_INICIO)
      .fechaCompletado(DEFAULT_FECHA_COMPLETADO)
      .estado(DEFAULT_ESTADO)
      .tiempoTotalSegundos(DEFAULT_TIEMPO_TOTAL_SEGUNDOS)
      .ipAddress(DEFAULT_IP_ADDRESS)
      .userAgent(DEFAULT_USER_AGENT);
    // Add required entity
    Cuestionario cuestionario;
    if (TestUtil.findAll(em, Cuestionario.class).isEmpty()) {
      cuestionario = CuestionarioResourceIT.createEntity();
      em.persist(cuestionario);
      em.flush();
    } else {
      cuestionario = TestUtil.findAll(em, Cuestionario.class).get(0);
    }
    respuestaUsuario.setCuestionario(cuestionario);
    return respuestaUsuario;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static RespuestaUsuario createUpdatedEntity(EntityManager em) {
    RespuestaUsuario updatedRespuestaUsuario = new RespuestaUsuario()
      .fechaInicio(UPDATED_FECHA_INICIO)
      .fechaCompletado(UPDATED_FECHA_COMPLETADO)
      .estado(UPDATED_ESTADO)
      .tiempoTotalSegundos(UPDATED_TIEMPO_TOTAL_SEGUNDOS)
      .ipAddress(UPDATED_IP_ADDRESS)
      .userAgent(UPDATED_USER_AGENT);
    // Add required entity
    Cuestionario cuestionario;
    if (TestUtil.findAll(em, Cuestionario.class).isEmpty()) {
      cuestionario = CuestionarioResourceIT.createUpdatedEntity();
      em.persist(cuestionario);
      em.flush();
    } else {
      cuestionario = TestUtil.findAll(em, Cuestionario.class).get(0);
    }
    updatedRespuestaUsuario.setCuestionario(cuestionario);
    return updatedRespuestaUsuario;
  }

  @BeforeEach
  void initTest() {
    respuestaUsuario = createEntity(em);
  }

  @AfterEach
  void cleanup() {
    if (insertedRespuestaUsuario != null) {
      respuestaUsuarioRepository.delete(insertedRespuestaUsuario);
      insertedRespuestaUsuario = null;
    }
  }

  @Test
  @Transactional
  void createRespuestaUsuario() throws Exception {
    long databaseSizeBeforeCreate = getRepositoryCount();
    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);
    var returnedRespuestaUsuarioDTO = om.readValue(
      restRespuestaUsuarioMockMvc
        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(respuestaUsuarioDTO)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(),
      RespuestaUsuarioDTO.class
    );

    // Validate the RespuestaUsuario in the database
    assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    var returnedRespuestaUsuario = respuestaUsuarioMapper.toEntity(returnedRespuestaUsuarioDTO);
    assertRespuestaUsuarioUpdatableFieldsEquals(returnedRespuestaUsuario, getPersistedRespuestaUsuario(returnedRespuestaUsuario));

    insertedRespuestaUsuario = returnedRespuestaUsuario;
  }

  @Test
  @Transactional
  void createRespuestaUsuarioWithExistingId() throws Exception {
    // Create the RespuestaUsuario with an existing ID
    respuestaUsuario.setId(1L);
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    long databaseSizeBeforeCreate = getRepositoryCount();

    // An entity with an existing ID cannot be created, so this API call must fail
    restRespuestaUsuarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(respuestaUsuarioDTO)))
      .andExpect(status().isBadRequest());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkFechaInicioIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    respuestaUsuario.setFechaInicio(null);

    // Create the RespuestaUsuario, which fails.
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    restRespuestaUsuarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(respuestaUsuarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkEstadoIsRequired() throws Exception {
    long databaseSizeBeforeTest = getRepositoryCount();
    // set the field null
    respuestaUsuario.setEstado(null);

    // Create the RespuestaUsuario, which fails.
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    restRespuestaUsuarioMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(respuestaUsuarioDTO)))
      .andExpect(status().isBadRequest());

    assertSameRepositoryCount(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllRespuestaUsuarios() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    // Get all the respuestaUsuarioList
    restRespuestaUsuarioMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(respuestaUsuario.getId().intValue())))
      .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
      .andExpect(jsonPath("$.[*].fechaCompletado").value(hasItem(DEFAULT_FECHA_COMPLETADO.toString())))
      .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
      .andExpect(jsonPath("$.[*].tiempoTotalSegundos").value(hasItem(DEFAULT_TIEMPO_TOTAL_SEGUNDOS)))
      .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
      .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)));
  }

  @SuppressWarnings({ "unchecked" })
  void getAllRespuestaUsuariosWithEagerRelationshipsIsEnabled() throws Exception {
    when(respuestaUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

    restRespuestaUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

    verify(respuestaUsuarioServiceMock, times(1)).findAllWithEagerRelationships(any());
  }

  @SuppressWarnings({ "unchecked" })
  void getAllRespuestaUsuariosWithEagerRelationshipsIsNotEnabled() throws Exception {
    when(respuestaUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

    restRespuestaUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
    verify(respuestaUsuarioRepositoryMock, times(1)).findAll(any(Pageable.class));
  }

  @Test
  @Transactional
  void getRespuestaUsuario() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    // Get the respuestaUsuario
    restRespuestaUsuarioMockMvc
      .perform(get(ENTITY_API_URL_ID, respuestaUsuario.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(respuestaUsuario.getId().intValue()))
      .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
      .andExpect(jsonPath("$.fechaCompletado").value(DEFAULT_FECHA_COMPLETADO.toString()))
      .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
      .andExpect(jsonPath("$.tiempoTotalSegundos").value(DEFAULT_TIEMPO_TOTAL_SEGUNDOS))
      .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
      .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT));
  }

  @Test
  @Transactional
  void getNonExistingRespuestaUsuario() throws Exception {
    // Get the respuestaUsuario
    restRespuestaUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putExistingRespuestaUsuario() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the respuestaUsuario
    RespuestaUsuario updatedRespuestaUsuario = respuestaUsuarioRepository.findById(respuestaUsuario.getId()).orElseThrow();
    // Disconnect from session so that the updates on updatedRespuestaUsuario are not directly saved in db
    em.detach(updatedRespuestaUsuario);
    updatedRespuestaUsuario
      .fechaInicio(UPDATED_FECHA_INICIO)
      .fechaCompletado(UPDATED_FECHA_COMPLETADO)
      .estado(UPDATED_ESTADO)
      .tiempoTotalSegundos(UPDATED_TIEMPO_TOTAL_SEGUNDOS)
      .ipAddress(UPDATED_IP_ADDRESS)
      .userAgent(UPDATED_USER_AGENT);
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(updatedRespuestaUsuario);

    restRespuestaUsuarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, respuestaUsuarioDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(respuestaUsuarioDTO))
      )
      .andExpect(status().isOk());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertPersistedRespuestaUsuarioToMatchAllProperties(updatedRespuestaUsuario);
  }

  @Test
  @Transactional
  void putNonExistingRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, respuestaUsuarioDTO.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(respuestaUsuarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(
        put(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(om.writeValueAsBytes(respuestaUsuarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(respuestaUsuarioDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateRespuestaUsuarioWithPatch() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the respuestaUsuario using partial update
    RespuestaUsuario partialUpdatedRespuestaUsuario = new RespuestaUsuario();
    partialUpdatedRespuestaUsuario.setId(respuestaUsuario.getId());

    partialUpdatedRespuestaUsuario.fechaInicio(UPDATED_FECHA_INICIO).ipAddress(UPDATED_IP_ADDRESS).userAgent(UPDATED_USER_AGENT);

    restRespuestaUsuarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedRespuestaUsuario.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedRespuestaUsuario))
      )
      .andExpect(status().isOk());

    // Validate the RespuestaUsuario in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertRespuestaUsuarioUpdatableFieldsEquals(
      createUpdateProxyForBean(partialUpdatedRespuestaUsuario, respuestaUsuario),
      getPersistedRespuestaUsuario(respuestaUsuario)
    );
  }

  @Test
  @Transactional
  void fullUpdateRespuestaUsuarioWithPatch() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    long databaseSizeBeforeUpdate = getRepositoryCount();

    // Update the respuestaUsuario using partial update
    RespuestaUsuario partialUpdatedRespuestaUsuario = new RespuestaUsuario();
    partialUpdatedRespuestaUsuario.setId(respuestaUsuario.getId());

    partialUpdatedRespuestaUsuario
      .fechaInicio(UPDATED_FECHA_INICIO)
      .fechaCompletado(UPDATED_FECHA_COMPLETADO)
      .estado(UPDATED_ESTADO)
      .tiempoTotalSegundos(UPDATED_TIEMPO_TOTAL_SEGUNDOS)
      .ipAddress(UPDATED_IP_ADDRESS)
      .userAgent(UPDATED_USER_AGENT);

    restRespuestaUsuarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedRespuestaUsuario.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(partialUpdatedRespuestaUsuario))
      )
      .andExpect(status().isOk());

    // Validate the RespuestaUsuario in the database

    assertSameRepositoryCount(databaseSizeBeforeUpdate);
    assertRespuestaUsuarioUpdatableFieldsEquals(
      partialUpdatedRespuestaUsuario,
      getPersistedRespuestaUsuario(partialUpdatedRespuestaUsuario)
    );
  }

  @Test
  @Transactional
  void patchNonExistingRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, respuestaUsuarioDTO.getId())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(respuestaUsuarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(om.writeValueAsBytes(respuestaUsuarioDTO))
      )
      .andExpect(status().isBadRequest());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamRespuestaUsuario() throws Exception {
    long databaseSizeBeforeUpdate = getRepositoryCount();
    respuestaUsuario.setId(longCount.incrementAndGet());

    // Create the RespuestaUsuario
    RespuestaUsuarioDTO respuestaUsuarioDTO = respuestaUsuarioMapper.toDto(respuestaUsuario);

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restRespuestaUsuarioMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(respuestaUsuarioDTO)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the RespuestaUsuario in the database
    assertSameRepositoryCount(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteRespuestaUsuario() throws Exception {
    // Initialize the database
    insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

    long databaseSizeBeforeDelete = getRepositoryCount();

    // Delete the respuestaUsuario
    restRespuestaUsuarioMockMvc
      .perform(delete(ENTITY_API_URL_ID, respuestaUsuario.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
  }

  protected long getRepositoryCount() {
    return respuestaUsuarioRepository.count();
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

  protected RespuestaUsuario getPersistedRespuestaUsuario(RespuestaUsuario respuestaUsuario) {
    return respuestaUsuarioRepository.findById(respuestaUsuario.getId()).orElseThrow();
  }

  protected void assertPersistedRespuestaUsuarioToMatchAllProperties(RespuestaUsuario expectedRespuestaUsuario) {
    assertRespuestaUsuarioAllPropertiesEquals(expectedRespuestaUsuario, getPersistedRespuestaUsuario(expectedRespuestaUsuario));
  }

  protected void assertPersistedRespuestaUsuarioToMatchUpdatableProperties(RespuestaUsuario expectedRespuestaUsuario) {
    assertRespuestaUsuarioAllUpdatablePropertiesEquals(expectedRespuestaUsuario, getPersistedRespuestaUsuario(expectedRespuestaUsuario));
  }
}
