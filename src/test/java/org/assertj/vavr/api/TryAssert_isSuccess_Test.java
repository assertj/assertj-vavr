package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TryAssert_isSuccess_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).isSuccess(),
                actualIsNull());
    }

    @Test
    void should_fail_when_try_is_failure() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.failure(new NullPointerException())).isSuccess(),
                "\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_pass_when_try_is_success() {
        assertThat(Try.success("OK")).isSuccess();
    }
}