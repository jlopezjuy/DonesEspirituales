package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.AuditoriaRespuesta;
import ar.com.dones.app.domain.DetalleRespuesta;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.service.dto.AuditoriaRespuestaDTO;
import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditoriaRespuesta} and its DTO {@link AuditoriaRespuestaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditoriaRespuestaMapper extends EntityMapper<AuditoriaRespuestaDTO, AuditoriaRespuesta> {
    @Mapping(target = "respuestaUsuario", source = "respuestaUsuario", qualifiedByName = "respuestaUsuarioId")
    @Mapping(target = "detalleRespuesta", source = "detalleRespuesta", qualifiedByName = "detalleRespuestaId")
    AuditoriaRespuestaDTO toDto(AuditoriaRespuesta s);

    @Named("respuestaUsuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RespuestaUsuarioDTO toDtoRespuestaUsuarioId(RespuestaUsuario respuestaUsuario);

    @Named("detalleRespuestaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DetalleRespuestaDTO toDtoDetalleRespuestaId(DetalleRespuesta detalleRespuesta);
}
