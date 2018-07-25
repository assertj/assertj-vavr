package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TryAssert_hasValueSatisfying_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).hasValueSatisfying(val -> {
                }),
                actualIsNull());
    }

    @Test
    void should_fail_when_try_is_failure() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.failure(new NullPointerException())).hasValueSatisfying(val -> {
                }),
                "\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_fail_when_value_does_not_satisfy_consumer() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success("OK"))
                        .hasValueSatisfying(val -> assertThat(val).isBlank()),
                "\nExpecting blank but was:<\"OK\">");
    }

    @Test
    void should_pass_when_value_satisfies_consumer() {
        assertThat(Try.success("OK"))
          .hasValueSatisfying(val -> assertThat(val).isNotBlank().isEqualTo("OK"));
    }
}