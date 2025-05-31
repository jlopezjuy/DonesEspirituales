package ar.com.dones.app.web.rest;

import static ar.com.dones.app.domain.UsuarioAsserts.*;
import static ar.com.dones.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.dones.app.IntegrationTest;
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.domain.enumeration.Genero;
import ar.com.dones.app.repository.UsuarioRepository;
import ar.com.dones.app.service.dto.UsuarioDTO;
import ar.com.dones.app.service.mapper.UsuarioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsuarioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = ";@G;.JB";
    private static final String UPDATED_EMAIL = "iF@z>.GZ";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final Genero DEFAULT_GENERO = Genero.MASCULINO;
    private static final Genero UPDATED_GENERO = Genero.FEMENINO;

    private static final String DEFAULT_IGLESIA = "AAAAAAAAAA";
    private static final String UPDATED_IGLESIA = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINACION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINACION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ULTIMA_ACTIVIDAD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULTIMA_ACTIVIDAD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    private Usuario insertedUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity() {
        return new Usuario()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .email(DEFAULT_EMAIL)
            .telefono(DEFAULT_TELEFONO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .genero(DEFAULT_GENERO)
            .iglesia(DEFAULT_IGLESIA)
            .denominacion(DEFAULT_DENOMINACION)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .ultimaActividad(DEFAULT_ULTIMA_ACTIVIDAD)
            .activo(DEFAULT_ACTIVO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity() {
        return new Usuario()
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .iglesia(UPDATED_IGLESIA)
            .denominacion(UPDATED_DENOMINACION)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .activo(UPDATED_ACTIVO);
    }

    @BeforeEach
    void initTest() {
        usuario = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUsuario != null) {
            usuarioRepository.delete(insertedUsuario);
            insertedUsuario = null;
        }
    }

    @Test
    @Transactional
    void createUsuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        var returnedUsuarioDTO = om.readValue(
            restUsuarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioDTO.class
        );

        // Validate the Usuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuario = usuarioMapper.toEntity(returnedUsuarioDTO);
        assertUsuarioUpdatableFieldsEquals(returnedUsuario, getPersistedUsuario(returnedUsuario));

        insertedUsuario = returnedUsuario;
    }

    @Test
    @Transactional
    void createUsuarioWithExistingId() throws Exception {
        // Create the Usuario with an existing ID
        usuario.setId(1L);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuario.setNombre(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuario.setApellido(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuario.setEmail(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaRegistroIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuario.setFechaRegistro(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuario.setActivo(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarios() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].iglesia").value(hasItem(DEFAULT_IGLESIA)))
            .andExpect(jsonPath("$.[*].denominacion").value(hasItem(DEFAULT_DENOMINACION)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].ultimaActividad").value(hasItem(DEFAULT_ULTIMA_ACTIVIDAD.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)));
    }

    @Test
    @Transactional
    void getUsuario() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.iglesia").value(DEFAULT_IGLESIA))
            .andExpect(jsonPath("$.denominacion").value(DEFAULT_DENOMINACION))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO.toString()))
            .andExpect(jsonPath("$.ultimaActividad").value(DEFAULT_ULTIMA_ACTIVIDAD.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO));
    }

    @Test
    @Transactional
    void getUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        Long id = usuario.getId();

        defaultUsuarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUsuarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUsuarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuariosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nombre equals to
        defaultUsuarioFiltering("nombre.equals=" + DEFAULT_NOMBRE, "nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllUsuariosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nombre in
        defaultUsuarioFiltering("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE, "nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllUsuariosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nombre is not null
        defaultUsuarioFiltering("nombre.specified=true", "nombre.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByNombreContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nombre contains
        defaultUsuarioFiltering("nombre.contains=" + DEFAULT_NOMBRE, "nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void getAllUsuariosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nombre does not contain
        defaultUsuarioFiltering("nombre.doesNotContain=" + UPDATED_NOMBRE, "nombre.doesNotContain=" + DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void getAllUsuariosByApellidoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where apellido equals to
        defaultUsuarioFiltering("apellido.equals=" + DEFAULT_APELLIDO, "apellido.equals=" + UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    void getAllUsuariosByApellidoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where apellido in
        defaultUsuarioFiltering("apellido.in=" + DEFAULT_APELLIDO + "," + UPDATED_APELLIDO, "apellido.in=" + UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    void getAllUsuariosByApellidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where apellido is not null
        defaultUsuarioFiltering("apellido.specified=true", "apellido.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByApellidoContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where apellido contains
        defaultUsuarioFiltering("apellido.contains=" + DEFAULT_APELLIDO, "apellido.contains=" + UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    void getAllUsuariosByApellidoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where apellido does not contain
        defaultUsuarioFiltering("apellido.doesNotContain=" + UPDATED_APELLIDO, "apellido.doesNotContain=" + DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    void getAllUsuariosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email equals to
        defaultUsuarioFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuariosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email in
        defaultUsuarioFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuariosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email is not null
        defaultUsuarioFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email contains
        defaultUsuarioFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuariosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where email does not contain
        defaultUsuarioFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuariosByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefono equals to
        defaultUsuarioFiltering("telefono.equals=" + DEFAULT_TELEFONO, "telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUsuariosByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefono in
        defaultUsuarioFiltering("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO, "telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUsuariosByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefono is not null
        defaultUsuarioFiltering("telefono.specified=true", "telefono.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefono contains
        defaultUsuarioFiltering("telefono.contains=" + DEFAULT_TELEFONO, "telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUsuariosByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where telefono does not contain
        defaultUsuarioFiltering("telefono.doesNotContain=" + UPDATED_TELEFONO, "telefono.doesNotContain=" + DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento equals to
        defaultUsuarioFiltering("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO, "fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento in
        defaultUsuarioFiltering(
            "fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO,
            "fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento is not null
        defaultUsuarioFiltering("fechaNacimiento.specified=true", "fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento is greater than or equal to
        defaultUsuarioFiltering(
            "fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO,
            "fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento is less than or equal to
        defaultUsuarioFiltering(
            "fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO,
            "fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento is less than
        defaultUsuarioFiltering(
            "fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO,
            "fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaNacimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaNacimiento is greater than
        defaultUsuarioFiltering(
            "fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO,
            "fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where genero equals to
        defaultUsuarioFiltering("genero.equals=" + DEFAULT_GENERO, "genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    void getAllUsuariosByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where genero in
        defaultUsuarioFiltering("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO, "genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    void getAllUsuariosByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where genero is not null
        defaultUsuarioFiltering("genero.specified=true", "genero.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIglesiaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where iglesia equals to
        defaultUsuarioFiltering("iglesia.equals=" + DEFAULT_IGLESIA, "iglesia.equals=" + UPDATED_IGLESIA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIglesiaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where iglesia in
        defaultUsuarioFiltering("iglesia.in=" + DEFAULT_IGLESIA + "," + UPDATED_IGLESIA, "iglesia.in=" + UPDATED_IGLESIA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIglesiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where iglesia is not null
        defaultUsuarioFiltering("iglesia.specified=true", "iglesia.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIglesiaContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where iglesia contains
        defaultUsuarioFiltering("iglesia.contains=" + DEFAULT_IGLESIA, "iglesia.contains=" + UPDATED_IGLESIA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIglesiaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where iglesia does not contain
        defaultUsuarioFiltering("iglesia.doesNotContain=" + UPDATED_IGLESIA, "iglesia.doesNotContain=" + DEFAULT_IGLESIA);
    }

    @Test
    @Transactional
    void getAllUsuariosByDenominacionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where denominacion equals to
        defaultUsuarioFiltering("denominacion.equals=" + DEFAULT_DENOMINACION, "denominacion.equals=" + UPDATED_DENOMINACION);
    }

    @Test
    @Transactional
    void getAllUsuariosByDenominacionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where denominacion in
        defaultUsuarioFiltering(
            "denominacion.in=" + DEFAULT_DENOMINACION + "," + UPDATED_DENOMINACION,
            "denominacion.in=" + UPDATED_DENOMINACION
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByDenominacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where denominacion is not null
        defaultUsuarioFiltering("denominacion.specified=true", "denominacion.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByDenominacionContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where denominacion contains
        defaultUsuarioFiltering("denominacion.contains=" + DEFAULT_DENOMINACION, "denominacion.contains=" + UPDATED_DENOMINACION);
    }

    @Test
    @Transactional
    void getAllUsuariosByDenominacionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where denominacion does not contain
        defaultUsuarioFiltering(
            "denominacion.doesNotContain=" + UPDATED_DENOMINACION,
            "denominacion.doesNotContain=" + DEFAULT_DENOMINACION
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaRegistro equals to
        defaultUsuarioFiltering("fechaRegistro.equals=" + DEFAULT_FECHA_REGISTRO, "fechaRegistro.equals=" + UPDATED_FECHA_REGISTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaRegistro in
        defaultUsuarioFiltering(
            "fechaRegistro.in=" + DEFAULT_FECHA_REGISTRO + "," + UPDATED_FECHA_REGISTRO,
            "fechaRegistro.in=" + UPDATED_FECHA_REGISTRO
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByFechaRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where fechaRegistro is not null
        defaultUsuarioFiltering("fechaRegistro.specified=true", "fechaRegistro.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByUltimaActividadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimaActividad equals to
        defaultUsuarioFiltering("ultimaActividad.equals=" + DEFAULT_ULTIMA_ACTIVIDAD, "ultimaActividad.equals=" + UPDATED_ULTIMA_ACTIVIDAD);
    }

    @Test
    @Transactional
    void getAllUsuariosByUltimaActividadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimaActividad in
        defaultUsuarioFiltering(
            "ultimaActividad.in=" + DEFAULT_ULTIMA_ACTIVIDAD + "," + UPDATED_ULTIMA_ACTIVIDAD,
            "ultimaActividad.in=" + UPDATED_ULTIMA_ACTIVIDAD
        );
    }

    @Test
    @Transactional
    void getAllUsuariosByUltimaActividadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where ultimaActividad is not null
        defaultUsuarioFiltering("ultimaActividad.specified=true", "ultimaActividad.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByActivoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where activo equals to
        defaultUsuarioFiltering("activo.equals=" + DEFAULT_ACTIVO, "activo.equals=" + UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void getAllUsuariosByActivoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where activo in
        defaultUsuarioFiltering("activo.in=" + DEFAULT_ACTIVO + "," + UPDATED_ACTIVO, "activo.in=" + UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    void getAllUsuariosByActivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where activo is not null
        defaultUsuarioFiltering("activo.specified=true", "activo.specified=false");
    }

    private void defaultUsuarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUsuarioShouldBeFound(shouldBeFound);
        defaultUsuarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioShouldBeFound(String filter) throws Exception {
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].iglesia").value(hasItem(DEFAULT_IGLESIA)))
            .andExpect(jsonPath("$.[*].denominacion").value(hasItem(DEFAULT_DENOMINACION)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].ultimaActividad").value(hasItem(DEFAULT_ULTIMA_ACTIVIDAD.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)));

        // Check, that the count call also returns 1
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioShouldNotBeFound(String filter) throws Exception {
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuario() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .iglesia(UPDATED_IGLESIA)
            .denominacion(UPDATED_DENOMINACION)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .activo(UPDATED_ACTIVO);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(updatedUsuario);

        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioToMatchAllProperties(updatedUsuario);
    }

    @Test
    @Transactional
    void putNonExistingUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .apellido(UPDATED_APELLIDO)
            .email(UPDATED_EMAIL)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .iglesia(UPDATED_IGLESIA)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .activo(UPDATED_ACTIVO);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedUsuario, usuario), getPersistedUsuario(usuario));
    }

    @Test
    @Transactional
    void fullUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .email(UPDATED_EMAIL)
            .telefono(UPDATED_TELEFONO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .iglesia(UPDATED_IGLESIA)
            .denominacion(UPDATED_DENOMINACION)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .activo(UPDATED_ACTIVO);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioUpdatableFieldsEquals(partialUpdatedUsuario, getPersistedUsuario(partialUpdatedUsuario));
    }

    @Test
    @Transactional
    void patchNonExistingUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuario.setId(longCount.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuario() throws Exception {
        // Initialize the database
        insertedUsuario = usuarioRepository.saveAndFlush(usuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuario
        restUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioRepository.count();
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

    protected Usuario getPersistedUsuario(Usuario usuario) {
        return usuarioRepository.findById(usuario.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioToMatchAllProperties(Usuario expectedUsuario) {
        assertUsuarioAllPropertiesEquals(expectedUsuario, getPersistedUsuario(expectedUsuario));
    }

    protected void assertPersistedUsuarioToMatchUpdatableProperties(Usuario expectedUsuario) {
        assertUsuarioAllUpdatablePropertiesEquals(expectedUsuario, getPersistedUsuario(expectedUsuario));
    }
}
