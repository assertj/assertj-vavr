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

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class SeqAssert_doesNotContain_atIndex_Test {

    @Test
    void should_pass_if_List_does_not_contain_provided_value_at_given_index() {
        final String value = "something";
        assertThat(List.of(value)).doesNotContain("nothing", atIndex(0));
    }

    @Test
    void should_fail_when_List_is_null() {
        assertThatThrownBy(
                () -> assertThat((List<String>) null).doesNotContain("something", atIndex(0))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_given_index_is_greater_than_list_size() {
        final Seq<String> actual = List.of("something");
        assertThatThrownBy(
                () -> assertThat(actual).doesNotContain(null, atIndex(2))
        )
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index should be between <0> and <0> (inclusive) but was:\n <2>");
    }

    @Test
    void should_fail_if_List_contains_provided_element_at_given_index() {
        Seq<String> actual = List.of("a", "b", "c");
        assertThatThrownBy(
                () -> assertThat(actual).doesNotContain("a", atIndex(0))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotContainAtIndex(actual, "a", atIndex(0)).create());
    }
}
