package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.AuditoriaRespuestaAsserts.*;
import static ar.com.dones.app.domain.AuditoriaRespuestaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditoriaRespuestaMapperTest {

  private AuditoriaRespuestaMapper auditoriaRespuestaMapper;

  @BeforeEach
  void setUp() {
    auditoriaRespuestaMapper = new AuditoriaRespuestaMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getAuditoriaRespuestaSample1();
    var actual = auditoriaRespuestaMapper.toEntity(auditoriaRespuestaMapper.toDto(expected));
    assertAuditoriaRespuestaAllPropertiesEquals(expected, actual);
  }
}
