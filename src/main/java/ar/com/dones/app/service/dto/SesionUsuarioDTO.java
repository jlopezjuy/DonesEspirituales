package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.SesionUsuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SesionUsuarioDTO implements Serializable {

  private Long id;

  private String respuestasTemporales;

  @NotNull
  private Instant fechaCreacion;

  @NotNull
  private Instant fechaExpiracion;

  @NotNull
  private Boolean completada;

  private UserDTO user;

  private RespuestaUsuarioDTO respuestaUsuario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRespuestasTemporales() {
    return respuestasTemporales;
  }

  public void setRespuestasTemporales(String respuestasTemporales) {
    this.respuestasTemporales = respuestasTemporales;
  }

  public Instant getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Instant getFechaExpiracion() {
    return fechaExpiracion;
  }

  public void setFechaExpiracion(Instant fechaExpiracion) {
    this.fechaExpiracion = fechaExpiracion;
  }

  public Boolean getCompletada() {
    return completada;
  }

  public void setCompletada(Boolean completada) {
    this.completada = completada;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
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
    if (!(o instanceof SesionUsuarioDTO)) {
      return false;
    }

    SesionUsuarioDTO sesionUsuarioDTO = (SesionUsuarioDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, sesionUsuarioDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "SesionUsuarioDTO{" +
            "id=" + getId() +
            ", respuestasTemporales='" + getRespuestasTemporales() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", completada='" + getCompletada() + "'" +
            ", user=" + getUser() +
            ", respuestaUsuario=" + getRespuestaUsuario() +
            "}";
    }
}
