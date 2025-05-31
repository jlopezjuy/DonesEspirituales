package ar.com.dones.app.service;

import ar.com.dones.app.domain.Cuestionario;
import ar.com.dones.app.repository.CuestionarioRepository;
import ar.com.dones.app.service.dto.CuestionarioDTO;
import ar.com.dones.app.service.mapper.CuestionarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Cuestionario}.
 */
@Service
@Transactional
public class CuestionarioService {

    private static final Logger LOG = LoggerFactory.getLogger(CuestionarioService.class);

    private final CuestionarioRepository cuestionarioRepository;

    private final CuestionarioMapper cuestionarioMapper;

    public CuestionarioService(CuestionarioRepository cuestionarioRepository, CuestionarioMapper cuestionarioMapper) {
        this.cuestionarioRepository = cuestionarioRepository;
        this.cuestionarioMapper = cuestionarioMapper;
    }

    /**
     * Save a cuestionario.
     *
     * @param cuestionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public CuestionarioDTO save(CuestionarioDTO cuestionarioDTO) {
        LOG.debug("Request to save Cuestionario : {}", cuestionarioDTO);
        Cuestionario cuestionario = cuestionarioMapper.toEntity(cuestionarioDTO);
        cuestionario = cuestionarioRepository.save(cuestionario);
        return cuestionarioMapper.toDto(cuestionario);
    }

    /**
     * Update a cuestionario.
     *
     * @param cuestionarioDTO the entity to save.
     * @return the persisted entity.
     */
    public CuestionarioDTO update(CuestionarioDTO cuestionarioDTO) {
        LOG.debug("Request to update Cuestionario : {}", cuestionarioDTO);
        Cuestionario cuestionario = cuestionarioMapper.toEntity(cuestionarioDTO);
        cuestionario = cuestionarioRepository.save(cuestionario);
        return cuestionarioMapper.toDto(cuestionario);
    }

    /**
     * Partially update a cuestionario.
     *
     * @param cuestionarioDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the cuestionarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CuestionarioDTO> findAll() {
        LOG.debug("Request to get all Cuestionarios");
        return cuestionarioRepository.findAll().stream().map(cuestionarioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cuestionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CuestionarioDTO> findOne(Long id) {
        LOG.debug("Request to get Cuestionario : {}", id);
        return cuestionarioRepository.findById(id).map(cuestionarioMapper::toDto);
    }

    /**
     * Delete the cuestionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Cuestionario : {}", id);
        cuestionarioRepository.deleteById(id);
    }
}
