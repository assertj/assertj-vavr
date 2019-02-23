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
 * Copyright 2012-2019 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnRight;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_containsOnRight_usingValueComparator_Test {

    private static Comparator<Foo> FOO_COMPARATOR = Comparator
            .comparing(o -> o.getValue().toLowerCase());

    @Test
    void should_fail_when_either_is_null() {
        assertThatThrownBy(() -> assertThat((Either<String, Foo>) null).usingValueComparator(FOO_COMPARATOR)
                .containsOnRight(new Foo("something"))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_either_is_left_sided() {
        final Either<Foo, Object> actual = Either.left(new Foo("something"));

        assertThatThrownBy(
                () -> assertThat(actual).usingValueComparator(FOO_COMPARATOR)
                        .containsOnRight(new Object())
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeRight(actual).create());
    }

    @Test
    void should_pass_if_right_sided_either_contains_expected_value() {
        assertThat(Either.right(new Foo("something")))
                .usingValueComparator(FOO_COMPARATOR)
                .containsOnRight(new Foo("SoMething"));
    }

    @Test
    void should_fail_if_right_sided_either_does_not_contain_expected_value() {
        Either<String, Foo> actual = Either.right(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        assertThatThrownBy(
                () -> assertThat(actual).usingValueComparator(FOO_COMPARATOR).containsOnRight(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainOnRight(actual, expectedValue).create());
    }

    private static class Foo {

        private final String value;

        Foo(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Foo{value='" + value + "'}";
        }
    }
}
