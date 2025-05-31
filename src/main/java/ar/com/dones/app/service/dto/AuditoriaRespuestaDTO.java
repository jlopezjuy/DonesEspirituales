package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.AuditoriaRespuesta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditoriaRespuestaDTO implements Serializable {

    private Long id;

    @Min(value = 0)
    @Max(value = 10)
    private Integer valorAnterior;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer valorNuevo;

    @NotNull
    private Instant timestampCambio;

    @Size(max = 500)
    private String motivoCambio;

    @NotNull
    private RespuestaUsuarioDTO respuestaUsuario;

    @NotNull
    private DetalleRespuestaDTO detalleRespuesta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(Integer valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public Integer getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(Integer valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public Instant getTimestampCambio() {
        return timestampCambio;
    }

    public void setTimestampCambio(Instant timestampCambio) {
        this.timestampCambio = timestampCambio;
    }

    public String getMotivoCambio() {
        return motivoCambio;
    }

    public void setMotivoCambio(String motivoCambio) {
        this.motivoCambio = motivoCambio;
    }

    public RespuestaUsuarioDTO getRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(RespuestaUsuarioDTO respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public DetalleRespuestaDTO getDetalleRespuesta() {
        return detalleRespuesta;
    }

    public void setDetalleRespuesta(DetalleRespuestaDTO detalleRespuesta) {
        this.detalleRespuesta = detalleRespuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditoriaRespuestaDTO)) {
            return false;
        }

        AuditoriaRespuestaDTO auditoriaRespuestaDTO = (AuditoriaRespuestaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditoriaRespuestaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditoriaRespuestaDTO{" +
            "id=" + getId() +
            ", valorAnterior=" + getValorAnterior() +
            ", valorNuevo=" + getValorNuevo() +
            ", timestampCambio='" + getTimestampCambio() + "'" +
            ", motivoCambio='" + getMotivoCambio() + "'" +
            ", respuestaUsuario=" + getRespuestaUsuario() +
            ", detalleRespuesta=" + getDetalleRespuesta() +
            "}";
    }
}
