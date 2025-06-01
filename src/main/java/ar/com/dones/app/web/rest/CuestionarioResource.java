package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.CuestionarioRepository;
import ar.com.dones.app.service.CuestionarioService;
import ar.com.dones.app.service.dto.CuestionarioDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.Cuestionario}.
 */
@RestController
@RequestMapping("/api/cuestionarios")
public class CuestionarioResource {

  private static final Logger LOG = LoggerFactory.getLogger(CuestionarioResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesCuestionario";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CuestionarioService cuestionarioService;

  private final CuestionarioRepository cuestionarioRepository;

  public CuestionarioResource(CuestionarioService cuestionarioService, CuestionarioRepository cuestionarioRepository) {
    this.cuestionarioService = cuestionarioService;
    this.cuestionarioRepository = cuestionarioRepository;
  }

  /**
   * {@code POST  /cuestionarios} : Create a new cuestionario.
   *
   * @param cuestionarioDTO the cuestionarioDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuestionarioDTO, or with status {@code 400 (Bad Request)} if the cuestionario has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<CuestionarioDTO> createCuestionario(@Valid @RequestBody CuestionarioDTO cuestionarioDTO) throws URISyntaxException {
    LOG.debug("REST request to save Cuestionario : {}", cuestionarioDTO);
    if (cuestionarioDTO.getId() != null) {
      throw new BadRequestAlertException("A new cuestionario cannot already have an ID", ENTITY_NAME, "idexists");
    }
    cuestionarioDTO = cuestionarioService.save(cuestionarioDTO);
    return ResponseEntity.created(new URI("/api/cuestionarios/" + cuestionarioDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cuestionarioDTO.getId().toString()))
      .body(cuestionarioDTO);
  }

  /**
   * {@code PUT  /cuestionarios/:id} : Updates an existing cuestionario.
   *
   * @param id the id of the cuestionarioDTO to save.
   * @param cuestionarioDTO the cuestionarioDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuestionarioDTO,
   * or with status {@code 400 (Bad Request)} if the cuestionarioDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the cuestionarioDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CuestionarioDTO> updateCuestionario(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody CuestionarioDTO cuestionarioDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update Cuestionario : {}, {}", id, cuestionarioDTO);
    if (cuestionarioDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, cuestionarioDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!cuestionarioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    cuestionarioDTO = cuestionarioService.update(cuestionarioDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuestionarioDTO.getId().toString()))
      .body(cuestionarioDTO);
  }

  /**
   * {@code PATCH  /cuestionarios/:id} : Partial updates given fields of an existing cuestionario, field will ignore if it is null
   *
   * @param id the id of the cuestionarioDTO to save.
   * @param cuestionarioDTO the cuestionarioDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuestionarioDTO,
   * or with status {@code 400 (Bad Request)} if the cuestionarioDTO is not valid,
   * or with status {@code 404 (Not Found)} if the cuestionarioDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the cuestionarioDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<CuestionarioDTO> partialUpdateCuestionario(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody CuestionarioDTO cuestionarioDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update Cuestionario partially : {}, {}", id, cuestionarioDTO);
    if (cuestionarioDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, cuestionarioDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!cuestionarioRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<CuestionarioDTO> result = cuestionarioService.partialUpdate(cuestionarioDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuestionarioDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /cuestionarios} : get all the cuestionarios.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuestionarios in body.
   */
  @GetMapping("")
  public ResponseEntity<List<CuestionarioDTO>> getAllCuestionarios(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
    LOG.debug("REST request to get a page of Cuestionarios");
    Page<CuestionarioDTO> page = cuestionarioService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /cuestionarios/:id} : get the "id" cuestionario.
   *
   * @param id the id of the cuestionarioDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuestionarioDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CuestionarioDTO> getCuestionario(@PathVariable("id") Long id) {
    LOG.debug("REST request to get Cuestionario : {}", id);
    Optional<CuestionarioDTO> cuestionarioDTO = cuestionarioService.findOne(id);
    return ResponseUtil.wrapOrNotFound(cuestionarioDTO);
  }

  /**
   * {@code DELETE  /cuestionarios/:id} : delete the "id" cuestionario.
   *
   * @param id the id of the cuestionarioDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCuestionario(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete Cuestionario : {}", id);
    cuestionarioService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
