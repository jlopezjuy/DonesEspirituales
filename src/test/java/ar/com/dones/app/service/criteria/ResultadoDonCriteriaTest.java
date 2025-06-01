package ar.com.dones.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ResultadoDonCriteriaTest {

    @Test
    void newResultadoDonCriteriaHasAllFiltersNullTest() {
        var resultadoDonCriteria = new ResultadoDonCriteria();
        assertThat(resultadoDonCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void resultadoDonCriteriaFluentMethodsCreatesFiltersTest() {
        var resultadoDonCriteria = new ResultadoDonCriteria();

        setAllFilters(resultadoDonCriteria);

        assertThat(resultadoDonCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void resultadoDonCriteriaCopyCreatesNullFilterTest() {
        var resultadoDonCriteria = new ResultadoDonCriteria();
        var copy = resultadoDonCriteria.copy();

        assertThat(resultadoDonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(resultadoDonCriteria)
        );
    }

    @Test
    void resultadoDonCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var resultadoDonCriteria = new ResultadoDonCriteria();
        setAllFilters(resultadoDonCriteria);

        var copy = resultadoDonCriteria.copy();

        assertThat(resultadoDonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(resultadoDonCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var resultadoDonCriteria = new ResultadoDonCriteria();

        assertThat(resultadoDonCriteria).hasToString("ResultadoDonCriteria{}");
    }

    private static void setAllFilters(ResultadoDonCriteria resultadoDonCriteria) {
        resultadoDonCriteria.id();
        resultadoDonCriteria.puntuacionTotal();
        resultadoDonCriteria.porcentaje();
        resultadoDonCriteria.rankingPosicion();
        resultadoDonCriteria.esDonPrincipal();
        resultadoDonCriteria.interpretacionId();
        resultadoDonCriteria.respuestaUsuarioId();
        resultadoDonCriteria.donEspiritualId();
        resultadoDonCriteria.distinct();
    }

    private static Condition<ResultadoDonCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPuntuacionTotal()) &&
                condition.apply(criteria.getPorcentaje()) &&
                condition.apply(criteria.getRankingPosicion()) &&
                condition.apply(criteria.getEsDonPrincipal()) &&
                condition.apply(criteria.getInterpretacionId()) &&
                condition.apply(criteria.getRespuestaUsuarioId()) &&
                condition.apply(criteria.getDonEspiritualId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ResultadoDonCriteria> copyFiltersAre(
        ResultadoDonCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPuntuacionTotal(), copy.getPuntuacionTotal()) &&
                condition.apply(criteria.getPorcentaje(), copy.getPorcentaje()) &&
                condition.apply(criteria.getRankingPosicion(), copy.getRankingPosicion()) &&
                condition.apply(criteria.getEsDonPrincipal(), copy.getEsDonPrincipal()) &&
                condition.apply(criteria.getInterpretacionId(), copy.getInterpretacionId()) &&
                condition.apply(criteria.getRespuestaUsuarioId(), copy.getRespuestaUsuarioId()) &&
                condition.apply(criteria.getDonEspiritualId(), copy.getDonEspiritualId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
