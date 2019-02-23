package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_containsInstanceOf_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).containsInstanceOf(String.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        assertThatThrownBy(
                () -> assertThat(Try.failure(new NullPointerException()))
                        .containsInstanceOf(NullPointerException.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_fail_when_success_try_contains_value_of_different_class() {
        assertThatThrownBy(
                () -> assertThat(Try.success("OK")).containsInstanceOf(Integer.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n <Success>\nto contain a value that is an instance of:\n <java.lang.Integer>\nbut did contain an instance of:\n <java.lang.String>");
    }

    @Test
    void should_pass_when_success_try_contains_value_of_expected_class() {
        assertThat(Try.success("OK")).containsInstanceOf(String.class);
    }
}