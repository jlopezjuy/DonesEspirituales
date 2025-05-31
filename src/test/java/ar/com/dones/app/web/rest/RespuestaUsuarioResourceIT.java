package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.RespuestaUsuarioAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.domain.enumeration.EstadoRespuesta;
import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.mapper.RespuestaUsuarioMapper;
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
 * Integration tests for the {@link RespuestaUsuarioResource} REST controller.
 */
@IntegrationTest
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
    private static final Integer SMALLER_TIEMPO_TOTAL_SEGUNDOS = 0 - 1;

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
    private RespuestaUsuarioMapper respuestaUsuarioMapper;

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
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            usuario = UsuarioResourceIT.createEntity();
            em.persist(usuario);
            em.flush();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        respuestaUsuario.setUsuario(usuario);
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
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            usuario = UsuarioResourceIT.createUpdatedEntity();
            em.persist(usuario);
            em.flush();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        updatedRespuestaUsuario.setUsuario(usuario);
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
    void getRespuestaUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        Long id = respuestaUsuario.getId();

        defaultRespuestaUsuarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultRespuestaUsuarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultRespuestaUsuarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaInicio equals to
        defaultRespuestaUsuarioFiltering("fechaInicio.equals=" + DEFAULT_FECHA_INICIO, "fechaInicio.equals=" + UPDATED_FECHA_INICIO);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaInicioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaInicio in
        defaultRespuestaUsuarioFiltering(
            "fechaInicio.in=" + DEFAULT_FECHA_INICIO + "," + UPDATED_FECHA_INICIO,
            "fechaInicio.in=" + UPDATED_FECHA_INICIO
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaInicio is not null
        defaultRespuestaUsuarioFiltering("fechaInicio.specified=true", "fechaInicio.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaCompletadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaCompletado equals to
        defaultRespuestaUsuarioFiltering(
            "fechaCompletado.equals=" + DEFAULT_FECHA_COMPLETADO,
            "fechaCompletado.equals=" + UPDATED_FECHA_COMPLETADO
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaCompletadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaCompletado in
        defaultRespuestaUsuarioFiltering(
            "fechaCompletado.in=" + DEFAULT_FECHA_COMPLETADO + "," + UPDATED_FECHA_COMPLETADO,
            "fechaCompletado.in=" + UPDATED_FECHA_COMPLETADO
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByFechaCompletadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where fechaCompletado is not null
        defaultRespuestaUsuarioFiltering("fechaCompletado.specified=true", "fechaCompletado.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where estado equals to
        defaultRespuestaUsuarioFiltering("estado.equals=" + DEFAULT_ESTADO, "estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where estado in
        defaultRespuestaUsuarioFiltering("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO, "estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where estado is not null
        defaultRespuestaUsuarioFiltering("estado.specified=true", "estado.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos equals to
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.equals=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.equals=" + UPDATED_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos in
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.in=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS + "," + UPDATED_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.in=" + UPDATED_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos is not null
        defaultRespuestaUsuarioFiltering("tiempoTotalSegundos.specified=true", "tiempoTotalSegundos.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos is greater than or equal to
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.greaterThanOrEqual=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.greaterThanOrEqual=" + UPDATED_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos is less than or equal to
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.lessThanOrEqual=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.lessThanOrEqual=" + SMALLER_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos is less than
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.lessThan=" + UPDATED_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.lessThan=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByTiempoTotalSegundosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where tiempoTotalSegundos is greater than
        defaultRespuestaUsuarioFiltering(
            "tiempoTotalSegundos.greaterThan=" + SMALLER_TIEMPO_TOTAL_SEGUNDOS,
            "tiempoTotalSegundos.greaterThan=" + DEFAULT_TIEMPO_TOTAL_SEGUNDOS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByIpAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where ipAddress equals to
        defaultRespuestaUsuarioFiltering("ipAddress.equals=" + DEFAULT_IP_ADDRESS, "ipAddress.equals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByIpAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where ipAddress in
        defaultRespuestaUsuarioFiltering(
            "ipAddress.in=" + DEFAULT_IP_ADDRESS + "," + UPDATED_IP_ADDRESS,
            "ipAddress.in=" + UPDATED_IP_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByIpAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where ipAddress is not null
        defaultRespuestaUsuarioFiltering("ipAddress.specified=true", "ipAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByIpAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where ipAddress contains
        defaultRespuestaUsuarioFiltering("ipAddress.contains=" + DEFAULT_IP_ADDRESS, "ipAddress.contains=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByIpAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where ipAddress does not contain
        defaultRespuestaUsuarioFiltering(
            "ipAddress.doesNotContain=" + UPDATED_IP_ADDRESS,
            "ipAddress.doesNotContain=" + DEFAULT_IP_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUserAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where userAgent equals to
        defaultRespuestaUsuarioFiltering("userAgent.equals=" + DEFAULT_USER_AGENT, "userAgent.equals=" + UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUserAgentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where userAgent in
        defaultRespuestaUsuarioFiltering(
            "userAgent.in=" + DEFAULT_USER_AGENT + "," + UPDATED_USER_AGENT,
            "userAgent.in=" + UPDATED_USER_AGENT
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUserAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where userAgent is not null
        defaultRespuestaUsuarioFiltering("userAgent.specified=true", "userAgent.specified=false");
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUserAgentContainsSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where userAgent contains
        defaultRespuestaUsuarioFiltering("userAgent.contains=" + DEFAULT_USER_AGENT, "userAgent.contains=" + UPDATED_USER_AGENT);
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUserAgentNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRespuestaUsuario = respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);

        // Get all the respuestaUsuarioList where userAgent does not contain
        defaultRespuestaUsuarioFiltering(
            "userAgent.doesNotContain=" + UPDATED_USER_AGENT,
            "userAgent.doesNotContain=" + DEFAULT_USER_AGENT
        );
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByUsuarioIsEqualToSomething() throws Exception {
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);
            usuario = UsuarioResourceIT.createEntity();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        em.persist(usuario);
        em.flush();
        respuestaUsuario.setUsuario(usuario);
        respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);
        Long usuarioId = usuario.getId();
        // Get all the respuestaUsuarioList where usuario equals to usuarioId
        defaultRespuestaUsuarioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the respuestaUsuarioList where usuario equals to (usuarioId + 1)
        defaultRespuestaUsuarioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    @Test
    @Transactional
    void getAllRespuestaUsuariosByCuestionarioIsEqualToSomething() throws Exception {
        Cuestionario cuestionario;
        if (TestUtil.findAll(em, Cuestionario.class).isEmpty()) {
            respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);
            cuestionario = CuestionarioResourceIT.createEntity();
        } else {
            cuestionario = TestUtil.findAll(em, Cuestionario.class).get(0);
        }
        em.persist(cuestionario);
        em.flush();
        respuestaUsuario.setCuestionario(cuestionario);
        respuestaUsuarioRepository.saveAndFlush(respuestaUsuario);
        Long cuestionarioId = cuestionario.getId();
        // Get all the respuestaUsuarioList where cuestionario equals to cuestionarioId
        defaultRespuestaUsuarioShouldBeFound("cuestionarioId.equals=" + cuestionarioId);

        // Get all the respuestaUsuarioList where cuestionario equals to (cuestionarioId + 1)
        defaultRespuestaUsuarioShouldNotBeFound("cuestionarioId.equals=" + (cuestionarioId + 1));
    }

    private void defaultRespuestaUsuarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultRespuestaUsuarioShouldBeFound(shouldBeFound);
        defaultRespuestaUsuarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRespuestaUsuarioShouldBeFound(String filter) throws Exception {
        restRespuestaUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(respuestaUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaCompletado").value(hasItem(DEFAULT_FECHA_COMPLETADO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].tiempoTotalSegundos").value(hasItem(DEFAULT_TIEMPO_TOTAL_SEGUNDOS)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT)));

        // Check, that the count call also returns 1
        restRespuestaUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRespuestaUsuarioShouldNotBeFound(String filter) throws Exception {
        restRespuestaUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRespuestaUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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

        partialUpdatedRespuestaUsuario
            .fechaInicio(UPDATED_FECHA_INICIO)
            .estado(UPDATED_ESTADO)
            .tiempoTotalSegundos(UPDATED_TIEMPO_TOTAL_SEGUNDOS)
            .ipAddress(UPDATED_IP_ADDRESS);

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
        assertRespuestaUsuarioAllUpdatablePropertiesEquals(
            expectedRespuestaUsuario,
            getPersistedRespuestaUsuario(expectedRespuestaUsuario)
        );
    }
}
