package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.EscalaRespuestaRepository;
import ar.com.dones.app.service.EscalaRespuestaService;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.EscalaRespuesta}.
 */
@RestController
@RequestMapping("/api/escala-respuestas")
public class EscalaRespuestaResource {

  private static final Logger LOG = LoggerFactory.getLogger(EscalaRespuestaResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesEscalaRespuesta";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final EscalaRespuestaService escalaRespuestaService;

  private final EscalaRespuestaRepository escalaRespuestaRepository;

  public EscalaRespuestaResource(EscalaRespuestaService escalaRespuestaService, EscalaRespuestaRepository escalaRespuestaRepository) {
    this.escalaRespuestaService = escalaRespuestaService;
    this.escalaRespuestaRepository = escalaRespuestaRepository;
  }

  /**
   * {@code POST  /escala-respuestas} : Create a new escalaRespuesta.
   *
   * @param escalaRespuestaDTO the escalaRespuestaDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escalaRespuestaDTO, or with status {@code 400 (Bad Request)} if the escalaRespuesta has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<EscalaRespuestaDTO> createEscalaRespuesta(@Valid @RequestBody EscalaRespuestaDTO escalaRespuestaDTO)
    throws URISyntaxException {
    LOG.debug("REST request to save EscalaRespuesta : {}", escalaRespuestaDTO);
    if (escalaRespuestaDTO.getId() != null) {
      throw new BadRequestAlertException("A new escalaRespuesta cannot already have an ID", ENTITY_NAME, "idexists");
    }
    escalaRespuestaDTO = escalaRespuestaService.save(escalaRespuestaDTO);
    return ResponseEntity.created(new URI("/api/escala-respuestas/" + escalaRespuestaDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, escalaRespuestaDTO.getId().toString()))
      .body(escalaRespuestaDTO);
  }

  /**
   * {@code PUT  /escala-respuestas/:id} : Updates an existing escalaRespuesta.
   *
   * @param id the id of the escalaRespuestaDTO to save.
   * @param escalaRespuestaDTO the escalaRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escalaRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the escalaRespuestaDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the escalaRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<EscalaRespuestaDTO> updateEscalaRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody EscalaRespuestaDTO escalaRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update EscalaRespuesta : {}, {}", id, escalaRespuestaDTO);
    if (escalaRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, escalaRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!escalaRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    escalaRespuestaDTO = escalaRespuestaService.update(escalaRespuestaDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escalaRespuestaDTO.getId().toString()))
      .body(escalaRespuestaDTO);
  }

  /**
   * {@code PATCH  /escala-respuestas/:id} : Partial updates given fields of an existing escalaRespuesta, field will ignore if it is null
   *
   * @param id the id of the escalaRespuestaDTO to save.
   * @param escalaRespuestaDTO the escalaRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escalaRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the escalaRespuestaDTO is not valid,
   * or with status {@code 404 (Not Found)} if the escalaRespuestaDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the escalaRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<EscalaRespuestaDTO> partialUpdateEscalaRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody EscalaRespuestaDTO escalaRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update EscalaRespuesta partially : {}, {}", id, escalaRespuestaDTO);
    if (escalaRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, escalaRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!escalaRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<EscalaRespuestaDTO> result = escalaRespuestaService.partialUpdate(escalaRespuestaDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escalaRespuestaDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /escala-respuestas} : get all the escalaRespuestas.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escalaRespuestas in body.
   */
  @GetMapping("")
  public ResponseEntity<List<EscalaRespuestaDTO>> getAllEscalaRespuestas(
    @org.springdoc.core.annotations.ParameterObject Pageable pageable
  ) {
    LOG.debug("REST request to get a page of EscalaRespuestas");
    Page<EscalaRespuestaDTO> page = escalaRespuestaService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /escala-respuestas/:id} : get the "id" escalaRespuesta.
   *
   * @param id the id of the escalaRespuestaDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escalaRespuestaDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<EscalaRespuestaDTO> getEscalaRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to get EscalaRespuesta : {}", id);
    Optional<EscalaRespuestaDTO> escalaRespuestaDTO = escalaRespuestaService.findOne(id);
    return ResponseUtil.wrapOrNotFound(escalaRespuestaDTO);
  }

  /**
   * {@code DELETE  /escala-respuestas/:id} : delete the "id" escalaRespuesta.
   *
   * @param id the id of the escalaRespuestaDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEscalaRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete EscalaRespuesta : {}", id);
    escalaRespuestaService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
