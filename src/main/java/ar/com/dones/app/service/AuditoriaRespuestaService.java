package ar.com.dones.app.service;

import ar.com.dones.app.domain.AuditoriaRespuesta;
import ar.com.dones.app.repository.AuditoriaRespuestaRepository;
import ar.com.dones.app.service.dto.AuditoriaRespuestaDTO;
import ar.com.dones.app.service.mapper.AuditoriaRespuestaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.AuditoriaRespuesta}.
 */
@Service
@Transactional
public class AuditoriaRespuestaService {

    private static final Logger LOG = LoggerFactory.getLogger(AuditoriaRespuestaService.class);

    private final AuditoriaRespuestaRepository auditoriaRespuestaRepository;

    private final AuditoriaRespuestaMapper auditoriaRespuestaMapper;

    public AuditoriaRespuestaService(
        AuditoriaRespuestaRepository auditoriaRespuestaRepository,
        AuditoriaRespuestaMapper auditoriaRespuestaMapper
    ) {
        this.auditoriaRespuestaRepository = auditoriaRespuestaRepository;
        this.auditoriaRespuestaMapper = auditoriaRespuestaMapper;
    }

    /**
     * Save a auditoriaRespuesta.
     *
     * @param auditoriaRespuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditoriaRespuestaDTO save(AuditoriaRespuestaDTO auditoriaRespuestaDTO) {
        LOG.debug("Request to save AuditoriaRespuesta : {}", auditoriaRespuestaDTO);
        AuditoriaRespuesta auditoriaRespuesta = auditoriaRespuestaMapper.toEntity(auditoriaRespuestaDTO);
        auditoriaRespuesta = auditoriaRespuestaRepository.save(auditoriaRespuesta);
        return auditoriaRespuestaMapper.toDto(auditoriaRespuesta);
    }

    /**
     * Update a auditoriaRespuesta.
     *
     * @param auditoriaRespuestaDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditoriaRespuestaDTO update(AuditoriaRespuestaDTO auditoriaRespuestaDTO) {
        LOG.debug("Request to update AuditoriaRespuesta : {}", auditoriaRespuestaDTO);
        AuditoriaRespuesta auditoriaRespuesta = auditoriaRespuestaMapper.toEntity(auditoriaRespuestaDTO);
        auditoriaRespuesta = auditoriaRespuestaRepository.save(auditoriaRespuesta);
        return auditoriaRespuestaMapper.toDto(auditoriaRespuesta);
    }

    /**
     * Partially update a auditoriaRespuesta.
     *
     * @param auditoriaRespuestaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditoriaRespuestaDTO> partialUpdate(AuditoriaRespuestaDTO auditoriaRespuestaDTO) {
        LOG.debug("Request to partially update AuditoriaRespuesta : {}", auditoriaRespuestaDTO);

        return auditoriaRespuestaRepository
            .findById(auditoriaRespuestaDTO.getId())
            .map(existingAuditoriaRespuesta -> {
                auditoriaRespuestaMapper.partialUpdate(existingAuditoriaRespuesta, auditoriaRespuestaDTO);

                return existingAuditoriaRespuesta;
            })
            .map(auditoriaRespuestaRepository::save)
            .map(auditoriaRespuestaMapper::toDto);
    }

    /**
     * Get all the auditoriaRespuestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaRespuestaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all AuditoriaRespuestas");
        return auditoriaRespuestaRepository.findAll(pageable).map(auditoriaRespuestaMapper::toDto);
    }

    /**
     * Get one auditoriaRespuesta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditoriaRespuestaDTO> findOne(Long id) {
        LOG.debug("Request to get AuditoriaRespuesta : {}", id);
        return auditoriaRespuestaRepository.findById(id).map(auditoriaRespuestaMapper::toDto);
    }

    /**
     * Delete the auditoriaRespuesta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete AuditoriaRespuesta : {}", id);
        auditoriaRespuestaRepository.deleteById(id);
    }
}
