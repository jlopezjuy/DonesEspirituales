package ar.com.dones.app.repository;

import ar.com.dones.app.domain.SesionUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SesionUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SesionUsuarioRepository extends JpaRepository<SesionUsuario, Long> {}
