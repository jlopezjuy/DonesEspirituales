package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.SesionUsuarioAsserts.*;
import static ar.com.dones.app.domain.SesionUsuarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SesionUsuarioMapperTest {

    private SesionUsuarioMapper sesionUsuarioMapper;

    @BeforeEach
    void setUp() {
        sesionUsuarioMapper = new SesionUsuarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSesionUsuarioSample1();
        var actual = sesionUsuarioMapper.toEntity(sesionUsuarioMapper.toDto(expected));
        assertSesionUsuarioAllPropertiesEquals(expected, actual);
    }
}
