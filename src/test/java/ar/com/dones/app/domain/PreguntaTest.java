package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.CuestionarioTestSamples.*;
import static ar.com.dones.app.domain.DetalleRespuestaTestSamples.*;
import static ar.com.dones.app.domain.PreguntaDonTestSamples.*;
import static ar.com.dones.app.domain.PreguntaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PreguntaTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Pregunta.class);
    Pregunta pregunta1 = getPreguntaSample1();
    Pregunta pregunta2 = new Pregunta();
    assertThat(pregunta1).isNotEqualTo(pregunta2);

    pregunta2.setId(pregunta1.getId());
    assertThat(pregunta1).isEqualTo(pregunta2);

    pregunta2 = getPreguntaSample2();
    assertThat(pregunta1).isNotEqualTo(pregunta2);
  }

  @Test
  void preguntaDonesTest() {
    Pregunta pregunta = getPreguntaRandomSampleGenerator();
    PreguntaDon preguntaDonBack = getPreguntaDonRandomSampleGenerator();

    pregunta.addPreguntaDones(preguntaDonBack);
    assertThat(pregunta.getPreguntaDones()).containsOnly(preguntaDonBack);
    assertThat(preguntaDonBack.getPregunta()).isEqualTo(pregunta);

    pregunta.removePreguntaDones(preguntaDonBack);
    assertThat(pregunta.getPreguntaDones()).doesNotContain(preguntaDonBack);
    assertThat(preguntaDonBack.getPregunta()).isNull();

    pregunta.preguntaDones(new HashSet<>(Set.of(preguntaDonBack)));
    assertThat(pregunta.getPreguntaDones()).containsOnly(preguntaDonBack);
    assertThat(preguntaDonBack.getPregunta()).isEqualTo(pregunta);

    pregunta.setPreguntaDones(new HashSet<>());
    assertThat(pregunta.getPreguntaDones()).doesNotContain(preguntaDonBack);
    assertThat(preguntaDonBack.getPregunta()).isNull();
  }

  @Test
  void detalleRespuestasTest() {
    Pregunta pregunta = getPreguntaRandomSampleGenerator();
    DetalleRespuesta detalleRespuestaBack = getDetalleRespuestaRandomSampleGenerator();

    pregunta.addDetalleRespuestas(detalleRespuestaBack);
    assertThat(pregunta.getDetalleRespuestas()).containsOnly(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getPregunta()).isEqualTo(pregunta);

    pregunta.removeDetalleRespuestas(detalleRespuestaBack);
    assertThat(pregunta.getDetalleRespuestas()).doesNotContain(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getPregunta()).isNull();

    pregunta.detalleRespuestas(new HashSet<>(Set.of(detalleRespuestaBack)));
    assertThat(pregunta.getDetalleRespuestas()).containsOnly(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getPregunta()).isEqualTo(pregunta);

    pregunta.setDetalleRespuestas(new HashSet<>());
    assertThat(pregunta.getDetalleRespuestas()).doesNotContain(detalleRespuestaBack);
    assertThat(detalleRespuestaBack.getPregunta()).isNull();
  }

  @Test
  void cuestionarioTest() {
    Pregunta pregunta = getPreguntaRandomSampleGenerator();
    Cuestionario cuestionarioBack = getCuestionarioRandomSampleGenerator();

    pregunta.setCuestionario(cuestionarioBack);
    assertThat(pregunta.getCuestionario()).isEqualTo(cuestionarioBack);

    pregunta.cuestionario(null);
    assertThat(pregunta.getCuestionario()).isNull();
  }
}
