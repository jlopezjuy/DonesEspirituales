package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.User;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RespuestaUsuario} and its DTO {@link RespuestaUsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface RespuestaUsuarioMapper extends EntityMapper<RespuestaUsuarioDTO, RespuestaUsuario> {
  @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
  @Mapping(target = "cuestionario", source = "cuestionario", qualifiedByName = "cuestionarioId")
  RespuestaUsuarioDTO toDto(RespuestaUsuario s);

  @Named("userLogin")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "login", source = "login")
  UserDTO toDtoUserLogin(User user);

  @Named("cuestionarioId")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  CuestionarioDTO toDtoCuestionarioId(Cuestionario cuestionario);
}
