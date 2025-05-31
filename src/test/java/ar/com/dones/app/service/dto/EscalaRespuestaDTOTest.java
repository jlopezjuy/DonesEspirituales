package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EscalaRespuestaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscalaRespuestaDTO.class);
        EscalaRespuestaDTO escalaRespuestaDTO1 = new EscalaRespuestaDTO();
        escalaRespuestaDTO1.setId(1L);
        EscalaRespuestaDTO escalaRespuestaDTO2 = new EscalaRespuestaDTO();
        assertThat(escalaRespuestaDTO1).isNotEqualTo(escalaRespuestaDTO2);
        escalaRespuestaDTO2.setId(escalaRespuestaDTO1.getId());
        assertThat(escalaRespuestaDTO1).isEqualTo(escalaRespuestaDTO2);
        escalaRespuestaDTO2.setId(2L);
        assertThat(escalaRespuestaDTO1).isNotEqualTo(escalaRespuestaDTO2);
        escalaRespuestaDTO1.setId(null);
        assertThat(escalaRespuestaDTO1).isNotEqualTo(escalaRespuestaDTO2);
    }
}
