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

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.ValidationShouldContainInstanceOf.shouldContainValidInstanceOf;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsValidInstanceOf_Test {

    @Test
    void should_fail_if_validation_is_null() {
        Validation<Object, Object> actual = null;

        assertThatThrownBy(
                () -> assertThat(actual).containsValidInstanceOf(Object.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_validation_is_invalid() {
        Validation<String, Object> actual = Validation.invalid("some");

        assertThatThrownBy(
                () -> assertThat(actual).containsValidInstanceOf(Object.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeValid(actual).create());
    }

    @Test
    void should_pass_if_validation_contains_valid_value_of_required_type() {
        assertThat(Validation.valid("something")).containsValidInstanceOf(String.class)
                .containsValidInstanceOf(Object.class);
    }

    @Test
    void should_pass_if_validation_contains_required_type_subclass() {
        assertThat(Validation.valid(new SubClass())).containsValidInstanceOf(ParentClass.class);
    }

    @Test
    void should_fail_if_validation_contains_other_type_than_required() {
        Validation<Object, ParentClass> actual = Validation.valid(new ParentClass());

        assertThatThrownBy(
                () -> assertThat(actual).containsValidInstanceOf(OtherClass.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainValidInstanceOf(actual, OtherClass.class).create());
    }

    private static class ParentClass {
    }

    private static class SubClass extends ParentClass {
    }

    private static class OtherClass {
    }
}