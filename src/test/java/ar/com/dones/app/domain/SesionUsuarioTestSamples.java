package ar.com.dones.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SesionUsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SesionUsuario getSesionUsuarioSample1() {
        return new SesionUsuario().id(1L);
    }

    public static SesionUsuario getSesionUsuarioSample2() {
        return new SesionUsuario().id(2L);
    }

    public static SesionUsuario getSesionUsuarioRandomSampleGenerator() {
        return new SesionUsuario().id(longCount.incrementAndGet());
    }
}
