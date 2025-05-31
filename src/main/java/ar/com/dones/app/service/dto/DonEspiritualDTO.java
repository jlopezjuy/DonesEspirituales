package ar.com.dones.app.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.DonEspiritual} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonEspiritualDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotNull
    @Size(max = 50)
    private String nombreCorto;

    @Lob
    private String descripcion;

    @Lob
    private String caracteristicas;

    @Lob
    private String versiculosBiblicos;

    @NotNull
    private Boolean activo;

    @Min(value = 1)
    private Integer ordenPresentacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getVersiculosBiblicos() {
        return versiculosBiblicos;
    }

    public void setVersiculosBiblicos(String versiculosBiblicos) {
        this.versiculosBiblicos = versiculosBiblicos;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getOrdenPresentacion() {
        return ordenPresentacion;
    }

    public void setOrdenPresentacion(Integer ordenPresentacion) {
        this.ordenPresentacion = ordenPresentacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonEspiritualDTO)) {
            return false;
        }

        DonEspiritualDTO donEspiritualDTO = (DonEspiritualDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donEspiritualDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonEspiritualDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", nombreCorto='" + getNombreCorto() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", caracteristicas='" + getCaracteristicas() + "'" +
            ", versiculosBiblicos='" + getVersiculosBiblicos() + "'" +
            ", activo='" + getActivo() + "'" +
            ", ordenPresentacion=" + getOrdenPresentacion() +
            "}";
    }
}
