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
 * A DetalleRespuesta.
 */
@Entity
@Table(name = "detalle_respuesta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalleRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "valor_respuesta", nullable = false)
    private Integer valorRespuesta;

    @NotNull
    @Column(name = "timestamp_respuesta", nullable = false)
    private Instant timestampRespuesta;

    @Min(value = 0)
    @Column(name = "tiempo_pregunta_segundos")
    private Integer tiempoPreguntaSegundos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "detalleRespuesta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "respuestaUsuario", "detalleRespuesta" }, allowSetters = true)
    private Set<AuditoriaRespuesta> auditorias = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private EscalaRespuesta escalaRespuesta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "preguntaDones", "detalleRespuestas", "cuestionario" }, allowSetters = true)
    private Pregunta pregunta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "detalleRespuestas", "resultadoDones", "sesiones", "auditorias", "usuario", "cuestionario" },
        allowSetters = true
    )
    private RespuestaUsuario respuestaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetalleRespuesta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValorRespuesta() {
        return this.valorRespuesta;
    }

    public DetalleRespuesta valorRespuesta(Integer valorRespuesta) {
        this.setValorRespuesta(valorRespuesta);
        return this;
    }

    public void setValorRespuesta(Integer valorRespuesta) {
        this.valorRespuesta = valorRespuesta;
    }

    public Instant getTimestampRespuesta() {
        return this.timestampRespuesta;
    }

    public DetalleRespuesta timestampRespuesta(Instant timestampRespuesta) {
        this.setTimestampRespuesta(timestampRespuesta);
        return this;
    }

    public void setTimestampRespuesta(Instant timestampRespuesta) {
        this.timestampRespuesta = timestampRespuesta;
    }

    public Integer getTiempoPreguntaSegundos() {
        return this.tiempoPreguntaSegundos;
    }

    public DetalleRespuesta tiempoPreguntaSegundos(Integer tiempoPreguntaSegundos) {
        this.setTiempoPreguntaSegundos(tiempoPreguntaSegundos);
        return this;
    }

    public void setTiempoPreguntaSegundos(Integer tiempoPreguntaSegundos) {
        this.tiempoPreguntaSegundos = tiempoPreguntaSegundos;
    }

    public Set<AuditoriaRespuesta> getAuditorias() {
        return this.auditorias;
    }

    public void setAuditorias(Set<AuditoriaRespuesta> auditoriaRespuestas) {
        if (this.auditorias != null) {
            this.auditorias.forEach(i -> i.setDetalleRespuesta(null));
        }
        if (auditoriaRespuestas != null) {
            auditoriaRespuestas.forEach(i -> i.setDetalleRespuesta(this));
        }
        this.auditorias = auditoriaRespuestas;
    }

    public DetalleRespuesta auditorias(Set<AuditoriaRespuesta> auditoriaRespuestas) {
        this.setAuditorias(auditoriaRespuestas);
        return this;
    }

    public DetalleRespuesta addAuditorias(AuditoriaRespuesta auditoriaRespuesta) {
        this.auditorias.add(auditoriaRespuesta);
        auditoriaRespuesta.setDetalleRespuesta(this);
        return this;
    }

    public DetalleRespuesta removeAuditorias(AuditoriaRespuesta auditoriaRespuesta) {
        this.auditorias.remove(auditoriaRespuesta);
        auditoriaRespuesta.setDetalleRespuesta(null);
        return this;
    }

    public EscalaRespuesta getEscalaRespuesta() {
        return this.escalaRespuesta;
    }

    public void setEscalaRespuesta(EscalaRespuesta escalaRespuesta) {
        this.escalaRespuesta = escalaRespuesta;
    }

    public DetalleRespuesta escalaRespuesta(EscalaRespuesta escalaRespuesta) {
        this.setEscalaRespuesta(escalaRespuesta);
        return this;
    }

    public Pregunta getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public DetalleRespuesta pregunta(Pregunta pregunta) {
        this.setPregunta(pregunta);
        return this;
    }

    public RespuestaUsuario getRespuestaUsuario() {
        return this.respuestaUsuario;
    }

    public void setRespuestaUsuario(RespuestaUsuario respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
    }

    public DetalleRespuesta respuestaUsuario(RespuestaUsuario respuestaUsuario) {
        this.setRespuestaUsuario(respuestaUsuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleRespuesta)) {
            return false;
        }
        return getId() != null && getId().equals(((DetalleRespuesta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalleRespuesta{" +
            "id=" + getId() +
            ", valorRespuesta=" + getValorRespuesta() +
            ", timestampRespuesta='" + getTimestampRespuesta() + "'" +
            ", tiempoPreguntaSegundos=" + getTiempoPreguntaSegundos() +
            "}";
    }
}
