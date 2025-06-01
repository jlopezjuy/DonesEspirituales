package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DonEspiritual.
 */
@Entity
@Table(name = "don_espiritual")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonEspiritual implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(max = 100)
  @Column(name = "nombre", length = 100, nullable = false, unique = true)
  private String nombre;

  @NotNull
  @Size(max = 50)
  @Column(name = "nombre_corto", length = 50, nullable = false, unique = true)
  private String nombreCorto;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "caracteristicas")
  private String caracteristicas;

  @Column(name = "versiculos_biblicos")
  private String versiculosBiblicos;

  @NotNull
  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @Min(value = 1)
  @Column(name = "orden_presentacion")
  private Integer ordenPresentacion;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "donEspiritual")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "pregunta", "donEspiritual" }, allowSetters = true)
  private Set<PreguntaDon> preguntaDones = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "donEspiritual")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "interpretacion", "respuestaUsuario", "donEspiritual" }, allowSetters = true)
  private Set<ResultadoDon> resultadoDones = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "donEspiritual")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "donEspiritual" }, allowSetters = true)
  private Set<Interpretacion> interpretaciones = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public DonEspiritual id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public DonEspiritual nombre(String nombre) {
    this.setNombre(nombre);
    return this;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombreCorto() {
    return this.nombreCorto;
  }

  public DonEspiritual nombreCorto(String nombreCorto) {
    this.setNombreCorto(nombreCorto);
    return this;
  }

  public void setNombreCorto(String nombreCorto) {
    this.nombreCorto = nombreCorto;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public DonEspiritual descripcion(String descripcion) {
    this.setDescripcion(descripcion);
    return this;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCaracteristicas() {
    return this.caracteristicas;
  }

  public DonEspiritual caracteristicas(String caracteristicas) {
    this.setCaracteristicas(caracteristicas);
    return this;
  }

  public void setCaracteristicas(String caracteristicas) {
    this.caracteristicas = caracteristicas;
  }

  public String getVersiculosBiblicos() {
    return this.versiculosBiblicos;
  }

  public DonEspiritual versiculosBiblicos(String versiculosBiblicos) {
    this.setVersiculosBiblicos(versiculosBiblicos);
    return this;
  }

  public void setVersiculosBiblicos(String versiculosBiblicos) {
    this.versiculosBiblicos = versiculosBiblicos;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public DonEspiritual activo(Boolean activo) {
    this.setActivo(activo);
    return this;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Integer getOrdenPresentacion() {
    return this.ordenPresentacion;
  }

  public DonEspiritual ordenPresentacion(Integer ordenPresentacion) {
    this.setOrdenPresentacion(ordenPresentacion);
    return this;
  }

  public void setOrdenPresentacion(Integer ordenPresentacion) {
    this.ordenPresentacion = ordenPresentacion;
  }

  public Set<PreguntaDon> getPreguntaDones() {
    return this.preguntaDones;
  }

  public void setPreguntaDones(Set<PreguntaDon> preguntaDons) {
    if (this.preguntaDones != null) {
      this.preguntaDones.forEach(i -> i.setDonEspiritual(null));
    }
    if (preguntaDons != null) {
      preguntaDons.forEach(i -> i.setDonEspiritual(this));
    }
    this.preguntaDones = preguntaDons;
  }

  public DonEspiritual preguntaDones(Set<PreguntaDon> preguntaDons) {
    this.setPreguntaDones(preguntaDons);
    return this;
  }

  public DonEspiritual addPreguntaDones(PreguntaDon preguntaDon) {
    this.preguntaDones.add(preguntaDon);
    preguntaDon.setDonEspiritual(this);
    return this;
  }

  public DonEspiritual removePreguntaDones(PreguntaDon preguntaDon) {
    this.preguntaDones.remove(preguntaDon);
    preguntaDon.setDonEspiritual(null);
    return this;
  }

  public Set<ResultadoDon> getResultadoDones() {
    return this.resultadoDones;
  }

  public void setResultadoDones(Set<ResultadoDon> resultadoDons) {
    if (this.resultadoDones != null) {
      this.resultadoDones.forEach(i -> i.setDonEspiritual(null));
    }
    if (resultadoDons != null) {
      resultadoDons.forEach(i -> i.setDonEspiritual(this));
    }
    this.resultadoDones = resultadoDons;
  }

  public DonEspiritual resultadoDones(Set<ResultadoDon> resultadoDons) {
    this.setResultadoDones(resultadoDons);
    return this;
  }

  public DonEspiritual addResultadoDones(ResultadoDon resultadoDon) {
    this.resultadoDones.add(resultadoDon);
    resultadoDon.setDonEspiritual(this);
    return this;
  }

  public DonEspiritual removeResultadoDones(ResultadoDon resultadoDon) {
    this.resultadoDones.remove(resultadoDon);
    resultadoDon.setDonEspiritual(null);
    return this;
  }

  public Set<Interpretacion> getInterpretaciones() {
    return this.interpretaciones;
  }

  public void setInterpretaciones(Set<Interpretacion> interpretacions) {
    if (this.interpretaciones != null) {
      this.interpretaciones.forEach(i -> i.setDonEspiritual(null));
    }
    if (interpretacions != null) {
      interpretacions.forEach(i -> i.setDonEspiritual(this));
    }
    this.interpretaciones = interpretacions;
  }

  public DonEspiritual interpretaciones(Set<Interpretacion> interpretacions) {
    this.setInterpretaciones(interpretacions);
    return this;
  }

  public DonEspiritual addInterpretaciones(Interpretacion interpretacion) {
    this.interpretaciones.add(interpretacion);
    interpretacion.setDonEspiritual(this);
    return this;
  }

  public DonEspiritual removeInterpretaciones(Interpretacion interpretacion) {
    this.interpretaciones.remove(interpretacion);
    interpretacion.setDonEspiritual(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DonEspiritual)) {
      return false;
    }
    return getId() != null && getId().equals(((DonEspiritual) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "DonEspiritual{" +
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
