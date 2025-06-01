package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.repository.EscalaRespuestaRepository;
import ar.com.dones.app.service.EscalaRespuestaService;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import ar.com.dones.app.service.mapper.EscalaRespuestaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.EscalaRespuesta}.
 */
@Service
@Transactional
public class EscalaRespuestaServiceImpl implements EscalaRespuestaService {

  private static final Logger LOG = LoggerFactory.getLogger(EscalaRespuestaServiceImpl.class);

  private final EscalaRespuestaRepository escalaRespuestaRepository;

  private final EscalaRespuestaMapper escalaRespuestaMapper;

  public EscalaRespuestaServiceImpl(EscalaRespuestaRepository escalaRespuestaRepository, EscalaRespuestaMapper escalaRespuestaMapper) {
    this.escalaRespuestaRepository = escalaRespuestaRepository;
    this.escalaRespuestaMapper = escalaRespuestaMapper;
  }

  @Override
  public EscalaRespuestaDTO save(EscalaRespuestaDTO escalaRespuestaDTO) {
    LOG.debug("Request to save EscalaRespuesta : {}", escalaRespuestaDTO);
    EscalaRespuesta escalaRespuesta = escalaRespuestaMapper.toEntity(escalaRespuestaDTO);
    escalaRespuesta = escalaRespuestaRepository.save(escalaRespuesta);
    return escalaRespuestaMapper.toDto(escalaRespuesta);
  }

  @Override
  public EscalaRespuestaDTO update(EscalaRespuestaDTO escalaRespuestaDTO) {
    LOG.debug("Request to update EscalaRespuesta : {}", escalaRespuestaDTO);
    EscalaRespuesta escalaRespuesta = escalaRespuestaMapper.toEntity(escalaRespuestaDTO);
    escalaRespuesta = escalaRespuestaRepository.save(escalaRespuesta);
    return escalaRespuestaMapper.toDto(escalaRespuesta);
  }

  @Override
  public Optional<EscalaRespuestaDTO> partialUpdate(EscalaRespuestaDTO escalaRespuestaDTO) {
    LOG.debug("Request to partially update EscalaRespuesta : {}", escalaRespuestaDTO);

    return escalaRespuestaRepository
      .findById(escalaRespuestaDTO.getId())
      .map(existingEscalaRespuesta -> {
        escalaRespuestaMapper.partialUpdate(existingEscalaRespuesta, escalaRespuestaDTO);

        return existingEscalaRespuesta;
      })
      .map(escalaRespuestaRepository::save)
      .map(escalaRespuestaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<EscalaRespuestaDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all EscalaRespuestas");
    return escalaRespuestaRepository.findAll(pageable).map(escalaRespuestaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<EscalaRespuestaDTO> findOne(Long id) {
    LOG.debug("Request to get EscalaRespuesta : {}", id);
    return escalaRespuestaRepository.findById(id).map(escalaRespuestaMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete EscalaRespuesta : {}", id);
    escalaRespuestaRepository.deleteById(id);
  }
}
