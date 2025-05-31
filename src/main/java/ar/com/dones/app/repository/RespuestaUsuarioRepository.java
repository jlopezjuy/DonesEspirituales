package ar.com.dones.app.repository;

import ar.com.dones.app.domain.RespuestaUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RespuestaUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespuestaUsuarioRepository extends JpaRepository<RespuestaUsuario, Long>, JpaSpecificationExecutor<RespuestaUsuario> {}
