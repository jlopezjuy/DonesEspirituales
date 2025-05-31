package ar.com.dones.app.service;

import ar.com.dones.app.domain.RespuestaUsuario;
import ar.com.dones.app.repository.RespuestaUsuarioRepository;
import ar.com.dones.app.service.dto.RespuestaUsuarioDTO;
import ar.com.dones.app.service.mapper.RespuestaUsuarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.RespuestaUsuario}.
 */
@Service
@Transactional
public class RespuestaUsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(RespuestaUsuarioService.class);

    private final RespuestaUsuarioRepository respuestaUsuarioRepository;

    private final RespuestaUsuarioMapper respuestaUsuarioMapper;

    public RespuestaUsuarioService(RespuestaUsuarioRepository respuestaUsuarioRepository, RespuestaUsuarioMapper respuestaUsuarioMapper) {
        this.respuestaUsuarioRepository = respuestaUsuarioRepository;
        this.respuestaUsuarioMapper = respuestaUsuarioMapper;
    }

    /**
     * Save a respuestaUsuario.
     *
     * @param respuestaUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public RespuestaUsuarioDTO save(RespuestaUsuarioDTO respuestaUsuarioDTO) {
        LOG.debug("Request to save RespuestaUsuario : {}", respuestaUsuarioDTO);
        RespuestaUsuario respuestaUsuario = respuestaUsuarioMapper.toEntity(respuestaUsuarioDTO);
        respuestaUsuario = respuestaUsuarioRepository.save(respuestaUsuario);
        return respuestaUsuarioMapper.toDto(respuestaUsuario);
    }

    /**
     * Update a respuestaUsuario.
     *
     * @param respuestaUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public RespuestaUsuarioDTO update(RespuestaUsuarioDTO respuestaUsuarioDTO) {
        LOG.debug("Request to update RespuestaUsuario : {}", respuestaUsuarioDTO);
        RespuestaUsuario respuestaUsuario = respuestaUsuarioMapper.toEntity(respuestaUsuarioDTO);
        respuestaUsuario = respuestaUsuarioRepository.save(respuestaUsuario);
        return respuestaUsuarioMapper.toDto(respuestaUsuario);
    }

    /**
     * Partially update a respuestaUsuario.
     *
     * @param respuestaUsuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get one respuestaUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RespuestaUsuarioDTO> findOne(Long id) {
        LOG.debug("Request to get RespuestaUsuario : {}", id);
        return respuestaUsuarioRepository.findById(id).map(respuestaUsuarioMapper::toDto);
    }

    /**
     * Delete the respuestaUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RespuestaUsuario : {}", id);
        respuestaUsuarioRepository.deleteById(id);
    }
}
