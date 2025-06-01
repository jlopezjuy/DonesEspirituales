package ar.com.dones.app.service.impl;

import ar.com.dones.app.domain.ResultadoDon;
import ar.com.dones.app.repository.ResultadoDonRepository;
import ar.com.dones.app.service.ResultadoDonService;
import ar.com.dones.app.service.dto.ResultadoDonDTO;
import ar.com.dones.app.service.mapper.ResultadoDonMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.dones.app.domain.ResultadoDon}.
 */
@Service
@Transactional
public class ResultadoDonServiceImpl implements ResultadoDonService {

  private static final Logger LOG = LoggerFactory.getLogger(ResultadoDonServiceImpl.class);

  private final ResultadoDonRepository resultadoDonRepository;

  private final ResultadoDonMapper resultadoDonMapper;

  public ResultadoDonServiceImpl(ResultadoDonRepository resultadoDonRepository, ResultadoDonMapper resultadoDonMapper) {
    this.resultadoDonRepository = resultadoDonRepository;
    this.resultadoDonMapper = resultadoDonMapper;
  }

  @Override
  public ResultadoDonDTO save(ResultadoDonDTO resultadoDonDTO) {
    LOG.debug("Request to save ResultadoDon : {}", resultadoDonDTO);
    ResultadoDon resultadoDon = resultadoDonMapper.toEntity(resultadoDonDTO);
    resultadoDon = resultadoDonRepository.save(resultadoDon);
    return resultadoDonMapper.toDto(resultadoDon);
  }

  @Override
  public ResultadoDonDTO update(ResultadoDonDTO resultadoDonDTO) {
    LOG.debug("Request to update ResultadoDon : {}", resultadoDonDTO);
    ResultadoDon resultadoDon = resultadoDonMapper.toEntity(resultadoDonDTO);
    resultadoDon = resultadoDonRepository.save(resultadoDon);
    return resultadoDonMapper.toDto(resultadoDon);
  }

  @Override
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

  @Override
  @Transactional(readOnly = true)
  public Page<ResultadoDonDTO> findAll(Pageable pageable) {
    LOG.debug("Request to get all ResultadoDons");
    return resultadoDonRepository.findAll(pageable).map(resultadoDonMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ResultadoDonDTO> findOne(Long id) {
    LOG.debug("Request to get ResultadoDon : {}", id);
    return resultadoDonRepository.findById(id).map(resultadoDonMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    LOG.debug("Request to delete ResultadoDon : {}", id);
    resultadoDonRepository.deleteById(id);
  }
}
