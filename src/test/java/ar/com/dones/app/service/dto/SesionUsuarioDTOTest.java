package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SesionUsuarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SesionUsuarioDTO.class);
        SesionUsuarioDTO sesionUsuarioDTO1 = new SesionUsuarioDTO();
        sesionUsuarioDTO1.setId(1L);
        SesionUsuarioDTO sesionUsuarioDTO2 = new SesionUsuarioDTO();
        assertThat(sesionUsuarioDTO1).isNotEqualTo(sesionUsuarioDTO2);
        sesionUsuarioDTO2.setId(sesionUsuarioDTO1.getId());
        assertThat(sesionUsuarioDTO1).isEqualTo(sesionUsuarioDTO2);
        sesionUsuarioDTO2.setId(2L);
        assertThat(sesionUsuarioDTO1).isNotEqualTo(sesionUsuarioDTO2);
        sesionUsuarioDTO1.setId(null);
        assertThat(sesionUsuarioDTO1).isNotEqualTo(sesionUsuarioDTO2);
    }
}
