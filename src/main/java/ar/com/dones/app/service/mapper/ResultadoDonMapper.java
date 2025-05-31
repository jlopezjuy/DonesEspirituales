package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.Interpretacion;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.ResultadoDon;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.dto.InterpretacionDTO;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResultadoDon} and its DTO {@link ResultadoDonDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResultadoDonMapper extends EntityMapper<ResultadoDonDTO, ResultadoDon> {
    @Mapping(target = "interpretacion", source = "interpretacion", qualifiedByName = "interpretacionId")
    @Mapping(target = "respuestaUsuario", source = "respuestaUsuario", qualifiedByName = "respuestaUsuarioId")
    @Mapping(target = "donEspiritual", source = "donEspiritual", qualifiedByName = "donEspiritualId")
    ResultadoDonDTO toDto(ResultadoDon s);

    @Named("interpretacionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InterpretacionDTO toDtoInterpretacionId(Interpretacion interpretacion);

    @Named("respuestaUsuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RespuestaUsuarioDTO toDtoRespuestaUsuarioId(RespuestaUsuario respuestaUsuario);

    @Named("donEspiritualId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonEspiritualDTO toDtoDonEspiritualId(DonEspiritual donEspiritual);
}
