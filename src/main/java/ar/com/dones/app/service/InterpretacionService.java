package ar.com.dones.app.service;

import ar.com.dones.app.domain.Interpretacion;
import ar.com.dones.app.repository.InterpretacionRepository;
import ar.com.dones.app.service.dto.InterpretacionDTO;
import ar.com.dones.app.service.mapper.InterpretacionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Interpretacion}.
 */
@Service
@Transactional
public class InterpretacionService {

    private static final Logger LOG = LoggerFactory.getLogger(InterpretacionService.class);

    private final InterpretacionRepository interpretacionRepository;

    private final InterpretacionMapper interpretacionMapper;

    public InterpretacionService(InterpretacionRepository interpretacionRepository, InterpretacionMapper interpretacionMapper) {
        this.interpretacionRepository = interpretacionRepository;
        this.interpretacionMapper = interpretacionMapper;
    }

    /**
     * Save a interpretacion.
     *
     * @param interpretacionDTO the entity to save.
     * @return the persisted entity.
     */
    public InterpretacionDTO save(InterpretacionDTO interpretacionDTO) {
        LOG.debug("Request to save Interpretacion : {}", interpretacionDTO);
        Interpretacion interpretacion = interpretacionMapper.toEntity(interpretacionDTO);
        interpretacion = interpretacionRepository.save(interpretacion);
        return interpretacionMapper.toDto(interpretacion);
    }

    /**
     * Update a interpretacion.
     *
     * @param interpretacionDTO the entity to save.
     * @return the persisted entity.
     */
    public InterpretacionDTO update(InterpretacionDTO interpretacionDTO) {
        LOG.debug("Request to update Interpretacion : {}", interpretacionDTO);
        Interpretacion interpretacion = interpretacionMapper.toEntity(interpretacionDTO);
        interpretacion = interpretacionRepository.save(interpretacion);
        return interpretacionMapper.toDto(interpretacion);
    }

    /**
     * Partially update a interpretacion.
     *
     * @param interpretacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterpretacionDTO> partialUpdate(InterpretacionDTO interpretacionDTO) {
        LOG.debug("Request to partially update Interpretacion : {}", interpretacionDTO);

        return interpretacionRepository
            .findById(interpretacionDTO.getId())
            .map(existingInterpretacion -> {
                interpretacionMapper.partialUpdate(existingInterpretacion, interpretacionDTO);

                return existingInterpretacion;
            })
            .map(interpretacionRepository::save)
            .map(interpretacionMapper::toDto);
    }

    /**
     * Get all the interpretacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InterpretacionDTO> findAll() {
        LOG.debug("Request to get all Interpretacions");
        return interpretacionRepository
            .findAll()
            .stream()
            .map(interpretacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one interpretacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InterpretacionDTO> findOne(Long id) {
        LOG.debug("Request to get Interpretacion : {}", id);
        return interpretacionRepository.findById(id).map(interpretacionMapper::toDto);
    }

    /**
     * Delete the interpretacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Interpretacion : {}", id);
        interpretacionRepository.deleteById(id);
    }
}
