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

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MultimapAssert_containsExactly_Test {

    private static final Tuple2<String, String> ENTRY1 = Tuple.of("key1", "value1");
    private static final Tuple2<String, String> ENTRY2 = Tuple.of("key2", "value2");
    private static final Tuple2<String, String> ENTRY3 = Tuple.of("key3", "value3");

    @Test
    void should_pass_if_Multimap_contains_given_entries_in_order() {
        Multimap<String, String> actual = HashMultimap.withSeq().of(
                "key1", "value1", "key2", "value2", "key3", "value3"
        );

        assertThat(actual).containsExactly(ENTRY1, ENTRY2, ENTRY3);
    }

    @Test
    void should_pass_if_Multimap_and_entries_parameter_are_empty() {
        final Tuple2<String, String>[] entries = new Tuple2[0];
        assertThat(HashMultimap.withSeq().<String, String>empty()).containsExactly(entries);
    }

    @Test
    void should_fail_if_Multimap_is_not_empty_and_entries_is_an_empty_array() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key3", "value3");
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
                () -> assertThat(HashMultimap.withSeq().<String, String>empty()).containsExactly((Tuple2<String, String>[]) null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The array of entries should not be null");
    }

    @Test
    void should_fail_if_Multimap_and_entries_have_different_sizes() {
        assertThatThrownBy(
                () -> assertThat(HashMultimap.withSeq().<String, String>empty()).containsExactly(ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nActual and expected should have same size but actual size is:\n" +
                        "  0\n" +
                        "while expected size is:\n" +
                        "  1\n" +
                        "Actual was:\n" +
                        "  HashMultimap[List]()\n" +
                        "Expected was:\n" +
                        "  [(key1, value1)]"
                );
    }

    @Test
    void should_fail_if_one_of_entries_is_null() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY1, null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("One of expected entries is null");
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).containsExactly(ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Multimap_does_not_contain_all_entries() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY1, ENTRY2)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nExpecting actual:\n" +
                        "  HashMultimap[List]((key1, value1), (key3, value3))\n" +
                        "to contain exactly (and in same order):\n" +
                        "  List((key1, value1), (key2, value2))\n" +
                        "but some elements were not found:\n" +
                        "  LinkedHashMultimap[List]((key2, value2))\n" +
                        "and others were not expected:\n" +
                        "  HashMultimap[List]((key3, value3))\n"
                );
    }

    @Test
    void should_fail_if_Multimap_does_not_contain_all_entries_in_same_order() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsExactly(ENTRY3, ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "\nActual and expected have the same elements but not in the same order, at index 0 actual element was:\n" +
                        "  (key1, List(value1))\n" +
                        "whereas expected element was:\n" +
                        "  (key3, value3)\n"
                );
    }
}
