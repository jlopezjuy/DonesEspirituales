package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.PreguntaRepository;
import ar.com.dones.app.service.PreguntaService;
import ar.com.dones.app.service.dto.PreguntaDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.dones.app.domain.Pregunta}.
 */
@RestController
@RequestMapping("/api/preguntas")
public class PreguntaResource {

    private static final Logger LOG = LoggerFactory.getLogger(PreguntaResource.class);

    private static final String ENTITY_NAME = "testDonesEspiritualesPregunta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreguntaService preguntaService;

    private final PreguntaRepository preguntaRepository;

    public PreguntaResource(PreguntaService preguntaService, PreguntaRepository preguntaRepository) {
        this.preguntaService = preguntaService;
        this.preguntaRepository = preguntaRepository;
    }

    /**
     * {@code POST  /preguntas} : Create a new pregunta.
     *
     * @param preguntaDTO the preguntaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preguntaDTO, or with status {@code 400 (Bad Request)} if the pregunta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PreguntaDTO> createPregunta(@Valid @RequestBody PreguntaDTO preguntaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Pregunta : {}", preguntaDTO);
        if (preguntaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pregunta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        preguntaDTO = preguntaService.save(preguntaDTO);
        return ResponseEntity.created(new URI("/api/preguntas/" + preguntaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, preguntaDTO.getId().toString()))
            .body(preguntaDTO);
    }

    /**
     * {@code PUT  /preguntas/:id} : Updates an existing pregunta.
     *
     * @param id the id of the preguntaDTO to save.
     * @param preguntaDTO the preguntaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntaDTO,
     * or with status {@code 400 (Bad Request)} if the preguntaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preguntaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> updatePregunta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PreguntaDTO preguntaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Pregunta : {}, {}", id, preguntaDTO);
        if (preguntaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preguntaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preguntaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        preguntaDTO = preguntaService.update(preguntaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntaDTO.getId().toString()))
            .body(preguntaDTO);
    }

    /**
     * {@code PATCH  /preguntas/:id} : Partial updates given fields of an existing pregunta, field will ignore if it is null
     *
     * @param id the id of the preguntaDTO to save.
     * @param preguntaDTO the preguntaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntaDTO,
     * or with status {@code 400 (Bad Request)} if the preguntaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the preguntaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the preguntaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PreguntaDTO> partialUpdatePregunta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PreguntaDTO preguntaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Pregunta partially : {}, {}", id, preguntaDTO);
        if (preguntaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preguntaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preguntaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreguntaDTO> result = preguntaService.partialUpdate(preguntaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /preguntas} : get all the preguntas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preguntas in body.
     */
    @GetMapping("")
    public List<PreguntaDTO> getAllPreguntas() {
        LOG.debug("REST request to get all Preguntas");
        return preguntaService.findAll();
    }

    /**
     * {@code GET  /preguntas/:id} : get the "id" pregunta.
     *
     * @param id the id of the preguntaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preguntaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> getPregunta(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Pregunta : {}", id);
        Optional<PreguntaDTO> preguntaDTO = preguntaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preguntaDTO);
    }

    /**
     * {@code DELETE  /preguntas/:id} : delete the "id" pregunta.
     *
     * @param id the id of the preguntaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePregunta(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Pregunta : {}", id);
        preguntaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
