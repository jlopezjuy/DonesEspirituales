package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.CuestionarioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.Cuestionario}.
 */
public interface CuestionarioService {
  /**
   * Save a cuestionario.
   *
   * @param cuestionarioDTO the entity to save.
   * @return the persisted entity.
   */
  CuestionarioDTO save(CuestionarioDTO cuestionarioDTO);

  /**
   * Updates a cuestionario.
   *
   * @param cuestionarioDTO the entity to update.
   * @return the persisted entity.
   */
  CuestionarioDTO update(CuestionarioDTO cuestionarioDTO);

  /**
   * Partially updates a cuestionario.
   *
   * @param cuestionarioDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<CuestionarioDTO> partialUpdate(CuestionarioDTO cuestionarioDTO);

  /**
   * Get all the cuestionarios.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<CuestionarioDTO> findAll(Pageable pageable);

  /**
   * Get the "id" cuestionario.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<CuestionarioDTO> findOne(Long id);

  /**
   * Delete the "id" cuestionario.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
