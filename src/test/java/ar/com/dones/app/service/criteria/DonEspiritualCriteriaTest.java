package ar.com.dones.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DonEspiritualCriteriaTest {

    @Test
    void newDonEspiritualCriteriaHasAllFiltersNullTest() {
        var donEspiritualCriteria = new DonEspiritualCriteria();
        assertThat(donEspiritualCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void donEspiritualCriteriaFluentMethodsCreatesFiltersTest() {
        var donEspiritualCriteria = new DonEspiritualCriteria();

        setAllFilters(donEspiritualCriteria);

        assertThat(donEspiritualCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void donEspiritualCriteriaCopyCreatesNullFilterTest() {
        var donEspiritualCriteria = new DonEspiritualCriteria();
        var copy = donEspiritualCriteria.copy();

        assertThat(donEspiritualCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(donEspiritualCriteria)
        );
    }

    @Test
    void donEspiritualCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var donEspiritualCriteria = new DonEspiritualCriteria();
        setAllFilters(donEspiritualCriteria);

        var copy = donEspiritualCriteria.copy();

        assertThat(donEspiritualCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(donEspiritualCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var donEspiritualCriteria = new DonEspiritualCriteria();

        assertThat(donEspiritualCriteria).hasToString("DonEspiritualCriteria{}");
    }

    private static void setAllFilters(DonEspiritualCriteria donEspiritualCriteria) {
        donEspiritualCriteria.id();
        donEspiritualCriteria.nombre();
        donEspiritualCriteria.nombreCorto();
        donEspiritualCriteria.activo();
        donEspiritualCriteria.ordenPresentacion();
        donEspiritualCriteria.preguntaDonesId();
        donEspiritualCriteria.resultadoDonesId();
        donEspiritualCriteria.interpretacionesId();
        donEspiritualCriteria.distinct();
    }

    private static Condition<DonEspiritualCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNombre()) &&
                condition.apply(criteria.getNombreCorto()) &&
                condition.apply(criteria.getActivo()) &&
                condition.apply(criteria.getOrdenPresentacion()) &&
                condition.apply(criteria.getPreguntaDonesId()) &&
                condition.apply(criteria.getResultadoDonesId()) &&
                condition.apply(criteria.getInterpretacionesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DonEspiritualCriteria> copyFiltersAre(
        DonEspiritualCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNombre(), copy.getNombre()) &&
                condition.apply(criteria.getNombreCorto(), copy.getNombreCorto()) &&
                condition.apply(criteria.getActivo(), copy.getActivo()) &&
                condition.apply(criteria.getOrdenPresentacion(), copy.getOrdenPresentacion()) &&
                condition.apply(criteria.getPreguntaDonesId(), copy.getPreguntaDonesId()) &&
                condition.apply(criteria.getResultadoDonesId(), copy.getResultadoDonesId()) &&
                condition.apply(criteria.getInterpretacionesId(), copy.getInterpretacionesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
