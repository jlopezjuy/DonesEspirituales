package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.InterpretacionAsserts.*;
import static ar.com.dones.app.domain.InterpretacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterpretacionMapperTest {

  private InterpretacionMapper interpretacionMapper;

  @BeforeEach
  void setUp() {
    interpretacionMapper = new InterpretacionMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getInterpretacionSample1();
    var actual = interpretacionMapper.toEntity(interpretacionMapper.toDto(expected));
    assertInterpretacionAllPropertiesEquals(expected, actual);
  }
}
