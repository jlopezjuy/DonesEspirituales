package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.DetalleRespuestaRepository;
import ar.com.dones.app.service.DetalleRespuestaService;
import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.DetalleRespuesta}.
 */
@RestController
@RequestMapping("/api/detalle-respuestas")
public class DetalleRespuestaResource {

  private static final Logger LOG = LoggerFactory.getLogger(DetalleRespuestaResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesDetalleRespuesta";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final DetalleRespuestaService detalleRespuestaService;

  private final DetalleRespuestaRepository detalleRespuestaRepository;

  public DetalleRespuestaResource(DetalleRespuestaService detalleRespuestaService, DetalleRespuestaRepository detalleRespuestaRepository) {
    this.detalleRespuestaService = detalleRespuestaService;
    this.detalleRespuestaRepository = detalleRespuestaRepository;
  }

  /**
   * {@code POST  /detalle-respuestas} : Create a new detalleRespuesta.
   *
   * @param detalleRespuestaDTO the detalleRespuestaDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalleRespuestaDTO, or with status {@code 400 (Bad Request)} if the detalleRespuesta has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<DetalleRespuestaDTO> createDetalleRespuesta(@Valid @RequestBody DetalleRespuestaDTO detalleRespuestaDTO)
    throws URISyntaxException {
    LOG.debug("REST request to save DetalleRespuesta : {}", detalleRespuestaDTO);
    if (detalleRespuestaDTO.getId() != null) {
      throw new BadRequestAlertException("A new detalleRespuesta cannot already have an ID", ENTITY_NAME, "idexists");
    }
    detalleRespuestaDTO = detalleRespuestaService.save(detalleRespuestaDTO);
    return ResponseEntity.created(new URI("/api/detalle-respuestas/" + detalleRespuestaDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, detalleRespuestaDTO.getId().toString()))
      .body(detalleRespuestaDTO);
  }

  /**
   * {@code PUT  /detalle-respuestas/:id} : Updates an existing detalleRespuesta.
   *
   * @param id the id of the detalleRespuestaDTO to save.
   * @param detalleRespuestaDTO the detalleRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the detalleRespuestaDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the detalleRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<DetalleRespuestaDTO> updateDetalleRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody DetalleRespuestaDTO detalleRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update DetalleRespuesta : {}, {}", id, detalleRespuestaDTO);
    if (detalleRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, detalleRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!detalleRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    detalleRespuestaDTO = detalleRespuestaService.update(detalleRespuestaDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalleRespuestaDTO.getId().toString()))
      .body(detalleRespuestaDTO);
  }

  /**
   * {@code PATCH  /detalle-respuestas/:id} : Partial updates given fields of an existing detalleRespuesta, field will ignore if it is null
   *
   * @param id the id of the detalleRespuestaDTO to save.
   * @param detalleRespuestaDTO the detalleRespuestaDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalleRespuestaDTO,
   * or with status {@code 400 (Bad Request)} if the detalleRespuestaDTO is not valid,
   * or with status {@code 404 (Not Found)} if the detalleRespuestaDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the detalleRespuestaDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<DetalleRespuestaDTO> partialUpdateDetalleRespuesta(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody DetalleRespuestaDTO detalleRespuestaDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update DetalleRespuesta partially : {}, {}", id, detalleRespuestaDTO);
    if (detalleRespuestaDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, detalleRespuestaDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!detalleRespuestaRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<DetalleRespuestaDTO> result = detalleRespuestaService.partialUpdate(detalleRespuestaDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalleRespuestaDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /detalle-respuestas} : get all the detalleRespuestas.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalleRespuestas in body.
   */
  @GetMapping("")
  public ResponseEntity<List<DetalleRespuestaDTO>> getAllDetalleRespuestas(
    @org.springdoc.core.annotations.ParameterObject Pageable pageable
  ) {
    LOG.debug("REST request to get a page of DetalleRespuestas");
    Page<DetalleRespuestaDTO> page = detalleRespuestaService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /detalle-respuestas/:id} : get the "id" detalleRespuesta.
   *
   * @param id the id of the detalleRespuestaDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalleRespuestaDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<DetalleRespuestaDTO> getDetalleRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to get DetalleRespuesta : {}", id);
    Optional<DetalleRespuestaDTO> detalleRespuestaDTO = detalleRespuestaService.findOne(id);
    return ResponseUtil.wrapOrNotFound(detalleRespuestaDTO);
  }

  /**
   * {@code DELETE  /detalle-respuestas/:id} : delete the "id" detalleRespuesta.
   *
   * @param id the id of the detalleRespuestaDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDetalleRespuesta(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete DetalleRespuesta : {}", id);
    detalleRespuestaService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
