package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.core.api.Condition;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_hasValueSatisfying_Condition_Test extends BaseTest {

  private Condition<Object> passingCondition = new TestCondition<>(true);
  private Condition<Object> notPassingCondition = new TestCondition<>();

  @Test
  public void should_fail_when_try_is_null() {
    thrown.expectAssertionError(actualIsNull());
    assertThat((Try<String>) null).hasValueSatisfying(passingCondition);
  }

  @Test
  public void should_fail_when_try_is_failure() {
    thrown.expectAssertionError("\nExpecting Try to be a Success, but wasn't");
    assertThat(Try.failure(new NullPointerException())).hasValueSatisfying(passingCondition);
  }

  @Test
  public void should_fail_when_value_does_not_satisfy_consumer() {
    thrown.expectAssertionError("\nExpecting:\n <\"OK\">\nto be <TestCondition>");
    assertThat(Try.success("OK")).hasValueSatisfying(notPassingCondition);
  }

  @Test
  public void should_pass_when_value_satisfies_consumer() {
    assertThat(Try.success("OK")).hasValueSatisfying(passingCondition);
  }

}