package ar.com.dones.app.service.criteria;

import ar.com.dones.app.domain.enumeration.Genero;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.com.dones.app.domain.Usuario} entity. This class is used
 * in {@link ar.com.dones.app.web.rest.UsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Genero
     */
    public static class GeneroFilter extends Filter<Genero> {

        public GeneroFilter() {}

        public GeneroFilter(GeneroFilter filter) {
            super(filter);
        }

        @Override
        public GeneroFilter copy() {
            return new GeneroFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido;

    private StringFilter email;

    private StringFilter telefono;

    private LocalDateFilter fechaNacimiento;

    private GeneroFilter genero;

    private StringFilter iglesia;

    private StringFilter denominacion;

    private InstantFilter fechaRegistro;

    private InstantFilter ultimaActividad;

    private BooleanFilter activo;

    private LongFilter respuestasId;

    private LongFilter sesionesId;

    private Boolean distinct;

    public UsuarioCriteria() {}

    public UsuarioCriteria(UsuarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nombre = other.optionalNombre().map(StringFilter::copy).orElse(null);
        this.apellido = other.optionalApellido().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.telefono = other.optionalTelefono().map(StringFilter::copy).orElse(null);
        this.fechaNacimiento = other.optionalFechaNacimiento().map(LocalDateFilter::copy).orElse(null);
        this.genero = other.optionalGenero().map(GeneroFilter::copy).orElse(null);
        this.iglesia = other.optionalIglesia().map(StringFilter::copy).orElse(null);
        this.denominacion = other.optionalDenominacion().map(StringFilter::copy).orElse(null);
        this.fechaRegistro = other.optionalFechaRegistro().map(InstantFilter::copy).orElse(null);
        this.ultimaActividad = other.optionalUltimaActividad().map(InstantFilter::copy).orElse(null);
        this.activo = other.optionalActivo().map(BooleanFilter::copy).orElse(null);
        this.respuestasId = other.optionalRespuestasId().map(LongFilter::copy).orElse(null);
        this.sesionesId = other.optionalSesionesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UsuarioCriteria copy() {
        return new UsuarioCriteria(this);
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

    public StringFilter getNombre() {
        return nombre;
    }

    public Optional<StringFilter> optionalNombre() {
        return Optional.ofNullable(nombre);
    }

    public StringFilter nombre() {
        if (nombre == null) {
            setNombre(new StringFilter());
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getApellido() {
        return apellido;
    }

    public Optional<StringFilter> optionalApellido() {
        return Optional.ofNullable(apellido);
    }

    public StringFilter apellido() {
        if (apellido == null) {
            setApellido(new StringFilter());
        }
        return apellido;
    }

    public void setApellido(StringFilter apellido) {
        this.apellido = apellido;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public Optional<StringFilter> optionalTelefono() {
        return Optional.ofNullable(telefono);
    }

    public StringFilter telefono() {
        if (telefono == null) {
            setTelefono(new StringFilter());
        }
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Optional<LocalDateFilter> optionalFechaNacimiento() {
        return Optional.ofNullable(fechaNacimiento);
    }

    public LocalDateFilter fechaNacimiento() {
        if (fechaNacimiento == null) {
            setFechaNacimiento(new LocalDateFilter());
        }
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public GeneroFilter getGenero() {
        return genero;
    }

    public Optional<GeneroFilter> optionalGenero() {
        return Optional.ofNullable(genero);
    }

    public GeneroFilter genero() {
        if (genero == null) {
            setGenero(new GeneroFilter());
        }
        return genero;
    }

    public void setGenero(GeneroFilter genero) {
        this.genero = genero;
    }

    public StringFilter getIglesia() {
        return iglesia;
    }

    public Optional<StringFilter> optionalIglesia() {
        return Optional.ofNullable(iglesia);
    }

    public StringFilter iglesia() {
        if (iglesia == null) {
            setIglesia(new StringFilter());
        }
        return iglesia;
    }

    public void setIglesia(StringFilter iglesia) {
        this.iglesia = iglesia;
    }

    public StringFilter getDenominacion() {
        return denominacion;
    }

    public Optional<StringFilter> optionalDenominacion() {
        return Optional.ofNullable(denominacion);
    }

    public StringFilter denominacion() {
        if (denominacion == null) {
            setDenominacion(new StringFilter());
        }
        return denominacion;
    }

    public void setDenominacion(StringFilter denominacion) {
        this.denominacion = denominacion;
    }

    public InstantFilter getFechaRegistro() {
        return fechaRegistro;
    }

    public Optional<InstantFilter> optionalFechaRegistro() {
        return Optional.ofNullable(fechaRegistro);
    }

    public InstantFilter fechaRegistro() {
        if (fechaRegistro == null) {
            setFechaRegistro(new InstantFilter());
        }
        return fechaRegistro;
    }

    public void setFechaRegistro(InstantFilter fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public InstantFilter getUltimaActividad() {
        return ultimaActividad;
    }

    public Optional<InstantFilter> optionalUltimaActividad() {
        return Optional.ofNullable(ultimaActividad);
    }

    public InstantFilter ultimaActividad() {
        if (ultimaActividad == null) {
            setUltimaActividad(new InstantFilter());
        }
        return ultimaActividad;
    }

    public void setUltimaActividad(InstantFilter ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public BooleanFilter getActivo() {
        return activo;
    }

    public Optional<BooleanFilter> optionalActivo() {
        return Optional.ofNullable(activo);
    }

    public BooleanFilter activo() {
        if (activo == null) {
            setActivo(new BooleanFilter());
        }
        return activo;
    }

    public void setActivo(BooleanFilter activo) {
        this.activo = activo;
    }

    public LongFilter getRespuestasId() {
        return respuestasId;
    }

    public Optional<LongFilter> optionalRespuestasId() {
        return Optional.ofNullable(respuestasId);
    }

    public LongFilter respuestasId() {
        if (respuestasId == null) {
            setRespuestasId(new LongFilter());
        }
        return respuestasId;
    }

    public void setRespuestasId(LongFilter respuestasId) {
        this.respuestasId = respuestasId;
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
        final UsuarioCriteria that = (UsuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido, that.apellido) &&
            Objects.equals(email, that.email) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(iglesia, that.iglesia) &&
            Objects.equals(denominacion, that.denominacion) &&
            Objects.equals(fechaRegistro, that.fechaRegistro) &&
            Objects.equals(ultimaActividad, that.ultimaActividad) &&
            Objects.equals(activo, that.activo) &&
            Objects.equals(respuestasId, that.respuestasId) &&
            Objects.equals(sesionesId, that.sesionesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nombre,
            apellido,
            email,
            telefono,
            fechaNacimiento,
            genero,
            iglesia,
            denominacion,
            fechaRegistro,
            ultimaActividad,
            activo,
            respuestasId,
            sesionesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNombre().map(f -> "nombre=" + f + ", ").orElse("") +
            optionalApellido().map(f -> "apellido=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalTelefono().map(f -> "telefono=" + f + ", ").orElse("") +
            optionalFechaNacimiento().map(f -> "fechaNacimiento=" + f + ", ").orElse("") +
            optionalGenero().map(f -> "genero=" + f + ", ").orElse("") +
            optionalIglesia().map(f -> "iglesia=" + f + ", ").orElse("") +
            optionalDenominacion().map(f -> "denominacion=" + f + ", ").orElse("") +
            optionalFechaRegistro().map(f -> "fechaRegistro=" + f + ", ").orElse("") +
            optionalUltimaActividad().map(f -> "ultimaActividad=" + f + ", ").orElse("") +
            optionalActivo().map(f -> "activo=" + f + ", ").orElse("") +
            optionalRespuestasId().map(f -> "respuestasId=" + f + ", ").orElse("") +
            optionalSesionesId().map(f -> "sesionesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
