package ar.com.dones.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UsuarioCriteriaTest {

    @Test
    void newUsuarioCriteriaHasAllFiltersNullTest() {
        var usuarioCriteria = new UsuarioCriteria();
        assertThat(usuarioCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void usuarioCriteriaFluentMethodsCreatesFiltersTest() {
        var usuarioCriteria = new UsuarioCriteria();

        setAllFilters(usuarioCriteria);

        assertThat(usuarioCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void usuarioCriteriaCopyCreatesNullFilterTest() {
        var usuarioCriteria = new UsuarioCriteria();
        var copy = usuarioCriteria.copy();

        assertThat(usuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(usuarioCriteria)
        );
    }

    @Test
    void usuarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var usuarioCriteria = new UsuarioCriteria();
        setAllFilters(usuarioCriteria);

        var copy = usuarioCriteria.copy();

        assertThat(usuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(usuarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var usuarioCriteria = new UsuarioCriteria();

        assertThat(usuarioCriteria).hasToString("UsuarioCriteria{}");
    }

    private static void setAllFilters(UsuarioCriteria usuarioCriteria) {
        usuarioCriteria.id();
        usuarioCriteria.nombre();
        usuarioCriteria.apellido();
        usuarioCriteria.email();
        usuarioCriteria.telefono();
        usuarioCriteria.fechaNacimiento();
        usuarioCriteria.genero();
        usuarioCriteria.iglesia();
        usuarioCriteria.denominacion();
        usuarioCriteria.fechaRegistro();
        usuarioCriteria.ultimaActividad();
        usuarioCriteria.activo();
        usuarioCriteria.respuestasId();
        usuarioCriteria.sesionesId();
        usuarioCriteria.distinct();
    }

    private static Condition<UsuarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNombre()) &&
                condition.apply(criteria.getApellido()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getTelefono()) &&
                condition.apply(criteria.getFechaNacimiento()) &&
                condition.apply(criteria.getGenero()) &&
                condition.apply(criteria.getIglesia()) &&
                condition.apply(criteria.getDenominacion()) &&
                condition.apply(criteria.getFechaRegistro()) &&
                condition.apply(criteria.getUltimaActividad()) &&
                condition.apply(criteria.getActivo()) &&
                condition.apply(criteria.getRespuestasId()) &&
                condition.apply(criteria.getSesionesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UsuarioCriteria> copyFiltersAre(UsuarioCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNombre(), copy.getNombre()) &&
                condition.apply(criteria.getApellido(), copy.getApellido()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getTelefono(), copy.getTelefono()) &&
                condition.apply(criteria.getFechaNacimiento(), copy.getFechaNacimiento()) &&
                condition.apply(criteria.getGenero(), copy.getGenero()) &&
                condition.apply(criteria.getIglesia(), copy.getIglesia()) &&
                condition.apply(criteria.getDenominacion(), copy.getDenominacion()) &&
                condition.apply(criteria.getFechaRegistro(), copy.getFechaRegistro()) &&
                condition.apply(criteria.getUltimaActividad(), copy.getUltimaActividad()) &&
                condition.apply(criteria.getActivo(), copy.getActivo()) &&
                condition.apply(criteria.getRespuestasId(), copy.getRespuestasId()) &&
                condition.apply(criteria.getSesionesId(), copy.getSesionesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
