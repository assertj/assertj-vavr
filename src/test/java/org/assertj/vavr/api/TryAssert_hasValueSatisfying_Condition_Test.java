package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_hasValueSatisfying_Condition_Test {

    private Condition<Object> passingCondition = new TestCondition<>(true);
    private Condition<Object> notPassingCondition = new TestCondition<>();

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).hasValueSatisfying(passingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_failure() {
        assertThatThrownBy(
                () -> assertThat(Try.failure(new NullPointerException())).hasValueSatisfying(passingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting Try to be a Success, but wasn't");
    }

    @Test
    void should_fail_when_value_does_not_satisfy_consumer() {
        assertThatThrownBy(
                () -> assertThat(Try.success("OK")).hasValueSatisfying(notPassingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n <\"OK\">\nto be <TestCondition>");
    }

    @Test
    void should_pass_when_value_satisfies_consumer() {
        assertThat(Try.success("OK")).hasValueSatisfying(passingCondition);
    }
}