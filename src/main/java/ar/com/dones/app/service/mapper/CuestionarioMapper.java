package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuestionario} and its DTO {@link CuestionarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuestionarioMapper extends EntityMapper<CuestionarioDTO, Cuestionario> {}
