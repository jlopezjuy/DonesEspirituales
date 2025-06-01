package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.AuditoriaRespuestaRepository;
import ar.com.dones.app.service.AuditoriaRespuestaService;
import ar.com.dones.app.service.dto.AuditoriaRespuestaDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.AuditoriaRespuesta}.
 */
@RestController
@RequestMapping("/api/auditoria-respuestas")
public class AuditoriaRespuestaResource {

  private static final Logger LOG = LoggerFactory.getLogger(AuditoriaRespuestaResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesAuditoriaRespuesta";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AuditoriaRespuestaService auditoriaRespuestaService;

  private final AuditoriaRespuestaRepository auditoriaRespuestaRepository;

  public AuditoriaRespuestaResource(
    AuditoriaRespuestaService auditoriaRespuestaService,
    AuditoriaRespuestaRepository auditoriaRespuestaRepository
  ) {
    this.auditoriaRespuestaService = auditoriaRespuestaService;
    this.auditoriaRespuestaRepository = auditoriaRespuestaRepository;
  }

  /**
   * {@code POST  /auditoria-respuestas} : Create a new auditoriaRespuesta.
   *
   * @param auditoriaRespuestaDTO the auditoriaRespuestaDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoriaRespuestaDTO, or with status {@code 400 (Bad Request)} if the auditoriaRespuesta has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<AuditoriaRespuestaDTO> createAuditoriaRespuesta(@Valid @RequestBody AuditoriaRespuestaDTO auditoriaRespuestaDTO)
    throws URISyntaxException {
    LOG.debug("REST request to save AuditoriaRespuesta : {}", auditoriaRespuestaDTO);
    if (auditoriaRespuestaDTO.getId() != null) {
      throw new BadRequestAlertException("A new auditoriaRespuesta cannot already have an ID", ENTITY_NAME, "idexists");
    }
    auditoriaRespuestaDTO = auditoriaRespuestaService.save(auditoriaRespuestaDTO);
    return ResponseEntity.created(new URI("/api/auditoria-respuestas/" + auditoriaRespuestaDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, auditoriaRespuestaDTO.getId().toString()))
      .body(auditoriaRespuestaDTO);
  }

  /**
   * {@code PUT  /auditoria-respuestas/:id} : Updates an existing auditoriaRespuesta.
   *
   * @param id the id of the auditoriaRespuestaDTO to save.
   * @param auditoriaRespuestaDTO the auditoriaRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the auditoriaRespuestaDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the auditoriaRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<AuditoriaRespuestaDTO> updateAuditoriaRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody AuditoriaRespuestaDTO auditoriaRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update AuditoriaRespuesta : {}, {}", id, auditoriaRespuestaDTO);
    if (auditoriaRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, auditoriaRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!auditoriaRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    auditoriaRespuestaDTO = auditoriaRespuestaService.update(auditoriaRespuestaDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaRespuestaDTO.getId().toString()))
      .body(auditoriaRespuestaDTO);
  }

  /**
   * {@code PATCH  /auditoria-respuestas/:id} : Partial updates given fields of an existing auditoriaRespuesta, field will ignore if it is null
   *
   * @param id the id of the auditoriaRespuestaDTO to save.
   * @param auditoriaRespuestaDTO the auditoriaRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the auditoriaRespuestaDTO is not valid,
   * or with status {@code 404 (Not Found)} if the auditoriaRespuestaDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the auditoriaRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<AuditoriaRespuestaDTO> partialUpdateAuditoriaRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody AuditoriaRespuestaDTO auditoriaRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update AuditoriaRespuesta partially : {}, {}", id, auditoriaRespuestaDTO);
    if (auditoriaRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, auditoriaRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!auditoriaRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<AuditoriaRespuestaDTO> result = auditoriaRespuestaService.partialUpdate(auditoriaRespuestaDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaRespuestaDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /auditoria-respuestas} : get all the auditoriaRespuestas.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditoriaRespuestas in body.
   */
  @GetMapping("")
  public ResponseEntity<List<AuditoriaRespuestaDTO>> getAllAuditoriaRespuestas(
    @org.springdoc.core.annotations.ParameterObject Pageable pageable
  ) {
    LOG.debug("REST request to get a page of AuditoriaRespuestas");
    Page<AuditoriaRespuestaDTO> page = auditoriaRespuestaService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /auditoria-respuestas/:id} : get the "id" auditoriaRespuesta.
   *
   * @param id the id of the auditoriaRespuestaDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoriaRespuestaDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<AuditoriaRespuestaDTO> getAuditoriaRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to get AuditoriaRespuesta : {}", id);
    Optional<AuditoriaRespuestaDTO> auditoriaRespuestaDTO = auditoriaRespuestaService.findOne(id);
    return ResponseUtil.wrapOrNotFound(auditoriaRespuestaDTO);
  }

  /**
   * {@code DELETE  /auditoria-respuestas/:id} : delete the "id" auditoriaRespuesta.
   *
   * @param id the id of the auditoriaRespuestaDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuditoriaRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete AuditoriaRespuesta : {}", id);
    auditoriaRespuestaService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
