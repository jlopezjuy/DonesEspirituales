package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RespuestaUsuarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RespuestaUsuarioDTO.class);
        RespuestaUsuarioDTO respuestaUsuarioDTO1 = new RespuestaUsuarioDTO();
        respuestaUsuarioDTO1.setId(1L);
        RespuestaUsuarioDTO respuestaUsuarioDTO2 = new RespuestaUsuarioDTO();
        assertThat(respuestaUsuarioDTO1).isNotEqualTo(respuestaUsuarioDTO2);
        respuestaUsuarioDTO2.setId(respuestaUsuarioDTO1.getId());
        assertThat(respuestaUsuarioDTO1).isEqualTo(respuestaUsuarioDTO2);
        respuestaUsuarioDTO2.setId(2L);
        assertThat(respuestaUsuarioDTO1).isNotEqualTo(respuestaUsuarioDTO2);
        respuestaUsuarioDTO1.setId(null);
        assertThat(respuestaUsuarioDTO1).isNotEqualTo(respuestaUsuarioDTO2);
    }
}
