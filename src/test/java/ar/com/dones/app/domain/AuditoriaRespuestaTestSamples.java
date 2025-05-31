package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AuditoriaRespuestaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AuditoriaRespuesta getAuditoriaRespuestaSample1() {
        return new AuditoriaRespuesta().id(1L).valorAnterior(1).valorNuevo(1).motivoCambio("motivoCambio1");
    }

    public static AuditoriaRespuesta getAuditoriaRespuestaSample2() {
        return new AuditoriaRespuesta().id(2L).valorAnterior(2).valorNuevo(2).motivoCambio("motivoCambio2");
    }

    public static AuditoriaRespuesta getAuditoriaRespuestaRandomSampleGenerator() {
        return new AuditoriaRespuesta()
            .id(longCount.incrementAndGet())
            .valorAnterior(intCount.incrementAndGet())
            .valorNuevo(intCount.incrementAndGet())
            .motivoCambio(UUID.randomUUID().toString());
    }
}
