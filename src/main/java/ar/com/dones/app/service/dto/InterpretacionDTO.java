package ar.com.dones.app.service.dto;

import ar.com.dones.app.domain.enumeration.NivelInterpretacion;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.Interpretacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterpretacionDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer puntuacionMinima;

    @NotNull
    @Min(value = 0)
    private Integer puntuacionMaxima;

    @NotNull
    private NivelInterpretacion nivel;

    @Lob
    private String descripcionNivel;

    @Lob
    private String recomendaciones;

    @Lob
    private String areasServicio;

    @NotNull
    private DonEspiritualDTO donEspiritual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntuacionMinima() {
        return puntuacionMinima;
    }

    public void setPuntuacionMinima(Integer puntuacionMinima) {
        this.puntuacionMinima = puntuacionMinima;
    }

    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    public NivelInterpretacion getNivel() {
        return nivel;
    }

    public void setNivel(NivelInterpretacion nivel) {
        this.nivel = nivel;
    }

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getAreasServicio() {
        return areasServicio;
    }

    public void setAreasServicio(String areasServicio) {
        this.areasServicio = areasServicio;
    }

    public DonEspiritualDTO getDonEspiritual() {
        return donEspiritual;
    }

    public void setDonEspiritual(DonEspiritualDTO donEspiritual) {
        this.donEspiritual = donEspiritual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterpretacionDTO)) {
            return false;
        }

        InterpretacionDTO interpretacionDTO = (InterpretacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, interpretacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterpretacionDTO{" +
            "id=" + getId() +
            ", puntuacionMinima=" + getPuntuacionMinima() +
            ", puntuacionMaxima=" + getPuntuacionMaxima() +
            ", nivel='" + getNivel() + "'" +
            ", descripcionNivel='" + getDescripcionNivel() + "'" +
            ", recomendaciones='" + getRecomendaciones() + "'" +
            ", areasServicio='" + getAreasServicio() + "'" +
            ", donEspiritual=" + getDonEspiritual() +
            "}";
    }
}
