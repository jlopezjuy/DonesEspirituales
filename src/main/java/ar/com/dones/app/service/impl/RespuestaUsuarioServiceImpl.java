package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.service.RespuestaUsuarioService;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.mapper.RespuestaUsuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.RespuestaUsuario}.
 */
@Service
@Transactional
public class RespuestaUsuarioServiceImpl implements RespuestaUsuarioService {

  private static final Logger LOG = LoggerFactory.getLogger(RespuestaUsuarioServiceImpl.class);

  private final RespuestaUsuarioRepository respuestaUsuarioRepository;

  private final RespuestaUsuarioMapper respuestaUsuarioMapper;

  public RespuestaUsuarioServiceImpl(RespuestaUsuarioRepository respuestaUsuarioRepository, RespuestaUsuarioMapper respuestaUsuarioMapper) {
    this.respuestaUsuarioRepository = respuestaUsuarioRepository;
    this.respuestaUsuarioMapper = respuestaUsuarioMapper;
  }

  @Override
  public RespuestaUsuarioDTO save(RespuestaUsuarioDTO respuestaUsuarioDTO) {
    LOG.debug("Request to save RespuestaUsuario : {}", respuestaUsuarioDTO);
    RespuestaUsuario respuestaUsuario = respuestaUsuarioMapper.toEntity(respuestaUsuarioDTO);
    respuestaUsuario = respuestaUsuarioRepository.save(respuestaUsuario);
    return respuestaUsuarioMapper.toDto(respuestaUsuario);
  }

  @Override
  public RespuestaUsuarioDTO update(RespuestaUsuarioDTO respuestaUsuarioDTO) {
    LOG.debug("Request to update RespuestaUsuario : {}", respuestaUsuarioDTO);
    RespuestaUsuario respuestaUsuario = respuestaUsuarioMapper.toEntity(respuestaUsuarioDTO);
    respuestaUsuario = respuestaUsuarioRepository.save(respuestaUsuario);
    return respuestaUsuarioMapper.toDto(respuestaUsuario);
  }

  @Override
  public Optional<RespuestaUsuarioDTO> partialUpdate(RespuestaUsuarioDTO respuestaUsuarioDTO) {
    LOG.debug("Request to partially update RespuestaUsuario : {}", respuestaUsuarioDTO);

    return respuestaUsuarioRepository
      .findById(respuestaUsuarioDTO.getId())
      .map(existingRespuestaUsuario -> {
        respuestaUsuarioMapper.partialUpdate(existingRespuestaUsuario, respuestaUsuarioDTO);

        return existingRespuestaUsuario;
      })
      .map(respuestaUsuarioRepository::save)
      .map(respuestaUsuarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RespuestaUsuarioDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all RespuestaUsuarios");
    return respuestaUsuarioRepository.findAll(pageable).map(respuestaUsuarioMapper::toDto);
  }

  public Page<RespuestaUsuarioDTO> findAllWithEagerRelationships(Pageable pageable) {
    return respuestaUsuarioRepository.findAllWithEagerRelationships(pageable).map(respuestaUsuarioMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RespuestaUsuarioDTO> findOne(Long id) {
    LOG.debug("Request to get RespuestaUsuario : {}", id);
    return respuestaUsuarioRepository.findOneWithEagerRelationships(id).map(respuestaUsuarioMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete RespuestaUsuario : {}", id);
    respuestaUsuarioRepository.deleteById(id);
  }
}
