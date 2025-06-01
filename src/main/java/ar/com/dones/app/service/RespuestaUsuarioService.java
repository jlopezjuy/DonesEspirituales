package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.RespuestaUsuario}.
 */
public interface RespuestaUsuarioService {
  /**
   * Save a respuestaUsuario.
   *
   * @param respuestaUsuarioDTO the entity to save.
   * @return the persisted entity.
   */
  RespuestaUsuarioDTO save(RespuestaUsuarioDTO respuestaUsuarioDTO);

  /**
   * Updates a respuestaUsuario.
   *
   * @param respuestaUsuarioDTO the entity to update.
   * @return the persisted entity.
   */
  RespuestaUsuarioDTO update(RespuestaUsuarioDTO respuestaUsuarioDTO);

  /**
   * Partially updates a respuestaUsuario.
   *
   * @param respuestaUsuarioDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<RespuestaUsuarioDTO> partialUpdate(RespuestaUsuarioDTO respuestaUsuarioDTO);

  /**
   * Get all the respuestaUsuarios.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<RespuestaUsuarioDTO> findAll(Pageable pageable);

  /**
   * Get all the respuestaUsuarios with eager load of many-to-many relationships.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<RespuestaUsuarioDTO> findAllWithEagerRelationships(Pageable pageable);

  /**
   * Get the "id" respuestaUsuario.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<RespuestaUsuarioDTO> findOne(Long id);

  /**
   * Delete the "id" respuestaUsuario.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
