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

import io.vavr.collection.HashMultimap;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_allSatisfy_Test {

    private static final BiConsumer<String, String> EMPTY_CONSUMER = (key, value) -> {
    };

    @Test
    void should_pass_if_Multimap_is_empty() {
        assertThat(HashMultimap.withSeq().<String, String>empty()).allSatisfy(EMPTY_CONSUMER);
    }

    @Test
    void should_pass_if_all_Multimap_entries_satisfy_consumer() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key2", "value2");

        assertThat(actual).allSatisfy((key, value) -> {
            assertThat(key).startsWith("key");
            assertThat(value).startsWith("value");
        });
    }

    @Test
    void should_fail_if_consumer_is_null() {
        assertThatThrownBy(
                () -> assertThat(HashMultimap.withSeq().<String, String>empty()).allSatisfy(null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The BiConsumer<K, V> expressing the assertions requirements must not be null");
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).allSatisfy(EMPTY_CONSUMER)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_all_Multimap_entries_do_not_satisfy_consumer() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key2", "value2");

        assertThatThrownBy(
                () -> assertThat(actual).allSatisfy((key, value) -> {
                    assertThat(key).isEqualTo("key1");
                })
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nexpected: \"key1\"\n but was: \"key2\"");
    }
}
