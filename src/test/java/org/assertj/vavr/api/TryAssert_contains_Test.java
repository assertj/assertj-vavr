package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_contains_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).contains("")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_when_success_try_contains_equal_value() {
        final String actual = "OK";
        final String expected = new String(actual);
        assertThat(Try.success(actual)).contains(expected);
    }

    @Test
    void should_pass_when_success_try_contains_same_value() {
        final String value = "OK";
        assertThat(Try.success(value)).contains(value);
    }

    @Test
    void should_fail_when_success_try_contains_different_value() {
        final String actual = "OK";
        final String expected = "different";
        assertThatThrownBy(
                () -> assertThat(Try.success(actual)).contains(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n  <Success(OK)>\nto contain:\n  <\"different\">\nbut did not.");
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        final NullPointerException exception = new NullPointerException();
        assertThatThrownBy(
                () -> assertThat(Try.failure(exception)).contains(exception)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
    }

    @Test
    void should_pass_when_success_try_contains_null() {
        assertThat(Try.success(null)).contains(null);
    }
}
