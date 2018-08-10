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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeqAssert_satisfies_atIndex_Test {

    static final Consumer<String> CONDITION_TO_SATISFY = str -> assertThat(str).startsWith("something");

    @Test
    void should_pass_if_List_satisfy_condition_at_given_index() {
        final String value = "something";
        assertThat(List.of(value)).satisfies(CONDITION_TO_SATISFY, atIndex(0));
    }

    @Test
    void should_fail_when_List_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((List<String>) null).satisfies(CONDITION_TO_SATISFY, atIndex(0)),
                actualIsNull());
    }

    @Test
    void should_fail_if_given_index_is_greater_than_list_size() {
        final Seq<String> actual = List.of("something");
        assertThrows(IndexOutOfBoundsException.class,
                () -> assertThat(actual).satisfies(CONDITION_TO_SATISFY, atIndex(2)),
                "Index should be between <0> and <0> (inclusive) but was:\n <2>");
    }

    @Test
    void should_fail_when_condition_to_satisfy_is_null() {
        final Seq<String> actual = List.of("something");
        assertThrows(NullPointerException.class,
                () -> assertThat(actual).satisfies(null, atIndex(0)),
                "The Consumer expressing the assertions requirements must not be null");
    }

    @Test
    void should_fail_if_List_does_not_satisfies_condition_at_given_index() {
        final Seq<String> actual = List.of("a", "b", "c");
        assertThrows(AssertionError.class,
                () -> assertThat(actual).satisfies(CONDITION_TO_SATISFY, atIndex(0)),
                "The Consumer expressing the assertions requirements must not be null");
    }
}
