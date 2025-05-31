package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.ConfiguracionSistemaAsserts.*;
import static ar.com.dones.app.domain.ConfiguracionSistemaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfiguracionSistemaMapperTest {

    private ConfiguracionSistemaMapper configuracionSistemaMapper;

    @BeforeEach
    void setUp() {
        configuracionSistemaMapper = new ConfiguracionSistemaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConfiguracionSistemaSample1();
        var actual = configuracionSistemaMapper.toEntity(configuracionSistemaMapper.toDto(expected));
        assertConfiguracionSistemaAllPropertiesEquals(expected, actual);
    }
}
