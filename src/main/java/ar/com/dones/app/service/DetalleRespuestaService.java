package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.DetalleRespuesta}.
 */
public interface DetalleRespuestaService {
  /**
   * Save a detalleRespuesta.
   *
   * @param detalleRespuestaDTO the entity to save.
   * @return the persisted entity.
   */
  DetalleRespuestaDTO save(DetalleRespuestaDTO detalleRespuestaDTO);

  /**
   * Updates a detalleRespuesta.
   *
   * @param detalleRespuestaDTO the entity to update.
   * @return the persisted entity.
   */
  DetalleRespuestaDTO update(DetalleRespuestaDTO detalleRespuestaDTO);

  /**
   * Partially updates a detalleRespuesta.
   *
   * @param detalleRespuestaDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<DetalleRespuestaDTO> partialUpdate(DetalleRespuestaDTO detalleRespuestaDTO);

  /**
   * Get all the detalleRespuestas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<DetalleRespuestaDTO> findAll(Pageable pageable);

  /**
   * Get the "id" detalleRespuesta.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<DetalleRespuestaDTO> findOne(Long id);

  /**
   * Delete the "id" detalleRespuesta.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
