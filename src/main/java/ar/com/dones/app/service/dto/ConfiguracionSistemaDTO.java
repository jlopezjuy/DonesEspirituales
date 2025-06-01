package ar.com.dones.app.service.dto;

import ar.com.dones.app.domain.enumeration.TipoDato;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.ConfiguracionSistema} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfiguracionSistemaDTO implements Serializable {

  private Long id;

  @NotNull
  @Size(max = 100)
  private String clave;

  @NotNull
  @Size(max = 1000)
  private String valor;

  private String descripcion;

  @NotNull
  private TipoDato tipoDato;

  @NotNull
  private Instant fechaActualizacion;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public TipoDato getTipoDato() {
    return tipoDato;
  }

  public void setTipoDato(TipoDato tipoDato) {
    this.tipoDato = tipoDato;
  }

  public Instant getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Instant fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConfiguracionSistemaDTO)) {
      return false;
    }

    ConfiguracionSistemaDTO configuracionSistemaDTO = (ConfiguracionSistemaDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, configuracionSistemaDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfiguracionSistemaDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoDato='" + getTipoDato() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            "}";
    }
}
