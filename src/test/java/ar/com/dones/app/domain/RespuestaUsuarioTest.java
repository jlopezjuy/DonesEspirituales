package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.AuditoriaRespuestaTestSamples.*;
import static ar.com.dones.app.domain.CuestionarioTestSamples.*;
import static ar.com.dones.app.domain.DetalleRespuestaTestSamples.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static ar.com.dones.app.domain.ResultadoDonTestSamples.*;
import static ar.com.dones.app.domain.SesionUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RespuestaUsuarioTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(RespuestaUsuario.class);
    RespuestaUsuario respuestaUsuario1 = getRespuestaUsuarioSample1();
    RespuestaUsuario respuestaUsuario2 = new RespuestaUsuario();
    assertThat(respuestaUsuario1).isNotEqualTo(respuestaUsuario2);

    respuestaUsuario2.setId(respuestaUsuario1.getId());
    assertThat(respuestaUsuario1).isEqualTo(respuestaUsuario2);

    respuestaUsuario2 = getRespuestaUsuarioSample2();
    assertThat(respuestaUsuario1).isNotEqualTo(respuestaUsuario2);
  }

  @Test
  void detalleRespuestasTest() {
    RespuestaUsuario respuestaUsuario = getRespuestaUsuarioRandomSampleGenerator();
    DetalleRespuesta detalleRespuestaBack = getDetalleRespuestaRandomSampleGenerator();

    respuestaUsuario.addDetalleRespuestas(detalleRespuestaBack);
    assertThat(respuestaUsuario.getDetalleRespuestas()).containsOnly(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.removeDetalleRespuestas(detalleRespuestaBack);
    assertThat(respuestaUsuario.getDetalleRespuestas()).doesNotContain(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getRespuestaUsuario()).isNull();

    respuestaUsuario.detalleRespuestas(new HashSet<>(Set.of(detalleRespuestaBack)));
    assertThat(respuestaUsuario.getDetalleRespuestas()).containsOnly(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.setDetalleRespuestas(new HashSet<>());
    assertThat(respuestaUsuario.getDetalleRespuestas()).doesNotContain(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getRespuestaUsuario()).isNull();
  }

  @Test
  void resultadoDonesTest() {
    RespuestaUsuario respuestaUsuario = getRespuestaUsuarioRandomSampleGenerator();
    ResultadoDon resultadoDonBack = getResultadoDonRandomSampleGenerator();

    respuestaUsuario.addResultadoDones(resultadoDonBack);
    assertThat(respuestaUsuario.getResultadoDones()).containsOnly(resultadoDonBack);
    assertThat(resultadoDonBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.removeResultadoDones(resultadoDonBack);
    assertThat(respuestaUsuario.getResultadoDones()).doesNotContain(resultadoDonBack);
    assertThat(resultadoDonBack.getRespuestaUsuario()).isNull();

    respuestaUsuario.resultadoDones(new HashSet<>(Set.of(resultadoDonBack)));
    assertThat(respuestaUsuario.getResultadoDones()).containsOnly(resultadoDonBack);
    assertThat(resultadoDonBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.setResultadoDones(new HashSet<>());
    assertThat(respuestaUsuario.getResultadoDones()).doesNotContain(resultadoDonBack);
    assertThat(resultadoDonBack.getRespuestaUsuario()).isNull();
  }

  @Test
  void sesionesTest() {
    RespuestaUsuario respuestaUsuario = getRespuestaUsuarioRandomSampleGenerator();
    SesionUsuario sesionUsuarioBack = getSesionUsuarioRandomSampleGenerator();

    respuestaUsuario.addSesiones(sesionUsuarioBack);
    assertThat(respuestaUsuario.getSesiones()).containsOnly(sesionUsuarioBack);
    assertThat(sesionUsuarioBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.removeSesiones(sesionUsuarioBack);
    assertThat(respuestaUsuario.getSesiones()).doesNotContain(sesionUsuarioBack);
    assertThat(sesionUsuarioBack.getRespuestaUsuario()).isNull();

    respuestaUsuario.sesiones(new HashSet<>(Set.of(sesionUsuarioBack)));
    assertThat(respuestaUsuario.getSesiones()).containsOnly(sesionUsuarioBack);
    assertThat(sesionUsuarioBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.setSesiones(new HashSet<>());
    assertThat(respuestaUsuario.getSesiones()).doesNotContain(sesionUsuarioBack);
    assertThat(sesionUsuarioBack.getRespuestaUsuario()).isNull();
  }

  @Test
  void auditoriasTest() {
    RespuestaUsuario respuestaUsuario = getRespuestaUsuarioRandomSampleGenerator();
    AuditoriaRespuesta auditoriaRespuestaBack = getAuditoriaRespuestaRandomSampleGenerator();

    respuestaUsuario.addAuditorias(auditoriaRespuestaBack);
    assertThat(respuestaUsuario.getAuditorias()).containsOnly(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.removeAuditorias(auditoriaRespuestaBack);
    assertThat(respuestaUsuario.getAuditorias()).doesNotContain(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getRespuestaUsuario()).isNull();

    respuestaUsuario.auditorias(new HashSet<>(Set.of(auditoriaRespuestaBack)));
    assertThat(respuestaUsuario.getAuditorias()).containsOnly(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getRespuestaUsuario()).isEqualTo(respuestaUsuario);

    respuestaUsuario.setAuditorias(new HashSet<>());
    assertThat(respuestaUsuario.getAuditorias()).doesNotContain(auditoriaRespuestaBack);
    assertThat(auditoriaRespuestaBack.getRespuestaUsuario()).isNull();
  }

  @Test
  void cuestionarioTest() {
    RespuestaUsuario respuestaUsuario = getRespuestaUsuarioRandomSampleGenerator();
    Cuestionario cuestionarioBack = getCuestionarioRandomSampleGenerator();

    respuestaUsuario.setCuestionario(cuestionarioBack);
    assertThat(respuestaUsuario.getCuestionario()).isEqualTo(cuestionarioBack);

    respuestaUsuario.cuestionario(null);
    assertThat(respuestaUsuario.getCuestionario()).isNull();
  }
}
