package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EscalaRespuesta} and its DTO {@link EscalaRespuestaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EscalaRespuestaMapper extends EntityMapper<EscalaRespuestaDTO, EscalaRespuesta> {}
