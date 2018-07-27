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

import static org.assertj.core.error.ShouldContainExactly.elementsDifferAtIndex;
import static org.assertj.core.internal.ErrorMessages.arrayOfValuesToLookForIsNull;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class ListAssert_containsExactly_Test extends BaseTest {

    @Test
    public void should_pass_if_List_contains_exactly_all_expected_values() {
        final String value = "something";
        assertThat(List.of(value)).containsExactly(value);
    }

    @Test
    public void should_pass_if_List_and_expected_values_are_empty() {
        assertThat(List.empty()).containsExactly();
    }


    @Test
    public void should_fail_when_List_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((List<String>) null).containsExactly("something");
    }

    @Test
    public void should_fail_when_expected_values_are_null() {
        thrown.expectNullPointerException(arrayOfValuesToLookForIsNull());

        assertThat(List.of("something")).containsExactly(null);
    }

    @Test
    public void should_fail_if_List_is_not_empty_but_expected_values_are() {
        thrown.expectAssertionError("\nExpecting:\n  <List(something)>\nto contain exactly (and in same order):\n  <[]>\nbut some elements were not expected:\n  <[\"something\"]>\n");

        assertThat(List.of("something")).containsExactly();
    }

    @Test
    public void should_fail_if_List_contains_no_element_from_expected_values() {
        List<String> actual = List.of("a", "b", "c");

        thrown.expectAssertionError(
                "\nExpecting:\n  <List(a, b, c)>\nto contain exactly (and in same order):\n  <[\"a\", \"d\"]>\nbut some elements were not found:\n  <[\"d\"]>\nand others were not expected:\n  <[\"b\", \"c\"]>\n"
        );

        assertThat(actual).containsExactly("a", "d");
    }

    @Test
    public void should_fail_if_List_contains_expected_elements_but_not_in_order() {
        List<String> actual = List.of("a", "b", "c");

        thrown.expectAssertionError(elementsDifferAtIndex("b", "c", 1));

        assertThat(actual).containsExactly("a", "c", "b");
    }
}
