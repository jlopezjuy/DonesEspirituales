package ar.com.dones.app.service.mapper;

import static ar.com.dones.app.domain.DonEspiritualAsserts.*;
import static ar.com.dones.app.domain.DonEspiritualTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonEspiritualMapperTest {

    private DonEspiritualMapper donEspiritualMapper;

    @BeforeEach
    void setUp() {
        donEspiritualMapper = new DonEspiritualMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDonEspiritualSample1();
        var actual = donEspiritualMapper.toEntity(donEspiritualMapper.toDto(expected));
        assertDonEspiritualAllPropertiesEquals(expected, actual);
    }
}
