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
 * Copyright 2017-2020 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.OptionShouldBeEmpty.shouldBeEmpty;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class OptionAssert_isEmpty_Test {

    @Test
    void should_pass_if_Option_is_empty() {
        assertThat(Option.none()).isEmpty();
    }

    @Test
    void should_fail_when_Option_is_null() {
        assertThatThrownBy(
                () -> assertThat((Option<String>) null).isEmpty()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_Option_is_present() {
        Option<String> actual = Option.of("not-empty");

        assertThatThrownBy(
                () -> assertThat(actual).isEmpty()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeEmpty(actual).create());
    }
}
