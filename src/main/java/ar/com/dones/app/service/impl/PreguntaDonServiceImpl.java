package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.PreguntaDon;
import ar.com.dones.app.repository.PreguntaDonRepository;
import ar.com.dones.app.service.PreguntaDonService;
import ar.com.dones.app.service.dto.PreguntaDonDTO;
import ar.com.dones.app.service.mapper.PreguntaDonMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.PreguntaDon}.
 */
@Service
@Transactional
public class PreguntaDonServiceImpl implements PreguntaDonService {

  private static final Logger LOG = LoggerFactory.getLogger(PreguntaDonServiceImpl.class);

  private final PreguntaDonRepository preguntaDonRepository;

  private final PreguntaDonMapper preguntaDonMapper;

  public PreguntaDonServiceImpl(PreguntaDonRepository preguntaDonRepository, PreguntaDonMapper preguntaDonMapper) {
    this.preguntaDonRepository = preguntaDonRepository;
    this.preguntaDonMapper = preguntaDonMapper;
  }

  @Override
  public PreguntaDonDTO save(PreguntaDonDTO preguntaDonDTO) {
    LOG.debug("Request to save PreguntaDon : {}", preguntaDonDTO);
    PreguntaDon preguntaDon = preguntaDonMapper.toEntity(preguntaDonDTO);
    preguntaDon = preguntaDonRepository.save(preguntaDon);
    return preguntaDonMapper.toDto(preguntaDon);
  }

  @Override
  public PreguntaDonDTO update(PreguntaDonDTO preguntaDonDTO) {
    LOG.debug("Request to update PreguntaDon : {}", preguntaDonDTO);
    PreguntaDon preguntaDon = preguntaDonMapper.toEntity(preguntaDonDTO);
    preguntaDon = preguntaDonRepository.save(preguntaDon);
    return preguntaDonMapper.toDto(preguntaDon);
  }

  @Override
  public Optional<PreguntaDonDTO> partialUpdate(PreguntaDonDTO preguntaDonDTO) {
    LOG.debug("Request to partially update PreguntaDon : {}", preguntaDonDTO);

    return preguntaDonRepository
      .findById(preguntaDonDTO.getId())
      .map(existingPreguntaDon -> {
        preguntaDonMapper.partialUpdate(existingPreguntaDon, preguntaDonDTO);

        return existingPreguntaDon;
      })
      .map(preguntaDonRepository::save)
      .map(preguntaDonMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PreguntaDonDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all PreguntaDons");
    return preguntaDonRepository.findAll(pageable).map(preguntaDonMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PreguntaDonDTO> findOne(Long id) {
    LOG.debug("Request to get PreguntaDon : {}", id);
    return preguntaDonRepository.findById(id).map(preguntaDonMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete PreguntaDon : {}", id);
    preguntaDonRepository.deleteById(id);
  }
}
