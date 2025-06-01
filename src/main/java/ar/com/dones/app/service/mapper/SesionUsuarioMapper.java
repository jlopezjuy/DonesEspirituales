package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.SesionUsuario;
import ar.com.dones.app.domain.User;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import ar.com.dones.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SesionUsuario} and its DTO {@link SesionUsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SesionUsuarioMapper extends EntityMapper<SesionUsuarioDTO, SesionUsuario> {
  @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
  @Mapping(target = "respuestaUsuario", source = "respuestaUsuario", qualifiedByName = "respuestaUsuarioId")
  SesionUsuarioDTO toDto(SesionUsuario s);

  @Named("userLogin")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "login", source = "login")
  UserDTO toDtoUserLogin(User user);

  @Named("respuestaUsuarioId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  RespuestaUsuarioDTO toDtoRespuestaUsuarioId(RespuestaUsuario respuestaUsuario);
}
