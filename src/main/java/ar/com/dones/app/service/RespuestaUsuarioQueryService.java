package ar.com.dones.app.service;

import ar.com.dones.app.domain.*; // for static metamodels
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.service.criteria.RespuestaUsuarioCriteria;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.mapper.RespuestaUsuarioMapper;
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
 * Service for executing complex queries for {@link RespuestaUsuario} entities in the database.
 * The main input is a {@link RespuestaUsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link RespuestaUsuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RespuestaUsuarioQueryService extends QueryService<RespuestaUsuario> {

    private static final Logger LOG = LoggerFactory.getLogger(RespuestaUsuarioQueryService.class);

    private final RespuestaUsuarioRepository respuestaUsuarioRepository;

    private final RespuestaUsuarioMapper respuestaUsuarioMapper;

    public RespuestaUsuarioQueryService(
        RespuestaUsuarioRepository respuestaUsuarioRepository,
        RespuestaUsuarioMapper respuestaUsuarioMapper
    ) {
        this.respuestaUsuarioRepository = respuestaUsuarioRepository;
        this.respuestaUsuarioMapper = respuestaUsuarioMapper;
    }

    /**
     * Return a {@link Page} of {@link RespuestaUsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RespuestaUsuarioDTO> findByCriteria(RespuestaUsuarioCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RespuestaUsuario> specification = createSpecification(criteria);
        return respuestaUsuarioRepository.findAll(specification, page).map(respuestaUsuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RespuestaUsuarioCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<RespuestaUsuario> specification = createSpecification(criteria);
        return respuestaUsuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link RespuestaUsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RespuestaUsuario> createSpecification(RespuestaUsuarioCriteria criteria) {
        Specification<RespuestaUsuario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), RespuestaUsuario_.id),
                buildRangeSpecification(criteria.getFechaInicio(), RespuestaUsuario_.fechaInicio),
                buildRangeSpecification(criteria.getFechaCompletado(), RespuestaUsuario_.fechaCompletado),
                buildSpecification(criteria.getEstado(), RespuestaUsuario_.estado),
                buildRangeSpecification(criteria.getTiempoTotalSegundos(), RespuestaUsuario_.tiempoTotalSegundos),
                buildStringSpecification(criteria.getIpAddress(), RespuestaUsuario_.ipAddress),
                buildStringSpecification(criteria.getUserAgent(), RespuestaUsuario_.userAgent),
                buildSpecification(criteria.getDetalleRespuestasId(), root ->
                    root.join(RespuestaUsuario_.detalleRespuestas, JoinType.LEFT).get(DetalleRespuesta_.id)
                ),
                buildSpecification(criteria.getResultadoDonesId(), root ->
                    root.join(RespuestaUsuario_.resultadoDones, JoinType.LEFT).get(ResultadoDon_.id)
                ),
                buildSpecification(criteria.getSesionesId(), root ->
                    root.join(RespuestaUsuario_.sesiones, JoinType.LEFT).get(SesionUsuario_.id)
                ),
                buildSpecification(criteria.getAuditoriasId(), root ->
                    root.join(RespuestaUsuario_.auditorias, JoinType.LEFT).get(AuditoriaRespuesta_.id)
                ),
                buildSpecification(criteria.getUsuarioId(), root -> root.join(RespuestaUsuario_.usuario, JoinType.LEFT).get(Usuario_.id)),
                buildSpecification(criteria.getCuestionarioId(), root ->
                    root.join(RespuestaUsuario_.cuestionario, JoinType.LEFT).get(Cuestionario_.id)
                )
            );
        }
        return specification;
    }
}
