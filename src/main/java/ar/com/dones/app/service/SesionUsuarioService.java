package ar.com.dones.app.service;

import ar.com.dones.app.domain.SesionUsuario;
import ar.com.dones.app.repository.SesionUsuarioRepository;
import ar.com.dones.app.service.dto.SesionUsuarioDTO;
import ar.com.dones.app.service.mapper.SesionUsuarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.SesionUsuario}.
 */
@Service
@Transactional
public class SesionUsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(SesionUsuarioService.class);

    private final SesionUsuarioRepository sesionUsuarioRepository;

    private final SesionUsuarioMapper sesionUsuarioMapper;

    public SesionUsuarioService(SesionUsuarioRepository sesionUsuarioRepository, SesionUsuarioMapper sesionUsuarioMapper) {
        this.sesionUsuarioRepository = sesionUsuarioRepository;
        this.sesionUsuarioMapper = sesionUsuarioMapper;
    }

    /**
     * Save a sesionUsuario.
     *
     * @param sesionUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public SesionUsuarioDTO save(SesionUsuarioDTO sesionUsuarioDTO) {
        LOG.debug("Request to save SesionUsuario : {}", sesionUsuarioDTO);
        SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
        sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
        return sesionUsuarioMapper.toDto(sesionUsuario);
    }

    /**
     * Update a sesionUsuario.
     *
     * @param sesionUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public SesionUsuarioDTO update(SesionUsuarioDTO sesionUsuarioDTO) {
        LOG.debug("Request to update SesionUsuario : {}", sesionUsuarioDTO);
        SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
        sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
        return sesionUsuarioMapper.toDto(sesionUsuario);
    }

    /**
     * Partially update a sesionUsuario.
     *
     * @param sesionUsuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the sesionUsuarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SesionUsuarioDTO> findAll() {
        LOG.debug("Request to get all SesionUsuarios");
        return sesionUsuarioRepository.findAll().stream().map(sesionUsuarioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sesionUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SesionUsuarioDTO> findOne(Long id) {
        LOG.debug("Request to get SesionUsuario : {}", id);
        return sesionUsuarioRepository.findById(id).map(sesionUsuarioMapper::toDto);
    }

    /**
     * Delete the sesionUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SesionUsuario : {}", id);
        sesionUsuarioRepository.deleteById(id);
    }
}
