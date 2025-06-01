package ar.com.dones.app.repository;

import ar.com.dones.app.domain.ConfiguracionSistema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConfiguracionSistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfiguracionSistemaRepository extends JpaRepository<ConfiguracionSistema, Long> {}
