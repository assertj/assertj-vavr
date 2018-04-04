package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_contains_Test extends BaseTest {

  @Test
  public void should_fail_when_try_is_null() {
    thrown.expectAssertionError(actualIsNull());
    assertThat((Try<String>) null).contains("");
  }

  @Test
  public void should_fail_when_expected_value_is_null()  {
    thrown.expectIllegalArgumentException("The expected value should not be <null>.");
    assertThat(Try.success("some value")).contains(null);
  }

  @Test
  public void should_pass_when_success_try_contains_equal_value()  {
    final String actual = "OK";
    final String expected = new String(actual);
    assertThat(Try.success(actual)).contains(expected);
  }

  @Test
  public void should_pass_when_success_try_contains_same_value()  {
    final String value = "OK";
    assertThat(Try.success(value)).contains(value);
  }

  @Test
  public void should_fail_when_success_try_contains_different_value()  {
    thrown.expectAssertionError(
        "\nExpecting:\n  <Success(OK)>\nto contain:\n  <\"different\">\nbut did not.");
    final String actual = "OK";
    final String expected = "different";
    assertThat(Try.success(actual)).contains(expected);
  }

  @Test
  public void should_fail_when_try_is_a_failure()  {
    thrown.expectAssertionError(
        "\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
    final NullPointerException exception = new NullPointerException();
    assertThat(Try.failure(exception)).contains(exception);
  }
}