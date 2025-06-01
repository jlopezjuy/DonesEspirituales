package ar.com.dones.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DetalleRespuestaTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static DetalleRespuesta getDetalleRespuestaSample1() {
    return new DetalleRespuesta().id(1L).valorRespuesta(1).tiempoPreguntaSegundos(1);
  }

  public static DetalleRespuesta getDetalleRespuestaSample2() {
    return new DetalleRespuesta().id(2L).valorRespuesta(2).tiempoPreguntaSegundos(2);
  }

  public static DetalleRespuesta getDetalleRespuestaRandomSampleGenerator() {
    return new DetalleRespuesta()
      .id(longCount.incrementAndGet())
      .valorRespuesta(intCount.incrementAndGet())
      .tiempoPreguntaSegundos(intCount.incrementAndGet());
  }
}
