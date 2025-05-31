package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.AuditoriaRespuestaTestSamples.*;
import static ar.com.dones.app.domain.DetalleRespuestaTestSamples.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditoriaRespuestaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaRespuesta.class);
        AuditoriaRespuesta auditoriaRespuesta1 = getAuditoriaRespuestaSample1();
        AuditoriaRespuesta auditoriaRespuesta2 = new AuditoriaRespuesta();
        assertThat(auditoriaRespuesta1).isNotEqualTo(auditoriaRespuesta2);

        auditoriaRespuesta2.setId(auditoriaRespuesta1.getId());
        assertThat(auditoriaRespuesta1).isEqualTo(auditoriaRespuesta2);

        auditoriaRespuesta2 = getAuditoriaRespuestaSample2();
        assertThat(auditoriaRespuesta1).isNotEqualTo(auditoriaRespuesta2);
    }

    @Test
    void respuestaUsuarioTest() {
        AuditoriaRespuesta auditoriaRespuesta = getAuditoriaRespuestaRandomSampleGenerator();
        RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

        auditoriaRespuesta.setRespuestaUsuario(respuestaUsuarioBack);
        assertThat(auditoriaRespuesta.getRespuestaUsuario()).isEqualTo(respuestaUsuarioBack);

        auditoriaRespuesta.respuestaUsuario(null);
        assertThat(auditoriaRespuesta.getRespuestaUsuario()).isNull();
    }

    @Test
    void detalleRespuestaTest() {
        AuditoriaRespuesta auditoriaRespuesta = getAuditoriaRespuestaRandomSampleGenerator();
        DetalleRespuesta detalleRespuestaBack = getDetalleRespuestaRandomSampleGenerator();

        auditoriaRespuesta.setDetalleRespuesta(detalleRespuestaBack);
        assertThat(auditoriaRespuesta.getDetalleRespuesta()).isEqualTo(detalleRespuestaBack);

        auditoriaRespuesta.detalleRespuesta(null);
        assertThat(auditoriaRespuesta.getDetalleRespuesta()).isNull();
    }
}
