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

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_containsInstanceOf_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).containsInstanceOf(String.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        assertThatThrownBy(
                () -> assertThat(Try.failure(new NullPointerException()))
                        .containsInstanceOf(NullPointerException.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessageMatching(
                        "\\nExpecting Try to be a Success, but was a Failure:\\n" +
                        "- exception class: java\\.lang\\.NullPointerException\\n" +
                        "- message: null\\n" +
                        "- stack trace:\\n" +
                        "(\\tat .*(\\n)?)+");
    }

    @Test
    void should_fail_when_success_try_contains_value_of_different_class() {
        assertThatThrownBy(
                () -> assertThat(Try.success("OK")).containsInstanceOf(Integer.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n <Success>\nto contain a value that is an instance of:\n <java.lang.Integer>\nbut did contain an instance of:\n <java.lang.String>");
    }

    @Test
    void should_pass_when_success_try_contains_value_of_expected_class() {
        assertThat(Try.success("OK")).containsInstanceOf(String.class);
    }
}
