package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_containsInstanceOf_Test extends BaseTest {

    @Test
    public void should_fail_when_try_is_null() throws Exception {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Try<String>) null).containsInstanceOf(String.class);
    }

    @Test
    public void should_fail_when_try_is_a_failure() throws Exception {
        thrown.expectAssertionError(
            "\nExpecting Try to be a Success, but wasn't");
        assertThat(Try.failure(new NullPointerException())).containsInstanceOf(NullPointerException.class);
    }

    @Test
    public void should_fail_when_success_try_contains_value_of_different_class() throws Exception {
        thrown.expectAssertionError(
            "\nExpecting:\n <Success>\nto contain a value that is an instance of:\n <java.lang.Integer>\nbut did contain an instance of:\n <java.lang.String>");
        assertThat(Try.success("OK")).containsInstanceOf(Integer.class);
    }

    @Test
    public void should_pass_when_success_try_contains_value_of_expected_class() throws Exception {
        assertThat(Try.success("OK")).containsInstanceOf(String.class);
    }
}