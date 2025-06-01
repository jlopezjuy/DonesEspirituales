package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SesionUsuarioTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  public static SesionUsuario getSesionUsuarioSample1() {
    return new SesionUsuario().id(1L).respuestasTemporales("respuestasTemporales1");
  }

  public static SesionUsuario getSesionUsuarioSample2() {
    return new SesionUsuario().id(2L).respuestasTemporales("respuestasTemporales2");
  }

  public static SesionUsuario getSesionUsuarioRandomSampleGenerator() {
    return new SesionUsuario().id(longCount.incrementAndGet()).respuestasTemporales(UUID.randomUUID().toString());
  }
}
