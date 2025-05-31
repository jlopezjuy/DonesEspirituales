package ar.com.dones.app.service.dto;

import ar.com.dones.app.domain.enumeration.EstadoRespuesta;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.RespuestaUsuario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RespuestaUsuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fechaInicio;

    private Instant fechaCompletado;

    @NotNull
    private EstadoRespuesta estado;

    @Min(value = 0)
    private Integer tiempoTotalSegundos;

    @Size(max = 45)
    private String ipAddress;

    @Size(max = 500)
    private String userAgent;

    @NotNull
    private UsuarioDTO usuario;

    @NotNull
    private CuestionarioDTO cuestionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(Instant fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public EstadoRespuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoRespuesta estado) {
        this.estado = estado;
    }

    public Integer getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(Integer tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
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
        if (!(o instanceof RespuestaUsuarioDTO)) {
            return false;
        }

        RespuestaUsuarioDTO respuestaUsuarioDTO = (RespuestaUsuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, respuestaUsuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RespuestaUsuarioDTO{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaCompletado='" + getFechaCompletado() + "'" +
            ", estado='" + getEstado() + "'" +
            ", tiempoTotalSegundos=" + getTiempoTotalSegundos() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            ", usuario=" + getUsuario() +
            ", cuestionario=" + getCuestionario() +
            "}";
    }
}
