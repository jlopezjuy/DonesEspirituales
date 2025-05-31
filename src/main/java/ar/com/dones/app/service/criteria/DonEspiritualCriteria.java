package ar.com.dones.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.com.dones.app.domain.DonEspiritual} entity. This class is used
 * in {@link ar.com.dones.app.web.rest.DonEspiritualResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /don-espirituals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonEspiritualCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter nombreCorto;

    private BooleanFilter activo;

    private IntegerFilter ordenPresentacion;

    private LongFilter preguntaDonesId;

    private LongFilter resultadoDonesId;

    private LongFilter interpretacionesId;

    private Boolean distinct;

    public DonEspiritualCriteria() {}

    public DonEspiritualCriteria(DonEspiritualCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nombre = other.optionalNombre().map(StringFilter::copy).orElse(null);
        this.nombreCorto = other.optionalNombreCorto().map(StringFilter::copy).orElse(null);
        this.activo = other.optionalActivo().map(BooleanFilter::copy).orElse(null);
        this.ordenPresentacion = other.optionalOrdenPresentacion().map(IntegerFilter::copy).orElse(null);
        this.preguntaDonesId = other.optionalPreguntaDonesId().map(LongFilter::copy).orElse(null);
        this.resultadoDonesId = other.optionalResultadoDonesId().map(LongFilter::copy).orElse(null);
        this.interpretacionesId = other.optionalInterpretacionesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DonEspiritualCriteria copy() {
        return new DonEspiritualCriteria(this);
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

    public StringFilter getNombreCorto() {
        return nombreCorto;
    }

    public Optional<StringFilter> optionalNombreCorto() {
        return Optional.ofNullable(nombreCorto);
    }

    public StringFilter nombreCorto() {
        if (nombreCorto == null) {
            setNombreCorto(new StringFilter());
        }
        return nombreCorto;
    }

    public void setNombreCorto(StringFilter nombreCorto) {
        this.nombreCorto = nombreCorto;
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

    public IntegerFilter getOrdenPresentacion() {
        return ordenPresentacion;
    }

    public Optional<IntegerFilter> optionalOrdenPresentacion() {
        return Optional.ofNullable(ordenPresentacion);
    }

    public IntegerFilter ordenPresentacion() {
        if (ordenPresentacion == null) {
            setOrdenPresentacion(new IntegerFilter());
        }
        return ordenPresentacion;
    }

    public void setOrdenPresentacion(IntegerFilter ordenPresentacion) {
        this.ordenPresentacion = ordenPresentacion;
    }

    public LongFilter getPreguntaDonesId() {
        return preguntaDonesId;
    }

    public Optional<LongFilter> optionalPreguntaDonesId() {
        return Optional.ofNullable(preguntaDonesId);
    }

    public LongFilter preguntaDonesId() {
        if (preguntaDonesId == null) {
            setPreguntaDonesId(new LongFilter());
        }
        return preguntaDonesId;
    }

    public void setPreguntaDonesId(LongFilter preguntaDonesId) {
        this.preguntaDonesId = preguntaDonesId;
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

    public LongFilter getInterpretacionesId() {
        return interpretacionesId;
    }

    public Optional<LongFilter> optionalInterpretacionesId() {
        return Optional.ofNullable(interpretacionesId);
    }

    public LongFilter interpretacionesId() {
        if (interpretacionesId == null) {
            setInterpretacionesId(new LongFilter());
        }
        return interpretacionesId;
    }

    public void setInterpretacionesId(LongFilter interpretacionesId) {
        this.interpretacionesId = interpretacionesId;
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
        final DonEspiritualCriteria that = (DonEspiritualCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(nombreCorto, that.nombreCorto) &&
            Objects.equals(activo, that.activo) &&
            Objects.equals(ordenPresentacion, that.ordenPresentacion) &&
            Objects.equals(preguntaDonesId, that.preguntaDonesId) &&
            Objects.equals(resultadoDonesId, that.resultadoDonesId) &&
            Objects.equals(interpretacionesId, that.interpretacionesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nombre,
            nombreCorto,
            activo,
            ordenPresentacion,
            preguntaDonesId,
            resultadoDonesId,
            interpretacionesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonEspiritualCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNombre().map(f -> "nombre=" + f + ", ").orElse("") +
            optionalNombreCorto().map(f -> "nombreCorto=" + f + ", ").orElse("") +
            optionalActivo().map(f -> "activo=" + f + ", ").orElse("") +
            optionalOrdenPresentacion().map(f -> "ordenPresentacion=" + f + ", ").orElse("") +
            optionalPreguntaDonesId().map(f -> "preguntaDonesId=" + f + ", ").orElse("") +
            optionalResultadoDonesId().map(f -> "resultadoDonesId=" + f + ", ").orElse("") +
            optionalInterpretacionesId().map(f -> "interpretacionesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
