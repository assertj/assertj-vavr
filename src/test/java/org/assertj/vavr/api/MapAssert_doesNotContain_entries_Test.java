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
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MapAssert_doesNotContain_entries_Test {

    private static final Tuple2<String, String> ENTRY1 = Tuple.of("key1", "value1");
    private static final Tuple2<String, String> ENTRY2 = Tuple.of("key2", "value2");

    @Test
    void should_pass_if_Map_does_not_contain_the_given_entries() {
        final Map<String, String> actual = HashMap.of("key3", "value3");
        assertThat(actual).doesNotContain(ENTRY1, ENTRY2);
    }

    @Test
    void should_fail_if_entries_parameter_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashMap.<String, String>empty()).doesNotContain((Tuple2<String, String>[]) null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The array of entries to look for should not be null");
    }

    @Test
    void should_fail_if_entries_parameter_are_empty() {
        final Tuple2<String, String>[] entries = new Tuple2[0];
        assertThatThrownBy(
                () ->
                        assertThat(HashMap.<String, String>empty()).doesNotContain(entries)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The array of entries to look for should not be empty");
    }

    @Test
    void should_fail_if_one_of_entries_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashMap.<String, String>empty()).doesNotContain(ENTRY1, null)
        )
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).doesNotContain(ENTRY1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_contains_all_entries() {
        final Map<String, String> actual = HashMap.of("key1", "value1", "key2", "value2");

        assertThatThrownBy(
                () -> assertThat(actual).doesNotContain(ENTRY1, ENTRY2)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting\n <HashMap((key1, value1), (key2, value2))>\nnot to contain\n <[(key1, value1), (key2, value2)]>\nbut found\n <HashSet((key1, value1), (key2, value2))>\n");
    }
}
