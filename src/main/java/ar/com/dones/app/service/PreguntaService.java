package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.PreguntaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.Pregunta}.
 */
public interface PreguntaService {
  /**
   * Save a pregunta.
   *
   * @param preguntaDTO the entity to save.
   * @return the persisted entity.
   */
  PreguntaDTO save(PreguntaDTO preguntaDTO);

  /**
   * Updates a pregunta.
   *
   * @param preguntaDTO the entity to update.
   * @return the persisted entity.
   */
  PreguntaDTO update(PreguntaDTO preguntaDTO);

  /**
   * Partially updates a pregunta.
   *
   * @param preguntaDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<PreguntaDTO> partialUpdate(PreguntaDTO preguntaDTO);

  /**
   * Get all the preguntas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<PreguntaDTO> findAll(Pageable pageable);

  /**
   * Get the "id" pregunta.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<PreguntaDTO> findOne(Long id);

  /**
   * Delete the "id" pregunta.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
