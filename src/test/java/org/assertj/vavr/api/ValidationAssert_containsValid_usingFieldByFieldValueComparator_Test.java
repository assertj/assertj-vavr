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
 * Copyright 2017-2020 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.ValidationShouldContain.shouldContainValid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsValid_usingFieldByFieldValueComparator_Test {

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, Foo>) null)
                        .usingFieldByFieldValueComparator()
                        .containsValid(new Foo("something"))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_if_valid_validation_contains_expected_value() {
        assertThat(Validation.valid(new Foo("something"))).usingFieldByFieldValueComparator()
                .containsValid(new Foo("something"));
    }

    @Test
    void should_fail_if_valid_validation_does_not_contain_expected_value() {
        Validation<String, Foo> actual = Validation.valid(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        assertThatThrownBy(
                () -> assertThat(actual).usingFieldByFieldValueComparator().containsValid(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainValid(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_validation_is_invalid() {
        Foo expectedValue = new Foo("test");
        final Validation<Foo, Object> actual = Validation.invalid(new Foo("something else"));

        assertThatThrownBy(
                () -> assertThat(actual).usingFieldByFieldValueComparator().containsValid(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeValid(actual).create());
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
