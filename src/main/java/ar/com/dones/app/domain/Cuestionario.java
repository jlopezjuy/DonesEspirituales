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
 * A Cuestionario.
 */
@Entity
@Table(name = "cuestionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cuestionario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(max = 200)
  @Column(name = "titulo", length = 200, nullable = false)
  private String titulo;

  @Column(name = "descripcion")
  private String descripcion;

  @NotNull
  @Column(name = "instrucciones", nullable = false)
  private String instrucciones;

  @NotNull
  @Min(value = 1)
  @Max(value = 1000)
  @Column(name = "total_preguntas", nullable = false)
  private Integer totalPreguntas;

  @NotNull
  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @NotNull
  @Column(name = "fecha_creacion", nullable = false)
  private Instant fechaCreacion;

  @Column(name = "fecha_actualizacion")
  private Instant fechaActualizacion;

  @NotNull
  @Min(value = 1)
  @Column(name = "version", nullable = false)
  private Integer version;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuestionario")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "preguntaDones", "detalleRespuestas", "cuestionario" }, allowSetters = true)
  private Set<Pregunta> preguntas = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuestionario")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(
    value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "user", "cuestionario" },
    allowSetters = true
  )
  private Set<RespuestaUsuario> respuestas = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Cuestionario id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public Cuestionario titulo(String titulo) {
    this.setTitulo(titulo);
    return this;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public Cuestionario descripcion(String descripcion) {
    this.setDescripcion(descripcion);
    return this;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getInstrucciones() {
    return this.instrucciones;
  }

  public Cuestionario instrucciones(String instrucciones) {
    this.setInstrucciones(instrucciones);
    return this;
  }

  public void setInstrucciones(String instrucciones) {
    this.instrucciones = instrucciones;
  }

  public Integer getTotalPreguntas() {
    return this.totalPreguntas;
  }

  public Cuestionario totalPreguntas(Integer totalPreguntas) {
    this.setTotalPreguntas(totalPreguntas);
    return this;
  }

  public void setTotalPreguntas(Integer totalPreguntas) {
    this.totalPreguntas = totalPreguntas;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public Cuestionario activo(Boolean activo) {
    this.setActivo(activo);
    return this;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Instant getFechaCreacion() {
    return this.fechaCreacion;
  }

  public Cuestionario fechaCreacion(Instant fechaCreacion) {
    this.setFechaCreacion(fechaCreacion);
    return this;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Instant getFechaActualizacion() {
    return this.fechaActualizacion;
  }

  public Cuestionario fechaActualizacion(Instant fechaActualizacion) {
    this.setFechaActualizacion(fechaActualizacion);
    return this;
  }

  public void setFechaActualizacion(Instant fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public Integer getVersion() {
    return this.version;
  }

  public Cuestionario version(Integer version) {
    this.setVersion(version);
    return this;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Set<Pregunta> getPreguntas() {
    return this.preguntas;
  }

  public void setPreguntas(Set<Pregunta> preguntas) {
    if (this.preguntas != null) {
      this.preguntas.forEach(i -> i.setCuestionario(null));
    }
    if (preguntas != null) {
      preguntas.forEach(i -> i.setCuestionario(this));
    }
    this.preguntas = preguntas;
  }

  public Cuestionario preguntas(Set<Pregunta> preguntas) {
    this.setPreguntas(preguntas);
    return this;
  }

  public Cuestionario addPreguntas(Pregunta pregunta) {
    this.preguntas.add(pregunta);
    pregunta.setCuestionario(this);
    return this;
  }

  public Cuestionario removePreguntas(Pregunta pregunta) {
    this.preguntas.remove(pregunta);
    pregunta.setCuestionario(null);
    return this;
  }

  public Set<RespuestaUsuario> getRespuestas() {
    return this.respuestas;
  }

  public void setRespuestas(Set<RespuestaUsuario> respuestaUsuarios) {
    if (this.respuestas != null) {
      this.respuestas.forEach(i -> i.setCuestionario(null));
    }
    if (respuestaUsuarios != null) {
      respuestaUsuarios.forEach(i -> i.setCuestionario(this));
    }
    this.respuestas = respuestaUsuarios;
  }

  public Cuestionario respuestas(Set<RespuestaUsuario> respuestaUsuarios) {
    this.setRespuestas(respuestaUsuarios);
    return this;
  }

  public Cuestionario addRespuestas(RespuestaUsuario respuestaUsuario) {
    this.respuestas.add(respuestaUsuario);
    respuestaUsuario.setCuestionario(this);
    return this;
  }

  public Cuestionario removeRespuestas(RespuestaUsuario respuestaUsuario) {
    this.respuestas.remove(respuestaUsuario);
    respuestaUsuario.setCuestionario(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Cuestionario)) {
      return false;
    }
    return getId() != null && getId().equals(((Cuestionario) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Cuestionario{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", instrucciones='" + getInstrucciones() + "'" +
            ", totalPreguntas=" + getTotalPreguntas() +
            ", activo='" + getActivo() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
