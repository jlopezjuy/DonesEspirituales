package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.repository.CuestionarioRepository;
import ar.com.dones.app.service.CuestionarioService;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.mapper.CuestionarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Cuestionario}.
 */
@Service
@Transactional
public class CuestionarioServiceImpl implements CuestionarioService {

  private static final Logger LOG = LoggerFactory.getLogger(CuestionarioServiceImpl.class);

  private final CuestionarioRepository cuestionarioRepository;

  private final CuestionarioMapper cuestionarioMapper;

  public CuestionarioServiceImpl(CuestionarioRepository cuestionarioRepository, CuestionarioMapper cuestionarioMapper) {
    this.cuestionarioRepository = cuestionarioRepository;
    this.cuestionarioMapper = cuestionarioMapper;
  }

  @Override
  public CuestionarioDTO save(CuestionarioDTO cuestionarioDTO) {
    LOG.debug("Request to save Cuestionario : {}", cuestionarioDTO);
    Cuestionario cuestionario = cuestionarioMapper.toEntity(cuestionarioDTO);
    cuestionario = cuestionarioRepository.save(cuestionario);
    return cuestionarioMapper.toDto(cuestionario);
  }

  @Override
  public CuestionarioDTO update(CuestionarioDTO cuestionarioDTO) {
    LOG.debug("Request to update Cuestionario : {}", cuestionarioDTO);
    Cuestionario cuestionario = cuestionarioMapper.toEntity(cuestionarioDTO);
    cuestionario = cuestionarioRepository.save(cuestionario);
    return cuestionarioMapper.toDto(cuestionario);
  }

  @Override
  public Optional<CuestionarioDTO> partialUpdate(CuestionarioDTO cuestionarioDTO) {
    LOG.debug("Request to partially update Cuestionario : {}", cuestionarioDTO);

    return cuestionarioRepository
      .findById(cuestionarioDTO.getId())
      .map(existingCuestionario -> {
        cuestionarioMapper.partialUpdate(existingCuestionario, cuestionarioDTO);

        return existingCuestionario;
      })
      .map(cuestionarioRepository::save)
      .map(cuestionarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CuestionarioDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all Cuestionarios");
    return cuestionarioRepository.findAll(pageable).map(cuestionarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CuestionarioDTO> findOne(Long id) {
    LOG.debug("Request to get Cuestionario : {}", id);
    return cuestionarioRepository.findById(id).map(cuestionarioMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete Cuestionario : {}", id);
    cuestionarioRepository.deleteById(id);
  }
}
