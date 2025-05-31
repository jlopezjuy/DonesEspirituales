package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.DetalleRespuestaAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.DetalleRespuesta;
import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.repository.DetalleRespuestaRepository;
import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
import ar.com.dones.app.service.mapper.DetalleRespuestaMapper;
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
 * Integration tests for the {@link DetalleRespuestaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetalleRespuestaResourceIT {

    private static final Integer DEFAULT_VALOR_RESPUESTA = 0;
    private static final Integer UPDATED_VALOR_RESPUESTA = 1;

    private static final Instant DEFAULT_TIMESTAMP_RESPUESTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP_RESPUESTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TIEMPO_PREGUNTA_SEGUNDOS = 0;
    private static final Integer UPDATED_TIEMPO_PREGUNTA_SEGUNDOS = 1;

    private static final String ENTITY_API_URL = "/api/detalle-respuestas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DetalleRespuestaRepository detalleRespuestaRepository;

    @Autowired
    private DetalleRespuestaMapper detalleRespuestaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetalleRespuestaMockMvc;

    private DetalleRespuesta detalleRespuesta;

    private DetalleRespuesta insertedDetalleRespuesta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleRespuesta createEntity(EntityManager em) {
        DetalleRespuesta detalleRespuesta = new DetalleRespuesta()
            .valorRespuesta(DEFAULT_VALOR_RESPUESTA)
            .timestampRespuesta(DEFAULT_TIMESTAMP_RESPUESTA)
            .tiempoPreguntaSegundos(DEFAULT_TIEMPO_PREGUNTA_SEGUNDOS);
        // Add required entity
        EscalaRespuesta escalaRespuesta;
        if (TestUtil.findAll(em, EscalaRespuesta.class).isEmpty()) {
            escalaRespuesta = EscalaRespuestaResourceIT.createEntity();
            em.persist(escalaRespuesta);
            em.flush();
        } else {
            escalaRespuesta = TestUtil.findAll(em, EscalaRespuesta.class).get(0);
        }
        detalleRespuesta.setEscalaRespuesta(escalaRespuesta);
        // Add required entity
        Pregunta pregunta;
        if (TestUtil.findAll(em, Pregunta.class).isEmpty()) {
            pregunta = PreguntaResourceIT.createEntity(em);
            em.persist(pregunta);
            em.flush();
        } else {
            pregunta = TestUtil.findAll(em, Pregunta.class).get(0);
        }
        detalleRespuesta.setPregunta(pregunta);
        // Add required entity
        RespuestaUsuario respuestaUsuario;
        if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
            respuestaUsuario = RespuestaUsuarioResourceIT.createEntity(em);
            em.persist(respuestaUsuario);
            em.flush();
        } else {
            respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
        }
        detalleRespuesta.setRespuestaUsuario(respuestaUsuario);
        return detalleRespuesta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleRespuesta createUpdatedEntity(EntityManager em) {
        DetalleRespuesta updatedDetalleRespuesta = new DetalleRespuesta()
            .valorRespuesta(UPDATED_VALOR_RESPUESTA)
            .timestampRespuesta(UPDATED_TIMESTAMP_RESPUESTA)
            .tiempoPreguntaSegundos(UPDATED_TIEMPO_PREGUNTA_SEGUNDOS);
        // Add required entity
        EscalaRespuesta escalaRespuesta;
        if (TestUtil.findAll(em, EscalaRespuesta.class).isEmpty()) {
            escalaRespuesta = EscalaRespuestaResourceIT.createUpdatedEntity();
            em.persist(escalaRespuesta);
            em.flush();
        } else {
            escalaRespuesta = TestUtil.findAll(em, EscalaRespuesta.class).get(0);
        }
        updatedDetalleRespuesta.setEscalaRespuesta(escalaRespuesta);
        // Add required entity
        Pregunta pregunta;
        if (TestUtil.findAll(em, Pregunta.class).isEmpty()) {
            pregunta = PreguntaResourceIT.createUpdatedEntity(em);
            em.persist(pregunta);
            em.flush();
        } else {
            pregunta = TestUtil.findAll(em, Pregunta.class).get(0);
        }
        updatedDetalleRespuesta.setPregunta(pregunta);
        // Add required entity
        RespuestaUsuario respuestaUsuario;
        if (TestUtil.findAll(em, RespuestaUsuario.class).isEmpty()) {
            respuestaUsuario = RespuestaUsuarioResourceIT.createUpdatedEntity(em);
            em.persist(respuestaUsuario);
            em.flush();
        } else {
            respuestaUsuario = TestUtil.findAll(em, RespuestaUsuario.class).get(0);
        }
        updatedDetalleRespuesta.setRespuestaUsuario(respuestaUsuario);
        return updatedDetalleRespuesta;
    }

    @BeforeEach
    void initTest() {
        detalleRespuesta = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedDetalleRespuesta != null) {
            detalleRespuestaRepository.delete(insertedDetalleRespuesta);
            insertedDetalleRespuesta = null;
        }
    }

    @Test
    @Transactional
    void createDetalleRespuesta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);
        var returnedDetalleRespuestaDTO = om.readValue(
            restDetalleRespuestaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detalleRespuestaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DetalleRespuestaDTO.class
        );

        // Validate the DetalleRespuesta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDetalleRespuesta = detalleRespuestaMapper.toEntity(returnedDetalleRespuestaDTO);
        assertDetalleRespuestaUpdatableFieldsEquals(returnedDetalleRespuesta, getPersistedDetalleRespuesta(returnedDetalleRespuesta));

        insertedDetalleRespuesta = returnedDetalleRespuesta;
    }

    @Test
    @Transactional
    void createDetalleRespuestaWithExistingId() throws Exception {
        // Create the DetalleRespuesta with an existing ID
        detalleRespuesta.setId(1L);
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detalleRespuestaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorRespuestaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        detalleRespuesta.setValorRespuesta(null);

        // Create the DetalleRespuesta, which fails.
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        restDetalleRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detalleRespuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimestampRespuestaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        detalleRespuesta.setTimestampRespuesta(null);

        // Create the DetalleRespuesta, which fails.
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        restDetalleRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detalleRespuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetalleRespuestas() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        // Get all the detalleRespuestaList
        restDetalleRespuestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleRespuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorRespuesta").value(hasItem(DEFAULT_VALOR_RESPUESTA)))
            .andExpect(jsonPath("$.[*].timestampRespuesta").value(hasItem(DEFAULT_TIMESTAMP_RESPUESTA.toString())))
            .andExpect(jsonPath("$.[*].tiempoPreguntaSegundos").value(hasItem(DEFAULT_TIEMPO_PREGUNTA_SEGUNDOS)));
    }

    @Test
    @Transactional
    void getDetalleRespuesta() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        // Get the detalleRespuesta
        restDetalleRespuestaMockMvc
            .perform(get(ENTITY_API_URL_ID, detalleRespuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detalleRespuesta.getId().intValue()))
            .andExpect(jsonPath("$.valorRespuesta").value(DEFAULT_VALOR_RESPUESTA))
            .andExpect(jsonPath("$.timestampRespuesta").value(DEFAULT_TIMESTAMP_RESPUESTA.toString()))
            .andExpect(jsonPath("$.tiempoPreguntaSegundos").value(DEFAULT_TIEMPO_PREGUNTA_SEGUNDOS));
    }

    @Test
    @Transactional
    void getNonExistingDetalleRespuesta() throws Exception {
        // Get the detalleRespuesta
        restDetalleRespuestaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetalleRespuesta() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detalleRespuesta
        DetalleRespuesta updatedDetalleRespuesta = detalleRespuestaRepository.findById(detalleRespuesta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDetalleRespuesta are not directly saved in db
        em.detach(updatedDetalleRespuesta);
        updatedDetalleRespuesta
            .valorRespuesta(UPDATED_VALOR_RESPUESTA)
            .timestampRespuesta(UPDATED_TIMESTAMP_RESPUESTA)
            .tiempoPreguntaSegundos(UPDATED_TIEMPO_PREGUNTA_SEGUNDOS);
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(updatedDetalleRespuesta);

        restDetalleRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleRespuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(detalleRespuestaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDetalleRespuestaToMatchAllProperties(updatedDetalleRespuesta);
    }

    @Test
    @Transactional
    void putNonExistingDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detalleRespuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(detalleRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(detalleRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(detalleRespuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetalleRespuestaWithPatch() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detalleRespuesta using partial update
        DetalleRespuesta partialUpdatedDetalleRespuesta = new DetalleRespuesta();
        partialUpdatedDetalleRespuesta.setId(detalleRespuesta.getId());

        partialUpdatedDetalleRespuesta
            .timestampRespuesta(UPDATED_TIMESTAMP_RESPUESTA)
            .tiempoPreguntaSegundos(UPDATED_TIEMPO_PREGUNTA_SEGUNDOS);

        restDetalleRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleRespuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDetalleRespuesta))
            )
            .andExpect(status().isOk());

        // Validate the DetalleRespuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDetalleRespuestaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDetalleRespuesta, detalleRespuesta),
            getPersistedDetalleRespuesta(detalleRespuesta)
        );
    }

    @Test
    @Transactional
    void fullUpdateDetalleRespuestaWithPatch() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the detalleRespuesta using partial update
        DetalleRespuesta partialUpdatedDetalleRespuesta = new DetalleRespuesta();
        partialUpdatedDetalleRespuesta.setId(detalleRespuesta.getId());

        partialUpdatedDetalleRespuesta
            .valorRespuesta(UPDATED_VALOR_RESPUESTA)
            .timestampRespuesta(UPDATED_TIMESTAMP_RESPUESTA)
            .tiempoPreguntaSegundos(UPDATED_TIEMPO_PREGUNTA_SEGUNDOS);

        restDetalleRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetalleRespuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDetalleRespuesta))
            )
            .andExpect(status().isOk());

        // Validate the DetalleRespuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDetalleRespuestaUpdatableFieldsEquals(
            partialUpdatedDetalleRespuesta,
            getPersistedDetalleRespuesta(partialUpdatedDetalleRespuesta)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detalleRespuestaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(detalleRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(detalleRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetalleRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        detalleRespuesta.setId(longCount.incrementAndGet());

        // Create the DetalleRespuesta
        DetalleRespuestaDTO detalleRespuestaDTO = detalleRespuestaMapper.toDto(detalleRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetalleRespuestaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(detalleRespuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetalleRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetalleRespuesta() throws Exception {
        // Initialize the database
        insertedDetalleRespuesta = detalleRespuestaRepository.saveAndFlush(detalleRespuesta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the detalleRespuesta
        restDetalleRespuestaMockMvc
            .perform(delete(ENTITY_API_URL_ID, detalleRespuesta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return detalleRespuestaRepository.count();
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

    protected DetalleRespuesta getPersistedDetalleRespuesta(DetalleRespuesta detalleRespuesta) {
        return detalleRespuestaRepository.findById(detalleRespuesta.getId()).orElseThrow();
    }

    protected void assertPersistedDetalleRespuestaToMatchAllProperties(DetalleRespuesta expectedDetalleRespuesta) {
        assertDetalleRespuestaAllPropertiesEquals(expectedDetalleRespuesta, getPersistedDetalleRespuesta(expectedDetalleRespuesta));
    }

    protected void assertPersistedDetalleRespuestaToMatchUpdatableProperties(DetalleRespuesta expectedDetalleRespuesta) {
        assertDetalleRespuestaAllUpdatablePropertiesEquals(
            expectedDetalleRespuesta,
            getPersistedDetalleRespuesta(expectedDetalleRespuesta)
        );
    }
}
