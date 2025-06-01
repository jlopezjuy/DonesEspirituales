package ar.com.dones.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ResultadoDonTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static ResultadoDon getResultadoDonSample1() {
    return new ResultadoDon().id(1L).puntuacionTotal(1).rankingPosicion(1);
  }

  public static ResultadoDon getResultadoDonSample2() {
    return new ResultadoDon().id(2L).puntuacionTotal(2).rankingPosicion(2);
  }

  public static ResultadoDon getResultadoDonRandomSampleGenerator() {
    return new ResultadoDon()
      .id(longCount.incrementAndGet())
      .puntuacionTotal(intCount.incrementAndGet())
      .rankingPosicion(intCount.incrementAndGet());
  }
}
