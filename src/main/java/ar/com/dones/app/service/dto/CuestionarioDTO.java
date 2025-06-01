package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.Cuestionario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuestionarioDTO implements Serializable {

  private Long id;

  @NotNull
  @Size(max = 200)
  private String titulo;

  private String descripcion;

  @NotNull
  private String instrucciones;

  @NotNull
  @Min(value = 1)
  @Max(value = 1000)
  private Integer totalPreguntas;

  @NotNull
  private Boolean activo;

  @NotNull
  private Instant fechaCreacion;

  private Instant fechaActualizacion;

  @NotNull
  @Min(value = 1)
  private Integer version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getInstrucciones() {
    return instrucciones;
  }

  public void setInstrucciones(String instrucciones) {
    this.instrucciones = instrucciones;
  }

  public Integer getTotalPreguntas() {
    return totalPreguntas;
  }

  public void setTotalPreguntas(Integer totalPreguntas) {
    this.totalPreguntas = totalPreguntas;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Instant getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Instant getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Instant fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CuestionarioDTO)) {
      return false;
    }

    CuestionarioDTO cuestionarioDTO = (CuestionarioDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, cuestionarioDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CuestionarioDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", instrucciones='" + getInstrucciones() + "'" +
            ", totalPreguntas=" + getTotalPreguntas() +
            ", activo='" + getActivo() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
