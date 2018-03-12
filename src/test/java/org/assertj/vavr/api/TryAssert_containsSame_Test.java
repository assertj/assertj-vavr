package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_containsSame_Test extends BaseTest {

  @Test
  public void should_fail_when_try_is_null() {
    thrown.expectAssertionError(actualIsNull());
    assertThat((Try<String>) null).containsSame("");
  }

  @Test
  public void should_fail_when_expected_value_is_null() {
    thrown.expectIllegalArgumentException("The expected value should not be <null>.");
    assertThat(Try.success("some value")).containsSame(null);
  }

  @Test
  public void should_fail_when_success_try_contains_not_the_same_value() {
    thrown.expectAssertionError(
        "\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"OK\">\nbut did not.");
    final String actual = "OK";
    final String expected = new String(actual);
    assertThat(Try.success(actual)).containsSame(expected);
  }

  @Test
  public void should_fail_when_success_try_contains_different_value() {
    thrown.expectAssertionError(
        "\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"different\">\nbut did not.");
    final String actual = "OK";
    final String expected = "different";
    assertThat(Try.success(actual)).containsSame(expected);
  }

  @Test
  public void should_pass_when_success_try_contains_the_same_value() {
    final String value = "OK";
    assertThat(Try.success(value)).containsSame(value);
  }

  @Test
  public void should_fail_when_try_is_a_failure() {
    thrown.expectAssertionError(
        "\nExpecting Try to contain:\n  <java.lang.NullPointerException>\nbut was empty.");
    final NullPointerException exception = new NullPointerException();
    assertThat(Try.failure(exception)).containsSame(exception);
  }
}