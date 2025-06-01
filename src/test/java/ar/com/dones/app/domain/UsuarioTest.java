package ar.com.dones.app.domain;

import static ar.com.dones.app.domain.RespuestaUsuarioTestSamples.*;
import static ar.com.dones.app.domain.SesionUsuarioTestSamples.*;
import static ar.com.dones.app.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.dones.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = getUsuarioSample1();
        Usuario usuario2 = new Usuario();
        assertThat(usuario1).isNotEqualTo(usuario2);

        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);

        usuario2 = getUsuarioSample2();
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    void respuestasTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        RespuestaUsuario respuestaUsuarioBack = getRespuestaUsuarioRandomSampleGenerator();

        usuario.addRespuestas(respuestaUsuarioBack);
        assertThat(usuario.getRespuestas()).containsOnly(respuestaUsuarioBack);
        assertThat(respuestaUsuarioBack.getUsuario()).isEqualTo(usuario);

        usuario.removeRespuestas(respuestaUsuarioBack);
        assertThat(usuario.getRespuestas()).doesNotContain(respuestaUsuarioBack);
        assertThat(respuestaUsuarioBack.getUsuario()).isNull();

        usuario.respuestas(new HashSet<>(Set.of(respuestaUsuarioBack)));
        assertThat(usuario.getRespuestas()).containsOnly(respuestaUsuarioBack);
        assertThat(respuestaUsuarioBack.getUsuario()).isEqualTo(usuario);

        usuario.setRespuestas(new HashSet<>());
        assertThat(usuario.getRespuestas()).doesNotContain(respuestaUsuarioBack);
        assertThat(respuestaUsuarioBack.getUsuario()).isNull();
    }

    @Test
    void sesionesTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        SesionUsuario sesionUsuarioBack = getSesionUsuarioRandomSampleGenerator();

        usuario.addSesiones(sesionUsuarioBack);
        assertThat(usuario.getSesiones()).containsOnly(sesionUsuarioBack);
        assertThat(sesionUsuarioBack.getUsuario()).isEqualTo(usuario);

        usuario.removeSesiones(sesionUsuarioBack);
        assertThat(usuario.getSesiones()).doesNotContain(sesionUsuarioBack);
        assertThat(sesionUsuarioBack.getUsuario()).isNull();

        usuario.sesiones(new HashSet<>(Set.of(sesionUsuarioBack)));
        assertThat(usuario.getSesiones()).containsOnly(sesionUsuarioBack);
        assertThat(sesionUsuarioBack.getUsuario()).isEqualTo(usuario);

        usuario.setSesiones(new HashSet<>());
        assertThat(usuario.getSesiones()).doesNotContain(sesionUsuarioBack);
        assertThat(sesionUsuarioBack.getUsuario()).isNull();
    }
}
