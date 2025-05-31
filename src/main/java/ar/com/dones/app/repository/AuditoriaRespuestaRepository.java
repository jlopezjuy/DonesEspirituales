package ar.com.dones.app.repository;

import ar.com.dones.app.domain.AuditoriaRespuesta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuditoriaRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaRespuestaRepository extends JpaRepository<AuditoriaRespuesta, Long> {}
