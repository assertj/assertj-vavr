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

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_containsOnly_Test {

  @Test
  void should_pass_if_Multimap_contains_only_given_entries() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");

    assertThat(actual).containsOnly(List.of(Tuple.of("key", "value")));
  }

  @Test
  void should_fail_when_Multimap_is_null() {
    assertThatThrownBy(
            () -> assertThat((Multimap<String, String>) null).containsOnly(List.of(Tuple.of("key", "value")))
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_entries_parameter_is_null() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsOnly(null)
    )
            .isInstanceOf(NullPointerException.class)
            .hasMessage("The entries should not be null");
  }

  @Test
  void should_fail_if_entries_parameter_is_empty_but_actual_Map_is_not() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");
    final Iterable<Tuple2<String, String>> entries = List.empty();

    assertThatThrownBy(
            () -> assertThat(actual).containsOnly(entries)
    )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The entries should not be empty");
  }

  @Test
  void should_fail_if_one_of_entries_is_null() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");
    final List<Tuple2<String, String>> entries = List.empty();

    assertThatThrownBy(
            () -> assertThat(actual).containsOnly(entries.append(null))
    )
            .isInstanceOf(NullPointerException.class);
  }

  @Test
  void should_fail_if_Multimap_contains_more_than_given_entries() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnly(HashMultimap.withSeq().of("key-1", "value-1"))
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting HashMultimap:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-2, value-2))\n" +
                "to contain only:\n" +
                "  HashMultimap[List]((key-1, value-1))\n" +
                "but the following element(s) were unexpected:\n" +
                "  HashMultimap[List]((key-2, value-2))\n"
        );
  }

  @Test
  void should_fail_if_Multimap_has_same_size_but_contains_different_entries() {
    final Multimap<String, String> actual = HashMultimap.withSeq().of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnly(HashMultimap.withSeq().of("key-1", "value-1", "key-3", "value-3"))
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting HashMultimap:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-2, value-2))\n" +
                "to contain only:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-3, value-3))\n" +
                "element(s) not found:\n" +
                "  HashMultimap[List]((key-3, value-3))\n" +
                "and element(s) not expected:\n" +
                "  HashMultimap[List]((key-2, value-2))\n"
        );
  }
}
