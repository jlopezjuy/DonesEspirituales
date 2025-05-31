package ar.com.dones.app.repository;

import ar.com.dones.app.domain.PreguntaDon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PreguntaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreguntaDonRepository extends JpaRepository<PreguntaDon, Long> {}
