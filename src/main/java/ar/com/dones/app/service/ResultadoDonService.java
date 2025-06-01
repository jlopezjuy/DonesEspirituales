package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.ResultadoDonDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.ResultadoDon}.
 */
public interface ResultadoDonService {
  /**
   * Save a resultadoDon.
   *
   * @param resultadoDonDTO the entity to save.
   * @return the persisted entity.
   */
  ResultadoDonDTO save(ResultadoDonDTO resultadoDonDTO);

  /**
   * Updates a resultadoDon.
   *
   * @param resultadoDonDTO the entity to update.
   * @return the persisted entity.
   */
  ResultadoDonDTO update(ResultadoDonDTO resultadoDonDTO);

  /**
   * Partially updates a resultadoDon.
   *
   * @param resultadoDonDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ResultadoDonDTO> partialUpdate(ResultadoDonDTO resultadoDonDTO);

  /**
   * Get all the resultadoDons.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<ResultadoDonDTO> findAll(Pageable pageable);

  /**
   * Get the "id" resultadoDon.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<ResultadoDonDTO> findOne(Long id);

  /**
   * Delete the "id" resultadoDon.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
