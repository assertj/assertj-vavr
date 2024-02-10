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
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_containsInvalidSatisfying_Condition_Test {

    private final Condition<String> witchTale = new Condition<>(s -> s.startsWith("Witch"), "a %s tale", "witch");

    @Test
    void should_fail_when_validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, String>) null).containsInvalidSatisfying(witchTale)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_provided_condition_is_null() {
        assertThatThrownBy(
                () -> assertThat(Validation.invalid("something")).containsInvalidSatisfying((Condition<? super String>) null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The condition to evaluate should not be null");
    }

    @Test
    void should_pass_if_validation_contains_invalid_value_satisfying_expected_conditions() {
        assertThat(Validation.invalid("Witch scary tale")).containsInvalidSatisfying(witchTale);
    }

    @Test
    void should_fail_if_validation_contains_invalid_value_not_satisfying_expected_conditions() {
        Validation<String, String> actual = Validation.invalid("something");

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalidSatisfying(witchTale)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting actual:\n  \"something\"\nto be a witch tale");
    }

    @Test
    void should_fail_if_validation_is_valid() {
        Validation<String, String> actual = Validation.valid("nothing");

        assertThatThrownBy(
                () -> assertThat(actual).containsInvalidSatisfying(witchTale)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInvalid(actual).create());
    }
}
