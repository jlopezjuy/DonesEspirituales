package ar.com.dones.app.service;

import ar.com.dones.app.domain.ConfiguracionSistema;
import ar.com.dones.app.repository.ConfiguracionSistemaRepository;
import ar.com.dones.app.service.dto.ConfiguracionSistemaDTO;
import ar.com.dones.app.service.mapper.ConfiguracionSistemaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.ConfiguracionSistema}.
 */
@Service
@Transactional
public class ConfiguracionSistemaService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionSistemaService.class);

    private final ConfiguracionSistemaRepository configuracionSistemaRepository;

    private final ConfiguracionSistemaMapper configuracionSistemaMapper;

    public ConfiguracionSistemaService(
        ConfiguracionSistemaRepository configuracionSistemaRepository,
        ConfiguracionSistemaMapper configuracionSistemaMapper
    ) {
        this.configuracionSistemaRepository = configuracionSistemaRepository;
        this.configuracionSistemaMapper = configuracionSistemaMapper;
    }

    /**
     * Save a configuracionSistema.
     *
     * @param configuracionSistemaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfiguracionSistemaDTO save(ConfiguracionSistemaDTO configuracionSistemaDTO) {
        LOG.debug("Request to save ConfiguracionSistema : {}", configuracionSistemaDTO);
        ConfiguracionSistema configuracionSistema = configuracionSistemaMapper.toEntity(configuracionSistemaDTO);
        configuracionSistema = configuracionSistemaRepository.save(configuracionSistema);
        return configuracionSistemaMapper.toDto(configuracionSistema);
    }

    /**
     * Update a configuracionSistema.
     *
     * @param configuracionSistemaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfiguracionSistemaDTO update(ConfiguracionSistemaDTO configuracionSistemaDTO) {
        LOG.debug("Request to update ConfiguracionSistema : {}", configuracionSistemaDTO);
        ConfiguracionSistema configuracionSistema = configuracionSistemaMapper.toEntity(configuracionSistemaDTO);
        configuracionSistema = configuracionSistemaRepository.save(configuracionSistema);
        return configuracionSistemaMapper.toDto(configuracionSistema);
    }

    /**
     * Partially update a configuracionSistema.
     *
     * @param configuracionSistemaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfiguracionSistemaDTO> partialUpdate(ConfiguracionSistemaDTO configuracionSistemaDTO) {
        LOG.debug("Request to partially update ConfiguracionSistema : {}", configuracionSistemaDTO);

        return configuracionSistemaRepository
            .findById(configuracionSistemaDTO.getId())
            .map(existingConfiguracionSistema -> {
                configuracionSistemaMapper.partialUpdate(existingConfiguracionSistema, configuracionSistemaDTO);

                return existingConfiguracionSistema;
            })
            .map(configuracionSistemaRepository::save)
            .map(configuracionSistemaMapper::toDto);
    }

    /**
     * Get all the configuracionSistemas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConfiguracionSistemaDTO> findAll() {
        LOG.debug("Request to get all ConfiguracionSistemas");
        return configuracionSistemaRepository
            .findAll()
            .stream()
            .map(configuracionSistemaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one configuracionSistema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfiguracionSistemaDTO> findOne(Long id) {
        LOG.debug("Request to get ConfiguracionSistema : {}", id);
        return configuracionSistemaRepository.findById(id).map(configuracionSistemaMapper::toDto);
    }

    /**
     * Delete the configuracionSistema by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ConfiguracionSistema : {}", id);
        configuracionSistemaRepository.deleteById(id);
    }
}
