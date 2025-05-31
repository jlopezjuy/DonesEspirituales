package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalleRespuestaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleRespuestaDTO.class);
        DetalleRespuestaDTO detalleRespuestaDTO1 = new DetalleRespuestaDTO();
        detalleRespuestaDTO1.setId(1L);
        DetalleRespuestaDTO detalleRespuestaDTO2 = new DetalleRespuestaDTO();
        assertThat(detalleRespuestaDTO1).isNotEqualTo(detalleRespuestaDTO2);
        detalleRespuestaDTO2.setId(detalleRespuestaDTO1.getId());
        assertThat(detalleRespuestaDTO1).isEqualTo(detalleRespuestaDTO2);
        detalleRespuestaDTO2.setId(2L);
        assertThat(detalleRespuestaDTO1).isNotEqualTo(detalleRespuestaDTO2);
        detalleRespuestaDTO1.setId(null);
        assertThat(detalleRespuestaDTO1).isNotEqualTo(detalleRespuestaDTO2);
    }
}
