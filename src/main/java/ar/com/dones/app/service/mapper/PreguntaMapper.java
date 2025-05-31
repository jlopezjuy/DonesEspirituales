package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.dto.PreguntaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pregunta} and its DTO {@link PreguntaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PreguntaMapper extends EntityMapper<PreguntaDTO, Pregunta> {
    @Mapping(target = "cuestionario", source = "cuestionario", qualifiedByName = "cuestionarioId")
    PreguntaDTO toDto(Pregunta s);

    @Named("cuestionarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuestionarioDTO toDtoCuestionarioId(Cuestionario cuestionario);
}
