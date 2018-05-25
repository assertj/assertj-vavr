package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_failReasonHasMessage_Test extends BaseTest {

    @Test
    public void should_fail_when_try_is_null() {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Try<String>) null).failReasonHasMessage("");
    }

    @Test
    public void should_fail_when_reason_is_null() {
        thrown.expectIllegalArgumentException("The expected value should not be <null>.");
        assertThat(Try.failure(new NullPointerException())).failReasonHasMessage(null);
    }

    @Test
    public void should_fail_when_try_is_success() {
        thrown.expectAssertionError("\nExpecting Try to be a Failure, but wasn't");
        assertThat(Try.success("OK")).failReasonHasMessage("Some reason");
    }

    @Test
    public void should_pass_when_try_fail_with_specific_reason() {
        assertThat(Try.failure(new NullPointerException("ops"))).failReasonHasMessage("ops");
    }
}