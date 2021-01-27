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
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class OptionAssert_flatMap_Test {

    private static final Function<String, Option<String>> UPPER_CASE_OPTIONAL_STRING = s -> (s == null)
            ? Option.none()
            : Option.of(s.toUpperCase());

    @Test
    void should_fail_when_option_is_null() {
        assertThatThrownBy(
                () -> assertThat((Option<String>) null).flatMap(UPPER_CASE_OPTIONAL_STRING)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_when_option_is_empty() {
        assertThat(Option.<String>none()).flatMap(UPPER_CASE_OPTIONAL_STRING).isEmpty();
    }

    @Test
    void should_pass_when_option_contains_a_value() {
        assertThat(Option.of("present")).contains("present")
                .flatMap(UPPER_CASE_OPTIONAL_STRING)
                .contains("PRESENT");
    }
}
