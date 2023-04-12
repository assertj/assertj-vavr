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
 * Copyright 2017-2023 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsValidSatisfying_Consumer_Test {

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, String>) null).containsValidSatisfying(valid -> {})
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_provided_consumer_is_null() {
        assertThatThrownBy(
                () -> assertThat(Validation.valid("something")).containsValidSatisfying((Consumer<String>) null)
        )
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_pass_if_validation_contains_valid_value_satisfying_expected_conditions() {
        assertThat(Validation.valid("something")).containsValidSatisfying(
                valid -> assertThat(valid).isEqualTo("something"));
    }

    @Test
    void should_fail_if_validation_contains_valid_value_not_satisfying_expected_conditions() {
        Validation<String, String> actual = Validation.valid("something");
        String expectedValue = "nothing";

        assertThatThrownBy(
                () -> assertThat(actual).containsValidSatisfying(
                        valid -> assertThat(valid).isEqualTo(expectedValue))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nexpected: \"nothing\"\n but was: \"something\"");
    }

    @Test
    void should_fail_if_validation_is_invalid() {
        Validation<String, String> actual = Validation.invalid("nothing");

        assertThatThrownBy(
                () -> assertThat(actual).containsValidSatisfying(valid -> {})
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeValid(actual).create());
    }
}
