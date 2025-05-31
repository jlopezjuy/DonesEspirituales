package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonEspiritualDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonEspiritualDTO.class);
        DonEspiritualDTO donEspiritualDTO1 = new DonEspiritualDTO();
        donEspiritualDTO1.setId(1L);
        DonEspiritualDTO donEspiritualDTO2 = new DonEspiritualDTO();
        assertThat(donEspiritualDTO1).isNotEqualTo(donEspiritualDTO2);
        donEspiritualDTO2.setId(donEspiritualDTO1.getId());
        assertThat(donEspiritualDTO1).isEqualTo(donEspiritualDTO2);
        donEspiritualDTO2.setId(2L);
        assertThat(donEspiritualDTO1).isNotEqualTo(donEspiritualDTO2);
        donEspiritualDTO1.setId(null);
        assertThat(donEspiritualDTO1).isNotEqualTo(donEspiritualDTO2);
    }
}
