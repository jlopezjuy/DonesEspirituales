package ar.com.dones.app.service;

import ar.com.dones.app.domain.Pregunta;
import ar.com.dones.app.repository.PreguntaRepository;
import ar.com.dones.app.service.dto.PreguntaDTO;
import ar.com.dones.app.service.mapper.PreguntaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.Pregunta}.
 */
@Service
@Transactional
public class PreguntaService {

    private static final Logger LOG = LoggerFactory.getLogger(PreguntaService.class);

    private final PreguntaRepository preguntaRepository;

    private final PreguntaMapper preguntaMapper;

    public PreguntaService(PreguntaRepository preguntaRepository, PreguntaMapper preguntaMapper) {
        this.preguntaRepository = preguntaRepository;
        this.preguntaMapper = preguntaMapper;
    }

    /**
     * Save a pregunta.
     *
     * @param preguntaDTO the entity to save.
     * @return the persisted entity.
     */
    public PreguntaDTO save(PreguntaDTO preguntaDTO) {
        LOG.debug("Request to save Pregunta : {}", preguntaDTO);
        Pregunta pregunta = preguntaMapper.toEntity(preguntaDTO);
        pregunta = preguntaRepository.save(pregunta);
        return preguntaMapper.toDto(pregunta);
    }

    /**
     * Update a pregunta.
     *
     * @param preguntaDTO the entity to save.
     * @return the persisted entity.
     */
    public PreguntaDTO update(PreguntaDTO preguntaDTO) {
        LOG.debug("Request to update Pregunta : {}", preguntaDTO);
        Pregunta pregunta = preguntaMapper.toEntity(preguntaDTO);
        pregunta = preguntaRepository.save(pregunta);
        return preguntaMapper.toDto(pregunta);
    }

    /**
     * Partially update a pregunta.
     *
     * @param preguntaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PreguntaDTO> partialUpdate(PreguntaDTO preguntaDTO) {
        LOG.debug("Request to partially update Pregunta : {}", preguntaDTO);

        return preguntaRepository
            .findById(preguntaDTO.getId())
            .map(existingPregunta -> {
                preguntaMapper.partialUpdate(existingPregunta, preguntaDTO);

                return existingPregunta;
            })
            .map(preguntaRepository::save)
            .map(preguntaMapper::toDto);
    }

    /**
     * Get all the preguntas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PreguntaDTO> findAll() {
        LOG.debug("Request to get all Preguntas");
        return preguntaRepository.findAll().stream().map(preguntaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pregunta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PreguntaDTO> findOne(Long id) {
        LOG.debug("Request to get Pregunta : {}", id);
        return preguntaRepository.findById(id).map(preguntaMapper::toDto);
    }

    /**
     * Delete the pregunta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Pregunta : {}", id);
        preguntaRepository.deleteById(id);
    }
}
