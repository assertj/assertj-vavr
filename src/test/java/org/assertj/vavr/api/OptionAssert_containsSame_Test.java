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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.OptionShouldContain.shouldContain;
import static org.assertj.vavr.api.OptionShouldContain.shouldContainSame;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionAssert_containsSame_Test {

    @Test
    void should_fail_when_option_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Option<String>) null).containsSame("something"),
                actualIsNull());
    }

    @Test
    void should_fail_if_expected_value_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> assertThat(Option.of("something")).containsSame(null),
                "The expected value should not be <null>.");
    }

    @Test
    void should_pass_if_option_contains_the_expected_object_reference() {
        String containedAndExpected = "something";

        assertThat(Option.of(containedAndExpected)).containsSame(containedAndExpected);
    }

    @Test
    void should_fail_if_option_does_not_contain_the_expected_object_reference() {
        Option<String> actual = Option.of("not-expected");
        String expectedValue = "something";

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsSame(expectedValue),
                shouldContainSame(actual, expectedValue).create());
    }

    @SuppressWarnings("RedundantStringConstructorCall")
    @Test
    void should_fail_if_option_contains_equal_but_not_same_value() {
        Option<String> actual = Option.of(new String("something"));
        String expectedValue = new String("something");

        assertThrows(AssertionError.class,
                () -> assertThat(actual).containsSame(expectedValue),
                shouldContainSame(actual, expectedValue).create());
    }

    @Test
    void should_fail_if_option_is_empty() {
        String expectedValue = "something";

        assertThrows(AssertionError.class,
                () -> assertThat(Option.none()).containsSame(expectedValue),
                shouldContain(expectedValue).create());
    }
}
