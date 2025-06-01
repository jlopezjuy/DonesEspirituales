package ar.com.dones.app.repository;

import ar.com.dones.app.domain.ResultadoDon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResultadoDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoDonRepository extends JpaRepository<ResultadoDon, Long> {}
