package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.AuditoriaRespuestaTestSamples.*;
import static ar.com.dones.app.domain.DetalleRespuestaTestSamples.*;
import static ar.com.dones.app.domain.EscalaRespuestaTestSamples.*;
import static ar.com.dones.app.domain.PreguntaTestSamples.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DetalleRespuestaTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DetalleRespuesta.class);
    DetalleRespuesta detalleRespuesta1 = getDetalleRespuestaSample1();
    DetalleRespuesta detalleRespuesta2 = new DetalleRespuesta();
    assertThat(detalleRespuesta1).isNotEqualTo(detalleRespuesta2);

    detalleRespuesta2.setId(detalleRespuesta1.getId());
    assertThat(detalleRespuesta1).isEqualTo(detalleRespuesta2);

    detalleRespuesta2 = getDetalleRespuestaSample2();
    assertThat(detalleRespuesta1).isNotEqualTo(detalleRespuesta2);
  }

  @Test
  void auditoriasTest() {
    DetalleRespuesta detalleRespuesta = getDetalleRespuestaRandomSampleGenerator();
    AuditoriaRespuesta auditoriaRespuestaBack = getAuditoriaRespuestaRandomSampleGenerator();

    detalleRespuesta.addAuditorias(auditoriaRespuestaBack);
    assertThat(detalleRespuesta.getAuditorias()).containsOnly(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getDetalleRespuesta()).isEqualTo(detalleRespuesta);

    detalleRespuesta.removeAuditorias(auditoriaRespuestaBack);
    assertThat(detalleRespuesta.getAuditorias()).doesNotContain(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getDetalleRespuesta()).isNull();

    detalleRespuesta.auditorias(new HashSet<>(Set.of(auditoriaRespuestaBack)));
    assertThat(detalleRespuesta.getAuditorias()).containsOnly(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getDetalleRespuesta()).isEqualTo(detalleRespuesta);

    detalleRespuesta.setAuditorias(new HashSet<>());
    assertThat(detalleRespuesta.getAuditorias()).doesNotContain(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getDetalleRespuesta()).isNull();
  }

  @Test
  void escalaRespuestaTest() {
    DetalleRespuesta detalleRespuesta = getDetalleRespuestaRandomSampleGenerator();
    EscalaRespuesta escalaRespuestaBack = getEscalaRespuestaRandomSampleGenerator();

    detalleRespuesta.setEscalaRespuesta(escalaRespuestaBack);
    assertThat(detalleRespuesta.getEscalaRespuesta()).isEqualTo(escalaRespuestaBack);

    detalleRespuesta.escalaRespuesta(null);
    assertThat(detalleRespuesta.getEscalaRespuesta()).isNull();
  }

  @Test
  void preguntaTest() {
    DetalleRespuesta detalleRespuesta = getDetalleRespuestaRandomSampleGenerator();
    Pregunta preguntaBack = getPreguntaRandomSampleGenerator();

    detalleRespuesta.setPregunta(preguntaBack);
    assertThat(detalleRespuesta.getPregunta()).isEqualTo(preguntaBack);

    detalleRespuesta.pregunta(null);
    assertThat(detalleRespuesta.getPregunta()).isNull();
  }

  @Test
  void respuestaUsuarioTest() {
    DetalleRespuesta detalleRespuesta = getDetalleRespuestaRandomSampleGenerator();
    RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

    detalleRespuesta.setRespuestaUsuario(respuestaUsuarioBack);
    assertThat(detalleRespuesta.getRespuestaUsuario()).isEqualTo(respuestaUsuarioBack);

    detalleRespuesta.respuestaUsuario(null);
    assertThat(detalleRespuesta.getRespuestaUsuario()).isNull();
  }
}
