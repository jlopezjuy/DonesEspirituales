package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.InterpretacionRepository;
import ar.com.dones.app.service.InterpretacionService;
import ar.com.dones.app.service.dto.InterpretacionDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.Interpretacion}.
 */
@RestController
@RequestMapping("/api/interpretacions")
public class InterpretacionResource {

  private static final Logger LOG = LoggerFactory.getLogger(InterpretacionResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesInterpretacion";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final InterpretacionService interpretacionService;

  private final InterpretacionRepository interpretacionRepository;

  public InterpretacionResource(InterpretacionService interpretacionService, InterpretacionRepository interpretacionRepository) {
    this.interpretacionService = interpretacionService;
    this.interpretacionRepository = interpretacionRepository;
  }

  /**
   * {@code POST  /interpretacions} : Create a new interpretacion.
   *
   * @param interpretacionDTO the interpretacionDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interpretacionDTO, or with status {@code 400 (Bad Request)} if the interpretacion has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<InterpretacionDTO> createInterpretacion(@Valid @RequestBody InterpretacionDTO interpretacionDTO)
    throws URISyntaxException {
    LOG.debug("REST request to save Interpretacion : {}", interpretacionDTO);
    if (interpretacionDTO.getId() != null) {
      throw new BadRequestAlertException("A new interpretacion cannot already have an ID", ENTITY_NAME, "idexists");
    }
    interpretacionDTO = interpretacionService.save(interpretacionDTO);
    return ResponseEntity.created(new URI("/api/interpretacions/" + interpretacionDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, interpretacionDTO.getId().toString()))
      .body(interpretacionDTO);
  }

  /**
   * {@code PUT  /interpretacions/:id} : Updates an existing interpretacion.
   *
   * @param id the id of the interpretacionDTO to save.
   * @param interpretacionDTO the interpretacionDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interpretacionDTO,
   * or with status {@code 400 (Bad Request)} if the interpretacionDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the interpretacionDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<InterpretacionDTO> updateInterpretacion(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody InterpretacionDTO interpretacionDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update Interpretacion : {}, {}", id, interpretacionDTO);
    if (interpretacionDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, interpretacionDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!interpretacionRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    interpretacionDTO = interpretacionService.update(interpretacionDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interpretacionDTO.getId().toString()))
      .body(interpretacionDTO);
  }

  /**
   * {@code PATCH  /interpretacions/:id} : Partial updates given fields of an existing interpretacion, field will ignore if it is null
   *
   * @param id the id of the interpretacionDTO to save.
   * @param interpretacionDTO the interpretacionDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interpretacionDTO,
   * or with status {@code 400 (Bad Request)} if the interpretacionDTO is not valid,
   * or with status {@code 404 (Not Found)} if the interpretacionDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the interpretacionDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<InterpretacionDTO> partialUpdateInterpretacion(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody InterpretacionDTO interpretacionDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update Interpretacion partially : {}, {}", id, interpretacionDTO);
    if (interpretacionDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, interpretacionDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!interpretacionRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<InterpretacionDTO> result = interpretacionService.partialUpdate(interpretacionDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interpretacionDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /interpretacions} : get all the interpretacions.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interpretacions in body.
   */
  @GetMapping("")
  public ResponseEntity<List<InterpretacionDTO>> getAllInterpretacions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
    LOG.debug("REST request to get a page of Interpretacions");
    Page<InterpretacionDTO> page = interpretacionService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /interpretacions/:id} : get the "id" interpretacion.
   *
   * @param id the id of the interpretacionDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interpretacionDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<InterpretacionDTO> getInterpretacion(@PathVariable("id") Long id) {
    LOG.debug("REST request to get Interpretacion : {}", id);
    Optional<InterpretacionDTO> interpretacionDTO = interpretacionService.findOne(id);
    return ResponseUtil.wrapOrNotFound(interpretacionDTO);
  }

  /**
   * {@code DELETE  /interpretacions/:id} : delete the "id" interpretacion.
   *
   * @param id the id of the interpretacionDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInterpretacion(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete Interpretacion : {}", id);
    interpretacionService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
