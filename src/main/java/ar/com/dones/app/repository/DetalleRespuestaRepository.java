package ar.com.dones.app.repository;

import ar.com.dones.app.domain.DetalleRespuesta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetalleRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleRespuestaRepository extends JpaRepository<DetalleRespuesta, Long> {}
