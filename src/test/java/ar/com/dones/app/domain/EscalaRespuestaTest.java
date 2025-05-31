package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.EscalaRespuestaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EscalaRespuestaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscalaRespuesta.class);
        EscalaRespuesta escalaRespuesta1 = getEscalaRespuestaSample1();
        EscalaRespuesta escalaRespuesta2 = new EscalaRespuesta();
        assertThat(escalaRespuesta1).isNotEqualTo(escalaRespuesta2);

        escalaRespuesta2.setId(escalaRespuesta1.getId());
        assertThat(escalaRespuesta1).isEqualTo(escalaRespuesta2);

        escalaRespuesta2 = getEscalaRespuestaSample2();
        assertThat(escalaRespuesta1).isNotEqualTo(escalaRespuesta2);
    }
}
