package org.assertj.vavr.api;

import org.assertj.core.util.FailureMessages;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import io.vavr.control.Either;

import static org.assertj.vavr.api.EitherShouldContain.shouldContain;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class EitherAssert_contains_Test extends BaseTest {
  @Test
  public void should_fail_when_either_is_null() throws Exception {
    thrown.expectAssertionError(FailureMessages.actualIsNull());

    assertThat((Either<String, String>)null).containsOnLeft("left");
  }

  @Test
  public void should_fail_when_expected_left_value_is_null() throws Exception {
    thrown.expectIllegalArgumentException("The expected value on the [LEFT] should not be <null>.");

    assertThat(Either.left("expected-left")).containsOnLeft(null);
  }

  @Test
  public void should_pass_when_contains_expected_value_on_left() throws Exception {
    assertThat(Either.left("expected-left")).containsOnLeft("expected-left");
  }

  @Test
  public void should_fail_when_contains_unexpected_value_on_left() throws Exception {
    Either<String, String> actual = Either.left("UN-expected-left");
    String expectedValue = "expected-left";

    thrown.expectAssertionError(shouldContain(actual, expectedValue).create());

    assertThat(actual).containsOnLeft(expectedValue);
  }

  @Test
  public void should_fail_when_either_is_right_but_queried_to_left() throws Exception {
    Either<Object, String> actual = Either.right("expected-right");
    String expected = "expected-left";

    thrown.expectAssertionError(EitherShouldContain.shouldContainOnOtherSide(actual, expected).create());

    assertThat(actual).containsOnLeft(expected);
  }

  @Test
  public void should_fail_when_expected_right_value_is_null() throws Exception {
    thrown.expectIllegalArgumentException("The expected value on the [RIGHT] should not be <null>.");

    assertThat(Either.right("expected-right")).containsOnRight(null);
  }

  @Test
  public void should_pass_when_contains_expected_value_on_right() throws Exception {
    assertThat(Either.right("expected-right")).containsOnRight("expected-right");
  }

  @Test
  public void should_fail_when_contains_unexpected_value_on_right() throws Exception {
    Either<String, String> actual = Either.right("UN-expected-right");
    String expectedValue = "expected-right";

    thrown.expectAssertionError(shouldContain(actual, expectedValue).create());

    assertThat(actual).containsOnRight(expectedValue);
  }

  @Test
  public void should_fail_when_either_is_left_but_queried_to_right() throws Exception {
    Either<Object, String> actual = Either.left("expected-left");
    String expected = "expected-right";

    thrown.expectAssertionError(EitherShouldContain.shouldContainOnOtherSide(actual, expected).create());

    assertThat(actual).containsOnRight(expected);
  }
}
