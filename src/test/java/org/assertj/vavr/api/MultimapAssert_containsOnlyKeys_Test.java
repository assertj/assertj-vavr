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

import io.vavr.collection.HashMultimap;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_containsOnlyKeys_Test {

  @Test
  void should_pass_if_Multimap_contains_only_given_keys() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");

    assertThat(actual).containsOnlyKeys("key");
  }

  @Test
  void should_fail_when_Multimap_is_null() {
    assertThatThrownBy(
            () -> assertThat((Multimap<String, String>) null).containsOnlyKeys("key")
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_keys_parameter_is_null() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsOnlyKeys((String[]) null)
    )
            .isInstanceOf(NullPointerException.class)
            .hasMessage("The array of keys to look for should not be null");
  }

  @Test
  void should_fail_if_keys_parameter_is_empty() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key", "value");
    final String[] keys = new String[0];

    assertThatThrownBy(
            () -> assertThat(actual).containsOnlyKeys(keys)
    )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The array of keys to look for should not be empty");
  }

  @Test
  void should_fail_if_Multimap_contains_more_than_given_keys() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnlyKeys("key-1")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-2, value-2))\n" +
                "to contain only following keys:\n" +
                "  HashSet(key-1)\n" +
                "keys not found:\n" +
                "  HashSet()\n" +
                "and keys not expected:\n" +
                "  HashSet(key-2)\n"
        );
  }

  @Test
  void should_fail_if_Multimap_has_same_size_but_contains_different_keys() {
    Multimap<String, String> actual = HashMultimap.withSeq().of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnlyKeys("key-1", "key-3")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  HashMultimap[List]((key-1, value-1), (key-2, value-2))\n" +
                "to contain only following keys:\n" +
                "  HashSet(key-1, key-3)\n" +
                "keys not found:\n" +
                "  HashSet(key-3)\n" +
                "and keys not expected:\n" +
                "  HashSet(key-2)\n"
        );
  }
}
