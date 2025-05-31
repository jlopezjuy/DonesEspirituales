package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RespuestaUsuario} and its DTO {@link RespuestaUsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface RespuestaUsuarioMapper extends EntityMapper<RespuestaUsuarioDTO, RespuestaUsuario> {
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    @Mapping(target = "cuestionario", source = "cuestionario", qualifiedByName = "cuestionarioId")
    RespuestaUsuarioDTO toDto(RespuestaUsuario s);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);

    @Named("cuestionarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuestionarioDTO toDtoCuestionarioId(Cuestionario cuestionario);
}
