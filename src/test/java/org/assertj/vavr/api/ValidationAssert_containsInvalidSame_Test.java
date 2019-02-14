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
 * Copyright 2012-2019 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.ValidationShouldContain.shouldContainInvalidSame;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsInvalidSame_Test {

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, String>) null).containsInvalidSame("something")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_expected_value_is_null() {
        assertThatThrownBy(
                () -> assertThat(Validation.invalid("something")).containsInvalidSame(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The expected value should not be <null>.");
    }

    @Test
    void should_pass_if_validation_contains_same_instance_as_invalid_value() {
        final String value = "something";
        assertThat(Validation.invalid(value)).containsInvalidSame(value);
    }

    @Test
    void should_fail_if_validation_does_not_contain_same_instance_as_invalid_value() {
        Validation<String, String> actual = Validation.invalid("something");
        final String expectedValue = new String("something");

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalidSame(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainInvalidSame(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_validation_is_valid() {
        Validation<String, String> actual = Validation.valid("nothing");
        String expectedValue = "something";

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalidSame(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInvalid(actual).create());
    }
}
