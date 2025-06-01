package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.SesionUsuario;
import ar.com.dones.app.repository.SesionUsuarioRepository;
import ar.com.dones.app.service.SesionUsuarioService;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import ar.com.dones.app.service.mapper.SesionUsuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.SesionUsuario}.
 */
@Service
@Transactional
public class SesionUsuarioServiceImpl implements SesionUsuarioService {

  private static final Logger LOG = LoggerFactory.getLogger(SesionUsuarioServiceImpl.class);

  private final SesionUsuarioRepository sesionUsuarioRepository;

  private final SesionUsuarioMapper sesionUsuarioMapper;

  public SesionUsuarioServiceImpl(SesionUsuarioRepository sesionUsuarioRepository, SesionUsuarioMapper sesionUsuarioMapper) {
    this.sesionUsuarioRepository = sesionUsuarioRepository;
    this.sesionUsuarioMapper = sesionUsuarioMapper;
  }

  @Override
  public SesionUsuarioDTO save(SesionUsuarioDTO sesionUsuarioDTO) {
    LOG.debug("Request to save SesionUsuario : {}", sesionUsuarioDTO);
    SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
    sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
    return sesionUsuarioMapper.toDto(sesionUsuario);
  }

  @Override
  public SesionUsuarioDTO update(SesionUsuarioDTO sesionUsuarioDTO) {
    LOG.debug("Request to update SesionUsuario : {}", sesionUsuarioDTO);
    SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
    sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
    return sesionUsuarioMapper.toDto(sesionUsuario);
  }

  @Override
  public Optional<SesionUsuarioDTO> partialUpdate(SesionUsuarioDTO sesionUsuarioDTO) {
    LOG.debug("Request to partially update SesionUsuario : {}", sesionUsuarioDTO);

    return sesionUsuarioRepository
      .findById(sesionUsuarioDTO.getId())
      .map(existingSesionUsuario -> {
        sesionUsuarioMapper.partialUpdate(existingSesionUsuario, sesionUsuarioDTO);

        return existingSesionUsuario;
      })
      .map(sesionUsuarioRepository::save)
      .map(sesionUsuarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SesionUsuarioDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all SesionUsuarios");
    return sesionUsuarioRepository.findAll(pageable).map(sesionUsuarioMapper::toDto);
  }

  public Page<SesionUsuarioDTO> findAllWithEagerRelationships(Pageable pageable) {
    return sesionUsuarioRepository.findAllWithEagerRelationships(pageable).map(sesionUsuarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SesionUsuarioDTO> findOne(Long id) {
    LOG.debug("Request to get SesionUsuario : {}", id);
    return sesionUsuarioRepository.findOneWithEagerRelationships(id).map(sesionUsuarioMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete SesionUsuario : {}", id);
    sesionUsuarioRepository.deleteById(id);
  }
}
