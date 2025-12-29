/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2017-2025 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnRight;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_containsRight_Test {

    @Test
    void should_fail_when_either_is_null() {
        assertThatThrownBy(
                () -> assertThat((Either<String, String>) null).containsOnRight("something")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_if_either_contains_expected_value_on_right_side() {
        assertThat(Either.right("something")).containsOnRight("something");
    }

    @Test
    void should_fail_if_either_does_not_contain_expected_value_on_right_side() {
        Either<String, String> actual = Either.right("something");
        String expectedValue = "nothing";

        assertThatThrownBy(
                () -> assertThat(actual).containsOnRight(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainOnRight(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_either_is_left() {
        Either<String, String> actual = Either.left("nothing");
        String expectedValue = "something";

        assertThatThrownBy(
                () -> assertThat(actual).containsOnRight(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeRight(actual).create());
    }
}
