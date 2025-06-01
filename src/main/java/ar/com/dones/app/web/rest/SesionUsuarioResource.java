package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.SesionUsuarioRepository;
import ar.com.dones.app.service.SesionUsuarioService;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.SesionUsuario}.
 */
@RestController
@RequestMapping("/api/sesion-usuarios")
public class SesionUsuarioResource {

  private static final Logger LOG = LoggerFactory.getLogger(SesionUsuarioResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesSesionUsuario";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final SesionUsuarioService sesionUsuarioService;

  private final SesionUsuarioRepository sesionUsuarioRepository;

  public SesionUsuarioResource(SesionUsuarioService sesionUsuarioService, SesionUsuarioRepository sesionUsuarioRepository) {
    this.sesionUsuarioService = sesionUsuarioService;
    this.sesionUsuarioRepository = sesionUsuarioRepository;
  }

  /**
   * {@code POST  /sesion-usuarios} : Create a new sesionUsuario.
   *
   * @param sesionUsuarioDTO the sesionUsuarioDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sesionUsuarioDTO, or with status {@code 400 (Bad Request)} if the sesionUsuario has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<SesionUsuarioDTO> createSesionUsuario(@Valid @RequestBody SesionUsuarioDTO sesionUsuarioDTO)
    throws URISyntaxException {
    LOG.debug("REST request to save SesionUsuario : {}", sesionUsuarioDTO);
    if (sesionUsuarioDTO.getId() != null) {
      throw new BadRequestAlertException("A new sesionUsuario cannot already have an ID", ENTITY_NAME, "idexists");
    }
    sesionUsuarioDTO = sesionUsuarioService.save(sesionUsuarioDTO);
    return ResponseEntity.created(new URI("/api/sesion-usuarios/" + sesionUsuarioDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sesionUsuarioDTO.getId().toString()))
      .body(sesionUsuarioDTO);
  }

  /**
   * {@code PUT  /sesion-usuarios/:id} : Updates an existing sesionUsuario.
   *
   * @param id the id of the sesionUsuarioDTO to save.
   * @param sesionUsuarioDTO the sesionUsuarioDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sesionUsuarioDTO,
   * or with status {@code 400 (Bad Request)} if the sesionUsuarioDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the sesionUsuarioDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<SesionUsuarioDTO> updateSesionUsuario(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody SesionUsuarioDTO sesionUsuarioDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update SesionUsuario : {}, {}", id, sesionUsuarioDTO);
    if (sesionUsuarioDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, sesionUsuarioDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!sesionUsuarioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    sesionUsuarioDTO = sesionUsuarioService.update(sesionUsuarioDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sesionUsuarioDTO.getId().toString()))
      .body(sesionUsuarioDTO);
  }

  /**
   * {@code PATCH  /sesion-usuarios/:id} : Partial updates given fields of an existing sesionUsuario, field will ignore if it is null
   *
   * @param id the id of the sesionUsuarioDTO to save.
   * @param sesionUsuarioDTO the sesionUsuarioDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sesionUsuarioDTO,
   * or with status {@code 400 (Bad Request)} if the sesionUsuarioDTO is not valid,
   * or with status {@code 404 (Not Found)} if the sesionUsuarioDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the sesionUsuarioDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<SesionUsuarioDTO> partialUpdateSesionUsuario(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody SesionUsuarioDTO sesionUsuarioDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update SesionUsuario partially : {}, {}", id, sesionUsuarioDTO);
    if (sesionUsuarioDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, sesionUsuarioDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!sesionUsuarioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<SesionUsuarioDTO> result = sesionUsuarioService.partialUpdate(sesionUsuarioDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sesionUsuarioDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /sesion-usuarios} : get all the sesionUsuarios.
   *
   * @param pageable the pagination information.
   * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sesionUsuarios in body.
   */
  @GetMapping("")
  public ResponseEntity<List<SesionUsuarioDTO>> getAllSesionUsuarios(
    @org.springdoc.core.annotations.ParameterObject Pageable pageable,
    @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
  ) {
    LOG.debug("REST request to get a page of SesionUsuarios");
    Page<SesionUsuarioDTO> page;
    if (eagerload) {
      page = sesionUsuarioService.findAllWithEagerRelationships(pageable);
    } else {
      page = sesionUsuarioService.findAll(pageable);
    }
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /sesion-usuarios/:id} : get the "id" sesionUsuario.
   *
   * @param id the id of the sesionUsuarioDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sesionUsuarioDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<SesionUsuarioDTO> getSesionUsuario(@PathVariable("id") Long id) {
    LOG.debug("REST request to get SesionUsuario : {}", id);
    Optional<SesionUsuarioDTO> sesionUsuarioDTO = sesionUsuarioService.findOne(id);
    return ResponseUtil.wrapOrNotFound(sesionUsuarioDTO);
  }

  /**
   * {@code DELETE  /sesion-usuarios/:id} : delete the "id" sesionUsuario.
   *
   * @param id the id of the sesionUsuarioDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSesionUsuario(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete SesionUsuario : {}", id);
    sesionUsuarioService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
