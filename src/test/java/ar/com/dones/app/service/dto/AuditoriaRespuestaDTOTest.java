package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditoriaRespuestaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaRespuestaDTO.class);
        AuditoriaRespuestaDTO auditoriaRespuestaDTO1 = new AuditoriaRespuestaDTO();
        auditoriaRespuestaDTO1.setId(1L);
        AuditoriaRespuestaDTO auditoriaRespuestaDTO2 = new AuditoriaRespuestaDTO();
        assertThat(auditoriaRespuestaDTO1).isNotEqualTo(auditoriaRespuestaDTO2);
        auditoriaRespuestaDTO2.setId(auditoriaRespuestaDTO1.getId());
        assertThat(auditoriaRespuestaDTO1).isEqualTo(auditoriaRespuestaDTO2);
        auditoriaRespuestaDTO2.setId(2L);
        assertThat(auditoriaRespuestaDTO1).isNotEqualTo(auditoriaRespuestaDTO2);
        auditoriaRespuestaDTO1.setId(null);
        assertThat(auditoriaRespuestaDTO1).isNotEqualTo(auditoriaRespuestaDTO2);
    }
}
