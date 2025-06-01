package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.repository.PreguntaRepository;
import ar.com.dones.app.service.PreguntaService;
import ar.com.dones.app.service.dto.PreguntaDTO;
import ar.com.dones.app.service.mapper.PreguntaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Pregunta}.
 */
@Service
@Transactional
public class PreguntaServiceImpl implements PreguntaService {

  private static final Logger LOG = LoggerFactory.getLogger(PreguntaServiceImpl.class);

  private final PreguntaRepository preguntaRepository;

  private final PreguntaMapper preguntaMapper;

  public PreguntaServiceImpl(PreguntaRepository preguntaRepository, PreguntaMapper preguntaMapper) {
    this.preguntaRepository = preguntaRepository;
    this.preguntaMapper = preguntaMapper;
  }

  @Override
  public PreguntaDTO save(PreguntaDTO preguntaDTO) {
    LOG.debug("Request to save Pregunta : {}", preguntaDTO);
    Pregunta pregunta = preguntaMapper.toEntity(preguntaDTO);
    pregunta = preguntaRepository.save(pregunta);
    return preguntaMapper.toDto(pregunta);
  }

  @Override
  public PreguntaDTO update(PreguntaDTO preguntaDTO) {
    LOG.debug("Request to update Pregunta : {}", preguntaDTO);
    Pregunta pregunta = preguntaMapper.toEntity(preguntaDTO);
    pregunta = preguntaRepository.save(pregunta);
    return preguntaMapper.toDto(pregunta);
  }

  @Override
  public Optional<PreguntaDTO> partialUpdate(PreguntaDTO preguntaDTO) {
    LOG.debug("Request to partially update Pregunta : {}", preguntaDTO);

    return preguntaRepository
      .findById(preguntaDTO.getId())
      .map(existingPregunta -> {
        preguntaMapper.partialUpdate(existingPregunta, preguntaDTO);

        return existingPregunta;
      })
      .map(preguntaRepository::save)
      .map(preguntaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PreguntaDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all Preguntas");
    return preguntaRepository.findAll(pageable).map(preguntaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PreguntaDTO> findOne(Long id) {
    LOG.debug("Request to get Pregunta : {}", id);
    return preguntaRepository.findById(id).map(preguntaMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete Pregunta : {}", id);
    preguntaRepository.deleteById(id);
  }
}
