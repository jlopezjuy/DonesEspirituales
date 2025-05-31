package ar.com.dones.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ar.com.dones.app.domain.ResultadoDon} entity. This class is used
 * in {@link ar.com.dones.app.web.rest.ResultadoDonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultado-dons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResultadoDonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter puntuacionTotal;

    private BigDecimalFilter porcentaje;

    private IntegerFilter rankingPosicion;

    private BooleanFilter esDonPrincipal;

    private LongFilter interpretacionId;

    private LongFilter respuestaUsuarioId;

    private LongFilter donEspiritualId;

    private Boolean distinct;

    public ResultadoDonCriteria() {}

    public ResultadoDonCriteria(ResultadoDonCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.puntuacionTotal = other.optionalPuntuacionTotal().map(IntegerFilter::copy).orElse(null);
        this.porcentaje = other.optionalPorcentaje().map(BigDecimalFilter::copy).orElse(null);
        this.rankingPosicion = other.optionalRankingPosicion().map(IntegerFilter::copy).orElse(null);
        this.esDonPrincipal = other.optionalEsDonPrincipal().map(BooleanFilter::copy).orElse(null);
        this.interpretacionId = other.optionalInterpretacionId().map(LongFilter::copy).orElse(null);
        this.respuestaUsuarioId = other.optionalRespuestaUsuarioId().map(LongFilter::copy).orElse(null);
        this.donEspiritualId = other.optionalDonEspiritualId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ResultadoDonCriteria copy() {
        return new ResultadoDonCriteria(this);
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

    public IntegerFilter getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public Optional<IntegerFilter> optionalPuntuacionTotal() {
        return Optional.ofNullable(puntuacionTotal);
    }

    public IntegerFilter puntuacionTotal() {
        if (puntuacionTotal == null) {
            setPuntuacionTotal(new IntegerFilter());
        }
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(IntegerFilter puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public BigDecimalFilter getPorcentaje() {
        return porcentaje;
    }

    public Optional<BigDecimalFilter> optionalPorcentaje() {
        return Optional.ofNullable(porcentaje);
    }

    public BigDecimalFilter porcentaje() {
        if (porcentaje == null) {
            setPorcentaje(new BigDecimalFilter());
        }
        return porcentaje;
    }

    public void setPorcentaje(BigDecimalFilter porcentaje) {
        this.porcentaje = porcentaje;
    }

    public IntegerFilter getRankingPosicion() {
        return rankingPosicion;
    }

    public Optional<IntegerFilter> optionalRankingPosicion() {
        return Optional.ofNullable(rankingPosicion);
    }

    public IntegerFilter rankingPosicion() {
        if (rankingPosicion == null) {
            setRankingPosicion(new IntegerFilter());
        }
        return rankingPosicion;
    }

    public void setRankingPosicion(IntegerFilter rankingPosicion) {
        this.rankingPosicion = rankingPosicion;
    }

    public BooleanFilter getEsDonPrincipal() {
        return esDonPrincipal;
    }

    public Optional<BooleanFilter> optionalEsDonPrincipal() {
        return Optional.ofNullable(esDonPrincipal);
    }

    public BooleanFilter esDonPrincipal() {
        if (esDonPrincipal == null) {
            setEsDonPrincipal(new BooleanFilter());
        }
        return esDonPrincipal;
    }

    public void setEsDonPrincipal(BooleanFilter esDonPrincipal) {
        this.esDonPrincipal = esDonPrincipal;
    }

    public LongFilter getInterpretacionId() {
        return interpretacionId;
    }

    public Optional<LongFilter> optionalInterpretacionId() {
        return Optional.ofNullable(interpretacionId);
    }

    public LongFilter interpretacionId() {
        if (interpretacionId == null) {
            setInterpretacionId(new LongFilter());
        }
        return interpretacionId;
    }

    public void setInterpretacionId(LongFilter interpretacionId) {
        this.interpretacionId = interpretacionId;
    }

    public LongFilter getRespuestaUsuarioId() {
        return respuestaUsuarioId;
    }

    public Optional<LongFilter> optionalRespuestaUsuarioId() {
        return Optional.ofNullable(respuestaUsuarioId);
    }

    public LongFilter respuestaUsuarioId() {
        if (respuestaUsuarioId == null) {
            setRespuestaUsuarioId(new LongFilter());
        }
        return respuestaUsuarioId;
    }

    public void setRespuestaUsuarioId(LongFilter respuestaUsuarioId) {
        this.respuestaUsuarioId = respuestaUsuarioId;
    }

    public LongFilter getDonEspiritualId() {
        return donEspiritualId;
    }

    public Optional<LongFilter> optionalDonEspiritualId() {
        return Optional.ofNullable(donEspiritualId);
    }

    public LongFilter donEspiritualId() {
        if (donEspiritualId == null) {
            setDonEspiritualId(new LongFilter());
        }
        return donEspiritualId;
    }

    public void setDonEspiritualId(LongFilter donEspiritualId) {
        this.donEspiritualId = donEspiritualId;
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
        final ResultadoDonCriteria that = (ResultadoDonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(puntuacionTotal, that.puntuacionTotal) &&
            Objects.equals(porcentaje, that.porcentaje) &&
            Objects.equals(rankingPosicion, that.rankingPosicion) &&
            Objects.equals(esDonPrincipal, that.esDonPrincipal) &&
            Objects.equals(interpretacionId, that.interpretacionId) &&
            Objects.equals(respuestaUsuarioId, that.respuestaUsuarioId) &&
            Objects.equals(donEspiritualId, that.donEspiritualId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            puntuacionTotal,
            porcentaje,
            rankingPosicion,
            esDonPrincipal,
            interpretacionId,
            respuestaUsuarioId,
            donEspiritualId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultadoDonCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPuntuacionTotal().map(f -> "puntuacionTotal=" + f + ", ").orElse("") +
            optionalPorcentaje().map(f -> "porcentaje=" + f + ", ").orElse("") +
            optionalRankingPosicion().map(f -> "rankingPosicion=" + f + ", ").orElse("") +
            optionalEsDonPrincipal().map(f -> "esDonPrincipal=" + f + ", ").orElse("") +
            optionalInterpretacionId().map(f -> "interpretacionId=" + f + ", ").orElse("") +
            optionalRespuestaUsuarioId().map(f -> "respuestaUsuarioId=" + f + ", ").orElse("") +
            optionalDonEspiritualId().map(f -> "donEspiritualId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
