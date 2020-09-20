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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_doesNotContainKey_Test {

  @Test
  void should_pass_if_Map_does_not_contain_given_key() {
    final Map<String, String> actual = HashMap.of("key1", "value1", "key2", "value2");

    assertThat(actual).doesNotContainKey("key3");
  }

  @Test
  void should_fail_when_Map_is_null() {
    assertThatThrownBy(
            () -> assertThat((Map<String, String>) null).doesNotContainKey("key")
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_Map_contains_given_key() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).doesNotContainKey("key-1")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  <[(key-1, value-1), (key-2, value-2)]>\n" +
                "not to contain key:\n" +
                "  <\"key-1\">"
        );
  }

  @Test
  void should_pass_if_Map_does_not_contain_null_as_key() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThat(actual).doesNotContainKey(null);
  }

  @Test
  void should_fail_if_Map_contains_null_as_key() {
    final Map<String, String> actual = HashMap.of(null, "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).doesNotContainKey(null)
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  <[(null, value-1), (key-2, value-2)]>\n" +
                "not to contain key:\n" +
                "  <null>"
        );
  }
}
