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

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class SetAssert_allSatisfy_Test {

    private static final Consumer<String> EMPTY_CONSUMER = element -> {};

    @Test
    void should_pass_if_Set_is_empty() {
        assertThat(HashSet.<String>empty()).allSatisfy(EMPTY_CONSUMER);
    }

    @Test
    void should_pass_if_all_Set_entries_satisfy_consumer() {
        final Set<String> actual = HashSet.of("value1", "value2");

        assertThat(actual).allSatisfy(element -> {
            assertThat(element).startsWith("value");
        });
    }

    @Test
    void should_fail_if_consumer_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashSet.empty()).allSatisfy(null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The Consumer<T> expressing the assertions requirements must not be null");
    }

    @Test
    void should_fail_when_Set_is_null() {
        assertThatThrownBy(
                () -> assertThat((Set<String>) null).allSatisfy(EMPTY_CONSUMER)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_all_Set_elements_do_not_satisfy_consumer() {
        final Set<String> actual = HashSet.of("value1", "value2");

        assertThatThrownBy(
                () -> assertThat(actual).allSatisfy(element -> {
                    assertThat(element).isEqualTo("value1");
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\n" +
                        "Expecting all elements of:\n" +
                        "  HashSet(value1, value2)\n" +
                        "to satisfy given requirements, but these elements did not:\n" +
                        "\n" +
                        "\"value2\"\n" +
                        "error: \n" +
                        "expected: \"value1\"\n" +
                        " but was: \"value2\"");
    }
}
