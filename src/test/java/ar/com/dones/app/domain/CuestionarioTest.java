package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.CuestionarioTestSamples.*;
import static ar.com.dones.app.domain.PreguntaTestSamples.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CuestionarioTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Cuestionario.class);
    Cuestionario cuestionario1 = getCuestionarioSample1();
    Cuestionario cuestionario2 = new Cuestionario();
    assertThat(cuestionario1).isNotEqualTo(cuestionario2);

    cuestionario2.setId(cuestionario1.getId());
    assertThat(cuestionario1).isEqualTo(cuestionario2);

    cuestionario2 = getCuestionarioSample2();
    assertThat(cuestionario1).isNotEqualTo(cuestionario2);
  }

  @Test
  void preguntasTest() {
    Cuestionario cuestionario = getCuestionarioRandomSampleGenerator();
    Pregunta preguntaBack = getPreguntaRandomSampleGenerator();

    cuestionario.addPreguntas(preguntaBack);
    assertThat(cuestionario.getPreguntas()).containsOnly(preguntaBack);
    assertThat(preguntaBack.getCuestionario()).isEqualTo(cuestionario);

    cuestionario.removePreguntas(preguntaBack);
    assertThat(cuestionario.getPreguntas()).doesNotContain(preguntaBack);
    assertThat(preguntaBack.getCuestionario()).isNull();

    cuestionario.preguntas(new HashSet<>(Set.of(preguntaBack)));
    assertThat(cuestionario.getPreguntas()).containsOnly(preguntaBack);
    assertThat(preguntaBack.getCuestionario()).isEqualTo(cuestionario);

    cuestionario.setPreguntas(new HashSet<>());
    assertThat(cuestionario.getPreguntas()).doesNotContain(preguntaBack);
    assertThat(preguntaBack.getCuestionario()).isNull();
  }

  @Test
  void respuestasTest() {
    Cuestionario cuestionario = getCuestionarioRandomSampleGenerator();
    RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

    cuestionario.addRespuestas(respuestaUsuarioBack);
    assertThat(cuestionario.getRespuestas()).containsOnly(respuestaUsuarioBack);
    assertThat(respuestaUsuarioBack.getCuestionario()).isEqualTo(cuestionario);

    cuestionario.removeRespuestas(respuestaUsuarioBack);
    assertThat(cuestionario.getRespuestas()).doesNotContain(respuestaUsuarioBack);
    assertThat(respuestaUsuarioBack.getCuestionario()).isNull();

    cuestionario.respuestas(new HashSet<>(Set.of(respuestaUsuarioBack)));
    assertThat(cuestionario.getRespuestas()).containsOnly(respuestaUsuarioBack);
    assertThat(respuestaUsuarioBack.getCuestionario()).isEqualTo(cuestionario);

    cuestionario.setRespuestas(new HashSet<>());
    assertThat(cuestionario.getRespuestas()).doesNotContain(respuestaUsuarioBack);
    assertThat(respuestaUsuarioBack.getCuestionario()).isNull();
  }
}
