package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.AuditoriaRespuestaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.AuditoriaRespuesta}.
 */
public interface AuditoriaRespuestaService {
  /**
   * Save a auditoriaRespuesta.
   *
   * @param auditoriaRespuestaDTO the entity to save.
   * @return the persisted entity.
   */
  AuditoriaRespuestaDTO save(AuditoriaRespuestaDTO auditoriaRespuestaDTO);

  /**
   * Updates a auditoriaRespuesta.
   *
   * @param auditoriaRespuestaDTO the entity to update.
   * @return the persisted entity.
   */
  AuditoriaRespuestaDTO update(AuditoriaRespuestaDTO auditoriaRespuestaDTO);

  /**
   * Partially updates a auditoriaRespuesta.
   *
   * @param auditoriaRespuestaDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<AuditoriaRespuestaDTO> partialUpdate(AuditoriaRespuestaDTO auditoriaRespuestaDTO);

  /**
   * Get all the auditoriaRespuestas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<AuditoriaRespuestaDTO> findAll(Pageable pageable);

  /**
   * Get the "id" auditoriaRespuesta.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<AuditoriaRespuestaDTO> findOne(Long id);

  /**
   * Delete the "id" auditoriaRespuesta.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
