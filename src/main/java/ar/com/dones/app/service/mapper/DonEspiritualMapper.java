package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonEspiritual} and its DTO {@link DonEspiritualDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonEspiritualMapper extends EntityMapper<DonEspiritualDTO, DonEspiritual> {}
