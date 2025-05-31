package ar.com.dones.app.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class RespuestaUsuarioCriteriaTest {

    @Test
    void newRespuestaUsuarioCriteriaHasAllFiltersNullTest() {
        var respuestaUsuarioCriteria = new RespuestaUsuarioCriteria();
        assertThat(respuestaUsuarioCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void respuestaUsuarioCriteriaFluentMethodsCreatesFiltersTest() {
        var respuestaUsuarioCriteria = new RespuestaUsuarioCriteria();

        setAllFilters(respuestaUsuarioCriteria);

        assertThat(respuestaUsuarioCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void respuestaUsuarioCriteriaCopyCreatesNullFilterTest() {
        var respuestaUsuarioCriteria = new RespuestaUsuarioCriteria();
        var copy = respuestaUsuarioCriteria.copy();

        assertThat(respuestaUsuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(respuestaUsuarioCriteria)
        );
    }

    @Test
    void respuestaUsuarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var respuestaUsuarioCriteria = new RespuestaUsuarioCriteria();
        setAllFilters(respuestaUsuarioCriteria);

        var copy = respuestaUsuarioCriteria.copy();

        assertThat(respuestaUsuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(respuestaUsuarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var respuestaUsuarioCriteria = new RespuestaUsuarioCriteria();

        assertThat(respuestaUsuarioCriteria).hasToString("RespuestaUsuarioCriteria{}");
    }

    private static void setAllFilters(RespuestaUsuarioCriteria respuestaUsuarioCriteria) {
        respuestaUsuarioCriteria.id();
        respuestaUsuarioCriteria.fechaInicio();
        respuestaUsuarioCriteria.fechaCompletado();
        respuestaUsuarioCriteria.estado();
        respuestaUsuarioCriteria.tiempoTotalSegundos();
        respuestaUsuarioCriteria.ipAddress();
        respuestaUsuarioCriteria.userAgent();
        respuestaUsuarioCriteria.detalleRespuestasId();
        respuestaUsuarioCriteria.resultadoDonesId();
        respuestaUsuarioCriteria.sesionesId();
        respuestaUsuarioCriteria.auditoriasId();
        respuestaUsuarioCriteria.usuarioId();
        respuestaUsuarioCriteria.cuestionarioId();
        respuestaUsuarioCriteria.distinct();
    }

    private static Condition<RespuestaUsuarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFechaInicio()) &&
                condition.apply(criteria.getFechaCompletado()) &&
                condition.apply(criteria.getEstado()) &&
                condition.apply(criteria.getTiempoTotalSegundos()) &&
                condition.apply(criteria.getIpAddress()) &&
                condition.apply(criteria.getUserAgent()) &&
                condition.apply(criteria.getDetalleRespuestasId()) &&
                condition.apply(criteria.getResultadoDonesId()) &&
                condition.apply(criteria.getSesionesId()) &&
                condition.apply(criteria.getAuditoriasId()) &&
                condition.apply(criteria.getUsuarioId()) &&
                condition.apply(criteria.getCuestionarioId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<RespuestaUsuarioCriteria> copyFiltersAre(
        RespuestaUsuarioCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFechaInicio(), copy.getFechaInicio()) &&
                condition.apply(criteria.getFechaCompletado(), copy.getFechaCompletado()) &&
                condition.apply(criteria.getEstado(), copy.getEstado()) &&
                condition.apply(criteria.getTiempoTotalSegundos(), copy.getTiempoTotalSegundos()) &&
                condition.apply(criteria.getIpAddress(), copy.getIpAddress()) &&
                condition.apply(criteria.getUserAgent(), copy.getUserAgent()) &&
                condition.apply(criteria.getDetalleRespuestasId(), copy.getDetalleRespuestasId()) &&
                condition.apply(criteria.getResultadoDonesId(), copy.getResultadoDonesId()) &&
                condition.apply(criteria.getSesionesId(), copy.getSesionesId()) &&
                condition.apply(criteria.getAuditoriasId(), copy.getAuditoriasId()) &&
                condition.apply(criteria.getUsuarioId(), copy.getUsuarioId()) &&
                condition.apply(criteria.getCuestionarioId(), copy.getCuestionarioId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
