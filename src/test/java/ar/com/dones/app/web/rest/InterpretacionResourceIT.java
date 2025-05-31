package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.InterpretacionAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.Interpretacion;
import ar.com.dones.app.domain.enumeration.NivelInterpretacion;
import ar.com.dones.app.repository.InterpretacionRepository;
import ar.com.dones.app.service.dto.InterpretacionDTO;
import ar.com.dones.app.service.mapper.InterpretacionMapper;
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
 * Integration tests for the {@link InterpretacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterpretacionResourceIT {

    private static final Integer DEFAULT_PUNTUACION_MINIMA = 0;
    private static final Integer UPDATED_PUNTUACION_MINIMA = 1;

    private static final Integer DEFAULT_PUNTUACION_MAXIMA = 0;
    private static final Integer UPDATED_PUNTUACION_MAXIMA = 1;

    private static final NivelInterpretacion DEFAULT_NIVEL = NivelInterpretacion.MUY_BAJO;
    private static final NivelInterpretacion UPDATED_NIVEL = NivelInterpretacion.BAJO;

    private static final String DEFAULT_DESCRIPCION_NIVEL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_NIVEL = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDACIONES = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_AREAS_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_AREAS_SERVICIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/interpretacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InterpretacionRepository interpretacionRepository;

    @Autowired
    private InterpretacionMapper interpretacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterpretacionMockMvc;

    private Interpretacion interpretacion;

    private Interpretacion insertedInterpretacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interpretacion createEntity(EntityManager em) {
        Interpretacion interpretacion = new Interpretacion()
            .puntuacionMinima(DEFAULT_PUNTUACION_MINIMA)
            .puntuacionMaxima(DEFAULT_PUNTUACION_MAXIMA)
            .nivel(DEFAULT_NIVEL)
            .descripcionNivel(DEFAULT_DESCRIPCION_NIVEL)
            .recomendaciones(DEFAULT_RECOMENDACIONES)
            .areasServicio(DEFAULT_AREAS_SERVICIO);
        // Add required entity
        DonEspiritual donEspiritual;
        if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
            donEspiritual = DonEspiritualResourceIT.createEntity();
            em.persist(donEspiritual);
            em.flush();
        } else {
            donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
        }
        interpretacion.setDonEspiritual(donEspiritual);
        return interpretacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interpretacion createUpdatedEntity(EntityManager em) {
        Interpretacion updatedInterpretacion = new Interpretacion()
            .puntuacionMinima(UPDATED_PUNTUACION_MINIMA)
            .puntuacionMaxima(UPDATED_PUNTUACION_MAXIMA)
            .nivel(UPDATED_NIVEL)
            .descripcionNivel(UPDATED_DESCRIPCION_NIVEL)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .areasServicio(UPDATED_AREAS_SERVICIO);
        // Add required entity
        DonEspiritual donEspiritual;
        if (TestUtil.findAll(em, DonEspiritual.class).isEmpty()) {
            donEspiritual = DonEspiritualResourceIT.createUpdatedEntity();
            em.persist(donEspiritual);
            em.flush();
        } else {
            donEspiritual = TestUtil.findAll(em, DonEspiritual.class).get(0);
        }
        updatedInterpretacion.setDonEspiritual(donEspiritual);
        return updatedInterpretacion;
    }

    @BeforeEach
    void initTest() {
        interpretacion = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedInterpretacion != null) {
            interpretacionRepository.delete(insertedInterpretacion);
            insertedInterpretacion = null;
        }
    }

    @Test
    @Transactional
    void createInterpretacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);
        var returnedInterpretacionDTO = om.readValue(
            restInterpretacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InterpretacionDTO.class
        );

        // Validate the Interpretacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInterpretacion = interpretacionMapper.toEntity(returnedInterpretacionDTO);
        assertInterpretacionUpdatableFieldsEquals(returnedInterpretacion, getPersistedInterpretacion(returnedInterpretacion));

        insertedInterpretacion = returnedInterpretacion;
    }

    @Test
    @Transactional
    void createInterpretacionWithExistingId() throws Exception {
        // Create the Interpretacion with an existing ID
        interpretacion.setId(1L);
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterpretacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPuntuacionMinimaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        interpretacion.setPuntuacionMinima(null);

        // Create the Interpretacion, which fails.
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        restInterpretacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPuntuacionMaximaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        interpretacion.setPuntuacionMaxima(null);

        // Create the Interpretacion, which fails.
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        restInterpretacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNivelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        interpretacion.setNivel(null);

        // Create the Interpretacion, which fails.
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        restInterpretacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInterpretacions() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        // Get all the interpretacionList
        restInterpretacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interpretacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntuacionMinima").value(hasItem(DEFAULT_PUNTUACION_MINIMA)))
            .andExpect(jsonPath("$.[*].puntuacionMaxima").value(hasItem(DEFAULT_PUNTUACION_MAXIMA)))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL.toString())))
            .andExpect(jsonPath("$.[*].descripcionNivel").value(hasItem(DEFAULT_DESCRIPCION_NIVEL)))
            .andExpect(jsonPath("$.[*].recomendaciones").value(hasItem(DEFAULT_RECOMENDACIONES)))
            .andExpect(jsonPath("$.[*].areasServicio").value(hasItem(DEFAULT_AREAS_SERVICIO)));
    }

    @Test
    @Transactional
    void getInterpretacion() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        // Get the interpretacion
        restInterpretacionMockMvc
            .perform(get(ENTITY_API_URL_ID, interpretacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interpretacion.getId().intValue()))
            .andExpect(jsonPath("$.puntuacionMinima").value(DEFAULT_PUNTUACION_MINIMA))
            .andExpect(jsonPath("$.puntuacionMaxima").value(DEFAULT_PUNTUACION_MAXIMA))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL.toString()))
            .andExpect(jsonPath("$.descripcionNivel").value(DEFAULT_DESCRIPCION_NIVEL))
            .andExpect(jsonPath("$.recomendaciones").value(DEFAULT_RECOMENDACIONES))
            .andExpect(jsonPath("$.areasServicio").value(DEFAULT_AREAS_SERVICIO));
    }

    @Test
    @Transactional
    void getNonExistingInterpretacion() throws Exception {
        // Get the interpretacion
        restInterpretacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterpretacion() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interpretacion
        Interpretacion updatedInterpretacion = interpretacionRepository.findById(interpretacion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInterpretacion are not directly saved in db
        em.detach(updatedInterpretacion);
        updatedInterpretacion
            .puntuacionMinima(UPDATED_PUNTUACION_MINIMA)
            .puntuacionMaxima(UPDATED_PUNTUACION_MAXIMA)
            .nivel(UPDATED_NIVEL)
            .descripcionNivel(UPDATED_DESCRIPCION_NIVEL)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .areasServicio(UPDATED_AREAS_SERVICIO);
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(updatedInterpretacion);

        restInterpretacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interpretacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interpretacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInterpretacionToMatchAllProperties(updatedInterpretacion);
    }

    @Test
    @Transactional
    void putNonExistingInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interpretacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interpretacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interpretacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterpretacionWithPatch() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interpretacion using partial update
        Interpretacion partialUpdatedInterpretacion = new Interpretacion();
        partialUpdatedInterpretacion.setId(interpretacion.getId());

        partialUpdatedInterpretacion
            .puntuacionMinima(UPDATED_PUNTUACION_MINIMA)
            .nivel(UPDATED_NIVEL)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .areasServicio(UPDATED_AREAS_SERVICIO);

        restInterpretacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterpretacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterpretacion))
            )
            .andExpect(status().isOk());

        // Validate the Interpretacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterpretacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInterpretacion, interpretacion),
            getPersistedInterpretacion(interpretacion)
        );
    }

    @Test
    @Transactional
    void fullUpdateInterpretacionWithPatch() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interpretacion using partial update
        Interpretacion partialUpdatedInterpretacion = new Interpretacion();
        partialUpdatedInterpretacion.setId(interpretacion.getId());

        partialUpdatedInterpretacion
            .puntuacionMinima(UPDATED_PUNTUACION_MINIMA)
            .puntuacionMaxima(UPDATED_PUNTUACION_MAXIMA)
            .nivel(UPDATED_NIVEL)
            .descripcionNivel(UPDATED_DESCRIPCION_NIVEL)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .areasServicio(UPDATED_AREAS_SERVICIO);

        restInterpretacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterpretacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterpretacion))
            )
            .andExpect(status().isOk());

        // Validate the Interpretacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterpretacionUpdatableFieldsEquals(partialUpdatedInterpretacion, getPersistedInterpretacion(partialUpdatedInterpretacion));
    }

    @Test
    @Transactional
    void patchNonExistingInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interpretacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interpretacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interpretacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterpretacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interpretacion.setId(longCount.incrementAndGet());

        // Create the Interpretacion
        InterpretacionDTO interpretacionDTO = interpretacionMapper.toDto(interpretacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterpretacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(interpretacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interpretacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterpretacion() throws Exception {
        // Initialize the database
        insertedInterpretacion = interpretacionRepository.saveAndFlush(interpretacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the interpretacion
        restInterpretacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, interpretacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return interpretacionRepository.count();
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

    protected Interpretacion getPersistedInterpretacion(Interpretacion interpretacion) {
        return interpretacionRepository.findById(interpretacion.getId()).orElseThrow();
    }

    protected void assertPersistedInterpretacionToMatchAllProperties(Interpretacion expectedInterpretacion) {
        assertInterpretacionAllPropertiesEquals(expectedInterpretacion, getPersistedInterpretacion(expectedInterpretacion));
    }

    protected void assertPersistedInterpretacionToMatchUpdatableProperties(Interpretacion expectedInterpretacion) {
        assertInterpretacionAllUpdatablePropertiesEquals(expectedInterpretacion, getPersistedInterpretacion(expectedInterpretacion));
    }
}
