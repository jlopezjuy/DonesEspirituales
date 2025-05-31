package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.service.RespuestaUsuarioQueryService;
import ar.com.dones.app.service.RespuestaUsuarioService;
import ar.com.dones.app.service.criteria.RespuestaUsuarioCriteria;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.dones.app.domain.RespuestaUsuario}.
 */
@RestController
@RequestMapping("/api/respuesta-usuarios")
public class RespuestaUsuarioResource {

    private static final Logger LOG = LoggerFactory.getLogger(RespuestaUsuarioResource.class);

    private static final String ENTITY_NAME = "testDonesEspiritualesRespuestaUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RespuestaUsuarioService respuestaUsuarioService;

    private final RespuestaUsuarioRepository respuestaUsuarioRepository;

    private final RespuestaUsuarioQueryService respuestaUsuarioQueryService;

    public RespuestaUsuarioResource(
        RespuestaUsuarioService respuestaUsuarioService,
        RespuestaUsuarioRepository respuestaUsuarioRepository,
        RespuestaUsuarioQueryService respuestaUsuarioQueryService
    ) {
        this.respuestaUsuarioService = respuestaUsuarioService;
        this.respuestaUsuarioRepository = respuestaUsuarioRepository;
        this.respuestaUsuarioQueryService = respuestaUsuarioQueryService;
    }

    /**
     * {@code POST  /respuesta-usuarios} : Create a new respuestaUsuario.
     *
     * @param respuestaUsuarioDTO the respuestaUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new respuestaUsuarioDTO, or with status {@code 400 (Bad Request)} if the respuestaUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RespuestaUsuarioDTO> createRespuestaUsuario(@Valid @RequestBody RespuestaUsuarioDTO respuestaUsuarioDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save RespuestaUsuario : {}", respuestaUsuarioDTO);
        if (respuestaUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new respuestaUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        respuestaUsuarioDTO = respuestaUsuarioService.save(respuestaUsuarioDTO);
        return ResponseEntity.created(new URI("/api/respuesta-usuarios/" + respuestaUsuarioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, respuestaUsuarioDTO.getId().toString()))
            .body(respuestaUsuarioDTO);
    }

    /**
     * {@code PUT  /respuesta-usuarios/:id} : Updates an existing respuestaUsuario.
     *
     * @param id the id of the respuestaUsuarioDTO to save.
     * @param respuestaUsuarioDTO the respuestaUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated respuestaUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the respuestaUsuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the respuestaUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> updateRespuestaUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RespuestaUsuarioDTO respuestaUsuarioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RespuestaUsuario : {}, {}", id, respuestaUsuarioDTO);
        if (respuestaUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, respuestaUsuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!respuestaUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        respuestaUsuarioDTO = respuestaUsuarioService.update(respuestaUsuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, respuestaUsuarioDTO.getId().toString()))
            .body(respuestaUsuarioDTO);
    }

    /**
     * {@code PATCH  /respuesta-usuarios/:id} : Partial updates given fields of an existing respuestaUsuario, field will ignore if it is null
     *
     * @param id the id of the respuestaUsuarioDTO to save.
     * @param respuestaUsuarioDTO the respuestaUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated respuestaUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the respuestaUsuarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the respuestaUsuarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the respuestaUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RespuestaUsuarioDTO> partialUpdateRespuestaUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RespuestaUsuarioDTO respuestaUsuarioDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RespuestaUsuario partially : {}, {}", id, respuestaUsuarioDTO);
        if (respuestaUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, respuestaUsuarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!respuestaUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RespuestaUsuarioDTO> result = respuestaUsuarioService.partialUpdate(respuestaUsuarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, respuestaUsuarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /respuesta-usuarios} : get all the respuestaUsuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of respuestaUsuarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RespuestaUsuarioDTO>> getAllRespuestaUsuarios(
        RespuestaUsuarioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RespuestaUsuarios by criteria: {}", criteria);

        Page<RespuestaUsuarioDTO> page = respuestaUsuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /respuesta-usuarios/count} : count all the respuestaUsuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRespuestaUsuarios(RespuestaUsuarioCriteria criteria) {
        LOG.debug("REST request to count RespuestaUsuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(respuestaUsuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /respuesta-usuarios/:id} : get the "id" respuestaUsuario.
     *
     * @param id the id of the respuestaUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the respuestaUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> getRespuestaUsuario(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RespuestaUsuario : {}", id);
        Optional<RespuestaUsuarioDTO> respuestaUsuarioDTO = respuestaUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(respuestaUsuarioDTO);
    }

    /**
     * {@code DELETE  /respuesta-usuarios/:id} : delete the "id" respuestaUsuario.
     *
     * @param id the id of the respuestaUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRespuestaUsuario(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RespuestaUsuario : {}", id);
        respuestaUsuarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
