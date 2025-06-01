package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.RespuestaUsuarioAsserts.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RespuestaUsuarioMapperTest {

  private RespuestaUsuarioMapper respuestaUsuarioMapper;

  @BeforeEach
  void setUp() {
    respuestaUsuarioMapper = new RespuestaUsuarioMapperImpl();
  }

  @Test
  void shouldConvertToDtoAndBack() {
    var expected = getRespuestaUsuarioSample1();
    var actual = respuestaUsuarioMapper.toEntity(respuestaUsuarioMapper.toDto(expected));
    assertRespuestaUsuarioAllPropertiesEquals(expected, actual);
  }
}
