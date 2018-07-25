package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TryAssert_containsInstanceOf_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).containsInstanceOf(String.class),
                actualIsNull());
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.failure(new NullPointerException()))
                        .containsInstanceOf(NullPointerException.class),
                "\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_fail_when_success_try_contains_value_of_different_class() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success("OK")).containsInstanceOf(Integer.class),
                "\nExpecting:\n <Success>\nto contain a value that is an instance of:\n <java.lang.Integer>\nbut did contain an instance of:\n <java.lang.String>");
    }

    @Test
    void should_pass_when_success_try_contains_value_of_expected_class() {
        assertThat(Try.success("OK")).containsInstanceOf(String.class);
    }
}