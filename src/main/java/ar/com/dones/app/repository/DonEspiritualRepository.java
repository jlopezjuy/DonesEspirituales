package ar.com.dones.app.repository;

import ar.com.dones.app.domain.DonEspiritual;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DonEspiritual entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonEspiritualRepository extends JpaRepository<DonEspiritual, Long> {}
