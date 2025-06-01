package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.domain.Interpretacion;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.dto.InterpretacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interpretacion} and its DTO {@link InterpretacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface InterpretacionMapper extends EntityMapper<InterpretacionDTO, Interpretacion> {
  @Mapping(target = "donEspiritual", source = "donEspiritual", qualifiedByName = "donEspiritualId")
  InterpretacionDTO toDto(Interpretacion s);

  @Named("donEspiritualId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  DonEspiritualDTO toDtoDonEspiritualId(DonEspiritual donEspiritual);
}
