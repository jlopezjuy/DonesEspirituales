package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.SesionUsuario}.
 */
public interface SesionUsuarioService {
  /**
   * Save a sesionUsuario.
   *
   * @param sesionUsuarioDTO the entity to save.
   * @return the persisted entity.
   */
  SesionUsuarioDTO save(SesionUsuarioDTO sesionUsuarioDTO);

  /**
   * Updates a sesionUsuario.
   *
   * @param sesionUsuarioDTO the entity to update.
   * @return the persisted entity.
   */
  SesionUsuarioDTO update(SesionUsuarioDTO sesionUsuarioDTO);

  /**
   * Partially updates a sesionUsuario.
   *
   * @param sesionUsuarioDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<SesionUsuarioDTO> partialUpdate(SesionUsuarioDTO sesionUsuarioDTO);

  /**
   * Get all the sesionUsuarios.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<SesionUsuarioDTO> findAll(Pageable pageable);

  /**
   * Get all the sesionUsuarios with eager load of many-to-many relationships.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<SesionUsuarioDTO> findAllWithEagerRelationships(Pageable pageable);

  /**
   * Get the "id" sesionUsuario.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<SesionUsuarioDTO> findOne(Long id);

  /**
   * Delete the "id" sesionUsuario.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
