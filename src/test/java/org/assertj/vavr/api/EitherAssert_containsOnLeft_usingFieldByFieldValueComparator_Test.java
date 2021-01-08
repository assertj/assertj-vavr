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
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnLeft;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_containsOnLeft_usingFieldByFieldValueComparator_Test {

    @Test
    void should_fail_when_either_is_null() {
        assertThatThrownBy(
                () -> assertThat((Either<Foo, String>) null)
                        .usingFieldByFieldValueComparator()
                        .containsOnLeft(new Foo("something"))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_if_left_sided_either_contains_expected_value() {
        assertThat(Either.left(new Foo("something")))
                .usingFieldByFieldValueComparator()
                .containsOnLeft(new Foo("something"));
    }

    @Test
    void should_fail_if_left_sided_either_does_not_contain_expected_value() {
        Either<Foo, String> actual = Either.left(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        assertThatThrownBy(
                () -> assertThat(actual)
                        .usingFieldByFieldValueComparator()
                        .containsOnLeft(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainOnLeft(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_either_is_right_sided() {
        Foo expectedValue = new Foo("test");
        final Either<Object, Foo> actual = Either.right(new Foo("something else"));

        assertThatThrownBy(
                () -> assertThat(actual)
                        .usingFieldByFieldValueComparator()
                        .containsOnLeft(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeLeft(actual).create());
    }

    private static class Foo {

        private final String value;

        Foo(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Foo{" + "value='" + value + '\'' + '}';
        }
    }
}
