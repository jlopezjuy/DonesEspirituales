package ar.com.dones.app.domain;

import ar.com.dones.app.domain.enumeration.NivelInterpretacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Interpretacion.
 */
@Entity
@Table(name = "interpretacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interpretacion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Min(value = 0)
  @Column(name = "puntuacion_minima", nullable = false)
  private Integer puntuacionMinima;

  @NotNull
  @Min(value = 0)
  @Column(name = "puntuacion_maxima", nullable = false)
  private Integer puntuacionMaxima;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "nivel", nullable = false)
  private NivelInterpretacion nivel;

  @NotNull
  @Column(name = "descripcion_nivel", nullable = false)
  private String descripcionNivel;

  @Column(name = "recomendaciones")
  private String recomendaciones;

  @Column(name = "areas_servicio")
  private String areasServicio;

  @ManyToOne(optional = false)
  @NotNull
  @JsonIgnoreProperties(value = { "preguntaDones", "resultadoDones", "interpretaciones" }, allowSetters = true)
  private DonEspiritual donEspiritual;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Interpretacion id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getPuntuacionMinima() {
    return this.puntuacionMinima;
  }

  public Interpretacion puntuacionMinima(Integer puntuacionMinima) {
    this.setPuntuacionMinima(puntuacionMinima);
    return this;
  }

  public void setPuntuacionMinima(Integer puntuacionMinima) {
    this.puntuacionMinima = puntuacionMinima;
  }

  public Integer getPuntuacionMaxima() {
    return this.puntuacionMaxima;
  }

  public Interpretacion puntuacionMaxima(Integer puntuacionMaxima) {
    this.setPuntuacionMaxima(puntuacionMaxima);
    return this;
  }

  public void setPuntuacionMaxima(Integer puntuacionMaxima) {
    this.puntuacionMaxima = puntuacionMaxima;
  }

  public NivelInterpretacion getNivel() {
    return this.nivel;
  }

  public Interpretacion nivel(NivelInterpretacion nivel) {
    this.setNivel(nivel);
    return this;
  }

  public void setNivel(NivelInterpretacion nivel) {
    this.nivel = nivel;
  }

  public String getDescripcionNivel() {
    return this.descripcionNivel;
  }

  public Interpretacion descripcionNivel(String descripcionNivel) {
    this.setDescripcionNivel(descripcionNivel);
    return this;
  }

  public void setDescripcionNivel(String descripcionNivel) {
    this.descripcionNivel = descripcionNivel;
  }

  public String getRecomendaciones() {
    return this.recomendaciones;
  }

  public Interpretacion recomendaciones(String recomendaciones) {
    this.setRecomendaciones(recomendaciones);
    return this;
  }

  public void setRecomendaciones(String recomendaciones) {
    this.recomendaciones = recomendaciones;
  }

  public String getAreasServicio() {
    return this.areasServicio;
  }

  public Interpretacion areasServicio(String areasServicio) {
    this.setAreasServicio(areasServicio);
    return this;
  }

  public void setAreasServicio(String areasServicio) {
    this.areasServicio = areasServicio;
  }

  public DonEspiritual getDonEspiritual() {
    return this.donEspiritual;
  }

  public void setDonEspiritual(DonEspiritual donEspiritual) {
    this.donEspiritual = donEspiritual;
  }

  public Interpretacion donEspiritual(DonEspiritual donEspiritual) {
    this.setDonEspiritual(donEspiritual);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Interpretacion)) {
      return false;
    }
    return getId() != null && getId().equals(((Interpretacion) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Interpretacion{" +
            "id=" + getId() +
            ", puntuacionMinima=" + getPuntuacionMinima() +
            ", puntuacionMaxima=" + getPuntuacionMaxima() +
            ", nivel='" + getNivel() + "'" +
            ", descripcionNivel='" + getDescripcionNivel() + "'" +
            ", recomendaciones='" + getRecomendaciones() + "'" +
            ", areasServicio='" + getAreasServicio() + "'" +
            "}";
    }
}
