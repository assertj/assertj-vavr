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

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.ValidationShouldContain.shouldContainInvalid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsInvalid_usingValueComparator_Test {

    private static Comparator<Foo> FOO_COMPARATOR = Comparator
            .comparing(o -> o.getValue().toLowerCase());

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, Foo>) null).usingValueComparator(FOO_COMPARATOR)
                        .containsInvalid("something")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_expected_value_is_null() {
        assertThatThrownBy(
                () -> assertThat(Validation.invalid(new Foo("something"))).usingValueComparator(FOO_COMPARATOR).containsInvalid(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The expected value should not be <null>.");
    }

    @Test
    void should_fail_if_validation_is_valid() {
        final Validation<Foo, Object> actual = Validation.valid(new Foo("something"));

        assertThatThrownBy(
                () -> assertThat(actual).usingValueComparator(FOO_COMPARATOR)
                        .containsInvalid(new Foo("something"))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInvalid(actual).create());
    }

    @Test
    void should_pass_if_invalid_validation_contains_expected_value() {
        assertThat(Validation.invalid(new Foo("something")))
                .usingValueComparator(FOO_COMPARATOR)
                .containsInvalid(new Foo("SoMething"));
    }

    @Test
    void should_fail_if_invalid_validation_does_not_contain_expected_value() {
        Validation<Foo, String> actual = Validation.invalid(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        assertThatThrownBy(
                () -> assertThat(actual).usingValueComparator(FOO_COMPARATOR).containsInvalid(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainInvalid(actual, expectedValue).create());
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
