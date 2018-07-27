package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_isFailure_Test extends BaseTest {

    @Test
    public void should_fail_when_try_is_null() {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Try<String>) null).isFailure();
    }

    @Test
    public void should_fail_when_try_is_success() {
        thrown.expectAssertionError("\nExpecting Try to be a Failure, but wasn't");
        assertThat(Try.success("OK")).isFailure();
    }

    @Test
    public void should_pass_when_try_is_failure() {
        assertThat(Try.failure(new NullPointerException())).isFailure();
    }
}