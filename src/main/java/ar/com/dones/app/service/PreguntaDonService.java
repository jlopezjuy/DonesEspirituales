package ar.com.dones.app.service;

import ar.com.dones.app.domain.PreguntaDon;
import ar.com.dones.app.repository.PreguntaDonRepository;
import ar.com.dones.app.service.dto.PreguntaDonDTO;
import ar.com.dones.app.service.mapper.PreguntaDonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.PreguntaDon}.
 */
@Service
@Transactional
public class PreguntaDonService {

    private static final Logger LOG = LoggerFactory.getLogger(PreguntaDonService.class);

    private final PreguntaDonRepository preguntaDonRepository;

    private final PreguntaDonMapper preguntaDonMapper;

    public PreguntaDonService(PreguntaDonRepository preguntaDonRepository, PreguntaDonMapper preguntaDonMapper) {
        this.preguntaDonRepository = preguntaDonRepository;
        this.preguntaDonMapper = preguntaDonMapper;
    }

    /**
     * Save a preguntaDon.
     *
     * @param preguntaDonDTO the entity to save.
     * @return the persisted entity.
     */
    public PreguntaDonDTO save(PreguntaDonDTO preguntaDonDTO) {
        LOG.debug("Request to save PreguntaDon : {}", preguntaDonDTO);
        PreguntaDon preguntaDon = preguntaDonMapper.toEntity(preguntaDonDTO);
        preguntaDon = preguntaDonRepository.save(preguntaDon);
        return preguntaDonMapper.toDto(preguntaDon);
    }

    /**
     * Update a preguntaDon.
     *
     * @param preguntaDonDTO the entity to save.
     * @return the persisted entity.
     */
    public PreguntaDonDTO update(PreguntaDonDTO preguntaDonDTO) {
        LOG.debug("Request to update PreguntaDon : {}", preguntaDonDTO);
        PreguntaDon preguntaDon = preguntaDonMapper.toEntity(preguntaDonDTO);
        preguntaDon = preguntaDonRepository.save(preguntaDon);
        return preguntaDonMapper.toDto(preguntaDon);
    }

    /**
     * Partially update a preguntaDon.
     *
     * @param preguntaDonDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PreguntaDonDTO> partialUpdate(PreguntaDonDTO preguntaDonDTO) {
        LOG.debug("Request to partially update PreguntaDon : {}", preguntaDonDTO);

        return preguntaDonRepository
            .findById(preguntaDonDTO.getId())
            .map(existingPreguntaDon -> {
                preguntaDonMapper.partialUpdate(existingPreguntaDon, preguntaDonDTO);

                return existingPreguntaDon;
            })
            .map(preguntaDonRepository::save)
            .map(preguntaDonMapper::toDto);
    }

    /**
     * Get all the preguntaDons.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PreguntaDonDTO> findAll() {
        LOG.debug("Request to get all PreguntaDons");
        return preguntaDonRepository.findAll().stream().map(preguntaDonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one preguntaDon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PreguntaDonDTO> findOne(Long id) {
        LOG.debug("Request to get PreguntaDon : {}", id);
        return preguntaDonRepository.findById(id).map(preguntaDonMapper::toDto);
    }

    /**
     * Delete the preguntaDon by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PreguntaDon : {}", id);
        preguntaDonRepository.deleteById(id);
    }
}
