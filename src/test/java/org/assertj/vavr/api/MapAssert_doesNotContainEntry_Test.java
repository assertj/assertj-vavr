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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MapAssert_doesNotContainEntry_Test {

    @Test
    void should_pass_if_Map_does_not_contain_the_given_entry() {
        final Map<String, String> actual = HashMap.of(
                "key1", "value1", "key2", "value2"
        );

        assertThat(actual).doesNotContainEntry("key3", "value3");
    }

    @Test
    void should_pass_if_Map_is_empty() {
        final Map<String, String> actual = HashMap.empty();
        assertThat(actual).doesNotContainEntry("key3", "value3");
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).doesNotContainEntry("key1", "value1")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_contains_the_given_entry() {
        final Map<String, String> actual = HashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).doesNotContainEntry("key1", "value1")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting\n  HashMap((key1, value1), (key3, value3))\nnot to contain\n  [(key1, value1)]\nbut found\n  HashSet((key1, value1))\n");
    }
}
