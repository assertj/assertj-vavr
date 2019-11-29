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
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

@SuppressWarnings("unchecked")
class MultimapAssert_hasEntrySatisfying_Test {

    private Condition<String> passingCondition = new TestCondition<>(true);
    private Condition<String> notPassingCondition = new TestCondition<>();

    @Test
    void should_pass_if_Multimap_contains_entry_satisfying_condition() {
        Multimap<String, String> actual = HashMultimap.withSeq().of(
                "key1", "value1", "key2", "value2"
        );

        assertThat(actual).hasEntrySatisfying("key1", passingCondition);
    }

    @Test
    void should_fail_if_Multimap_is_empty() {
        Multimap<String, String> actual = HashMultimap.withSeq().empty();

        assertThatThrownBy(
                () -> assertThat(actual).hasEntrySatisfying("key1", passingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n" +
                        " <HashMultimap[List]()>\n" +
                        "to contain key:\n" +
                        " <\"key1\">");
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).hasEntrySatisfying("key1", passingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_when_Multimap_does_not_contain_given_key() {
        Multimap<String, String> actual = HashMultimap.withSeq().of(
                "key1", "value1", "key2", "value2"
        );

        assertThatThrownBy(
                () -> assertThat(actual).hasEntrySatisfying("key3", passingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting:\n" +
                        " <HashMultimap[List]((key1, value1), (key2, value2))>\n" +
                        "to contain key:\n" +
                        " <\"key3\">");
    }

    @Test
    void should_fail_when_condition_is_null() {
        Multimap<String, String> actual = HashMultimap.withSeq().of(
                "key1", "value1", "key2", "value2"
        );

        assertThatThrownBy(
                () -> assertThat(actual).hasEntrySatisfying("key1", null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The condition to evaluate should not be null");
    }

    @Test
    void should_fail_if_Multimap_does_not_contain_entry_satisfying_condition() {
        Multimap<String, String> actual = HashMultimap.withSeq().of("key1", "value1", "key3", "value3");

        assertThatThrownBy(
                () -> assertThat(actual).hasEntrySatisfying("key1", notPassingCondition)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting elements:\n<Some(List(value1))>\n of \n<HashMultimap[List]((key1, value1), (key3, value3))>\n to be <TestCondition>");
    }
}
