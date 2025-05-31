package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.SesionUsuarioAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.SesionUsuario;
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.repository.SesionUsuarioRepository;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import ar.com.dones.app.service.mapper.SesionUsuarioMapper;
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
 * Integration tests for the {@link SesionUsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SesionUsuarioResourceIT {

    private static final String DEFAULT_RESPUESTAS_TEMPORALES = "AAAAAAAAAA";
    private static final String UPDATED_RESPUESTAS_TEMPORALES = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_EXPIRACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_EXPIRACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_COMPLETADA = false;
    private static final Boolean UPDATED_COMPLETADA = true;

    private static final String ENTITY_API_URL = "/api/sesion-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SesionUsuarioRepository sesionUsuarioRepository;

    @Autowired
    private SesionUsuarioMapper sesionUsuarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSesionUsuarioMockMvc;

    private SesionUsuario sesionUsuario;

    private SesionUsuario insertedSesionUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SesionUsuario createEntity(EntityManager em) {
        SesionUsuario sesionUsuario = new SesionUsuario()
            .respuestasTemporales(DEFAULT_RESPUESTAS_TEMPORALES)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaExpiracion(DEFAULT_FECHA_EXPIRACION)
            .completada(DEFAULT_COMPLETADA);
        // Add required entity
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            usuario = UsuarioResourceIT.createEntity();
            em.persist(usuario);
            em.flush();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        sesionUsuario.setUsuario(usuario);
        return sesionUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SesionUsuario createUpdatedEntity(EntityManager em) {
        SesionUsuario updatedSesionUsuario = new SesionUsuario()
            .respuestasTemporales(UPDATED_RESPUESTAS_TEMPORALES)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .completada(UPDATED_COMPLETADA);
        // Add required entity
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            usuario = UsuarioResourceIT.createUpdatedEntity();
            em.persist(usuario);
            em.flush();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        updatedSesionUsuario.setUsuario(usuario);
        return updatedSesionUsuario;
    }

    @BeforeEach
    void initTest() {
        sesionUsuario = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSesionUsuario != null) {
            sesionUsuarioRepository.delete(insertedSesionUsuario);
            insertedSesionUsuario = null;
        }
    }

    @Test
    @Transactional
    void createSesionUsuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);
        var returnedSesionUsuarioDTO = om.readValue(
            restSesionUsuarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SesionUsuarioDTO.class
        );

        // Validate the SesionUsuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSesionUsuario = sesionUsuarioMapper.toEntity(returnedSesionUsuarioDTO);
        assertSesionUsuarioUpdatableFieldsEquals(returnedSesionUsuario, getPersistedSesionUsuario(returnedSesionUsuario));

        insertedSesionUsuario = returnedSesionUsuario;
    }

    @Test
    @Transactional
    void createSesionUsuarioWithExistingId() throws Exception {
        // Create the SesionUsuario with an existing ID
        sesionUsuario.setId(1L);
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaCreacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sesionUsuario.setFechaCreacion(null);

        // Create the SesionUsuario, which fails.
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaExpiracionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sesionUsuario.setFechaExpiracion(null);

        // Create the SesionUsuario, which fails.
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompletadaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sesionUsuario.setCompletada(null);

        // Create the SesionUsuario, which fails.
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSesionUsuarios() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        // Get all the sesionUsuarioList
        restSesionUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sesionUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].respuestasTemporales").value(hasItem(DEFAULT_RESPUESTAS_TEMPORALES)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaExpiracion").value(hasItem(DEFAULT_FECHA_EXPIRACION.toString())))
            .andExpect(jsonPath("$.[*].completada").value(hasItem(DEFAULT_COMPLETADA)));
    }

    @Test
    @Transactional
    void getSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        // Get the sesionUsuario
        restSesionUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, sesionUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sesionUsuario.getId().intValue()))
            .andExpect(jsonPath("$.respuestasTemporales").value(DEFAULT_RESPUESTAS_TEMPORALES))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaExpiracion").value(DEFAULT_FECHA_EXPIRACION.toString()))
            .andExpect(jsonPath("$.completada").value(DEFAULT_COMPLETADA));
    }

    @Test
    @Transactional
    void getNonExistingSesionUsuario() throws Exception {
        // Get the sesionUsuario
        restSesionUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario
        SesionUsuario updatedSesionUsuario = sesionUsuarioRepository.findById(sesionUsuario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSesionUsuario are not directly saved in db
        em.detach(updatedSesionUsuario);
        updatedSesionUsuario
            .respuestasTemporales(UPDATED_RESPUESTAS_TEMPORALES)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .completada(UPDATED_COMPLETADA);
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(updatedSesionUsuario);

        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSesionUsuarioToMatchAllProperties(updatedSesionUsuario);
    }

    @Test
    @Transactional
    void putNonExistingSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSesionUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario using partial update
        SesionUsuario partialUpdatedSesionUsuario = new SesionUsuario();
        partialUpdatedSesionUsuario.setId(sesionUsuario.getId());

        partialUpdatedSesionUsuario.fechaCreacion(UPDATED_FECHA_CREACION);

        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesionUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesionUsuario))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUsuarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSesionUsuario, sesionUsuario),
            getPersistedSesionUsuario(sesionUsuario)
        );
    }

    @Test
    @Transactional
    void fullUpdateSesionUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario using partial update
        SesionUsuario partialUpdatedSesionUsuario = new SesionUsuario();
        partialUpdatedSesionUsuario.setId(sesionUsuario.getId());

        partialUpdatedSesionUsuario
            .respuestasTemporales(UPDATED_RESPUESTAS_TEMPORALES)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .completada(UPDATED_COMPLETADA);

        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesionUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesionUsuario))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUsuarioUpdatableFieldsEquals(partialUpdatedSesionUsuario, getPersistedSesionUsuario(partialUpdatedSesionUsuario));
    }

    @Test
    @Transactional
    void patchNonExistingSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sesionUsuario
        restSesionUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, sesionUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sesionUsuarioRepository.count();
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

    protected SesionUsuario getPersistedSesionUsuario(SesionUsuario sesionUsuario) {
        return sesionUsuarioRepository.findById(sesionUsuario.getId()).orElseThrow();
    }

    protected void assertPersistedSesionUsuarioToMatchAllProperties(SesionUsuario expectedSesionUsuario) {
        assertSesionUsuarioAllPropertiesEquals(expectedSesionUsuario, getPersistedSesionUsuario(expectedSesionUsuario));
    }

    protected void assertPersistedSesionUsuarioToMatchUpdatableProperties(SesionUsuario expectedSesionUsuario) {
        assertSesionUsuarioAllUpdatablePropertiesEquals(expectedSesionUsuario, getPersistedSesionUsuario(expectedSesionUsuario));
    }
}
