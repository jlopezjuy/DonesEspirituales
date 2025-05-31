package ar.com.dones.app.service;

import ar.com.dones.app.domain.ResultadoDon;
import ar.com.dones.app.repository.ResultadoDonRepository;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
import ar.com.dones.app.service.mapper.ResultadoDonMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.ResultadoDon}.
 */
@Service
@Transactional
public class ResultadoDonService {

    private static final Logger LOG = LoggerFactory.getLogger(ResultadoDonService.class);

    private final ResultadoDonRepository resultadoDonRepository;

    private final ResultadoDonMapper resultadoDonMapper;

    public ResultadoDonService(ResultadoDonRepository resultadoDonRepository, ResultadoDonMapper resultadoDonMapper) {
        this.resultadoDonRepository = resultadoDonRepository;
        this.resultadoDonMapper = resultadoDonMapper;
    }

    /**
     * Save a resultadoDon.
     *
     * @param resultadoDonDTO the entity to save.
     * @return the persisted entity.
     */
    public ResultadoDonDTO save(ResultadoDonDTO resultadoDonDTO) {
        LOG.debug("Request to save ResultadoDon : {}", resultadoDonDTO);
        ResultadoDon resultadoDon = resultadoDonMapper.toEntity(resultadoDonDTO);
        resultadoDon = resultadoDonRepository.save(resultadoDon);
        return resultadoDonMapper.toDto(resultadoDon);
    }

    /**
     * Update a resultadoDon.
     *
     * @param resultadoDonDTO the entity to save.
     * @return the persisted entity.
     */
    public ResultadoDonDTO update(ResultadoDonDTO resultadoDonDTO) {
        LOG.debug("Request to update ResultadoDon : {}", resultadoDonDTO);
        ResultadoDon resultadoDon = resultadoDonMapper.toEntity(resultadoDonDTO);
        resultadoDon = resultadoDonRepository.save(resultadoDon);
        return resultadoDonMapper.toDto(resultadoDon);
    }

    /**
     * Partially update a resultadoDon.
     *
     * @param resultadoDonDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResultadoDonDTO> partialUpdate(ResultadoDonDTO resultadoDonDTO) {
        LOG.debug("Request to partially update ResultadoDon : {}", resultadoDonDTO);

        return resultadoDonRepository
            .findById(resultadoDonDTO.getId())
            .map(existingResultadoDon -> {
                resultadoDonMapper.partialUpdate(existingResultadoDon, resultadoDonDTO);

                return existingResultadoDon;
            })
            .map(resultadoDonRepository::save)
            .map(resultadoDonMapper::toDto);
    }

    /**
     * Get one resultadoDon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultadoDonDTO> findOne(Long id) {
        LOG.debug("Request to get ResultadoDon : {}", id);
        return resultadoDonRepository.findById(id).map(resultadoDonMapper::toDto);
    }

    /**
     * Delete the resultadoDon by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ResultadoDon : {}", id);
        resultadoDonRepository.deleteById(id);
    }
}
