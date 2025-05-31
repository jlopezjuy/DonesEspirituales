package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuestionarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuestionarioDTO.class);
        CuestionarioDTO cuestionarioDTO1 = new CuestionarioDTO();
        cuestionarioDTO1.setId(1L);
        CuestionarioDTO cuestionarioDTO2 = new CuestionarioDTO();
        assertThat(cuestionarioDTO1).isNotEqualTo(cuestionarioDTO2);
        cuestionarioDTO2.setId(cuestionarioDTO1.getId());
        assertThat(cuestionarioDTO1).isEqualTo(cuestionarioDTO2);
        cuestionarioDTO2.setId(2L);
        assertThat(cuestionarioDTO1).isNotEqualTo(cuestionarioDTO2);
        cuestionarioDTO1.setId(null);
        assertThat(cuestionarioDTO1).isNotEqualTo(cuestionarioDTO2);
    }
}
