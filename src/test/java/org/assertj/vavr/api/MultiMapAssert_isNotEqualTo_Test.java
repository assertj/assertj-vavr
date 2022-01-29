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

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMultimap;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultiMapAssert_isNotEqualTo_Test {

    @Test
    void should_pass_if_Map_is_not_equal_to() {
        assertThat(HashMultimap.withSeq().of(
                "key1", "value2",
                "key2", "value2"))
                .isNotEqualTo(HashMultimap.withSeq().of(
                        "otherKey1", "value2",
                        "key2", "value2"));
    }

    @Test
    void should_pass_when_Map_is_null() {
        final HashMultimap<String, String> actual = null;

        final HashMultimap<String, String> expected = HashMultimap.withSeq().of("a", "b");

        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void should_fail_if_Map_is_equal_to() {
        final HashMultimap<Object, Object> actual = HashMultimap.withSeq().of(
                "key1", "value2",
                "key2", "value2");

        final HashMultimap<Object, Object> expected = actual;

        assertThatThrownBy(
                () -> assertThat(actual).isNotEqualTo(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\n"
                        + "Expecting actual:\n"
                        + "  HashMultimap[List]((key1, value2), (key2, value2))\n"
                        + "not to be equal to:\n"
                        + "  HashMultimap[List]((key1, value2), (key2, value2))\n");
    }
}
