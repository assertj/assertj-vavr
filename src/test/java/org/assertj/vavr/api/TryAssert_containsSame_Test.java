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
 * Copyright 2017-2025 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_containsSame_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).containsSame("")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_expected_value_is_null() {
        assertThatThrownBy(
                () -> assertThat(Try.success("some value")).containsSame(null)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n  <Success(some value)>\nto contain the instance (i.e. compared with ==):\n  <null>\nbut did not.");
    }

    @Test
    void should_pass_when_null_success_try_checked_on_containing_same_value() {
        assertThat(Try.success(null)).containsSame(null);
    }

    @Test
    void should_fail_when_success_try_contains_not_the_same_value() {
        final String actual = "OK";
        final String expected = new String(actual);
        assertThatThrownBy(
                () -> assertThat(Try.success(actual)).containsSame(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"OK\">\nbut did not.");
    }

    @Test
    void should_fail_when_success_try_contains_different_value() {
        final String actual = "OK";
        final String expected = "different";
        assertThatThrownBy(
                () -> assertThat(Try.success(actual)).containsSame(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n  <Success(OK)>\nto contain the instance (i.e. compared with ==):\n  <\"different\">\nbut did not.");
    }

    @Test
    void should_pass_when_success_try_contains_the_same_value() {
        final String value = "OK";
        assertThat(Try.success(value)).containsSame(value);
    }

    @Test
    void should_fail_when_try_is_a_failure() {
        final NullPointerException exception = new NullPointerException();
        assertThatThrownBy(
                () -> assertThat(Try.failure(exception)).containsSame(exception)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessageStartingWith("\nExpecting Try to contain:\n  <java.lang.NullPointerException");
    }
}
