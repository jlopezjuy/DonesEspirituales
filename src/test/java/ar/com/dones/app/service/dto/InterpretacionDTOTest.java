package ar.com.dones.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterpretacionDTOTest {

  @Test
  void dtoEqualsVerifier() throws Exception {
    TestUtil.equalsVerifier(InterpretacionDTO.class);
    InterpretacionDTO interpretacionDTO1 = new InterpretacionDTO();
    interpretacionDTO1.setId(1L);
    InterpretacionDTO interpretacionDTO2 = new InterpretacionDTO();
    assertThat(interpretacionDTO1).isNotEqualTo(interpretacionDTO2);
    interpretacionDTO2.setId(interpretacionDTO1.getId());
    assertThat(interpretacionDTO1).isEqualTo(interpretacionDTO2);
    interpretacionDTO2.setId(2L);
    assertThat(interpretacionDTO1).isNotEqualTo(interpretacionDTO2);
    interpretacionDTO1.setId(null);
    assertThat(interpretacionDTO1).isNotEqualTo(interpretacionDTO2);
  }
}
