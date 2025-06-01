package ar.com.dones.app.repository;

import ar.com.dones.app.domain.Cuestionario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cuestionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuestionarioRepository extends JpaRepository<Cuestionario, Long> {}
