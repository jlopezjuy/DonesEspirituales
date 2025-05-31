package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.PreguntaDonAsserts.*;
import static ar.com.dones.app.domain.PreguntaDonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreguntaDonMapperTest {

    private PreguntaDonMapper preguntaDonMapper;

    @BeforeEach
    void setUp() {
        preguntaDonMapper = new PreguntaDonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPreguntaDonSample1();
        var actual = preguntaDonMapper.toEntity(preguntaDonMapper.toDto(expected));
        assertPreguntaDonAllPropertiesEquals(expected, actual);
    }
}
