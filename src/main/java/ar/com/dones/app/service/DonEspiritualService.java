package ar.com.dones.app.service;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.repository.DonEspiritualRepository;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.mapper.DonEspiritualMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.DonEspiritual}.
 */
@Service
@Transactional
public class DonEspiritualService {

    private static final Logger LOG = LoggerFactory.getLogger(DonEspiritualService.class);

    private final DonEspiritualRepository donEspiritualRepository;

    private final DonEspiritualMapper donEspiritualMapper;

    public DonEspiritualService(DonEspiritualRepository donEspiritualRepository, DonEspiritualMapper donEspiritualMapper) {
        this.donEspiritualRepository = donEspiritualRepository;
        this.donEspiritualMapper = donEspiritualMapper;
    }

    /**
     * Save a donEspiritual.
     *
     * @param donEspiritualDTO the entity to save.
     * @return the persisted entity.
     */
    public DonEspiritualDTO save(DonEspiritualDTO donEspiritualDTO) {
        LOG.debug("Request to save DonEspiritual : {}", donEspiritualDTO);
        DonEspiritual donEspiritual = donEspiritualMapper.toEntity(donEspiritualDTO);
        donEspiritual = donEspiritualRepository.save(donEspiritual);
        return donEspiritualMapper.toDto(donEspiritual);
    }

    /**
     * Update a donEspiritual.
     *
     * @param donEspiritualDTO the entity to save.
     * @return the persisted entity.
     */
    public DonEspiritualDTO update(DonEspiritualDTO donEspiritualDTO) {
        LOG.debug("Request to update DonEspiritual : {}", donEspiritualDTO);
        DonEspiritual donEspiritual = donEspiritualMapper.toEntity(donEspiritualDTO);
        donEspiritual = donEspiritualRepository.save(donEspiritual);
        return donEspiritualMapper.toDto(donEspiritual);
    }

    /**
     * Partially update a donEspiritual.
     *
     * @param donEspiritualDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DonEspiritualDTO> partialUpdate(DonEspiritualDTO donEspiritualDTO) {
        LOG.debug("Request to partially update DonEspiritual : {}", donEspiritualDTO);

        return donEspiritualRepository
            .findById(donEspiritualDTO.getId())
            .map(existingDonEspiritual -> {
                donEspiritualMapper.partialUpdate(existingDonEspiritual, donEspiritualDTO);

                return existingDonEspiritual;
            })
            .map(donEspiritualRepository::save)
            .map(donEspiritualMapper::toDto);
    }

    /**
     * Get one donEspiritual by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DonEspiritualDTO> findOne(Long id) {
        LOG.debug("Request to get DonEspiritual : {}", id);
        return donEspiritualRepository.findById(id).map(donEspiritualMapper::toDto);
    }

    /**
     * Delete the donEspiritual by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DonEspiritual : {}", id);
        donEspiritualRepository.deleteById(id);
    }
}
