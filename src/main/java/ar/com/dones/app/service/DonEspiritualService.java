package ar.com.dones.app.service;

import ar.com.dones.app.service.dto.DonEspiritualDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.com.dones.app.domain.DonEspiritual}.
 */
public interface DonEspiritualService {
  /**
   * Save a donEspiritual.
   *
   * @param donEspiritualDTO the entity to save.
   * @return the persisted entity.
   */
  DonEspiritualDTO save(DonEspiritualDTO donEspiritualDTO);

  /**
   * Updates a donEspiritual.
   *
   * @param donEspiritualDTO the entity to update.
   * @return the persisted entity.
   */
  DonEspiritualDTO update(DonEspiritualDTO donEspiritualDTO);

  /**
   * Partially updates a donEspiritual.
   *
   * @param donEspiritualDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<DonEspiritualDTO> partialUpdate(DonEspiritualDTO donEspiritualDTO);

  /**
   * Get all the donEspirituals.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<DonEspiritualDTO> findAll(Pageable pageable);

  /**
   * Get the "id" donEspiritual.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<DonEspiritualDTO> findOne(Long id);

  /**
   * Delete the "id" donEspiritual.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
