package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.DonEspiritualTestSamples.*;
import static ar.com.dones.app.domain.InterpretacionTestSamples.*;
import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static ar.com.dones.app.domain.ResultadoDonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultadoDonTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ResultadoDon.class);
    ResultadoDon resultadoDon1 = getResultadoDonSample1();
    ResultadoDon resultadoDon2 = new ResultadoDon();
    assertThat(resultadoDon1).isNotEqualTo(resultadoDon2);

    resultadoDon2.setId(resultadoDon1.getId());
    assertThat(resultadoDon1).isEqualTo(resultadoDon2);

    resultadoDon2 = getResultadoDonSample2();
    assertThat(resultadoDon1).isNotEqualTo(resultadoDon2);
  }

  @Test
  void interpretacionTest() {
    ResultadoDon resultadoDon = getResultadoDonRandomSampleGenerator();
    Interpretacion interpretacionBack = getInterpretacionRandomSampleGenerator();

    resultadoDon.setInterpretacion(interpretacionBack);
    assertThat(resultadoDon.getInterpretacion()).isEqualTo(interpretacionBack);

    resultadoDon.interpretacion(null);
    assertThat(resultadoDon.getInterpretacion()).isNull();
  }

  @Test
  void respuestaUsuarioTest() {
    ResultadoDon resultadoDon = getResultadoDonRandomSampleGenerator();
    RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

    resultadoDon.setRespuestaUsuario(respuestaUsuarioBack);
    assertThat(resultadoDon.getRespuestaUsuario()).isEqualTo(respuestaUsuarioBack);

    resultadoDon.respuestaUsuario(null);
    assertThat(resultadoDon.getRespuestaUsuario()).isNull();
  }

  @Test
  void donEspiritualTest() {
    ResultadoDon resultadoDon = getResultadoDonRandomSampleGenerator();
    DonEspiritual donEspiritualBack = getDonEspiritualRandomSampleGenerator();

    resultadoDon.setDonEspiritual(donEspiritualBack);
    assertThat(resultadoDon.getDonEspiritual()).isEqualTo(donEspiritualBack);

    resultadoDon.donEspiritual(null);
    assertThat(resultadoDon.getDonEspiritual()).isNull();
  }
}
