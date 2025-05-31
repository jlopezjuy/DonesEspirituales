package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.domain.PreguntaDon;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.dto.PreguntaDTO;
import ar.com.dones.app.service.dto.PreguntaDonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PreguntaDon} and its DTO {@link PreguntaDonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PreguntaDonMapper extends EntityMapper<PreguntaDonDTO, PreguntaDon> {
    @Mapping(target = "pregunta", source = "pregunta", qualifiedByName = "preguntaId")
    @Mapping(target = "donEspiritual", source = "donEspiritual", qualifiedByName = "donEspiritualId")
    PreguntaDonDTO toDto(PreguntaDon s);

    @Named("preguntaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PreguntaDTO toDtoPreguntaId(Pregunta pregunta);

    @Named("donEspiritualId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonEspiritualDTO toDtoDonEspiritualId(DonEspiritual donEspiritual);
}
