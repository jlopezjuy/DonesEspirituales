package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.DonEspiritualAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.repository.DonEspiritualRepository;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.mapper.DonEspiritualMapper;
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
 * Integration tests for the {@link DonEspiritualResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonEspiritualResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CORTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CORTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CARACTERISTICAS = "AAAAAAAAAA";
    private static final String UPDATED_CARACTERISTICAS = "BBBBBBBBBB";

    private static final String DEFAULT_VERSICULOS_BIBLICOS = "AAAAAAAAAA";
    private static final String UPDATED_VERSICULOS_BIBLICOS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final Integer DEFAULT_ORDEN_PRESENTACION = 1;
    private static final Integer UPDATED_ORDEN_PRESENTACION = 2;
    private static final Integer SMALLER_ORDEN_PRESENTACION = 1 - 1;

    private static final String ENTITY_API_URL = "/api/don-espirituals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DonEspiritualRepository donEspiritualRepository;

    @Autowired
    private DonEspiritualMapper donEspiritualMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonEspiritualMockMvc;

    private DonEspiritual donEspiritual;

    private DonEspiritual insertedDonEspiritual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonEspiritual createEntity() {
        return new DonEspiritual()
            .nombre(DEFAULT_NOMBRE)
            .nombreCorto(DEFAULT_NOMBRE_CORTO)
            .descripcion(DEFAULT_DESCRIPCION)
            .caracteristicas(DEFAULT_CARACTERISTICAS)
            .versiculosBiblicos(DEFAULT_VERSICULOS_BIBLICOS)
            .activo(DEFAULT_ACTIVO)
            .ordenPresentacion(DEFAULT_ORDEN_PRESENTACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonEspiritual createUpdatedEntity() {
        return new DonEspiritual()
            .nombre(UPDATED_NOMBRE)
            .nombreCorto(UPDATED_NOMBRE_CORTO)
            .descripcion(UPDATED_DESCRIPCION)
            .caracteristicas(UPDATED_CARACTERISTICAS)
            .versiculosBiblicos(UPDATED_VERSICULOS_BIBLICOS)
            .activo(UPDATED_ACTIVO)
            .ordenPresentacion(UPDATED_ORDEN_PRESENTACION);
    }

    @BeforeEach
    void initTest() {
        donEspiritual = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDonEspiritual != null) {
            donEspiritualRepository.delete(insertedDonEspiritual);
            insertedDonEspiritual = null;
        }
    }

    @Test
    @Transactional
    void createDonEspiritual() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);
        var returnedDonEspiritualDTO = om.readValue(
            restDonEspiritualMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DonEspiritualDTO.class
        );

        // Validate the DonEspiritual in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDonEspiritual = donEspiritualMapper.toEntity(returnedDonEspiritualDTO);
        assertDonEspiritualUpdatableFieldsEquals(returnedDonEspiritual, getPersistedDonEspiritual(returnedDonEspiritual));

        insertedDonEspiritual = returnedDonEspiritual;
    }

    @Test
    @Transactional
    void createDonEspiritualWithExistingId() throws Exception {
        // Create the DonEspiritual with an existing ID
        donEspiritual.setId(1L);
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonEspiritualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        donEspiritual.setNombre(null);

        // Create the DonEspiritual, which fails.
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        restDonEspiritualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreCortoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        donEspiritual.setNombreCorto(null);

        // Create the DonEspiritual, which fails.
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        restDonEspiritualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        donEspiritual.setActivo(null);

        // Create the DonEspiritual, which fails.
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        restDonEspiritualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonEspirituals() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donEspiritual.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nombreCorto").value(hasItem(DEFAULT_NOMBRE_CORTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].caracteristicas").value(hasItem(DEFAULT_CARACTERISTICAS)))
            .andExpect(jsonPath("$.[*].versiculosBiblicos").value(hasItem(DEFAULT_VERSICULOS_BIBLICOS)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)))
            .andExpect(jsonPath("$.[*].ordenPresentacion").value(hasItem(DEFAULT_ORDEN_PRESENTACION)));
    }

    @Test
    @Transactional
    void getDonEspiritual() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get the donEspiritual
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL_ID, donEspiritual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donEspiritual.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.nombreCorto").value(DEFAULT_NOMBRE_CORTO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.caracteristicas").value(DEFAULT_CARACTERISTICAS))
            .andExpect(jsonPath("$.versiculosBiblicos").value(DEFAULT_VERSICULOS_BIBLICOS))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO))
            .andExpect(jsonPath("$.ordenPresentacion").value(DEFAULT_ORDEN_PRESENTACION));
    }

    @Test
    @Transactional
    void getDonEspiritualsByIdFiltering() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        Long id = donEspiritual.getId();

        defaultDonEspiritualFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDonEspiritualFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDonEspiritualFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombre equals to
        defaultDonEspiritualFiltering("nombre.equals=" + DEFAULT_NOMBRE, "nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombre in
        defaultDonEspiritualFiltering("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE, "nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombre is not null
        defaultDonEspiritualFiltering("nombre.specified=true", "nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreContainsSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombre contains
        defaultDonEspiritualFiltering("nombre.contains=" + DEFAULT_NOMBRE, "nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombre does not contain
        defaultDonEspiritualFiltering("nombre.doesNotContain=" + UPDATED_NOMBRE, "nombre.doesNotContain=" + DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreCortoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombreCorto equals to
        defaultDonEspiritualFiltering("nombreCorto.equals=" + DEFAULT_NOMBRE_CORTO, "nombreCorto.equals=" + UPDATED_NOMBRE_CORTO);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreCortoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombreCorto in
        defaultDonEspiritualFiltering(
            "nombreCorto.in=" + DEFAULT_NOMBRE_CORTO + "," + UPDATED_NOMBRE_CORTO,
            "nombreCorto.in=" + UPDATED_NOMBRE_CORTO
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreCortoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombreCorto is not null
        defaultDonEspiritualFiltering("nombreCorto.specified=true", "nombreCorto.specified=false");
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreCortoContainsSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombreCorto contains
        defaultDonEspiritualFiltering("nombreCorto.contains=" + DEFAULT_NOMBRE_CORTO, "nombreCorto.contains=" + UPDATED_NOMBRE_CORTO);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByNombreCortoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where nombreCorto does not contain
        defaultDonEspiritualFiltering(
            "nombreCorto.doesNotContain=" + UPDATED_NOMBRE_CORTO,
            "nombreCorto.doesNotContain=" + DEFAULT_NOMBRE_CORTO
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByActivoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where activo equals to
        defaultDonEspiritualFiltering("activo.equals=" + DEFAULT_ACTIVO, "activo.equals=" + UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByActivoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where activo in
        defaultDonEspiritualFiltering("activo.in=" + DEFAULT_ACTIVO + "," + UPDATED_ACTIVO, "activo.in=" + UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByActivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where activo is not null
        defaultDonEspiritualFiltering("activo.specified=true", "activo.specified=false");
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion equals to
        defaultDonEspiritualFiltering(
            "ordenPresentacion.equals=" + DEFAULT_ORDEN_PRESENTACION,
            "ordenPresentacion.equals=" + UPDATED_ORDEN_PRESENTACION
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion in
        defaultDonEspiritualFiltering(
            "ordenPresentacion.in=" + DEFAULT_ORDEN_PRESENTACION + "," + UPDATED_ORDEN_PRESENTACION,
            "ordenPresentacion.in=" + UPDATED_ORDEN_PRESENTACION
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion is not null
        defaultDonEspiritualFiltering("ordenPresentacion.specified=true", "ordenPresentacion.specified=false");
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion is greater than or equal to
        defaultDonEspiritualFiltering(
            "ordenPresentacion.greaterThanOrEqual=" + DEFAULT_ORDEN_PRESENTACION,
            "ordenPresentacion.greaterThanOrEqual=" + UPDATED_ORDEN_PRESENTACION
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion is less than or equal to
        defaultDonEspiritualFiltering(
            "ordenPresentacion.lessThanOrEqual=" + DEFAULT_ORDEN_PRESENTACION,
            "ordenPresentacion.lessThanOrEqual=" + SMALLER_ORDEN_PRESENTACION
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion is less than
        defaultDonEspiritualFiltering(
            "ordenPresentacion.lessThan=" + UPDATED_ORDEN_PRESENTACION,
            "ordenPresentacion.lessThan=" + DEFAULT_ORDEN_PRESENTACION
        );
    }

    @Test
    @Transactional
    void getAllDonEspiritualsByOrdenPresentacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        // Get all the donEspiritualList where ordenPresentacion is greater than
        defaultDonEspiritualFiltering(
            "ordenPresentacion.greaterThan=" + SMALLER_ORDEN_PRESENTACION,
            "ordenPresentacion.greaterThan=" + DEFAULT_ORDEN_PRESENTACION
        );
    }

    private void defaultDonEspiritualFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDonEspiritualShouldBeFound(shouldBeFound);
        defaultDonEspiritualShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDonEspiritualShouldBeFound(String filter) throws Exception {
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donEspiritual.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nombreCorto").value(hasItem(DEFAULT_NOMBRE_CORTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].caracteristicas").value(hasItem(DEFAULT_CARACTERISTICAS)))
            .andExpect(jsonPath("$.[*].versiculosBiblicos").value(hasItem(DEFAULT_VERSICULOS_BIBLICOS)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)))
            .andExpect(jsonPath("$.[*].ordenPresentacion").value(hasItem(DEFAULT_ORDEN_PRESENTACION)));

        // Check, that the count call also returns 1
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDonEspiritualShouldNotBeFound(String filter) throws Exception {
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDonEspiritualMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDonEspiritual() throws Exception {
        // Get the donEspiritual
        restDonEspiritualMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDonEspiritual() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the donEspiritual
        DonEspiritual updatedDonEspiritual = donEspiritualRepository.findById(donEspiritual.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDonEspiritual are not directly saved in db
        em.detach(updatedDonEspiritual);
        updatedDonEspiritual
            .nombre(UPDATED_NOMBRE)
            .nombreCorto(UPDATED_NOMBRE_CORTO)
            .descripcion(UPDATED_DESCRIPCION)
            .caracteristicas(UPDATED_CARACTERISTICAS)
            .versiculosBiblicos(UPDATED_VERSICULOS_BIBLICOS)
            .activo(UPDATED_ACTIVO)
            .ordenPresentacion(UPDATED_ORDEN_PRESENTACION);
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(updatedDonEspiritual);

        restDonEspiritualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donEspiritualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(donEspiritualDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDonEspiritualToMatchAllProperties(updatedDonEspiritual);
    }

    @Test
    @Transactional
    void putNonExistingDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donEspiritualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(donEspiritualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(donEspiritualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonEspiritualWithPatch() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the donEspiritual using partial update
        DonEspiritual partialUpdatedDonEspiritual = new DonEspiritual();
        partialUpdatedDonEspiritual.setId(donEspiritual.getId());

        partialUpdatedDonEspiritual
            .nombre(UPDATED_NOMBRE)
            .nombreCorto(UPDATED_NOMBRE_CORTO)
            .versiculosBiblicos(UPDATED_VERSICULOS_BIBLICOS)
            .activo(UPDATED_ACTIVO);

        restDonEspiritualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonEspiritual.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDonEspiritual))
            )
            .andExpect(status().isOk());

        // Validate the DonEspiritual in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDonEspiritualUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDonEspiritual, donEspiritual),
            getPersistedDonEspiritual(donEspiritual)
        );
    }

    @Test
    @Transactional
    void fullUpdateDonEspiritualWithPatch() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the donEspiritual using partial update
        DonEspiritual partialUpdatedDonEspiritual = new DonEspiritual();
        partialUpdatedDonEspiritual.setId(donEspiritual.getId());

        partialUpdatedDonEspiritual
            .nombre(UPDATED_NOMBRE)
            .nombreCorto(UPDATED_NOMBRE_CORTO)
            .descripcion(UPDATED_DESCRIPCION)
            .caracteristicas(UPDATED_CARACTERISTICAS)
            .versiculosBiblicos(UPDATED_VERSICULOS_BIBLICOS)
            .activo(UPDATED_ACTIVO)
            .ordenPresentacion(UPDATED_ORDEN_PRESENTACION);

        restDonEspiritualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonEspiritual.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDonEspiritual))
            )
            .andExpect(status().isOk());

        // Validate the DonEspiritual in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDonEspiritualUpdatableFieldsEquals(partialUpdatedDonEspiritual, getPersistedDonEspiritual(partialUpdatedDonEspiritual));
    }

    @Test
    @Transactional
    void patchNonExistingDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donEspiritualDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(donEspiritualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(donEspiritualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonEspiritual() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        donEspiritual.setId(longCount.incrementAndGet());

        // Create the DonEspiritual
        DonEspiritualDTO donEspiritualDTO = donEspiritualMapper.toDto(donEspiritual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonEspiritualMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(donEspiritualDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonEspiritual in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonEspiritual() throws Exception {
        // Initialize the database
        insertedDonEspiritual = donEspiritualRepository.saveAndFlush(donEspiritual);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the donEspiritual
        restDonEspiritualMockMvc
            .perform(delete(ENTITY_API_URL_ID, donEspiritual.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return donEspiritualRepository.count();
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

    protected DonEspiritual getPersistedDonEspiritual(DonEspiritual donEspiritual) {
        return donEspiritualRepository.findById(donEspiritual.getId()).orElseThrow();
    }

    protected void assertPersistedDonEspiritualToMatchAllProperties(DonEspiritual expectedDonEspiritual) {
        assertDonEspiritualAllPropertiesEquals(expectedDonEspiritual, getPersistedDonEspiritual(expectedDonEspiritual));
    }

    protected void assertPersistedDonEspiritualToMatchUpdatableProperties(DonEspiritual expectedDonEspiritual) {
        assertDonEspiritualAllUpdatablePropertiesEquals(expectedDonEspiritual, getPersistedDonEspiritual(expectedDonEspiritual));
    }
}
