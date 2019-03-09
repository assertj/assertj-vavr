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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldContainInstanceOf.shouldContainOnLeftInstanceOf;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_containsLeftInstanceOf_Test {

    @Test
    void should_fail_if_either_is_null() {
        Either<Object, Object> actual = null;

        assertThatThrownBy(
                () -> assertThat(actual).containsLeftInstanceOf(Object.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_either_is_right() {
        Either<String, Object> actual = Either.right("some");

        assertThatThrownBy(
                () -> assertThat(actual).containsLeftInstanceOf(Object.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeLeft(actual).create());
    }

    @Test
    void should_pass_if_either_contains_required_type_on_left() {
        assertThat(Either.left("something"))
                .containsLeftInstanceOf(String.class)
                .containsLeftInstanceOf(Object.class);
    }

    @Test
    void should_pass_if_either_contains_required_type_subclass_on_left() {
        assertThat(Either.left(new SubClass())).containsLeftInstanceOf(ParentClass.class);
    }

    @Test
    void should_fail_if_either_contains_other_type_on_left_than_required() {
        Either<Object, ParentClass> actual = Either.left(new ParentClass());

        assertThatThrownBy(
                () -> assertThat(actual).containsLeftInstanceOf(OtherClass.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainOnLeftInstanceOf(actual, OtherClass.class).create());
    }

    private static class ParentClass {
    }

    private static class SubClass extends ParentClass {
    }

    private static class OtherClass {
    }
}