package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.CuestionarioAsserts.*;
import static ar.com.dones.app.domain.CuestionarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuestionarioMapperTest {

  private CuestionarioMapper cuestionarioMapper;

  @BeforeEach
  void setUp() {
    cuestionarioMapper = new CuestionarioMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getCuestionarioSample1();
    var actual = cuestionarioMapper.toEntity(cuestionarioMapper.toDto(expected));
    assertCuestionarioAllPropertiesEquals(expected, actual);
  }
}
