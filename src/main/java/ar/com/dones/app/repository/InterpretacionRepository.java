package ar.com.dones.app.repository;

import ar.com.dones.app.domain.Interpretacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Interpretacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterpretacionRepository extends JpaRepository<Interpretacion, Long> {}
