package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.EscalaRespuestaAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.repository.EscalaRespuestaRepository;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import ar.com.dones.app.service.mapper.EscalaRespuestaMapper;
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
 * Integration tests for the {@link EscalaRespuestaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EscalaRespuestaResourceIT {

    private static final Integer DEFAULT_VALOR = 0;
    private static final Integer UPDATED_VALOR = 1;

    private static final String DEFAULT_ETIQUETA = "AAAAAAAAAA";
    private static final String UPDATED_ETIQUETA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEN = 1;
    private static final Integer UPDATED_ORDEN = 2;

    private static final String ENTITY_API_URL = "/api/escala-respuestas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EscalaRespuestaRepository escalaRespuestaRepository;

    @Autowired
    private EscalaRespuestaMapper escalaRespuestaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEscalaRespuestaMockMvc;

    private EscalaRespuesta escalaRespuesta;

    private EscalaRespuesta insertedEscalaRespuesta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EscalaRespuesta createEntity() {
        return new EscalaRespuesta().valor(DEFAULT_VALOR).etiqueta(DEFAULT_ETIQUETA).descripcion(DEFAULT_DESCRIPCION).orden(DEFAULT_ORDEN);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EscalaRespuesta createUpdatedEntity() {
        return new EscalaRespuesta().valor(UPDATED_VALOR).etiqueta(UPDATED_ETIQUETA).descripcion(UPDATED_DESCRIPCION).orden(UPDATED_ORDEN);
    }

    @BeforeEach
    void initTest() {
        escalaRespuesta = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEscalaRespuesta != null) {
            escalaRespuestaRepository.delete(insertedEscalaRespuesta);
            insertedEscalaRespuesta = null;
        }
    }

    @Test
    @Transactional
    void createEscalaRespuesta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);
        var returnedEscalaRespuestaDTO = om.readValue(
            restEscalaRespuestaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EscalaRespuestaDTO.class
        );

        // Validate the EscalaRespuesta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEscalaRespuesta = escalaRespuestaMapper.toEntity(returnedEscalaRespuestaDTO);
        assertEscalaRespuestaUpdatableFieldsEquals(returnedEscalaRespuesta, getPersistedEscalaRespuesta(returnedEscalaRespuesta));

        insertedEscalaRespuesta = returnedEscalaRespuesta;
    }

    @Test
    @Transactional
    void createEscalaRespuestaWithExistingId() throws Exception {
        // Create the EscalaRespuesta with an existing ID
        escalaRespuesta.setId(1L);
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscalaRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        escalaRespuesta.setValor(null);

        // Create the EscalaRespuesta, which fails.
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        restEscalaRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtiquetaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        escalaRespuesta.setEtiqueta(null);

        // Create the EscalaRespuesta, which fails.
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        restEscalaRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrdenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        escalaRespuesta.setOrden(null);

        // Create the EscalaRespuesta, which fails.
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        restEscalaRespuestaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEscalaRespuestas() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        // Get all the escalaRespuestaList
        restEscalaRespuestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escalaRespuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].etiqueta").value(hasItem(DEFAULT_ETIQUETA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN)));
    }

    @Test
    @Transactional
    void getEscalaRespuesta() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        // Get the escalaRespuesta
        restEscalaRespuestaMockMvc
            .perform(get(ENTITY_API_URL_ID, escalaRespuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(escalaRespuesta.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.etiqueta").value(DEFAULT_ETIQUETA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.orden").value(DEFAULT_ORDEN));
    }

    @Test
    @Transactional
    void getNonExistingEscalaRespuesta() throws Exception {
        // Get the escalaRespuesta
        restEscalaRespuestaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEscalaRespuesta() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escalaRespuesta
        EscalaRespuesta updatedEscalaRespuesta = escalaRespuestaRepository.findById(escalaRespuesta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEscalaRespuesta are not directly saved in db
        em.detach(updatedEscalaRespuesta);
        updatedEscalaRespuesta.valor(UPDATED_VALOR).etiqueta(UPDATED_ETIQUETA).descripcion(UPDATED_DESCRIPCION).orden(UPDATED_ORDEN);
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(updatedEscalaRespuesta);

        restEscalaRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, escalaRespuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escalaRespuestaDTO))
            )
            .andExpect(status().isOk());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEscalaRespuestaToMatchAllProperties(updatedEscalaRespuesta);
    }

    @Test
    @Transactional
    void putNonExistingEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, escalaRespuestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escalaRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escalaRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEscalaRespuestaWithPatch() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escalaRespuesta using partial update
        EscalaRespuesta partialUpdatedEscalaRespuesta = new EscalaRespuesta();
        partialUpdatedEscalaRespuesta.setId(escalaRespuesta.getId());

        partialUpdatedEscalaRespuesta.valor(UPDATED_VALOR).etiqueta(UPDATED_ETIQUETA).orden(UPDATED_ORDEN);

        restEscalaRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscalaRespuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscalaRespuesta))
            )
            .andExpect(status().isOk());

        // Validate the EscalaRespuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscalaRespuestaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEscalaRespuesta, escalaRespuesta),
            getPersistedEscalaRespuesta(escalaRespuesta)
        );
    }

    @Test
    @Transactional
    void fullUpdateEscalaRespuestaWithPatch() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escalaRespuesta using partial update
        EscalaRespuesta partialUpdatedEscalaRespuesta = new EscalaRespuesta();
        partialUpdatedEscalaRespuesta.setId(escalaRespuesta.getId());

        partialUpdatedEscalaRespuesta.valor(UPDATED_VALOR).etiqueta(UPDATED_ETIQUETA).descripcion(UPDATED_DESCRIPCION).orden(UPDATED_ORDEN);

        restEscalaRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscalaRespuesta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscalaRespuesta))
            )
            .andExpect(status().isOk());

        // Validate the EscalaRespuesta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscalaRespuestaUpdatableFieldsEquals(
            partialUpdatedEscalaRespuesta,
            getPersistedEscalaRespuesta(partialUpdatedEscalaRespuesta)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, escalaRespuestaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escalaRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escalaRespuestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEscalaRespuesta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escalaRespuesta.setId(longCount.incrementAndGet());

        // Create the EscalaRespuesta
        EscalaRespuestaDTO escalaRespuestaDTO = escalaRespuestaMapper.toDto(escalaRespuesta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscalaRespuestaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(escalaRespuestaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EscalaRespuesta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEscalaRespuesta() throws Exception {
        // Initialize the database
        insertedEscalaRespuesta = escalaRespuestaRepository.saveAndFlush(escalaRespuesta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the escalaRespuesta
        restEscalaRespuestaMockMvc
            .perform(delete(ENTITY_API_URL_ID, escalaRespuesta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return escalaRespuestaRepository.count();
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

    protected EscalaRespuesta getPersistedEscalaRespuesta(EscalaRespuesta escalaRespuesta) {
        return escalaRespuestaRepository.findById(escalaRespuesta.getId()).orElseThrow();
    }

    protected void assertPersistedEscalaRespuestaToMatchAllProperties(EscalaRespuesta expectedEscalaRespuesta) {
        assertEscalaRespuestaAllPropertiesEquals(expectedEscalaRespuesta, getPersistedEscalaRespuesta(expectedEscalaRespuesta));
    }

    protected void assertPersistedEscalaRespuestaToMatchUpdatableProperties(EscalaRespuesta expectedEscalaRespuesta) {
        assertEscalaRespuestaAllUpdatablePropertiesEquals(expectedEscalaRespuesta, getPersistedEscalaRespuesta(expectedEscalaRespuesta));
    }
}
