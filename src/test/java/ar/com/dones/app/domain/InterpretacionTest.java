package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.DonEspiritualTestSamples.*;
import static ar.com.dones.app.domain.InterpretacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterpretacionTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Interpretacion.class);
    Interpretacion interpretacion1 = getInterpretacionSample1();
    Interpretacion interpretacion2 = new Interpretacion();
    assertThat(interpretacion1).isNotEqualTo(interpretacion2);

    interpretacion2.setId(interpretacion1.getId());
    assertThat(interpretacion1).isEqualTo(interpretacion2);

    interpretacion2 = getInterpretacionSample2();
    assertThat(interpretacion1).isNotEqualTo(interpretacion2);
  }

  @Test
  void donEspiritualTest() {
    Interpretacion interpretacion = getInterpretacionRandomSampleGenerator();
    DonEspiritual donEspiritualBack = getDonEspiritualRandomSampleGenerator();

    interpretacion.setDonEspiritual(donEspiritualBack);
    assertThat(interpretacion.getDonEspiritual()).isEqualTo(donEspiritualBack);

    interpretacion.donEspiritual(null);
    assertThat(interpretacion.getDonEspiritual()).isNull();
  }
}
