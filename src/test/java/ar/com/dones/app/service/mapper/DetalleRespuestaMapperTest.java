package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.DetalleRespuestaAsserts.*;
import static ar.com.dones.app.domain.DetalleRespuestaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetalleRespuestaMapperTest {

    private DetalleRespuestaMapper detalleRespuestaMapper;

    @BeforeEach
    void setUp() {
        detalleRespuestaMapper = new DetalleRespuestaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDetalleRespuestaSample1();
        var actual = detalleRespuestaMapper.toEntity(detalleRespuestaMapper.toDto(expected));
        assertDetalleRespuestaAllPropertiesEquals(expected, actual);
    }
}
