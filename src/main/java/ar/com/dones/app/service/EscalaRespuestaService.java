package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.EscalaRespuesta}.
 */
public interface EscalaRespuestaService {
  /**
   * Save a escalaRespuesta.
   *
   * @param escalaRespuestaDTO the entity to save.
   * @return the persisted entity.
   */
  EscalaRespuestaDTO save(EscalaRespuestaDTO escalaRespuestaDTO);

  /**
   * Updates a escalaRespuesta.
   *
   * @param escalaRespuestaDTO the entity to update.
   * @return the persisted entity.
   */
  EscalaRespuestaDTO update(EscalaRespuestaDTO escalaRespuestaDTO);

  /**
   * Partially updates a escalaRespuesta.
   *
   * @param escalaRespuestaDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<EscalaRespuestaDTO> partialUpdate(EscalaRespuestaDTO escalaRespuestaDTO);

  /**
   * Get all the escalaRespuestas.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<EscalaRespuestaDTO> findAll(Pageable pageable);

  /**
   * Get the "id" escalaRespuesta.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<EscalaRespuestaDTO> findOne(Long id);

  /**
   * Delete the "id" escalaRespuesta.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
