package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.PreguntaAsserts.*;
import static ar.com.dones.app.domain.PreguntaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreguntaMapperTest {

  private PreguntaMapper preguntaMapper;

  @BeforeEach
  void setUp() {
    preguntaMapper = new PreguntaMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getPreguntaSample1();
    var actual = preguntaMapper.toEntity(preguntaMapper.toDto(expected));
    assertPreguntaAllPropertiesEquals(expected, actual);
  }
}
