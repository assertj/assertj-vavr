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

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.ValidationShouldContain.shouldContainValid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationAssert_containsValid_Test {

    @Test
    void should_fail_when_validation_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Validation<String, String>) null).containsValid("something"),
                actualIsNull());
    }

    @Test
    void should_fail_if_expected_value_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> assertThat(Validation.valid("something")).containsValid(null),
                "The expected value should not be <null>.");
    }

    @Test
    void should_pass_if_validation_contains_expected_valid_value() {
        assertThat(Validation.valid("something")).containsValid("something");
    }

    @Test
    void should_fail_if_validation_does_not_contain_expected_valid_value() {
        Validation<String, String> actual = Validation.valid("something");
        String expectedValue = "nothing";

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsValid(expectedValue),
                shouldContainValid(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_validation_is_invalid() {
        Validation<String, String> actual = Validation.invalid("nothing");
        String expectedValue = "something";

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsValid(expectedValue),
                shouldBeValid(actual).create());
    }
}