package ar.com.dones.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PreguntaDonTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static PreguntaDon getPreguntaDonSample1() {
    return new PreguntaDon().id(1L).peso(1);
  }

  public static PreguntaDon getPreguntaDonSample2() {
    return new PreguntaDon().id(2L).peso(2);
  }

  public static PreguntaDon getPreguntaDonRandomSampleGenerator() {
    return new PreguntaDon().id(longCount.incrementAndGet()).peso(intCount.incrementAndGet());
  }
}
