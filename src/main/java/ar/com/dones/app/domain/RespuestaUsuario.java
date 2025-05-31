package ar.com.dones.app.domain;

import ar.com.dones.app.domain.enumeration.EstadoRespuesta;
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
 * A RespuestaUsuario.
 */
@Entity
@Table(name = "respuesta_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RespuestaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Instant fechaInicio;

    @Column(name = "fecha_completado")
    private Instant fechaCompletado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoRespuesta estado;

    @Min(value = 0)
    @Column(name = "tiempo_total_segundos")
    private Integer tiempoTotalSegundos;

    @Size(max = 45)
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Size(max = 500)
    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "respuestaUsuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "auditorias", "escalaRespuesta", "pregunta", "respuestaUsuario" }, allowSetters = true)
    private Set<DetalleRespuesta> detalleRespuestas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "respuestaUsuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "interpretacion", "respuestaUsuario", "donEspiritual" }, allowSetters = true)
    private Set<ResultadoDon> resultadoDones = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "respuestaUsuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuario", "respuestaUsuario" }, allowSetters = true)
    private Set<SesionUsuario> sesiones = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "respuestaUsuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "respuestaUsuario", "detalleRespuesta" }, allowSetters = true)
    private Set<AuditoriaRespuesta> auditorias = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "respuestas", "sesiones" }, allowSetters = true)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "preguntas", "respuestas" }, allowSetters = true)
    private Cuestionario cuestionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RespuestaUsuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaInicio() {
        return this.fechaInicio;
    }

    public RespuestaUsuario fechaInicio(Instant fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaCompletado() {
        return this.fechaCompletado;
    }

    public RespuestaUsuario fechaCompletado(Instant fechaCompletado) {
        this.setFechaCompletado(fechaCompletado);
        return this;
    }

    public void setFechaCompletado(Instant fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public EstadoRespuesta getEstado() {
        return this.estado;
    }

    public RespuestaUsuario estado(EstadoRespuesta estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoRespuesta estado) {
        this.estado = estado;
    }

    public Integer getTiempoTotalSegundos() {
        return this.tiempoTotalSegundos;
    }

    public RespuestaUsuario tiempoTotalSegundos(Integer tiempoTotalSegundos) {
        this.setTiempoTotalSegundos(tiempoTotalSegundos);
        return this;
    }

    public void setTiempoTotalSegundos(Integer tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public RespuestaUsuario ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public RespuestaUsuario userAgent(String userAgent) {
        this.setUserAgent(userAgent);
        return this;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Set<DetalleRespuesta> getDetalleRespuestas() {
        return this.detalleRespuestas;
    }

    public void setDetalleRespuestas(Set<DetalleRespuesta> detalleRespuestas) {
        if (this.detalleRespuestas != null) {
            this.detalleRespuestas.forEach(i -> i.setRespuestaUsuario(null));
        }
        if (detalleRespuestas != null) {
            detalleRespuestas.forEach(i -> i.setRespuestaUsuario(this));
        }
        this.detalleRespuestas = detalleRespuestas;
    }

    public RespuestaUsuario detalleRespuestas(Set<DetalleRespuesta> detalleRespuestas) {
        this.setDetalleRespuestas(detalleRespuestas);
        return this;
    }

    public RespuestaUsuario addDetalleRespuestas(DetalleRespuesta detalleRespuesta) {
        this.detalleRespuestas.add(detalleRespuesta);
        detalleRespuesta.setRespuestaUsuario(this);
        return this;
    }

    public RespuestaUsuario removeDetalleRespuestas(DetalleRespuesta detalleRespuesta) {
        this.detalleRespuestas.remove(detalleRespuesta);
        detalleRespuesta.setRespuestaUsuario(null);
        return this;
    }

    public Set<ResultadoDon> getResultadoDones() {
        return this.resultadoDones;
    }

    public void setResultadoDones(Set<ResultadoDon> resultadoDons) {
        if (this.resultadoDones != null) {
            this.resultadoDones.forEach(i -> i.setRespuestaUsuario(null));
        }
        if (resultadoDons != null) {
            resultadoDons.forEach(i -> i.setRespuestaUsuario(this));
        }
        this.resultadoDones = resultadoDons;
    }

    public RespuestaUsuario resultadoDones(Set<ResultadoDon> resultadoDons) {
        this.setResultadoDones(resultadoDons);
        return this;
    }

    public RespuestaUsuario addResultadoDones(ResultadoDon resultadoDon) {
        this.resultadoDones.add(resultadoDon);
        resultadoDon.setRespuestaUsuario(this);
        return this;
    }

    public RespuestaUsuario removeResultadoDones(ResultadoDon resultadoDon) {
        this.resultadoDones.remove(resultadoDon);
        resultadoDon.setRespuestaUsuario(null);
        return this;
    }

    public Set<SesionUsuario> getSesiones() {
        return this.sesiones;
    }

    public void setSesiones(Set<SesionUsuario> sesionUsuarios) {
        if (this.sesiones != null) {
            this.sesiones.forEach(i -> i.setRespuestaUsuario(null));
        }
        if (sesionUsuarios != null) {
            sesionUsuarios.forEach(i -> i.setRespuestaUsuario(this));
        }
        this.sesiones = sesionUsuarios;
    }

    public RespuestaUsuario sesiones(Set<SesionUsuario> sesionUsuarios) {
        this.setSesiones(sesionUsuarios);
        return this;
    }

    public RespuestaUsuario addSesiones(SesionUsuario sesionUsuario) {
        this.sesiones.add(sesionUsuario);
        sesionUsuario.setRespuestaUsuario(this);
        return this;
    }

    public RespuestaUsuario removeSesiones(SesionUsuario sesionUsuario) {
        this.sesiones.remove(sesionUsuario);
        sesionUsuario.setRespuestaUsuario(null);
        return this;
    }

    public Set<AuditoriaRespuesta> getAuditorias() {
        return this.auditorias;
    }

    public void setAuditorias(Set<AuditoriaRespuesta> auditoriaRespuestas) {
        if (this.auditorias != null) {
            this.auditorias.forEach(i -> i.setRespuestaUsuario(null));
        }
        if (auditoriaRespuestas != null) {
            auditoriaRespuestas.forEach(i -> i.setRespuestaUsuario(this));
        }
        this.auditorias = auditoriaRespuestas;
    }

    public RespuestaUsuario auditorias(Set<AuditoriaRespuesta> auditoriaRespuestas) {
        this.setAuditorias(auditoriaRespuestas);
        return this;
    }

    public RespuestaUsuario addAuditorias(AuditoriaRespuesta auditoriaRespuesta) {
        this.auditorias.add(auditoriaRespuesta);
        auditoriaRespuesta.setRespuestaUsuario(this);
        return this;
    }

    public RespuestaUsuario removeAuditorias(AuditoriaRespuesta auditoriaRespuesta) {
        this.auditorias.remove(auditoriaRespuesta);
        auditoriaRespuesta.setRespuestaUsuario(null);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RespuestaUsuario usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public Cuestionario getCuestionario() {
        return this.cuestionario;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    public RespuestaUsuario cuestionario(Cuestionario cuestionario) {
        this.setCuestionario(cuestionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RespuestaUsuario)) {
            return false;
        }
        return getId() != null && getId().equals(((RespuestaUsuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RespuestaUsuario{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaCompletado='" + getFechaCompletado() + "'" +
            ", estado='" + getEstado() + "'" +
            ", tiempoTotalSegundos=" + getTiempoTotalSegundos() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", userAgent='" + getUserAgent() + "'" +
            "}";
    }
}
