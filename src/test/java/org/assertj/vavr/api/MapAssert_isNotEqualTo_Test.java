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

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_isNotEqualTo_Test {

    @Test
    void should_pass_if_Map_is_not_equal_to() {
        assertThat(HashMap.of(
                "key1", "value2",
                "key2", "value2"))
                .isNotEqualTo(HashMap.of(
                        "otherKey1", "value2",
                        "key2", "value2"));
    }

    @Test
    void should_pass_when_Map_is_null() {
        final Map<String, String> actual = null;

        final Map<String, String> expected = HashMap.of("a", "b");

        assertThat(actual).isNotEqualTo(expected);

    }

    @Test
    void should_fail_if_Map_is_equal_to() {
        final Map<Object, Object> actual = HashMap.of(
                                        "key1", "value2",
                                        "key2", "value2");

        final Map<Object, Object> expected = actual;

        assertThatThrownBy(
                () -> assertThat(actual).isNotEqualTo(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\n"
                        + "Expecting actual:\n"
                        + "  HashMap((key1, value2), (key2, value2))\n"
                        + "not to be equal to:\n"
                        + "  HashMap((key1, value2), (key2, value2))\n");
    }
}
