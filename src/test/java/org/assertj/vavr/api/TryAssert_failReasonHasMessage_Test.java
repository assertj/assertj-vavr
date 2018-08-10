package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TryAssert_failReasonHasMessage_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Try<String>) null).failReasonHasMessage(""),
                actualIsNull());
    }

    @Test
    void should_fail_when_reason_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> assertThat(Try.failure(new NullPointerException())).failReasonHasMessage(null),
                "The expected value should not be <null>.");
    }

    @Test
    void should_fail_when_try_is_success() {
        assertThrows(AssertionError.class,
                () -> assertThat(Try.success("OK")).failReasonHasMessage("Some reason"),
                "\nExpecting Try to be a Failure, but wasn't");
    }

    @Test
    void should_pass_when_try_fail_with_specific_reason() {
        assertThat(Try.failure(new NullPointerException("ops"))).failReasonHasMessage("ops");
    }
}