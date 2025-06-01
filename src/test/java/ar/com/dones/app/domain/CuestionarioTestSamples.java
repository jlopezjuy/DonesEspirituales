package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CuestionarioTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static Cuestionario getCuestionarioSample1() {
    return new Cuestionario().id(1L).titulo("titulo1").totalPreguntas(1).version(1);
  }

  public static Cuestionario getCuestionarioSample2() {
    return new Cuestionario().id(2L).titulo("titulo2").totalPreguntas(2).version(2);
  }

  public static Cuestionario getCuestionarioRandomSampleGenerator() {
    return new Cuestionario()
      .id(longCount.incrementAndGet())
      .titulo(UUID.randomUUID().toString())
      .totalPreguntas(intCount.incrementAndGet())
      .version(intCount.incrementAndGet());
  }
}
