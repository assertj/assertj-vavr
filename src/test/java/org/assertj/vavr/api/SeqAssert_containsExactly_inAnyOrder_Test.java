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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Traversable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class SeqAssert_containsExactly_inAnyOrder_Test {

    @Test
    void should_pass_if_List_contains_exactly_elements_in_any_order() {
        final Set<String> expectedInAnyOrder = HashSet.of("other", "and", "else", "something");
        assertThat(List.of("something", "else", "and", "other"))
                .containsExactlyInAnyOrder(expectedInAnyOrder);
    }

    @Test
    void should_fail_when_List_is_null() {
        final Set<String> expectedInAnyOrder = HashSet.of("other", "and", "else", "something");
        assertThatThrownBy(
                () -> assertThat((List<String>) null).containsExactlyInAnyOrder(expectedInAnyOrder)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_list_contains_not_all_expected_elements() {
        final Set<String> expectedInAnyOrder = HashSet.of("other", "and", "else", "something");
        final Seq<String> actual = List.of("something", "else");
        assertThatThrownBy(
                () -> assertThat(actual).containsExactlyInAnyOrder(expectedInAnyOrder)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting actual:\n  List(something, else)\nto contain exactly in any order:\n  [\"other\", \"and\", \"else\", \"something\"]\nbut could not find the following elements:\n  [\"other\", \"and\"]\n");
    }

    @Test
    void should_fail_if_list_contains_more_than_expected_elements() {
        final Set<String> expectedInAnyOrder = HashSet.of("something", "else");
        final Seq<String> actual = List.of("something", "else", "more");
        assertThatThrownBy(
                () -> assertThat(actual).containsExactlyInAnyOrder(expectedInAnyOrder)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("\nExpecting actual:\n  List(something, else, more)\nto contain exactly in any order:\n  [\"else\", \"something\"]\nbut the following elements were unexpected:\n  [\"more\"]\n");
    }

    @Test
    void should_fail_when_expected_elements_param_is_null() {
        final Seq<String> actual = List.of("something");
        assertThatThrownBy(
                () -> assertThat(actual).containsExactlyInAnyOrder((Traversable<String>) null)
        )
                .isInstanceOf(NullPointerException.class);
    }
}
