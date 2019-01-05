package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TryAssert_containsSame_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).containsSame(""),
                actualIsNull());
    }

    @Test
    void should_fail_when_expected_value_is_null() {
        assertThrows(AssertionError.class, () -> assertThat(Try.success("some value")).containsSame(null));
    }

    @Test
    void should_pass_when_null_success_try_checked_on_containing_same_value() {
        assertThat(Try.success(null)).containsSame(null);
    }

    @Test
    void should_fail_when_success_try_contains_not_the_same_value() {
        final String actual = "OK";
        final String expected = new String(actual);
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success(actual)).containsSame(expected),
                "\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"OK\">\nbut did not.");
    }

    @Test
    void should_fail_when_success_try_contains_different_value() {
        final String actual = "OK";
        final String expected = "different";
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success(actual)).containsSame(expected),
                "\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"different\">\nbut did not.");
    }

    @Test
    void should_pass_when_success_try_contains_the_same_value() {
        final String value = "OK";
        assertThat(Try.success(value)).containsSame(value);
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        final NullPointerException exception = new NullPointerException();
        assertThrows(AssertionError.class,
                () -> assertThat(Try.failure(exception)).containsSame(exception),
                "\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
    }
}