package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class InterpretacionTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static Interpretacion getInterpretacionSample1() {
    return new Interpretacion()
      .id(1L)
      .puntuacionMinima(1)
      .puntuacionMaxima(1)
      .descripcionNivel("descripcionNivel1")
      .recomendaciones("recomendaciones1")
      .areasServicio("areasServicio1");
  }

  public static Interpretacion getInterpretacionSample2() {
    return new Interpretacion()
      .id(2L)
      .puntuacionMinima(2)
      .puntuacionMaxima(2)
      .descripcionNivel("descripcionNivel2")
      .recomendaciones("recomendaciones2")
      .areasServicio("areasServicio2");
  }

  public static Interpretacion getInterpretacionRandomSampleGenerator() {
    return new Interpretacion()
      .id(longCount.incrementAndGet())
      .puntuacionMinima(intCount.incrementAndGet())
      .puntuacionMaxima(intCount.incrementAndGet())
      .descripcionNivel(UUID.randomUUID().toString())
      .recomendaciones(UUID.randomUUID().toString())
      .areasServicio(UUID.randomUUID().toString());
  }
}
