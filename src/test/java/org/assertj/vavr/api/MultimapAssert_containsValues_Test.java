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

import io.vavr.collection.HashMultimap;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_containsValues_Test {

  @Test
  void should_pass_if_Multimap_contains_given_values() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key2", "value2");

    assertThat(actual).containsValues("value1", "value2");
  }

  @Test
  void should_fail_when_Multimap_is_null() {
    assertThatThrownBy(
            () -> assertThat((Multimap<String, String>) null).containsValues("value")
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_values_parameter_is_null() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsValues((String[]) null)
    )
            .isInstanceOf(NullPointerException.class)
            .hasMessage("The array of values to look for should not be null");
  }

  @Test
  void should_fail_if_Multimap_does_not_contain_all_given_values() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsValues("value-1", "value-3")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting actual:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-2, value-2))\n" +
                "to contain value:\n" +
                "  \"value-3\""
        );
  }
}
