package ar.com.dones.app.service;

import ar.com.dones.app.domain.*; // for static metamodels
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.repository.UsuarioRepository;
import ar.com.dones.app.service.criteria.UsuarioCriteria;
import ar.com.dones.app.service.dto.UsuarioDTO;
import ar.com.dones.app.service.mapper.UsuarioMapper;
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
 * Service for executing complex queries for {@link Usuario} entities in the database.
 * The main input is a {@link UsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UsuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioQueryService extends QueryService<Usuario> {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioQueryService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioQueryService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Return a {@link Page} of {@link UsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findByCriteria(UsuarioCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.findAll(specification, page).map(usuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Usuario> createSpecification(UsuarioCriteria criteria) {
        Specification<Usuario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Usuario_.id),
                buildStringSpecification(criteria.getNombre(), Usuario_.nombre),
                buildStringSpecification(criteria.getApellido(), Usuario_.apellido),
                buildStringSpecification(criteria.getEmail(), Usuario_.email),
                buildStringSpecification(criteria.getTelefono(), Usuario_.telefono),
                buildRangeSpecification(criteria.getFechaNacimiento(), Usuario_.fechaNacimiento),
                buildSpecification(criteria.getGenero(), Usuario_.genero),
                buildStringSpecification(criteria.getIglesia(), Usuario_.iglesia),
                buildStringSpecification(criteria.getDenominacion(), Usuario_.denominacion),
                buildRangeSpecification(criteria.getFechaRegistro(), Usuario_.fechaRegistro),
                buildRangeSpecification(criteria.getUltimaActividad(), Usuario_.ultimaActividad),
                buildSpecification(criteria.getActivo(), Usuario_.activo),
                buildSpecification(criteria.getRespuestasId(), root ->
                    root.join(Usuario_.respuestas, JoinType.LEFT).get(RespuestaUsuario_.id)
                ),
                buildSpecification(criteria.getSesionesId(), root -> root.join(Usuario_.sesiones, JoinType.LEFT).get(SesionUsuario_.id))
            );
        }
        return specification;
    }
}
