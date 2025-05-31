package ar.com.dones.app.service.criteria;

import ar.com.dones.app.domain.enumeration.EstadoRespuesta;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.com.dones.app.domain.RespuestaUsuario} entity. This class is used
 * in {@link ar.com.dones.app.web.rest.RespuestaUsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /respuesta-usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RespuestaUsuarioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoRespuesta
     */
    public static class EstadoRespuestaFilter extends Filter<EstadoRespuesta> {

        public EstadoRespuestaFilter() {}

        public EstadoRespuestaFilter(EstadoRespuestaFilter filter) {
            super(filter);
        }

        @Override
        public EstadoRespuestaFilter copy() {
            return new EstadoRespuestaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fechaInicio;

    private InstantFilter fechaCompletado;

    private EstadoRespuestaFilter estado;

    private IntegerFilter tiempoTotalSegundos;

    private StringFilter ipAddress;

    private StringFilter userAgent;

    private LongFilter detalleRespuestasId;

    private LongFilter resultadoDonesId;

    private LongFilter sesionesId;

    private LongFilter auditoriasId;

    private LongFilter usuarioId;

    private LongFilter cuestionarioId;

    private Boolean distinct;

    public RespuestaUsuarioCriteria() {}

    public RespuestaUsuarioCriteria(RespuestaUsuarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fechaInicio = other.optionalFechaInicio().map(InstantFilter::copy).orElse(null);
        this.fechaCompletado = other.optionalFechaCompletado().map(InstantFilter::copy).orElse(null);
        this.estado = other.optionalEstado().map(EstadoRespuestaFilter::copy).orElse(null);
        this.tiempoTotalSegundos = other.optionalTiempoTotalSegundos().map(IntegerFilter::copy).orElse(null);
        this.ipAddress = other.optionalIpAddress().map(StringFilter::copy).orElse(null);
        this.userAgent = other.optionalUserAgent().map(StringFilter::copy).orElse(null);
        this.detalleRespuestasId = other.optionalDetalleRespuestasId().map(LongFilter::copy).orElse(null);
        this.resultadoDonesId = other.optionalResultadoDonesId().map(LongFilter::copy).orElse(null);
        this.sesionesId = other.optionalSesionesId().map(LongFilter::copy).orElse(null);
        this.auditoriasId = other.optionalAuditoriasId().map(LongFilter::copy).orElse(null);
        this.usuarioId = other.optionalUsuarioId().map(LongFilter::copy).orElse(null);
        this.cuestionarioId = other.optionalCuestionarioId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public RespuestaUsuarioCriteria copy() {
        return new RespuestaUsuarioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFechaInicio() {
        return fechaInicio;
    }

    public Optional<InstantFilter> optionalFechaInicio() {
        return Optional.ofNullable(fechaInicio);
    }

    public InstantFilter fechaInicio() {
        if (fechaInicio == null) {
            setFechaInicio(new InstantFilter());
        }
        return fechaInicio;
    }

    public void setFechaInicio(InstantFilter fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public InstantFilter getFechaCompletado() {
        return fechaCompletado;
    }

    public Optional<InstantFilter> optionalFechaCompletado() {
        return Optional.ofNullable(fechaCompletado);
    }

    public InstantFilter fechaCompletado() {
        if (fechaCompletado == null) {
            setFechaCompletado(new InstantFilter());
        }
        return fechaCompletado;
    }

    public void setFechaCompletado(InstantFilter fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public EstadoRespuestaFilter getEstado() {
        return estado;
    }

    public Optional<EstadoRespuestaFilter> optionalEstado() {
        return Optional.ofNullable(estado);
    }

    public EstadoRespuestaFilter estado() {
        if (estado == null) {
            setEstado(new EstadoRespuestaFilter());
        }
        return estado;
    }

    public void setEstado(EstadoRespuestaFilter estado) {
        this.estado = estado;
    }

    public IntegerFilter getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public Optional<IntegerFilter> optionalTiempoTotalSegundos() {
        return Optional.ofNullable(tiempoTotalSegundos);
    }

    public IntegerFilter tiempoTotalSegundos() {
        if (tiempoTotalSegundos == null) {
            setTiempoTotalSegundos(new IntegerFilter());
        }
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(IntegerFilter tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }

    public StringFilter getIpAddress() {
        return ipAddress;
    }

    public Optional<StringFilter> optionalIpAddress() {
        return Optional.ofNullable(ipAddress);
    }

    public StringFilter ipAddress() {
        if (ipAddress == null) {
            setIpAddress(new StringFilter());
        }
        return ipAddress;
    }

    public void setIpAddress(StringFilter ipAddress) {
        this.ipAddress = ipAddress;
    }

    public StringFilter getUserAgent() {
        return userAgent;
    }

    public Optional<StringFilter> optionalUserAgent() {
        return Optional.ofNullable(userAgent);
    }

    public StringFilter userAgent() {
        if (userAgent == null) {
            setUserAgent(new StringFilter());
        }
        return userAgent;
    }

    public void setUserAgent(StringFilter userAgent) {
        this.userAgent = userAgent;
    }

    public LongFilter getDetalleRespuestasId() {
        return detalleRespuestasId;
    }

    public Optional<LongFilter> optionalDetalleRespuestasId() {
        return Optional.ofNullable(detalleRespuestasId);
    }

    public LongFilter detalleRespuestasId() {
        if (detalleRespuestasId == null) {
            setDetalleRespuestasId(new LongFilter());
        }
        return detalleRespuestasId;
    }

    public void setDetalleRespuestasId(LongFilter detalleRespuestasId) {
        this.detalleRespuestasId = detalleRespuestasId;
    }

    public LongFilter getResultadoDonesId() {
        return resultadoDonesId;
    }

    public Optional<LongFilter> optionalResultadoDonesId() {
        return Optional.ofNullable(resultadoDonesId);
    }

    public LongFilter resultadoDonesId() {
        if (resultadoDonesId == null) {
            setResultadoDonesId(new LongFilter());
        }
        return resultadoDonesId;
    }

    public void setResultadoDonesId(LongFilter resultadoDonesId) {
        this.resultadoDonesId = resultadoDonesId;
    }

    public LongFilter getSesionesId() {
        return sesionesId;
    }

    public Optional<LongFilter> optionalSesionesId() {
        return Optional.ofNullable(sesionesId);
    }

    public LongFilter sesionesId() {
        if (sesionesId == null) {
            setSesionesId(new LongFilter());
        }
        return sesionesId;
    }

    public void setSesionesId(LongFilter sesionesId) {
        this.sesionesId = sesionesId;
    }

    public LongFilter getAuditoriasId() {
        return auditoriasId;
    }

    public Optional<LongFilter> optionalAuditoriasId() {
        return Optional.ofNullable(auditoriasId);
    }

    public LongFilter auditoriasId() {
        if (auditoriasId == null) {
            setAuditoriasId(new LongFilter());
        }
        return auditoriasId;
    }

    public void setAuditoriasId(LongFilter auditoriasId) {
        this.auditoriasId = auditoriasId;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public Optional<LongFilter> optionalUsuarioId() {
        return Optional.ofNullable(usuarioId);
    }

    public LongFilter usuarioId() {
        if (usuarioId == null) {
            setUsuarioId(new LongFilter());
        }
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LongFilter getCuestionarioId() {
        return cuestionarioId;
    }

    public Optional<LongFilter> optionalCuestionarioId() {
        return Optional.ofNullable(cuestionarioId);
    }

    public LongFilter cuestionarioId() {
        if (cuestionarioId == null) {
            setCuestionarioId(new LongFilter());
        }
        return cuestionarioId;
    }

    public void setCuestionarioId(LongFilter cuestionarioId) {
        this.cuestionarioId = cuestionarioId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RespuestaUsuarioCriteria that = (RespuestaUsuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fechaInicio, that.fechaInicio) &&
            Objects.equals(fechaCompletado, that.fechaCompletado) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(tiempoTotalSegundos, that.tiempoTotalSegundos) &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(userAgent, that.userAgent) &&
            Objects.equals(detalleRespuestasId, that.detalleRespuestasId) &&
            Objects.equals(resultadoDonesId, that.resultadoDonesId) &&
            Objects.equals(sesionesId, that.sesionesId) &&
            Objects.equals(auditoriasId, that.auditoriasId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(cuestionarioId, that.cuestionarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fechaInicio,
            fechaCompletado,
            estado,
            tiempoTotalSegundos,
            ipAddress,
            userAgent,
            detalleRespuestasId,
            resultadoDonesId,
            sesionesId,
            auditoriasId,
            usuarioId,
            cuestionarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RespuestaUsuarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFechaInicio().map(f -> "fechaInicio=" + f + ", ").orElse("") +
            optionalFechaCompletado().map(f -> "fechaCompletado=" + f + ", ").orElse("") +
            optionalEstado().map(f -> "estado=" + f + ", ").orElse("") +
            optionalTiempoTotalSegundos().map(f -> "tiempoTotalSegundos=" + f + ", ").orElse("") +
            optionalIpAddress().map(f -> "ipAddress=" + f + ", ").orElse("") +
            optionalUserAgent().map(f -> "userAgent=" + f + ", ").orElse("") +
            optionalDetalleRespuestasId().map(f -> "detalleRespuestasId=" + f + ", ").orElse("") +
            optionalResultadoDonesId().map(f -> "resultadoDonesId=" + f + ", ").orElse("") +
            optionalSesionesId().map(f -> "sesionesId=" + f + ", ").orElse("") +
            optionalAuditoriasId().map(f -> "auditoriasId=" + f + ", ").orElse("") +
            optionalUsuarioId().map(f -> "usuarioId=" + f + ", ").orElse("") +
            optionalCuestionarioId().map(f -> "cuestionarioId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
