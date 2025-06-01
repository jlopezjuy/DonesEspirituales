package ar.com.dones.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EscalaRespuesta.
 */
@Entity
@Table(name = "escala_respuesta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EscalaRespuesta implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @NotNull
  @Min(value = 0)
  @Max(value = 10)
  @Column(name = "valor", nullable = false)
  private Integer valor;

  @NotNull
  @Size(max = 100)
  @Column(name = "etiqueta", length = 100, nullable = false)
  private String etiqueta;

  @Column(name = "descripcion")
  private String descripcion;

  @NotNull
  @Min(value = 1)
  @Column(name = "orden", nullable = false)
  private Integer orden;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public EscalaRespuesta id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getValor() {
    return this.valor;
  }

  public EscalaRespuesta valor(Integer valor) {
    this.setValor(valor);
    return this;
  }

  public void setValor(Integer valor) {
    this.valor = valor;
  }

  public String getEtiqueta() {
    return this.etiqueta;
  }

  public EscalaRespuesta etiqueta(String etiqueta) {
    this.setEtiqueta(etiqueta);
    return this;
  }

  public void setEtiqueta(String etiqueta) {
    this.etiqueta = etiqueta;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public EscalaRespuesta descripcion(String descripcion) {
    this.setDescripcion(descripcion);
    return this;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getOrden() {
    return this.orden;
  }

  public EscalaRespuesta orden(Integer orden) {
    this.setOrden(orden);
    return this;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EscalaRespuesta)) {
      return false;
    }
    return getId() != null && getId().equals(((EscalaRespuesta) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "EscalaRespuesta{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", etiqueta='" + getEtiqueta() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", orden=" + getOrden() +
            "}";
    }
}
