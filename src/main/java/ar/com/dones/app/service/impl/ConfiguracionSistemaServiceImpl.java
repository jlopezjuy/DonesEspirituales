package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.ConfiguracionSistema;
import ar.com.dones.app.repository.ConfiguracionSistemaRepository;
import ar.com.dones.app.service.ConfiguracionSistemaService;
import ar.com.dones.app.service.dto.ConfiguracionSistemaDTO;
import ar.com.dones.app.service.mapper.ConfiguracionSistemaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.ConfiguracionSistema}.
 */
@Service
@Transactional
public class ConfiguracionSistemaServiceImpl implements ConfiguracionSistemaService {

  private static final Logger LOG = LoggerFactory.getLogger(ConfiguracionSistemaServiceImpl.class);

  private final ConfiguracionSistemaRepository configuracionSistemaRepository;

  private final ConfiguracionSistemaMapper configuracionSistemaMapper;

  public ConfiguracionSistemaServiceImpl(
    ConfiguracionSistemaRepository configuracionSistemaRepository,
    ConfiguracionSistemaMapper configuracionSistemaMapper
  ) {
    this.configuracionSistemaRepository = configuracionSistemaRepository;
    this.configuracionSistemaMapper = configuracionSistemaMapper;
  }

  @Override
  public ConfiguracionSistemaDTO save(ConfiguracionSistemaDTO configuracionSistemaDTO) {
    LOG.debug("Request to save ConfiguracionSistema : {}", configuracionSistemaDTO);
    ConfiguracionSistema configuracionSistema = configuracionSistemaMapper.toEntity(configuracionSistemaDTO);
    configuracionSistema = configuracionSistemaRepository.save(configuracionSistema);
    return configuracionSistemaMapper.toDto(configuracionSistema);
  }

  @Override
  public ConfiguracionSistemaDTO update(ConfiguracionSistemaDTO configuracionSistemaDTO) {
    LOG.debug("Request to update ConfiguracionSistema : {}", configuracionSistemaDTO);
    ConfiguracionSistema configuracionSistema = configuracionSistemaMapper.toEntity(configuracionSistemaDTO);
    configuracionSistema = configuracionSistemaRepository.save(configuracionSistema);
    return configuracionSistemaMapper.toDto(configuracionSistema);
  }

  @Override
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

  @Override
  @Transactional(readOnly = true)
  public Page<ConfiguracionSistemaDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all ConfiguracionSistemas");
    return configuracionSistemaRepository.findAll(pageable).map(configuracionSistemaMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ConfiguracionSistemaDTO> findOne(Long id) {
    LOG.debug("Request to get ConfiguracionSistema : {}", id);
    return configuracionSistemaRepository.findById(id).map(configuracionSistemaMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete ConfiguracionSistema : {}", id);
    configuracionSistemaRepository.deleteById(id);
  }
}
