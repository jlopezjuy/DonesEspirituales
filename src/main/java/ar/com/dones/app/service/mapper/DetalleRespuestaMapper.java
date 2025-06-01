package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.DetalleRespuesta;
import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import ar.com.dones.app.service.dto.PreguntaDTO;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleRespuesta} and its DTO {@link DetalleRespuestaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetalleRespuestaMapper extends EntityMapper<DetalleRespuestaDTO, DetalleRespuesta> {
  @Mapping(target = "escalaRespuesta", source = "escalaRespuesta", qualifiedByName = "escalaRespuestaId")
  @Mapping(target = "pregunta", source = "pregunta", qualifiedByName = "preguntaId")
  @Mapping(target = "respuestaUsuario", source = "respuestaUsuario", qualifiedByName = "respuestaUsuarioId")
  DetalleRespuestaDTO toDto(DetalleRespuesta s);

  @Named("escalaRespuestaId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  EscalaRespuestaDTO toDtoEscalaRespuestaId(EscalaRespuesta escalaRespuesta);

  @Named("preguntaId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  PreguntaDTO toDtoPreguntaId(Pregunta pregunta);

  @Named("respuestaUsuarioId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  RespuestaUsuarioDTO toDtoRespuestaUsuarioId(RespuestaUsuario respuestaUsuario);
}
