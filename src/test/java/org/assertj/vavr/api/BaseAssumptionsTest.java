package org.assertj.vavr.api;

import org.junit.AssumptionViolatedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

abstract class BaseAssumptionsTest {

    @ParameterizedTest
    @MethodSource("provideAssumptionsRunners")
    public void should_ignore_test_when_assumption_fails(AssumptionRunner<?> assumptionRunner) {
        assertThatExceptionOfType(AssumptionViolatedException.class).isThrownBy(assumptionRunner::runFailingAssumption);
    }

    @ParameterizedTest
    @MethodSource("provideAssumptionsRunners")
    public void should_run_test_when_assumption_passes(AssumptionRunner<?> assumptionRunner) {
        assertThatCode(assumptionRunner::runPassingAssumption).doesNotThrowAnyException();
    }

}
