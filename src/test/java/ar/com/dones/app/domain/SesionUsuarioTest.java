package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static ar.com.dones.app.domain.SesionUsuarioTestSamples.*;
import static ar.com.dones.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SesionUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SesionUsuario.class);
        SesionUsuario sesionUsuario1 = getSesionUsuarioSample1();
        SesionUsuario sesionUsuario2 = new SesionUsuario();
        assertThat(sesionUsuario1).isNotEqualTo(sesionUsuario2);

        sesionUsuario2.setId(sesionUsuario1.getId());
        assertThat(sesionUsuario1).isEqualTo(sesionUsuario2);

        sesionUsuario2 = getSesionUsuarioSample2();
        assertThat(sesionUsuario1).isNotEqualTo(sesionUsuario2);
    }

    @Test
    void usuarioTest() {
        SesionUsuario sesionUsuario = getSesionUsuarioRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        sesionUsuario.setUsuario(usuarioBack);
        assertThat(sesionUsuario.getUsuario()).isEqualTo(usuarioBack);

        sesionUsuario.usuario(null);
        assertThat(sesionUsuario.getUsuario()).isNull();
    }

    @Test
    void respuestaUsuarioTest() {
        SesionUsuario sesionUsuario = getSesionUsuarioRandomSampleGenerator();
        RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

        sesionUsuario.setRespuestaUsuario(respuestaUsuarioBack);
        assertThat(sesionUsuario.getRespuestaUsuario()).isEqualTo(respuestaUsuarioBack);

        sesionUsuario.respuestaUsuario(null);
        assertThat(sesionUsuario.getRespuestaUsuario()).isNull();
    }
}
