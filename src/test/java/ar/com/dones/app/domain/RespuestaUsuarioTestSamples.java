package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RespuestaUsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RespuestaUsuario getRespuestaUsuarioSample1() {
        return new RespuestaUsuario().id(1L).tiempoTotalSegundos(1).ipAddress("ipAddress1").userAgent("userAgent1");
    }

    public static RespuestaUsuario getRespuestaUsuarioSample2() {
        return new RespuestaUsuario().id(2L).tiempoTotalSegundos(2).ipAddress("ipAddress2").userAgent("userAgent2");
    }

    public static RespuestaUsuario getRespuestaUsuarioRandomSampleGenerator() {
        return new RespuestaUsuario()
            .id(longCount.incrementAndGet())
            .tiempoTotalSegundos(intCount.incrementAndGet())
            .ipAddress(UUID.randomUUID().toString())
            .userAgent(UUID.randomUUID().toString());
    }
}
