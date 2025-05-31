package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.ResultadoDonAsserts.*;
import static ar.com.dones.app.domain.ResultadoDonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultadoDonMapperTest {

    private ResultadoDonMapper resultadoDonMapper;

    @BeforeEach
    void setUp() {
        resultadoDonMapper = new ResultadoDonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getResultadoDonSample1();
        var actual = resultadoDonMapper.toEntity(resultadoDonMapper.toDto(expected));
        assertResultadoDonAllPropertiesEquals(expected, actual);
    }
}
