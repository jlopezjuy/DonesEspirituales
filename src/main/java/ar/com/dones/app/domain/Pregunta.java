package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pregunta.
 */
@Entity
@Table(name = "pregunta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pregunta implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Min(value = 1)
  @Column(name = "numero_pregunta", nullable = false)
  private Integer numeroPregunta;

  @NotNull
  @Column(name = "texto_pregunta", nullable = false)
  private String textoPregunta;

  @NotNull
  @Column(name = "obligatoria", nullable = false)
  private Boolean obligatoria;

  @NotNull
  @Column(name = "activa", nullable = false)
  private Boolean activa;

  @NotNull
  @Column(name = "fecha_creacion", nullable = false)
  private Instant fechaCreacion;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pregunta")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "pregunta", "donEspiritual" }, allowSetters = true)
  private Set<PreguntaDon> preguntaDones = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pregunta")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "auditorias", "escalaRespuesta", "pregunta", "respuestaUsuario" }, allowSetters = true)
  private Set<DetalleRespuesta> detalleRespuestas = new HashSet<>();

  @ManyToOne(optional = false)
  @NotNull
  @JsonIgnoreProperties(value = { "preguntas", "respuestas" }, allowSetters = true)
  private Cuestionario cuestionario;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Pregunta id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumeroPregunta() {
    return this.numeroPregunta;
  }

  public Pregunta numeroPregunta(Integer numeroPregunta) {
    this.setNumeroPregunta(numeroPregunta);
    return this;
  }

  public void setNumeroPregunta(Integer numeroPregunta) {
    this.numeroPregunta = numeroPregunta;
  }

  public String getTextoPregunta() {
    return this.textoPregunta;
  }

  public Pregunta textoPregunta(String textoPregunta) {
    this.setTextoPregunta(textoPregunta);
    return this;
  }

  public void setTextoPregunta(String textoPregunta) {
    this.textoPregunta = textoPregunta;
  }

  public Boolean getObligatoria() {
    return this.obligatoria;
  }

  public Pregunta obligatoria(Boolean obligatoria) {
    this.setObligatoria(obligatoria);
    return this;
  }

  public void setObligatoria(Boolean obligatoria) {
    this.obligatoria = obligatoria;
  }

  public Boolean getActiva() {
    return this.activa;
  }

  public Pregunta activa(Boolean activa) {
    this.setActiva(activa);
    return this;
  }

  public void setActiva(Boolean activa) {
    this.activa = activa;
  }

  public Instant getFechaCreacion() {
    return this.fechaCreacion;
  }

  public Pregunta fechaCreacion(Instant fechaCreacion) {
    this.setFechaCreacion(fechaCreacion);
    return this;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Set<PreguntaDon> getPreguntaDones() {
    return this.preguntaDones;
  }

  public void setPreguntaDones(Set<PreguntaDon> preguntaDons) {
    if (this.preguntaDones != null) {
      this.preguntaDones.forEach(i -> i.setPregunta(null));
    }
    if (preguntaDons != null) {
      preguntaDons.forEach(i -> i.setPregunta(this));
    }
    this.preguntaDones = preguntaDons;
  }

  public Pregunta preguntaDones(Set<PreguntaDon> preguntaDons) {
    this.setPreguntaDones(preguntaDons);
    return this;
  }

  public Pregunta addPreguntaDones(PreguntaDon preguntaDon) {
    this.preguntaDones.add(preguntaDon);
    preguntaDon.setPregunta(this);
    return this;
  }

  public Pregunta removePreguntaDones(PreguntaDon preguntaDon) {
    this.preguntaDones.remove(preguntaDon);
    preguntaDon.setPregunta(null);
    return this;
  }

  public Set<DetalleRespuesta> getDetalleRespuestas() {
    return this.detalleRespuestas;
  }

  public void setDetalleRespuestas(Set<DetalleRespuesta> detalleRespuestas) {
    if (this.detalleRespuestas != null) {
      this.detalleRespuestas.forEach(i -> i.setPregunta(null));
    }
    if (detalleRespuestas != null) {
      detalleRespuestas.forEach(i -> i.setPregunta(this));
    }
    this.detalleRespuestas = detalleRespuestas;
  }

  public Pregunta detalleRespuestas(Set<DetalleRespuesta> detalleRespuestas) {
    this.setDetalleRespuestas(detalleRespuestas);
    return this;
  }

  public Pregunta addDetalleRespuestas(DetalleRespuesta detalleRespuesta) {
    this.detalleRespuestas.add(detalleRespuesta);
    detalleRespuesta.setPregunta(this);
    return this;
  }

  public Pregunta removeDetalleRespuestas(DetalleRespuesta detalleRespuesta) {
    this.detalleRespuestas.remove(detalleRespuesta);
    detalleRespuesta.setPregunta(null);
    return this;
  }

  public Cuestionario getCuestionario() {
    return this.cuestionario;
  }

  public void setCuestionario(Cuestionario cuestionario) {
    this.cuestionario = cuestionario;
  }

  public Pregunta cuestionario(Cuestionario cuestionario) {
    this.setCuestionario(cuestionario);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pregunta)) {
      return false;
    }
    return getId() != null && getId().equals(((Pregunta) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Pregunta{" +
            "id=" + getId() +
            ", numeroPregunta=" + getNumeroPregunta() +
            ", textoPregunta='" + getTextoPregunta() + "'" +
            ", obligatoria='" + getObligatoria() + "'" +
            ", activa='" + getActiva() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
