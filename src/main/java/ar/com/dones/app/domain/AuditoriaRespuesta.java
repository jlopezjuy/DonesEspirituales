package ar.com.dones.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuditoriaRespuesta.
 */
@Entity
@Table(name = "auditoria_respuesta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditoriaRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "valor_anterior")
    private Integer valorAnterior;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "valor_nuevo", nullable = false)
    private Integer valorNuevo;

    @NotNull
    @Column(name = "timestamp_cambio", nullable = false)
    private Instant timestampCambio;

    @Size(max = 500)
    @Column(name = "motivo_cambio", length = 500)
    private String motivoCambio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "usuario", "cuestionario" },
        allowSetters = true
    )
    private RespuestaUsuario respuestaUsuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "auditorias", "escalaRespuesta", "pregunta", "respuestaUsuario" }, allowSetters = true)
    private DetalleRespuesta detalleRespuesta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuditoriaRespuesta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValorAnterior() {
        return this.valorAnterior;
    }

    public AuditoriaRespuesta valorAnterior(Integer valorAnterior) {
        this.setValorAnterior(valorAnterior);
        return this;
    }

    public void setValorAnterior(Integer valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public Integer getValorNuevo() {
        return this.valorNuevo;
    }

    public AuditoriaRespuesta valorNuevo(Integer valorNuevo) {
        this.setValorNuevo(valorNuevo);
        return this;
    }

    public void setValorNuevo(Integer valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public Instant getTimestampCambio() {
        return this.timestampCambio;
    }

    public AuditoriaRespuesta timestampCambio(Instant timestampCambio) {
        this.setTimestampCambio(timestampCambio);
        return this;
    }

    public void setTimestampCambio(Instant timestampCambio) {
        this.timestampCambio = timestampCambio;
    }

    public String getMotivoCambio() {
        return this.motivoCambio;
    }

    public AuditoriaRespuesta motivoCambio(String motivoCambio) {
        this.setMotivoCambio(motivoCambio);
        return this;
    }

    public void setMotivoCambio(String motivoCambio) {
        this.motivoCambio = motivoCambio;
    }

    public RespuestaUsuario getRespuestaUsuario() {
        return this.respuestaUsuario;
    }

    public void setRespuestaUsuario(RespuestaUsuario respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public AuditoriaRespuesta respuestaUsuario(RespuestaUsuario respuestaUsuario) {
        this.setRespuestaUsuario(respuestaUsuario);
        return this;
    }

    public DetalleRespuesta getDetalleRespuesta() {
        return this.detalleRespuesta;
    }

    public void setDetalleRespuesta(DetalleRespuesta detalleRespuesta) {
        this.detalleRespuesta = detalleRespuesta;
    }

    public AuditoriaRespuesta detalleRespuesta(DetalleRespuesta detalleRespuesta) {
        this.setDetalleRespuesta(detalleRespuesta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditoriaRespuesta)) {
            return false;
        }
        return getId() != null && getId().equals(((AuditoriaRespuesta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditoriaRespuesta{" +
            "id=" + getId() +
            ", valorAnterior=" + getValorAnterior() +
            ", valorNuevo=" + getValorNuevo() +
            ", timestampCambio='" + getTimestampCambio() + "'" +
            ", motivoCambio='" + getMotivoCambio() + "'" +
            "}";
    }
}
