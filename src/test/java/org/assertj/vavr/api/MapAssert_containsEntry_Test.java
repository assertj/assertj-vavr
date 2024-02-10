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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MapAssert_containsEntry_Test {

    @Test
    void should_pass_if_Map_contains_the_given_entry() {
        final Map<String, String> actual = HashMap.of(
                "key1", "value1", "key2", "value2", "key3", "value3"
        );

        assertThat(actual).containsEntry("key1", "value1");
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).containsEntry("key1", "value1")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_does_not_contain_the_given_entry() {
        final Map<String, String> actual = HashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsEntry("key2", "value2")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting HashMap:\n  HashMap((key1, value1), (key3, value3))\nto contain:\n  [(key2, value2)]\nbut could not find the following element(s):\n  HashSet((key2, value2))\n");
    }
}
