package ar.com.dones.app.service;

import ar.com.dones.app.domain.*; // for static metamodels
import ar.com.dones.app.domain.ResultadoDon;
import ar.com.dones.app.repository.ResultadoDonRepository;
import ar.com.dones.app.service.criteria.ResultadoDonCriteria;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
import ar.com.dones.app.service.mapper.ResultadoDonMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ResultadoDon} entities in the database.
 * The main input is a {@link ResultadoDonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ResultadoDonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultadoDonQueryService extends QueryService<ResultadoDon> {

    private static final Logger LOG = LoggerFactory.getLogger(ResultadoDonQueryService.class);

    private final ResultadoDonRepository resultadoDonRepository;

    private final ResultadoDonMapper resultadoDonMapper;

    public ResultadoDonQueryService(ResultadoDonRepository resultadoDonRepository, ResultadoDonMapper resultadoDonMapper) {
        this.resultadoDonRepository = resultadoDonRepository;
        this.resultadoDonMapper = resultadoDonMapper;
    }

    /**
     * Return a {@link Page} of {@link ResultadoDonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultadoDonDTO> findByCriteria(ResultadoDonCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResultadoDon> specification = createSpecification(criteria);
        return resultadoDonRepository.findAll(specification, page).map(resultadoDonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultadoDonCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ResultadoDon> specification = createSpecification(criteria);
        return resultadoDonRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultadoDonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResultadoDon> createSpecification(ResultadoDonCriteria criteria) {
        Specification<ResultadoDon> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ResultadoDon_.id),
                buildRangeSpecification(criteria.getPuntuacionTotal(), ResultadoDon_.puntuacionTotal),
                buildRangeSpecification(criteria.getPorcentaje(), ResultadoDon_.porcentaje),
                buildRangeSpecification(criteria.getRankingPosicion(), ResultadoDon_.rankingPosicion),
                buildSpecification(criteria.getEsDonPrincipal(), ResultadoDon_.esDonPrincipal),
                buildSpecification(criteria.getInterpretacionId(), root ->
                    root.join(ResultadoDon_.interpretacion, JoinType.LEFT).get(Interpretacion_.id)
                ),
                buildSpecification(criteria.getRespuestaUsuarioId(), root ->
                    root.join(ResultadoDon_.respuestaUsuario, JoinType.LEFT).get(RespuestaUsuario_.id)
                ),
                buildSpecification(criteria.getDonEspiritualId(), root ->
                    root.join(ResultadoDon_.donEspiritual, JoinType.LEFT).get(DonEspiritual_.id)
                )
            );
        }
        return specification;
    }
}
