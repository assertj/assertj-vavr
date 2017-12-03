package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_containsSame_Test extends BaseTest {

    @Test
    public void should_fail_when_try_is_null() throws Exception {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Try<String>) null).containsSame("");
    }

    @Test
    public void should_fail_when_expected_value_is_null() throws Exception {
        thrown.expectIllegalArgumentException("The expected value should not be <null>.");
        assertThat(Try.success("some value")).containsSame(null);
    }

    @Test
    public void should_fail_when_success_try_contains_different_value() throws Exception {
        thrown.expectAssertionError(
            "\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"not ok\">\nbut did not.");
        assertThat(Try.success("OK")).containsSame("not ok");
    }

    @Test
    public void should_pass_when_success_try_contains_the_same_value() throws Exception {
        assertThat(Try.success("OK")).containsSame("OK");
    }

    @Test
    public void should_fail_when_try_is_a_failure() throws Exception {
        thrown.expectAssertionError("\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
        assertThat(Try.failure(new NullPointerException())).containsSame(new NullPointerException());
    }
}