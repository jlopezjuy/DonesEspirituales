package ar.com.dones.app.web.rest;

import ar.com.dones.app.repository.DonEspiritualRepository;
import ar.com.dones.app.service.DonEspiritualQueryService;
import ar.com.dones.app.service.DonEspiritualService;
import ar.com.dones.app.service.criteria.DonEspiritualCriteria;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
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
 * REST controller for managing {@link ar.com.dones.app.domain.DonEspiritual}.
 */
@RestController
@RequestMapping("/api/don-espirituals")
public class DonEspiritualResource {

    private static final Logger LOG = LoggerFactory.getLogger(DonEspiritualResource.class);

    private static final String ENTITY_NAME = "testDonesEspiritualesDonEspiritual";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonEspiritualService donEspiritualService;

    private final DonEspiritualRepository donEspiritualRepository;

    private final DonEspiritualQueryService donEspiritualQueryService;

    public DonEspiritualResource(
        DonEspiritualService donEspiritualService,
        DonEspiritualRepository donEspiritualRepository,
        DonEspiritualQueryService donEspiritualQueryService
    ) {
        this.donEspiritualService = donEspiritualService;
        this.donEspiritualRepository = donEspiritualRepository;
        this.donEspiritualQueryService = donEspiritualQueryService;
    }

    /**
     * {@code POST  /don-espirituals} : Create a new donEspiritual.
     *
     * @param donEspiritualDTO the donEspiritualDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donEspiritualDTO, or with status {@code 400 (Bad Request)} if the donEspiritual has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DonEspiritualDTO> createDonEspiritual(@Valid @RequestBody DonEspiritualDTO donEspiritualDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DonEspiritual : {}", donEspiritualDTO);
        if (donEspiritualDTO.getId() != null) {
            throw new BadRequestAlertException("A new donEspiritual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        donEspiritualDTO = donEspiritualService.save(donEspiritualDTO);
        return ResponseEntity.created(new URI("/api/don-espirituals/" + donEspiritualDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, donEspiritualDTO.getId().toString()))
            .body(donEspiritualDTO);
    }

    /**
     * {@code PUT  /don-espirituals/:id} : Updates an existing donEspiritual.
     *
     * @param id the id of the donEspiritualDTO to save.
     * @param donEspiritualDTO the donEspiritualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donEspiritualDTO,
     * or with status {@code 400 (Bad Request)} if the donEspiritualDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donEspiritualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DonEspiritualDTO> updateDonEspiritual(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonEspiritualDTO donEspiritualDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DonEspiritual : {}, {}", id, donEspiritualDTO);
        if (donEspiritualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donEspiritualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donEspiritualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        donEspiritualDTO = donEspiritualService.update(donEspiritualDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donEspiritualDTO.getId().toString()))
            .body(donEspiritualDTO);
    }

    /**
     * {@code PATCH  /don-espirituals/:id} : Partial updates given fields of an existing donEspiritual, field will ignore if it is null
     *
     * @param id the id of the donEspiritualDTO to save.
     * @param donEspiritualDTO the donEspiritualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donEspiritualDTO,
     * or with status {@code 400 (Bad Request)} if the donEspiritualDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donEspiritualDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donEspiritualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonEspiritualDTO> partialUpdateDonEspiritual(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonEspiritualDTO donEspiritualDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DonEspiritual partially : {}, {}", id, donEspiritualDTO);
        if (donEspiritualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donEspiritualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donEspiritualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonEspiritualDTO> result = donEspiritualService.partialUpdate(donEspiritualDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donEspiritualDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /don-espirituals} : get all the donEspirituals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donEspirituals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DonEspiritualDTO>> getAllDonEspirituals(DonEspiritualCriteria criteria) {
        LOG.debug("REST request to get DonEspirituals by criteria: {}", criteria);

        List<DonEspiritualDTO> entityList = donEspiritualQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /don-espirituals/count} : count all the donEspirituals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDonEspirituals(DonEspiritualCriteria criteria) {
        LOG.debug("REST request to count DonEspirituals by criteria: {}", criteria);
        return ResponseEntity.ok().body(donEspiritualQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /don-espirituals/:id} : get the "id" donEspiritual.
     *
     * @param id the id of the donEspiritualDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donEspiritualDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DonEspiritualDTO> getDonEspiritual(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DonEspiritual : {}", id);
        Optional<DonEspiritualDTO> donEspiritualDTO = donEspiritualService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donEspiritualDTO);
    }

    /**
     * {@code DELETE  /don-espirituals/:id} : delete the "id" donEspiritual.
     *
     * @param id the id of the donEspiritualDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonEspiritual(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DonEspiritual : {}", id);
        donEspiritualService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
