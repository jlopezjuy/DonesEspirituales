package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.UsuarioAsserts.*;
import static ar.com.dones.app.domain.UsuarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioMapperTest {

    private UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        usuarioMapper = new UsuarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioSample1();
        var actual = usuarioMapper.toEntity(usuarioMapper.toDto(expected));
        assertUsuarioAllPropertiesEquals(expected, actual);
    }
}
