package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.ConfiguracionSistemaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.ConfiguracionSistema}.
 */
public interface ConfiguracionSistemaService {
  /**
   * Save a configuracionSistema.
   *
   * @param configuracionSistemaDTO the entity to save.
   * @return the persisted entity.
   */
  ConfiguracionSistemaDTO save(ConfiguracionSistemaDTO configuracionSistemaDTO);

  /**
   * Updates a configuracionSistema.
   *
   * @param configuracionSistemaDTO the entity to update.
   * @return the persisted entity.
   */
  ConfiguracionSistemaDTO update(ConfiguracionSistemaDTO configuracionSistemaDTO);

  /**
   * Partially updates a configuracionSistema.
   *
   * @param configuracionSistemaDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ConfiguracionSistemaDTO> partialUpdate(ConfiguracionSistemaDTO configuracionSistemaDTO);

  /**
   * Get all the configuracionSistemas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<ConfiguracionSistemaDTO> findAll(Pageable pageable);

  /**
   * Get the "id" configuracionSistema.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ConfiguracionSistemaDTO> findOne(Long id);

  /**
   * Delete the "id" configuracionSistema.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
