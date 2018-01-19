/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;

import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnLeft;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class EitherAssert_containsLeft_Test extends BaseTest {

  @Test
  public void should_fail_when_either_is_null() throws Exception {
    thrown.expectAssertionError(actualIsNull());

    assertThat((Either<String, String>) null).containsLeft("something");
  }

  @Test
  public void should_fail_if_expected_value_is_null() throws Exception {
    thrown.expectIllegalArgumentException("The expected value should not be <null>.");

    assertThat(Either.left("something")).containsLeft(null);
  }

  @Test
  public void should_pass_if_either_contains_expected_value_on_left_side() throws Exception {
    assertThat(Either.left("something")).containsLeft("something");
  }

  @Test
  public void should_fail_if_either_does_not_contain_expected_value_on_left_side() throws Exception {
    Either<String, String> actual = Either.left("something");
    String expectedValue = "nothing";

    thrown.expectAssertionError(shouldContainOnLeft(actual, expectedValue).create());

    assertThat(actual).containsLeft(expectedValue);
  }

  @Test
  public void should_fail_if_either_is_right() throws Exception {
    Either<String, String> actual = Either.right("nothing");
    String expectedValue = "something";

    thrown.expectAssertionError(shouldBeLeft(actual).create());

    assertThat(actual).containsLeft(expectedValue);
  }
}
