package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.DonEspiritualTestSamples.*;
import static ar.com.dones.app.domain.PreguntaDonTestSamples.*;
import static ar.com.dones.app.domain.PreguntaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreguntaDonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaDon.class);
        PreguntaDon preguntaDon1 = getPreguntaDonSample1();
        PreguntaDon preguntaDon2 = new PreguntaDon();
        assertThat(preguntaDon1).isNotEqualTo(preguntaDon2);

        preguntaDon2.setId(preguntaDon1.getId());
        assertThat(preguntaDon1).isEqualTo(preguntaDon2);

        preguntaDon2 = getPreguntaDonSample2();
        assertThat(preguntaDon1).isNotEqualTo(preguntaDon2);
    }

    @Test
    void preguntaTest() {
        PreguntaDon preguntaDon = getPreguntaDonRandomSampleGenerator();
        Pregunta preguntaBack = getPreguntaRandomSampleGenerator();

        preguntaDon.setPregunta(preguntaBack);
        assertThat(preguntaDon.getPregunta()).isEqualTo(preguntaBack);

        preguntaDon.pregunta(null);
        assertThat(preguntaDon.getPregunta()).isNull();
    }

    @Test
    void donEspiritualTest() {
        PreguntaDon preguntaDon = getPreguntaDonRandomSampleGenerator();
        DonEspiritual donEspiritualBack = getDonEspiritualRandomSampleGenerator();

        preguntaDon.setDonEspiritual(donEspiritualBack);
        assertThat(preguntaDon.getDonEspiritual()).isEqualTo(donEspiritualBack);

        preguntaDon.donEspiritual(null);
        assertThat(preguntaDon.getDonEspiritual()).isNull();
    }
}
