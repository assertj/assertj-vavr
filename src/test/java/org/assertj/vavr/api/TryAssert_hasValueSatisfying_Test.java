package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_hasValueSatisfying_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).hasValueSatisfying(val -> {
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_failure() {
        assertThatThrownBy(
                () -> assertThat(Try.failure(new NullPointerException())).hasValueSatisfying(val -> {
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_fail_when_value_does_not_satisfy_consumer() {
        assertThatThrownBy(
                () -> assertThat(Try.success("OK"))
                        .hasValueSatisfying(val -> assertThat(val).isBlank())
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting blank but was:<\"OK\">");
    }

    @Test
    void should_pass_when_value_satisfies_consumer() {
        assertThat(Try.success("OK"))
                .hasValueSatisfying(val -> assertThat(val).isNotBlank().isEqualTo("OK"));
    }
}