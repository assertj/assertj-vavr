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

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.error.ShouldContainAtIndex.shouldContainAtIndex;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.SeqShouldBeAtIndex.shouldBeAtIndex;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class SeqAssert_is_atIndex_Test {

    private final Condition<String> condition = new Condition<>(str -> str.startsWith("some"), "starts with some");

    @Test
    void should_pass_if_element_on_List_at_given_index_is_value_fulfilling_provided_condition() {
        final String value = "something";
        assertThat(Array.of(value)).is(condition, atIndex(0));
    }

    @Test
    void should_fail_when_List_is_null() {
        assertThatThrownBy(
                () -> assertThat((List<String>) null).is(condition, atIndex(0))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_given_index_is_greater_than_list_size() {
        final Seq<String> actual = Array.of("something");
        assertThatThrownBy(
                () -> assertThat(actual).is(condition, atIndex(2))
        )
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index should be between <0> and <0> (inclusive) but was:\n <2>");
    }

    @Test
    void should_fail_when_provided_condition_is_null() {
        final Seq<String> actual = Array.of("something");
        assertThatThrownBy(
                () -> assertThat(actual).is(null, atIndex(0))
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The condition to evaluate should not be null");
    }

    @Test
    void should_fail_if_element_on_List_at_given_index_is_not_value_fulfilling_provided_condition() {
        Seq<String> actual = Array.of("a", "b", "c");
        assertThatThrownBy(
                () -> assertThat(actual).is(condition, atIndex(2))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeAtIndex(actual, condition, atIndex(2), "c").create());
    }
}
