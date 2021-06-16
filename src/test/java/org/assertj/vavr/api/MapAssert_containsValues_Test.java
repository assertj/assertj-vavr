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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_containsValues_Test {

  @Test
  void should_pass_if_Map_contains_given_values() {
    final Map<String, String> actual = HashMap.of("key1", "value1", "key2", "value2");

    assertThat(actual).containsValues("value1", "value2");
  }

  @Test
  void should_fail_when_Map_is_null() {
    assertThatThrownBy(
            () -> assertThat((Map<String, String>) null).containsValues("value")
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_values_parameter_is_null() {
    final Map<String, String> actual = HashMap.of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsValues((String[]) null)
    )
            .isInstanceOf(NullPointerException.class)
            .hasMessage("The array of values to look for should not be null");
  }

  @Test
  void should_fail_if_Map_does_not_contain_all_given_values() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsValues("value-1", "value-3")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting actual:\n" +
                "  HashMap((key-1, value-1), (key-2, value-2))\n" +
                "to contain value:\n" +
                "  \"value-3\""
        );
  }
}
