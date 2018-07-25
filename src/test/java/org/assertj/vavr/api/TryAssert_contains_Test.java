package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TryAssert_contains_Test {

    @Test
    public void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).contains(""),
                actualIsNull());
    }

    @Test
    public void should_fail_when_expected_value_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> assertThat(Try.success("some value")).contains(null),
                "The expected value should not be <null>.");
    }

    @Test
    public void should_pass_when_success_try_contains_equal_value() {
        final String actual = "OK";
        final String expected = new String(actual);
        assertThat(Try.success(actual)).contains(expected);
    }

    @Test
    public void should_pass_when_success_try_contains_same_value() {
        final String value = "OK";
        assertThat(Try.success(value)).contains(value);
    }

    @Test
    public void should_fail_when_success_try_contains_different_value() {
        final String actual = "OK";
        final String expected = "different";
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success(actual)).contains(expected),
                "\nExpecting:\n  <Success(OK)>\nto contain:\n  <\"different\">\nbut did not.");
    }

    @Test
    public void should_fail_when_try_is_a_failure() {
        final NullPointerException exception = new NullPointerException();
        assertThrows(AssertionError.class,
                () -> assertThat(Try.failure(exception)).contains(exception),
                "\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
    }
}