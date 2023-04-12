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

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.OptionShouldBePresent.shouldBePresent;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class OptionAssert_hasValueSatisfying_Test {

    @Test
    void should_fail_when_option_is_null() {
        assertThatThrownBy(
                () -> assertThat((Option<String>) null).hasValueSatisfying(s -> {
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_option_is_empty() {
        assertThatThrownBy(
                () -> assertThat(Option.none()).hasValueSatisfying(o -> {
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBePresent().create());
    }

    @Test
    void should_pass_when_consumer_passes() {
        assertThat(Option.of("something")).hasValueSatisfying(s -> assertThat(s).isEqualTo("something")
                .startsWith("some")
                .endsWith("thing"));
        assertThat(Option.of(10)).hasValueSatisfying(i -> assertThat(i).isGreaterThan(9));
    }

    @Test
    void should_fail_from_consumer() {
        assertThatThrownBy(
                () -> assertThat(Option.of("something else"))
                        .hasValueSatisfying(s -> assertThat(s).isEqualTo("something"))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nexpected: \"something\"\n but was: \"something else\"");
    }
}
