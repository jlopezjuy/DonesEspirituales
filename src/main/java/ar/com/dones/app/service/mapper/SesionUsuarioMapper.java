package ar.com.dones.app.service.mapper;

import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.domain.SesionUsuario;
import ar.com.dones.app.domain.Usuario;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import ar.com.dones.app.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SesionUsuario} and its DTO {@link SesionUsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface SesionUsuarioMapper extends EntityMapper<SesionUsuarioDTO, SesionUsuario> {
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    @Mapping(target = "respuestaUsuario", source = "respuestaUsuario", qualifiedByName = "respuestaUsuarioId")
    SesionUsuarioDTO toDto(SesionUsuario s);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);

    @Named("respuestaUsuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RespuestaUsuarioDTO toDtoRespuestaUsuarioId(RespuestaUsuario respuestaUsuario);
}
