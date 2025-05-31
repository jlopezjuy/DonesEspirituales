package ar.com.dones.app.service;

import ar.com.dones.app.domain.*; // for static metamodels
import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.repository.DonEspiritualRepository;
import ar.com.dones.app.service.criteria.DonEspiritualCriteria;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.mapper.DonEspiritualMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DonEspiritual} entities in the database.
 * The main input is a {@link DonEspiritualCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DonEspiritualDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DonEspiritualQueryService extends QueryService<DonEspiritual> {

    private static final Logger LOG = LoggerFactory.getLogger(DonEspiritualQueryService.class);

    private final DonEspiritualRepository donEspiritualRepository;

    private final DonEspiritualMapper donEspiritualMapper;

    public DonEspiritualQueryService(DonEspiritualRepository donEspiritualRepository, DonEspiritualMapper donEspiritualMapper) {
        this.donEspiritualRepository = donEspiritualRepository;
        this.donEspiritualMapper = donEspiritualMapper;
    }

    /**
     * Return a {@link List} of {@link DonEspiritualDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DonEspiritualDTO> findByCriteria(DonEspiritualCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<DonEspiritual> specification = createSpecification(criteria);
        return donEspiritualMapper.toDto(donEspiritualRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DonEspiritualCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<DonEspiritual> specification = createSpecification(criteria);
        return donEspiritualRepository.count(specification);
    }

    /**
     * Function to convert {@link DonEspiritualCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DonEspiritual> createSpecification(DonEspiritualCriteria criteria) {
        Specification<DonEspiritual> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), DonEspiritual_.id),
                buildStringSpecification(criteria.getNombre(), DonEspiritual_.nombre),
                buildStringSpecification(criteria.getNombreCorto(), DonEspiritual_.nombreCorto),
                buildSpecification(criteria.getActivo(), DonEspiritual_.activo),
                buildRangeSpecification(criteria.getOrdenPresentacion(), DonEspiritual_.ordenPresentacion),
                buildSpecification(criteria.getPreguntaDonesId(), root ->
                    root.join(DonEspiritual_.preguntaDones, JoinType.LEFT).get(PreguntaDon_.id)
                ),
                buildSpecification(criteria.getResultadoDonesId(), root ->
                    root.join(DonEspiritual_.resultadoDones, JoinType.LEFT).get(ResultadoDon_.id)
                ),
                buildSpecification(criteria.getInterpretacionesId(), root ->
                    root.join(DonEspiritual_.interpretaciones, JoinType.LEFT).get(Interpretacion_.id)
                )
            );
        }
        return specification;
    }
}
