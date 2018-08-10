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
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import java.util.Comparator;

import static org.assertj.core.error.ShouldBeSorted.shouldHaveComparableElementsAccordingToGivenComparator;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.ListShouldBeSorted.shouldBeSorted;
import static org.assertj.vavr.api.ListShouldBeSorted.shouldBeSortedAccordingToGivenComparator;
import static org.assertj.vavr.api.ListShouldBeSorted.shouldHaveMutuallyComparableElements;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class ListAssert_isSorted_Test extends BaseTest {

    private static final Comparator<Object> LIST_ELEMENT_COMPARATOR = (s1, s2) -> {
        if (s1 == null) return s2 == null ? 0 : 1;
        if (s2 == null) return -1;
        Comparable c1 = (Comparable) s1;
        Comparable c2 = (Comparable) s2;
        return c1.compareTo(c2);
    };

    @Test
    public void should_pass_if_List_contains_element_sorted_in_comparator_order() {
        assertThat(List.of("some", "something", "thing")).isSorted();
    }

    @Test
    public void should_pass_if_List_is_empty() {
        assertThat(List.of()).isSorted();
    }

    @Test
    public void should_fail_if_List_is_sorted_not_in_comparator_order() {
        final List<String> values = List.of("some", "thing", "something");

        thrown.expectAssertionError(shouldBeSortedAccordingToGivenComparator(1, values, LIST_ELEMENT_COMPARATOR));

        assertThat(values).usingElementComparator(LIST_ELEMENT_COMPARATOR).isSorted();
    }

    @Test
    public void should_fail_if_List_contains_no_comparable_elements() {
        List<Foo> values = List.of(new Foo("some"), new Foo("thing"), new Foo("something"));

        thrown.expectAssertionError(shouldHaveComparableElementsAccordingToGivenComparator(values, LIST_ELEMENT_COMPARATOR));

        assertThat(values).usingElementComparator(LIST_ELEMENT_COMPARATOR).isSorted();
    }

    @Test
    public void should_fail_if_List_is_not_sorted() {
        final List<Integer> values = List.of(0, 1, 3, 2, 9);

        thrown.expectAssertionError(shouldBeSorted(2, values));

        assertThat(values).isSorted();
    }

    @Test
    public void should_fail_if_elements_cannot_be_compared() {
        final List<Foo> values = List.of(new Foo("some"), new Foo("thing"));

        thrown.expectAssertionError(shouldHaveMutuallyComparableElements(values));

        assertThat(values).isSorted();
    }

    @Test
    public void should_fail_when_List_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((List<String>) null).isSorted();
    }

    private static class Foo {

        private final String value;

        Foo(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Foo{value='" + value + "'}";
        }
    }
}
