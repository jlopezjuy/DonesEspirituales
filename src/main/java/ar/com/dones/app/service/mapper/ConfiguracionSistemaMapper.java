package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.ConfiguracionSistema;
import ar.com.dones.app.service.dto.ConfiguracionSistemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfiguracionSistema} and its DTO {@link ConfiguracionSistemaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfiguracionSistemaMapper extends EntityMapper<ConfiguracionSistemaDTO, ConfiguracionSistema> {}
