package ar.com.dones.app.repository;

import ar.com.dones.app.domain.EscalaRespuesta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EscalaRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscalaRespuestaRepository extends JpaRepository<EscalaRespuesta, Long> {}
