package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.DetalleRespuesta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleRespuestaDTO implements Serializable {

  private Long id;

  @NotNull
  @Min(value = 0)
  @Max(value = 10)
  private Integer valorRespuesta;

  @NotNull
  private Instant timestampRespuesta;

  @Min(value = 0)
  private Integer tiempoPreguntaSegundos;

  @NotNull
  private EscalaRespuestaDTO escalaRespuesta;

  @NotNull
  private PreguntaDTO pregunta;

  @NotNull
  private RespuestaUsuarioDTO respuestaUsuario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getValorRespuesta() {
    return valorRespuesta;
  }

  public void setValorRespuesta(Integer valorRespuesta) {
    this.valorRespuesta = valorRespuesta;
  }

  public Instant getTimestampRespuesta() {
    return timestampRespuesta;
  }

  public void setTimestampRespuesta(Instant timestampRespuesta) {
    this.timestampRespuesta = timestampRespuesta;
  }

  public Integer getTiempoPreguntaSegundos() {
    return tiempoPreguntaSegundos;
  }

  public void setTiempoPreguntaSegundos(Integer tiempoPreguntaSegundos) {
    this.tiempoPreguntaSegundos = tiempoPreguntaSegundos;
  }

  public EscalaRespuestaDTO getEscalaRespuesta() {
    return escalaRespuesta;
  }

  public void setEscalaRespuesta(EscalaRespuestaDTO escalaRespuesta) {
    this.escalaRespuesta = escalaRespuesta;
  }

  public PreguntaDTO getPregunta() {
    return pregunta;
  }

  public void setPregunta(PreguntaDTO pregunta) {
    this.pregunta = pregunta;
  }

  public RespuestaUsuarioDTO getRespuestaUsuario() {
    return respuestaUsuario;
  }

  public void setRespuestaUsuario(RespuestaUsuarioDTO respuestaUsuario) {
    this.respuestaUsuario = respuestaUsuario;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DetalleRespuestaDTO)) {
      return false;
    }

    DetalleRespuestaDTO detalleRespuestaDTO = (DetalleRespuestaDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, detalleRespuestaDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "DetalleRespuestaDTO{" +
            "id=" + getId() +
            ", valorRespuesta=" + getValorRespuesta() +
            ", timestampRespuesta='" + getTimestampRespuesta() + "'" +
            ", tiempoPreguntaSegundos=" + getTiempoPreguntaSegundos() +
            ", escalaRespuesta=" + getEscalaRespuesta() +
            ", pregunta=" + getPregunta() +
            ", respuestaUsuario=" + getRespuestaUsuario() +
            "}";
    }
}
