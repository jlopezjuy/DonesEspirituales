package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PreguntaDon.
 */
@Entity
@Table(name = "pregunta_don")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PreguntaDon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "peso", nullable = false)
    private Integer peso;

    @NotNull
    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "preguntaDones", "detalleRespuestas", "cuestionario" }, allowSetters = true)
    private Pregunta pregunta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "preguntaDones", "resultadoDones", "interpretaciones" }, allowSetters = true)
    private DonEspiritual donEspiritual;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PreguntaDon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeso() {
        return this.peso;
    }

    public PreguntaDon peso(Integer peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Boolean getActiva() {
        return this.activa;
    }

    public PreguntaDon activa(Boolean activa) {
        this.setActiva(activa);
        return this;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Pregunta getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public PreguntaDon pregunta(Pregunta pregunta) {
        this.setPregunta(pregunta);
        return this;
    }

    public DonEspiritual getDonEspiritual() {
        return this.donEspiritual;
    }

    public void setDonEspiritual(DonEspiritual donEspiritual) {
        this.donEspiritual = donEspiritual;
    }

    public PreguntaDon donEspiritual(DonEspiritual donEspiritual) {
        this.setDonEspiritual(donEspiritual);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaDon)) {
            return false;
        }
        return getId() != null && getId().equals(((PreguntaDon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaDon{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", activa='" + getActiva() + "'" +
            "}";
    }
}
