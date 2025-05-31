package ar.com.dones.app.domain;

import ar.com.dones.app.domain.enumeration.TipoDato;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConfiguracionSistema.
 */
@Entity
@Table(name = "configuracion_sistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfiguracionSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "clave", length = 100, nullable = false, unique = true)
    private String clave;

    @NotNull
    @Size(max = 1000)
    @Column(name = "valor", length = 1000, nullable = false)
    private String valor;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dato", nullable = false)
    private TipoDato tipoDato;

    @NotNull
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConfiguracionSistema id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return this.clave;
    }

    public ConfiguracionSistema clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return this.valor;
    }

    public ConfiguracionSistema valor(String valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public ConfiguracionSistema descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoDato getTipoDato() {
        return this.tipoDato;
    }

    public ConfiguracionSistema tipoDato(TipoDato tipoDato) {
        this.setTipoDato(tipoDato);
        return this;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Instant getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public ConfiguracionSistema fechaActualizacion(Instant fechaActualizacion) {
        this.setFechaActualizacion(fechaActualizacion);
        return this;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfiguracionSistema)) {
            return false;
        }
        return getId() != null && getId().equals(((ConfiguracionSistema) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfiguracionSistema{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", valor='" + getValor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoDato='" + getTipoDato() + "'" +
            ", fechaActualizacion='" + getFechaActualizacion() + "'" +
            "}";
    }
}
