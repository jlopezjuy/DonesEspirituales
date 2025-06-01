package ar.com.dones.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PreguntaTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static Pregunta getPreguntaSample1() {
    return new Pregunta().id(1L).numeroPregunta(1);
  }

  public static Pregunta getPreguntaSample2() {
    return new Pregunta().id(2L).numeroPregunta(2);
  }

  public static Pregunta getPreguntaRandomSampleGenerator() {
    return new Pregunta().id(longCount.incrementAndGet()).numeroPregunta(intCount.incrementAndGet());
  }
}
