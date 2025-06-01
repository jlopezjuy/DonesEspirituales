package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.DetalleRespuesta;
import ar.com.dones.app.repository.DetalleRespuestaRepository;
import ar.com.dones.app.service.DetalleRespuestaService;
import ar.com.dones.app.service.dto.DetalleRespuestaDTO;
import ar.com.dones.app.service.mapper.DetalleRespuestaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.DetalleRespuesta}.
 */
@Service
@Transactional
public class DetalleRespuestaServiceImpl implements DetalleRespuestaService {

  private static final Logger LOG = LoggerFactory.getLogger(DetalleRespuestaServiceImpl.class);

  private final DetalleRespuestaRepository detalleRespuestaRepository;

  private final DetalleRespuestaMapper detalleRespuestaMapper;

  public DetalleRespuestaServiceImpl(DetalleRespuestaRepository detalleRespuestaRepository, DetalleRespuestaMapper detalleRespuestaMapper) {
    this.detalleRespuestaRepository = detalleRespuestaRepository;
    this.detalleRespuestaMapper = detalleRespuestaMapper;
  }

  @Override
  public DetalleRespuestaDTO save(DetalleRespuestaDTO detalleRespuestaDTO) {
    LOG.debug("Request to save DetalleRespuesta : {}", detalleRespuestaDTO);
    DetalleRespuesta detalleRespuesta = detalleRespuestaMapper.toEntity(detalleRespuestaDTO);
    detalleRespuesta = detalleRespuestaRepository.save(detalleRespuesta);
    return detalleRespuestaMapper.toDto(detalleRespuesta);
  }

  @Override
  public DetalleRespuestaDTO update(DetalleRespuestaDTO detalleRespuestaDTO) {
    LOG.debug("Request to update DetalleRespuesta : {}", detalleRespuestaDTO);
    DetalleRespuesta detalleRespuesta = detalleRespuestaMapper.toEntity(detalleRespuestaDTO);
    detalleRespuesta = detalleRespuestaRepository.save(detalleRespuesta);
    return detalleRespuestaMapper.toDto(detalleRespuesta);
  }

  @Override
  public Optional<DetalleRespuestaDTO> partialUpdate(DetalleRespuestaDTO detalleRespuestaDTO) {
    LOG.debug("Request to partially update DetalleRespuesta : {}", detalleRespuestaDTO);

    return detalleRespuestaRepository
      .findById(detalleRespuestaDTO.getId())
      .map(existingDetalleRespuesta -> {
        detalleRespuestaMapper.partialUpdate(existingDetalleRespuesta, detalleRespuestaDTO);

        return existingDetalleRespuesta;
      })
      .map(detalleRespuestaRepository::save)
      .map(detalleRespuestaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DetalleRespuestaDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all DetalleRespuestas");
    return detalleRespuestaRepository.findAll(pageable).map(detalleRespuestaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<DetalleRespuestaDTO> findOne(Long id) {
    LOG.debug("Request to get DetalleRespuesta : {}", id);
    return detalleRespuestaRepository.findById(id).map(detalleRespuestaMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete DetalleRespuesta : {}", id);
    detalleRespuestaRepository.deleteById(id);
  }
}
