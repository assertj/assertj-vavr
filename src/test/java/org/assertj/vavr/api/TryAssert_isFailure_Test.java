package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_isFailure_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).isFailure()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_success() {
        assertThatThrownBy(
                () -> assertThat(Try.success("OK")).isFailure()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting Try to be a Failure, but wasn't");
    }

    @Test
    void should_pass_when_try_is_failure() {
        assertThat(Try.failure(new NullPointerException())).isFailure();
    }
}