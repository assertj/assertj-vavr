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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_containsKeys_Test {

  @Test
  void should_pass_if_Map_contains_given_keys() {
    final Map<String, String> actual = HashMap.of("key1", "value1", "key2", "value2");

    assertThat(actual).containsKeys("key1");
  }

  @Test
  void should_fail_when_Map_is_null() {
    assertThatThrownBy(
            () -> assertThat((Map<String, String>) null).containsKeys("key")
    )
            .isInstanceOf(AssertionError.class)
            .hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_keys_parameter_is_null() {
    final Map<String, String> actual = HashMap.of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsKeys((String[]) null)
    )
            .isInstanceOf(NullPointerException.class)
            .hasMessage("The array of keys to look for should not be null");
  }

  @Test
  void should_fail_if_keys_parameter_is_empty() {
    final Map<String, String> actual = HashMap.of("key", "value");

    assertThatThrownBy(
            () -> assertThat(actual).containsKeys(new String[0])
    )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The array of keys to look for should not be empty");
  }

  @Test
  void should_fail_if_Map_contains_different_keys() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsKeys("key-1", "key-3")
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                " <HashMap((key-1, value-1), (key-2, value-2))>\n" +
                "to contain key:\n" +
                " <\"key-3\">"
        );
  }
}
