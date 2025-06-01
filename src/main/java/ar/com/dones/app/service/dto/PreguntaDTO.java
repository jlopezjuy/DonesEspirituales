package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.Pregunta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreguntaDTO implements Serializable {

  private Long id;

  @NotNull
  @Min(value = 1)
  private Integer numeroPregunta;

  @NotNull
  private String textoPregunta;

  @NotNull
  private Boolean obligatoria;

  @NotNull
  private Boolean activa;

  @NotNull
  private Instant fechaCreacion;

  @NotNull
  private CuestionarioDTO cuestionario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumeroPregunta() {
    return numeroPregunta;
  }

  public void setNumeroPregunta(Integer numeroPregunta) {
    this.numeroPregunta = numeroPregunta;
  }

  public String getTextoPregunta() {
    return textoPregunta;
  }

  public void setTextoPregunta(String textoPregunta) {
    this.textoPregunta = textoPregunta;
  }

  public Boolean getObligatoria() {
    return obligatoria;
  }

  public void setObligatoria(Boolean obligatoria) {
    this.obligatoria = obligatoria;
  }

  public Boolean getActiva() {
    return activa;
  }

  public void setActiva(Boolean activa) {
    this.activa = activa;
  }

  public Instant getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public CuestionarioDTO getCuestionario() {
    return cuestionario;
  }

  public void setCuestionario(CuestionarioDTO cuestionario) {
    this.cuestionario = cuestionario;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PreguntaDTO)) {
      return false;
    }

    PreguntaDTO preguntaDTO = (PreguntaDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, preguntaDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaDTO{" +
            "id=" + getId() +
            ", numeroPregunta=" + getNumeroPregunta() +
            ", textoPregunta='" + getTextoPregunta() + "'" +
            ", obligatoria='" + getObligatoria() + "'" +
            ", activa='" + getActiva() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", cuestionario=" + getCuestionario() +
            "}";
    }
}
