package ar.com.dones.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.dones.app.domain.PreguntaDon} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreguntaDonDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer peso;

    @NotNull
    private Boolean activa;

    @NotNull
    private PreguntaDTO pregunta;

    @NotNull
    private DonEspiritualDTO donEspiritual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public PreguntaDTO getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaDTO pregunta) {
        this.pregunta = pregunta;
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
        if (!(o instanceof PreguntaDonDTO)) {
            return false;
        }

        PreguntaDonDTO preguntaDonDTO = (PreguntaDonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, preguntaDonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaDonDTO{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", activa='" + getActiva() + "'" +
            ", pregunta=" + getPregunta() +
            ", donEspiritual=" + getDonEspiritual() +
            "}";
    }
}
