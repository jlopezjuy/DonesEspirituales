package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.ConfiguracionSistemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfiguracionSistemaTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ConfiguracionSistema.class);
    ConfiguracionSistema configuracionSistema1 = getConfiguracionSistemaSample1();
    ConfiguracionSistema configuracionSistema2 = new ConfiguracionSistema();
    assertThat(configuracionSistema1).isNotEqualTo(configuracionSistema2);

    configuracionSistema2.setId(configuracionSistema1.getId());
    assertThat(configuracionSistema1).isEqualTo(configuracionSistema2);

    configuracionSistema2 = getConfiguracionSistemaSample2();
    assertThat(configuracionSistema1).isNotEqualTo(configuracionSistema2);
  }
}
