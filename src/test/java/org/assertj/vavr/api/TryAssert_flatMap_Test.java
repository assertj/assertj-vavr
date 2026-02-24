/*
 * Copyright 2017-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_flatMap_Test {

    private static final Function<String, Try<Integer>> PARSE_INT = s -> Try.of(() -> Integer.parseInt(s));

    @Test
    void should_fail_when_Try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).flatMap(PARSE_INT)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_pass_when_Try_is_failure() {
        assertThat(Try.<String>failure(new RuntimeException("boom")))
                .flatMap(PARSE_INT)
                .isFailure();
    }

    @Test
    void should_pass_when_Try_contains_a_value() {
        assertThat(Try.success("42"))
                .contains("42")
                .flatMap(PARSE_INT)
                .contains(42);
    }

    @Test
    void should_return_TryAssert_to_allow_chaining() {
        TryAssert<Integer> mapped = assertThat(Try.success("42")).flatMap(PARSE_INT);
        mapped.contains(42);
    }
}
