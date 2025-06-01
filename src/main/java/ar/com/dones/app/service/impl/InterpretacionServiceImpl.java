package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.Interpretacion;
import ar.com.dones.app.repository.InterpretacionRepository;
import ar.com.dones.app.service.InterpretacionService;
import ar.com.dones.app.service.dto.InterpretacionDTO;
import ar.com.dones.app.service.mapper.InterpretacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Interpretacion}.
 */
@Service
@Transactional
public class InterpretacionServiceImpl implements InterpretacionService {

  private static final Logger LOG = LoggerFactory.getLogger(InterpretacionServiceImpl.class);

  private final InterpretacionRepository interpretacionRepository;

  private final InterpretacionMapper interpretacionMapper;

  public InterpretacionServiceImpl(InterpretacionRepository interpretacionRepository, InterpretacionMapper interpretacionMapper) {
    this.interpretacionRepository = interpretacionRepository;
    this.interpretacionMapper = interpretacionMapper;
  }

  @Override
  public InterpretacionDTO save(InterpretacionDTO interpretacionDTO) {
    LOG.debug("Request to save Interpretacion : {}", interpretacionDTO);
    Interpretacion interpretacion = interpretacionMapper.toEntity(interpretacionDTO);
    interpretacion = interpretacionRepository.save(interpretacion);
    return interpretacionMapper.toDto(interpretacion);
  }

  @Override
  public InterpretacionDTO update(InterpretacionDTO interpretacionDTO) {
    LOG.debug("Request to update Interpretacion : {}", interpretacionDTO);
    Interpretacion interpretacion = interpretacionMapper.toEntity(interpretacionDTO);
    interpretacion = interpretacionRepository.save(interpretacion);
    return interpretacionMapper.toDto(interpretacion);
  }

  @Override
  public Optional<InterpretacionDTO> partialUpdate(InterpretacionDTO interpretacionDTO) {
    LOG.debug("Request to partially update Interpretacion : {}", interpretacionDTO);

    return interpretacionRepository
      .findById(interpretacionDTO.getId())
      .map(existingInterpretacion -> {
        interpretacionMapper.partialUpdate(existingInterpretacion, interpretacionDTO);

        return existingInterpretacion;
      })
      .map(interpretacionRepository::save)
      .map(interpretacionMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InterpretacionDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all Interpretacions");
    return interpretacionRepository.findAll(pageable).map(interpretacionMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<InterpretacionDTO> findOne(Long id) {
    LOG.debug("Request to get Interpretacion : {}", id);
    return interpretacionRepository.findById(id).map(interpretacionMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete Interpretacion : {}", id);
    interpretacionRepository.deleteById(id);
  }
}
