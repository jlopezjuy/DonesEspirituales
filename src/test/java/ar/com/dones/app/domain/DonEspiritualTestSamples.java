package ar.com.dones.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DonEspiritualTestSamples {

  private static final Random random = new Random();
  private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
  private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

  public static DonEspiritual getDonEspiritualSample1() {
    return new DonEspiritual()
      .id(1L)
      .nombre("nombre1")
      .nombreCorto("nombreCorto1")
      .descripcion("descripcion1")
      .caracteristicas("caracteristicas1")
      .versiculosBiblicos("versiculosBiblicos1")
      .ordenPresentacion(1);
  }

  public static DonEspiritual getDonEspiritualSample2() {
    return new DonEspiritual()
      .id(2L)
      .nombre("nombre2")
      .nombreCorto("nombreCorto2")
      .descripcion("descripcion2")
      .caracteristicas("caracteristicas2")
      .versiculosBiblicos("versiculosBiblicos2")
      .ordenPresentacion(2);
  }

  public static DonEspiritual getDonEspiritualRandomSampleGenerator() {
    return new DonEspiritual()
      .id(longCount.incrementAndGet())
      .nombre(UUID.randomUUID().toString())
      .nombreCorto(UUID.randomUUID().toString())
      .descripcion(UUID.randomUUID().toString())
      .caracteristicas(UUID.randomUUID().toString())
      .versiculosBiblicos(UUID.randomUUID().toString())
      .ordenPresentacion(intCount.incrementAndGet());
  }
}
