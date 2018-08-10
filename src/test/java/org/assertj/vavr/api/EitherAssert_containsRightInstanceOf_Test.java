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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;
import static org.assertj.vavr.api.EitherShouldContainInstanceOf.shouldContainOnRightInstanceOf;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EitherAssert_containsRightInstanceOf_Test {

    @Test
    void should_fail_if_either_is_null() {
        Either<Object, Object> actual = null;

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsRightInstanceOf(Object.class),
                actualIsNull());
    }

    @Test
    void should_fail_if_either_is_left() {
        Either<String, Object> actual = Either.left("some");

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsRightInstanceOf(Object.class),
                shouldBeRight(actual).create());
    }

    @Test
    void should_pass_if_either_contains_required_type_on_right() {
        assertThat(Either.right("something")).containsRightInstanceOf(String.class)
          .containsRightInstanceOf(Object.class);
    }

    @Test
    void should_pass_if_either_contains_required_type_subclass_on_right() {
        assertThat(Either.right(new SubClass())).containsRightInstanceOf(ParentClass.class);
    }

    @Test
    void should_fail_if_either_contains_other_type_on_right_than_required() {
        Either<Object, ParentClass> actual = Either.right(new ParentClass());

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsRightInstanceOf(OtherClass.class),
                shouldContainOnRightInstanceOf(actual, OtherClass.class).create());
    }

    private static class ParentClass {
    }

    private static class SubClass extends ParentClass {
    }

    private static class OtherClass {
    }
}