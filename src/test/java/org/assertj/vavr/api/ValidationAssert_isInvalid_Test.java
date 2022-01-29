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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ValidationAssert_isInvalid_Test {

    @Test
    void should_pass_if_Validation_is_invalid() {
        assertThat(Validation.invalid("invalid")).isInvalid();
    }

    @Test
    void should_fail_when_Validation_is_null() {
        assertThatThrownBy(
                () -> assertThat((Validation<String, String>) null).isInvalid()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_Validation_is_valid() {
        Validation<String, String> actual = Validation.valid("valid");

        assertThatThrownBy(
                () -> assertThat(actual).isInvalid()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeInvalid(actual).create());
    }
}
