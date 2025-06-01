package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultadoDonDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(ResultadoDonDTO.class);
    ResultadoDonDTO resultadoDonDTO1 = new ResultadoDonDTO();
    resultadoDonDTO1.setId(1L);
    ResultadoDonDTO resultadoDonDTO2 = new ResultadoDonDTO();
    assertThat(resultadoDonDTO1).isNotEqualTo(resultadoDonDTO2);
    resultadoDonDTO2.setId(resultadoDonDTO1.getId());
    assertThat(resultadoDonDTO1).isEqualTo(resultadoDonDTO2);
    resultadoDonDTO2.setId(2L);
    assertThat(resultadoDonDTO1).isNotEqualTo(resultadoDonDTO2);
    resultadoDonDTO1.setId(null);
    assertThat(resultadoDonDTO1).isNotEqualTo(resultadoDonDTO2);
  }
}
