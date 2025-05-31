package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreguntaDonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaDonDTO.class);
        PreguntaDonDTO preguntaDonDTO1 = new PreguntaDonDTO();
        preguntaDonDTO1.setId(1L);
        PreguntaDonDTO preguntaDonDTO2 = new PreguntaDonDTO();
        assertThat(preguntaDonDTO1).isNotEqualTo(preguntaDonDTO2);
        preguntaDonDTO2.setId(preguntaDonDTO1.getId());
        assertThat(preguntaDonDTO1).isEqualTo(preguntaDonDTO2);
        preguntaDonDTO2.setId(2L);
        assertThat(preguntaDonDTO1).isNotEqualTo(preguntaDonDTO2);
        preguntaDonDTO1.setId(null);
        assertThat(preguntaDonDTO1).isNotEqualTo(preguntaDonDTO2);
    }
}
