package org.assertj.vavr.api;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2019 the original author or authors.
 */

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MapAssert_containsExactly_Test {

    private static final Tuple2<String, String> ENTRY1 = Tuple.of("key1", "value1");
    private static final Tuple2<String, String> ENTRY2 = Tuple.of("key2", "value2");
    private static final Tuple2<String, String> ENTRY3 = Tuple.of("key3", "value3");

    @Test
    void should_pass_if_Map_contains_given_entries_in_order() {
        final Map<String, String> actual = LinkedHashMap.of(
                "key1", "value1", "key2", "value2", "key3", "value3"
        );

        assertThat(actual).containsExactly(ENTRY1, ENTRY2, ENTRY3);
    }

    @Test
    void should_pass_if_Map_and_entries_parameter_are_empty() {
        final Tuple2<String, String>[] entries = new Tuple2[0];
        assertThat(LinkedHashMap.<String, String>empty()).containsExactly(entries);
    }

    @Test
    void should_fail_if_Map_is_not_empty_and_entries_is_an_empty_array() {
        final Map<String, String> actual = LinkedHashMap.of("key1", "value1", "key3", "value3");
        final Tuple2<String, String>[] entries = new Tuple2[]{};

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(entries)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The array of entries to look for should not be empty");
    }

    @Test
    void should_fail_if_entries_parameter_is_null() {
        assertThatThrownBy(
                () -> assertThat(LinkedHashMap.<String, String>empty()).containsExactly((Tuple2<String, String>[]) null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The array of entries to look for should not be null");
    }

    @Test
    void should_fail_if_Map_and_entries_have_different_sizes() {
        assertThatThrownBy(
                () -> assertThat(LinkedHashMap.<String, String>empty()).containsExactly(ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nActual and expected should have same size but actual size is:\n" +
                        " <0>\n" +
                        "while expected size is:\n" +
                        " <1>\n" +
                        "Actual was:\n" +
                        " []\n" +
                        "Expected was:\n" +
                        " [(key1, value1)]"
                );
    }

    @Test
    void should_fail_if_one_of_entries_is_null() {
        final Map<String, String> actual = LinkedHashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY1, null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("One of expected entries is null");
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).containsExactly(ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_does_not_contain_all_entries() {
        final Map<String, String> actual = LinkedHashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY1, ENTRY2)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nExpecting:\n" +
                        "  <[(key1, value1), (key3, value3)]>\n" +
                        "to contain exactly (and in same order):\n" +
                        "  <[(key1, value1), (key2, value2)]>\n" +
                        "but some elements were not found:\n" +
                        "  <[(key2, value2)]>\n" +
                        "and others were not expected:\n" +
                        "  <[(key3, value3)]>\n"
                );
    }

    @Test
    void should_fail_if_Map_does_not_contain_all_entries_in_same_order() {
        final Map<String, String> actual = LinkedHashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY3, ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nActual and expected have the same elements but not in the same order, at index 0 actual element was:\n" +
                        "  <(key1, value1)>\n" +
                        "whereas expected element was:\n" +
                        "  <(key3, value3)>\n"
                );
    }
}
