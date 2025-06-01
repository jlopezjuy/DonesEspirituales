package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.InterpretacionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.Interpretacion}.
 */
public interface InterpretacionService {
  /**
   * Save a interpretacion.
   *
   * @param interpretacionDTO the entity to save.
   * @return the persisted entity.
   */
  InterpretacionDTO save(InterpretacionDTO interpretacionDTO);

  /**
   * Updates a interpretacion.
   *
   * @param interpretacionDTO the entity to update.
   * @return the persisted entity.
   */
  InterpretacionDTO update(InterpretacionDTO interpretacionDTO);

  /**
   * Partially updates a interpretacion.
   *
   * @param interpretacionDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<InterpretacionDTO> partialUpdate(InterpretacionDTO interpretacionDTO);

  /**
   * Get all the interpretacions.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<InterpretacionDTO> findAll(Pageable pageable);

  /**
   * Get the "id" interpretacion.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<InterpretacionDTO> findOne(Long id);

  /**
   * Delete the "id" interpretacion.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
