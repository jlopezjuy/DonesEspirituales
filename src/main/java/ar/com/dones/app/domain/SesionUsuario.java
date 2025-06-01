package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SesionUsuario.
 */
@Entity
@Table(name = "sesion_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SesionUsuario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "respuestas_temporales")
  private String respuestasTemporales;

  @NotNull
  @Column(name = "fecha_creacion", nullable = false)
  private Instant fechaCreacion;

  @NotNull
  @Column(name = "fecha_expiracion", nullable = false)
  private Instant fechaExpiracion;

  @NotNull
  @Column(name = "completada", nullable = false)
  private Boolean completada;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnoreProperties(
    value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "user", "cuestionario" },
    allowSetters = true
  )
  private RespuestaUsuario respuestaUsuario;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public SesionUsuario id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRespuestasTemporales() {
    return this.respuestasTemporales;
  }

  public SesionUsuario respuestasTemporales(String respuestasTemporales) {
    this.setRespuestasTemporales(respuestasTemporales);
    return this;
  }

  public void setRespuestasTemporales(String respuestasTemporales) {
    this.respuestasTemporales = respuestasTemporales;
  }

  public Instant getFechaCreacion() {
    return this.fechaCreacion;
  }

  public SesionUsuario fechaCreacion(Instant fechaCreacion) {
    this.setFechaCreacion(fechaCreacion);
    return this;
  }

  public void setFechaCreacion(Instant fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Instant getFechaExpiracion() {
    return this.fechaExpiracion;
  }

  public SesionUsuario fechaExpiracion(Instant fechaExpiracion) {
    this.setFechaExpiracion(fechaExpiracion);
    return this;
  }

  public void setFechaExpiracion(Instant fechaExpiracion) {
    this.fechaExpiracion = fechaExpiracion;
  }

  public Boolean getCompletada() {
    return this.completada;
  }

  public SesionUsuario completada(Boolean completada) {
    this.setCompletada(completada);
    return this;
  }

  public void setCompletada(Boolean completada) {
    this.completada = completada;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public SesionUsuario user(User user) {
    this.setUser(user);
    return this;
  }

  public RespuestaUsuario getRespuestaUsuario() {
    return this.respuestaUsuario;
  }

  public void setRespuestaUsuario(RespuestaUsuario respuestaUsuario) {
    this.respuestaUsuario = respuestaUsuario;
  }

  public SesionUsuario respuestaUsuario(RespuestaUsuario respuestaUsuario) {
    this.setRespuestaUsuario(respuestaUsuario);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SesionUsuario)) {
      return false;
    }
    return getId() != null && getId().equals(((SesionUsuario) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "SesionUsuario{" +
            "id=" + getId() +
            ", respuestasTemporales='" + getRespuestasTemporales() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", completada='" + getCompletada() + "'" +
            "}";
    }
}
