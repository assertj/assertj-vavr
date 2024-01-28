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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.ValidationShouldContain.shouldContainInvalid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsInvalid_Test {

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, String>) null).containsInvalid("something")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_expected_value_is_null() {
        assertThatThrownBy(
                () -> assertThat(Validation.invalid("something")).containsInvalid(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The expected value should not be <null>.");
    }

    @Test
    void should_pass_if_validation_contains_expected_invalid_value() {
        assertThat(Validation.invalid("something")).containsInvalid("something");
    }

    @Test
    void should_fail_if_validation_does_not_contain_expected_invalid_value() {
        Validation<String, String> actual = Validation.invalid("something");
        String expectedValue = "nothing";

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalid(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainInvalid(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_validation_is_valid() {
        Validation<String, String> actual = Validation.valid("nothing");
        String expectedValue = "something";

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalid(expectedValue)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInvalid(actual).create());
    }
}
