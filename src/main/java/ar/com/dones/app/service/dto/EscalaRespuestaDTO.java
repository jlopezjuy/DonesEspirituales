package ar.com.dones.app.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.EscalaRespuesta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EscalaRespuestaDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer valor;

    @NotNull
    @Size(max = 100)
    private String etiqueta;

    @Lob
    private String descripcion;

    @NotNull
    @Min(value = 1)
    private Integer orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EscalaRespuestaDTO)) {
            return false;
        }

        EscalaRespuestaDTO escalaRespuestaDTO = (EscalaRespuestaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, escalaRespuestaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EscalaRespuestaDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", etiqueta='" + getEtiqueta() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", orden=" + getOrden() +
            "}";
    }
}
