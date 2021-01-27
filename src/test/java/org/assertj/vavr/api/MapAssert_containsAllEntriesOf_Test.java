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

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MapAssert_containsAllEntriesOf_Test {

    private static final Tuple2<String, String> ENTRY1 = Tuple.of("key1", "value1");
    private static final Tuple2<String, String> ENTRY2 = Tuple.of("key2", "value2");

    @Test
    void should_pass_if_Map_contains_all_entries() {
        final Map<String, String> actual = HashMap.of(
                "key1", "value1", "key2", "value2", "key3", "value3"
        );

        assertThat(actual).containsAllEntriesOf(List.of(ENTRY1, ENTRY2));
    }

    @Test
    void should_pass_if_map_and_entries_parameter_are_empty() {
        assertThat(HashMap.<String, String>empty()).containsAllEntriesOf(List.empty());
    }

    @Test
    void should_fail_if_Map_is_not_empty_and_entries_is_an_empty_array() {
        final Map<String, String> actual = HashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsAllEntriesOf(List.empty())
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("actual is not empty");
    }

    @Test
    void should_fail_if_entries_parameter_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashMap.<String, String>empty()).containsAllEntriesOf(null)
        )
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_fail_if_one_of_entries_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashMap.<String, String>empty()).containsAllEntriesOf(List.of(ENTRY1, null))
        )
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).containsAllEntriesOf(List.of(ENTRY1))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_does_not_contain_all_entries() {
        final Map<String, String> actual = HashMap.of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).containsAllEntriesOf(List.of(ENTRY1, ENTRY2))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting HashMap:\n <HashMap((key1, value1), (key3, value3))>\nto contain:\n <[(key1, value1), (key2, value2)]>\nbut could not find the following element(s):\n <HashSet((key2, value2))>\n");
    }
}
