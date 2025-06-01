package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EscalaRespuestaTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static EscalaRespuesta getEscalaRespuestaSample1() {
    return new EscalaRespuesta().id(1L).valor(1).etiqueta("etiqueta1").descripcion("descripcion1").orden(1);
  }

  public static EscalaRespuesta getEscalaRespuestaSample2() {
    return new EscalaRespuesta().id(2L).valor(2).etiqueta("etiqueta2").descripcion("descripcion2").orden(2);
  }

  public static EscalaRespuesta getEscalaRespuestaRandomSampleGenerator() {
    return new EscalaRespuesta()
      .id(longCount.incrementAndGet())
      .valor(intCount.incrementAndGet())
      .etiqueta(UUID.randomUUID().toString())
      .descripcion(UUID.randomUUID().toString())
      .orden(intCount.incrementAndGet());
  }
}
