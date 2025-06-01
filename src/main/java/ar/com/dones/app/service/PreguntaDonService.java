package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.PreguntaDonDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.PreguntaDon}.
 */
public interface PreguntaDonService {
  /**
   * Save a preguntaDon.
   *
   * @param preguntaDonDTO the entity to save.
   * @return the persisted entity.
   */
  PreguntaDonDTO save(PreguntaDonDTO preguntaDonDTO);

  /**
   * Updates a preguntaDon.
   *
   * @param preguntaDonDTO the entity to update.
   * @return the persisted entity.
   */
  PreguntaDonDTO update(PreguntaDonDTO preguntaDonDTO);

  /**
   * Partially updates a preguntaDon.
   *
   * @param preguntaDonDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<PreguntaDonDTO> partialUpdate(PreguntaDonDTO preguntaDonDTO);

  /**
   * Get all the preguntaDons.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<PreguntaDonDTO> findAll(Pageable pageable);

  /**
   * Get the "id" preguntaDon.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<PreguntaDonDTO> findOne(Long id);

  /**
   * Delete the "id" preguntaDon.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
