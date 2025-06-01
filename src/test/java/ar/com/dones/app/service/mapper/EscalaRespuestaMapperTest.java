package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.EscalaRespuestaAsserts.*;
import static ar.com.dones.app.domain.EscalaRespuestaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EscalaRespuestaMapperTest {

  private EscalaRespuestaMapper escalaRespuestaMapper;

  @BeforeEach
  void setUp() {
    escalaRespuestaMapper = new EscalaRespuestaMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getEscalaRespuestaSample1();
    var actual = escalaRespuestaMapper.toEntity(escalaRespuestaMapper.toDto(expected));
    assertEscalaRespuestaAllPropertiesEquals(expected, actual);
  }
}
