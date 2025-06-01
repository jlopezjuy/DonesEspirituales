package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.DonEspiritual;
import ar.com.dones.app.repository.DonEspiritualRepository;
import ar.com.dones.app.service.DonEspiritualService;
import ar.com.dones.app.service.dto.DonEspiritualDTO;
import ar.com.dones.app.service.mapper.DonEspiritualMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.DonEspiritual}.
 */
@Service
@Transactional
public class DonEspiritualServiceImpl implements DonEspiritualService {

  private static final Logger LOG = LoggerFactory.getLogger(DonEspiritualServiceImpl.class);

  private final DonEspiritualRepository donEspiritualRepository;

  private final DonEspiritualMapper donEspiritualMapper;

  public DonEspiritualServiceImpl(DonEspiritualRepository donEspiritualRepository, DonEspiritualMapper donEspiritualMapper) {
    this.donEspiritualRepository = donEspiritualRepository;
    this.donEspiritualMapper = donEspiritualMapper;
  }

  @Override
  public DonEspiritualDTO save(DonEspiritualDTO donEspiritualDTO) {
    LOG.debug("Request to save DonEspiritual : {}", donEspiritualDTO);
    DonEspiritual donEspiritual = donEspiritualMapper.toEntity(donEspiritualDTO);
    donEspiritual = donEspiritualRepository.save(donEspiritual);
    return donEspiritualMapper.toDto(donEspiritual);
  }

  @Override
  public DonEspiritualDTO update(DonEspiritualDTO donEspiritualDTO) {
    LOG.debug("Request to update DonEspiritual : {}", donEspiritualDTO);
    DonEspiritual donEspiritual = donEspiritualMapper.toEntity(donEspiritualDTO);
    donEspiritual = donEspiritualRepository.save(donEspiritual);
    return donEspiritualMapper.toDto(donEspiritual);
  }

  @Override
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

  @Override
  @Transactional(readOnly = true)
  public Page<DonEspiritualDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all DonEspirituals");
    return donEspiritualRepository.findAll(pageable).map(donEspiritualMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<DonEspiritualDTO> findOne(Long id) {
    LOG.debug("Request to get DonEspiritual : {}", id);
    return donEspiritualRepository.findById(id).map(donEspiritualMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete DonEspiritual : {}", id);
    donEspiritualRepository.deleteById(id);
  }
}
