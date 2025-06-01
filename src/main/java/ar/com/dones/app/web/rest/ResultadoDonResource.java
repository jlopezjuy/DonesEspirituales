package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.ResultadoDonRepository;
import ar.com.dones.app.service.ResultadoDonService;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.ResultadoDon}.
 */
@RestController
@RequestMapping("/api/resultado-dons")
public class ResultadoDonResource {

  private static final Logger LOG = LoggerFactory.getLogger(ResultadoDonResource.class);

  private static final String ENTITY_NAME = "testDonesEspiritualesResultadoDon";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ResultadoDonService resultadoDonService;

  private final ResultadoDonRepository resultadoDonRepository;

  public ResultadoDonResource(ResultadoDonService resultadoDonService, ResultadoDonRepository resultadoDonRepository) {
    this.resultadoDonService = resultadoDonService;
    this.resultadoDonRepository = resultadoDonRepository;
  }

  /**
   * {@code POST  /resultado-dons} : Create a new resultadoDon.
   *
   * @param resultadoDonDTO the resultadoDonDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadoDonDTO, or with status {@code 400 (Bad Request)} if the resultadoDon has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<ResultadoDonDTO> createResultadoDon(@Valid @RequestBody ResultadoDonDTO resultadoDonDTO) throws URISyntaxException {
    LOG.debug("REST request to save ResultadoDon : {}", resultadoDonDTO);
    if (resultadoDonDTO.getId() != null) {
      throw new BadRequestAlertException("A new resultadoDon cannot already have an ID", ENTITY_NAME, "idexists");
    }
    resultadoDonDTO = resultadoDonService.save(resultadoDonDTO);
    return ResponseEntity.created(new URI("/api/resultado-dons/" + resultadoDonDTO.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, resultadoDonDTO.getId().toString()))
      .body(resultadoDonDTO);
  }

  /**
   * {@code PUT  /resultado-dons/:id} : Updates an existing resultadoDon.
   *
   * @param id the id of the resultadoDonDTO to save.
   * @param resultadoDonDTO the resultadoDonDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoDonDTO,
   * or with status {@code 400 (Bad Request)} if the resultadoDonDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the resultadoDonDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ResultadoDonDTO> updateResultadoDon(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody ResultadoDonDTO resultadoDonDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to update ResultadoDon : {}, {}", id, resultadoDonDTO);
    if (resultadoDonDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, resultadoDonDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!resultadoDonRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    resultadoDonDTO = resultadoDonService.update(resultadoDonDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultadoDonDTO.getId().toString()))
      .body(resultadoDonDTO);
  }

  /**
   * {@code PATCH  /resultado-dons/:id} : Partial updates given fields of an existing resultadoDon, field will ignore if it is null
   *
   * @param id the id of the resultadoDonDTO to save.
   * @param resultadoDonDTO the resultadoDonDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoDonDTO,
   * or with status {@code 400 (Bad Request)} if the resultadoDonDTO is not valid,
   * or with status {@code 404 (Not Found)} if the resultadoDonDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the resultadoDonDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<ResultadoDonDTO> partialUpdateResultadoDon(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody ResultadoDonDTO resultadoDonDTO
  ) throws URISyntaxException {
    LOG.debug("REST request to partial update ResultadoDon partially : {}, {}", id, resultadoDonDTO);
    if (resultadoDonDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, resultadoDonDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!resultadoDonRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<ResultadoDonDTO> result = resultadoDonService.partialUpdate(resultadoDonDTO);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultadoDonDTO.getId().toString())
    );
  }

  /**
   * {@code GET  /resultado-dons} : get all the resultadoDons.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadoDons in body.
   */
  @GetMapping("")
  public ResponseEntity<List<ResultadoDonDTO>> getAllResultadoDons(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
    LOG.debug("REST request to get a page of ResultadoDons");
    Page<ResultadoDonDTO> page = resultadoDonService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /resultado-dons/:id} : get the "id" resultadoDon.
   *
   * @param id the id of the resultadoDonDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadoDonDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResultadoDonDTO> getResultadoDon(@PathVariable("id") Long id) {
    LOG.debug("REST request to get ResultadoDon : {}", id);
    Optional<ResultadoDonDTO> resultadoDonDTO = resultadoDonService.findOne(id);
    return ResponseUtil.wrapOrNotFound(resultadoDonDTO);
  }

  /**
   * {@code DELETE  /resultado-dons/:id} : delete the "id" resultadoDon.
   *
   * @param id the id of the resultadoDonDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteResultadoDon(@PathVariable("id") Long id) {
    LOG.debug("REST request to delete ResultadoDon : {}", id);
    resultadoDonService.delete(id);
    return ResponseEntity.noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
