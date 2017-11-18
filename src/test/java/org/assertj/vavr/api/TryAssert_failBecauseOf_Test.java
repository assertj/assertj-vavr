package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_failBecauseOf_Test extends BaseTest {

    @Test
    public void should_fail_when_try_is_null() throws Exception {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Try<String>) null).failBecauseOf(NullPointerException.class);
    }

    @Test
    public void should_fail_when_reason_is_null() throws Exception {
        thrown.expectIllegalArgumentException("The expected value should not be <null>.");
        assertThat(Try.failure(new NullPointerException())).failBecauseOf(null);
    }

    @Test
    public void should_fail_when_try_is_success() throws Exception {
        thrown.expectAssertionError("\nExpecting Try to be a Failure, but wasn't");
        assertThat(Try.success("OK")).failBecauseOf(Throwable.class);
    }

    @Test
    public void should_pass_when_try_fail_with_specific_reason() throws Exception {
        assertThat(Try.failure(new NullPointerException())).failBecauseOf(NullPointerException.class);
    }
}