package ar.com.dones.app.service;

import ar.com.dones.app.domain.EscalaRespuesta;
import ar.com.dones.app.repository.EscalaRespuestaRepository;
import ar.com.dones.app.service.dto.EscalaRespuestaDTO;
import ar.com.dones.app.service.mapper.EscalaRespuestaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.EscalaRespuesta}.
 */
@Service
@Transactional
public class EscalaRespuestaService {

    private static final Logger LOG = LoggerFactory.getLogger(EscalaRespuestaService.class);

    private final EscalaRespuestaRepository escalaRespuestaRepository;

    private final EscalaRespuestaMapper escalaRespuestaMapper;

    public EscalaRespuestaService(EscalaRespuestaRepository escalaRespuestaRepository, EscalaRespuestaMapper escalaRespuestaMapper) {
        this.escalaRespuestaRepository = escalaRespuestaRepository;
        this.escalaRespuestaMapper = escalaRespuestaMapper;
    }

    /**
     * Save a escalaRespuesta.
     *
     * @param escalaRespuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public EscalaRespuestaDTO save(EscalaRespuestaDTO escalaRespuestaDTO) {
        LOG.debug("Request to save EscalaRespuesta : {}", escalaRespuestaDTO);
        EscalaRespuesta escalaRespuesta = escalaRespuestaMapper.toEntity(escalaRespuestaDTO);
        escalaRespuesta = escalaRespuestaRepository.save(escalaRespuesta);
        return escalaRespuestaMapper.toDto(escalaRespuesta);
    }

    /**
     * Update a escalaRespuesta.
     *
     * @param escalaRespuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public EscalaRespuestaDTO update(EscalaRespuestaDTO escalaRespuestaDTO) {
        LOG.debug("Request to update EscalaRespuesta : {}", escalaRespuestaDTO);
        EscalaRespuesta escalaRespuesta = escalaRespuestaMapper.toEntity(escalaRespuestaDTO);
        escalaRespuesta = escalaRespuestaRepository.save(escalaRespuesta);
        return escalaRespuestaMapper.toDto(escalaRespuesta);
    }

    /**
     * Partially update a escalaRespuesta.
     *
     * @param escalaRespuestaDTO the entity to update partially.
     * @return the persisted entity.
     */
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

    /**
     * Get all the escalaRespuestas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EscalaRespuestaDTO> findAll() {
        LOG.debug("Request to get all EscalaRespuestas");
        return escalaRespuestaRepository
            .findAll()
            .stream()
            .map(escalaRespuestaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one escalaRespuesta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EscalaRespuestaDTO> findOne(Long id) {
        LOG.debug("Request to get EscalaRespuesta : {}", id);
        return escalaRespuestaRepository.findById(id).map(escalaRespuestaMapper::toDto);
    }

    /**
     * Delete the escalaRespuesta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete EscalaRespuesta : {}", id);
        escalaRespuestaRepository.deleteById(id);
    }
}
