package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.DonEspiritualTestSamples.*;
import static ar.com.dones.app.domain.InterpretacionTestSamples.*;
import static ar.com.dones.app.domain.PreguntaDonTestSamples.*;
import static ar.com.dones.app.domain.ResultadoDonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DonEspiritualTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(DonEspiritual.class);
    DonEspiritual donEspiritual1 = getDonEspiritualSample1();
    DonEspiritual donEspiritual2 = new DonEspiritual();
    assertThat(donEspiritual1).isNotEqualTo(donEspiritual2);

    donEspiritual2.setId(donEspiritual1.getId());
    assertThat(donEspiritual1).isEqualTo(donEspiritual2);

    donEspiritual2 = getDonEspiritualSample2();
    assertThat(donEspiritual1).isNotEqualTo(donEspiritual2);
  }

  @Test
  void preguntaDonesTest() {
    DonEspiritual donEspiritual = getDonEspiritualRandomSampleGenerator();
    PreguntaDon preguntaDonBack = getPreguntaDonRandomSampleGenerator();

    donEspiritual.addPreguntaDones(preguntaDonBack);
    assertThat(donEspiritual.getPreguntaDones()).containsOnly(preguntaDonBack);
    assertThat(preguntaDonBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.removePreguntaDones(preguntaDonBack);
    assertThat(donEspiritual.getPreguntaDones()).doesNotContain(preguntaDonBack);
    assertThat(preguntaDonBack.getDonEspiritual()).isNull();

    donEspiritual.preguntaDones(new HashSet<>(Set.of(preguntaDonBack)));
    assertThat(donEspiritual.getPreguntaDones()).containsOnly(preguntaDonBack);
    assertThat(preguntaDonBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.setPreguntaDones(new HashSet<>());
    assertThat(donEspiritual.getPreguntaDones()).doesNotContain(preguntaDonBack);
    assertThat(preguntaDonBack.getDonEspiritual()).isNull();
  }

  @Test
  void resultadoDonesTest() {
    DonEspiritual donEspiritual = getDonEspiritualRandomSampleGenerator();
    ResultadoDon resultadoDonBack = getResultadoDonRandomSampleGenerator();

    donEspiritual.addResultadoDones(resultadoDonBack);
    assertThat(donEspiritual.getResultadoDones()).containsOnly(resultadoDonBack);
    assertThat(resultadoDonBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.removeResultadoDones(resultadoDonBack);
    assertThat(donEspiritual.getResultadoDones()).doesNotContain(resultadoDonBack);
    assertThat(resultadoDonBack.getDonEspiritual()).isNull();

    donEspiritual.resultadoDones(new HashSet<>(Set.of(resultadoDonBack)));
    assertThat(donEspiritual.getResultadoDones()).containsOnly(resultadoDonBack);
    assertThat(resultadoDonBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.setResultadoDones(new HashSet<>());
    assertThat(donEspiritual.getResultadoDones()).doesNotContain(resultadoDonBack);
    assertThat(resultadoDonBack.getDonEspiritual()).isNull();
  }

  @Test
  void interpretacionesTest() {
    DonEspiritual donEspiritual = getDonEspiritualRandomSampleGenerator();
    Interpretacion interpretacionBack = getInterpretacionRandomSampleGenerator();

    donEspiritual.addInterpretaciones(interpretacionBack);
    assertThat(donEspiritual.getInterpretaciones()).containsOnly(interpretacionBack);
    assertThat(interpretacionBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.removeInterpretaciones(interpretacionBack);
    assertThat(donEspiritual.getInterpretaciones()).doesNotContain(interpretacionBack);
    assertThat(interpretacionBack.getDonEspiritual()).isNull();

    donEspiritual.interpretaciones(new HashSet<>(Set.of(interpretacionBack)));
    assertThat(donEspiritual.getInterpretaciones()).containsOnly(interpretacionBack);
    assertThat(interpretacionBack.getDonEspiritual()).isEqualTo(donEspiritual);

    donEspiritual.setInterpretaciones(new HashSet<>());
    assertThat(donEspiritual.getInterpretaciones()).doesNotContain(interpretacionBack);
    assertThat(interpretacionBack.getDonEspiritual()).isNull();
  }
}
