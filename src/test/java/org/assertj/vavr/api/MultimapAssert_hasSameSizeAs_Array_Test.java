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
 * Copyright 2017-2023 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.HashMultimap;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldBeAnArray.shouldBeAnArray;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_hasSameSizeAs_Array_Test {

    @Test
    void should_pass_if_Multimap_has_same_size_as_given_array() {
        String[] array = new String[]{"single element"};
        assertThat(HashMultimap.withSeq().of("key", "value")).hasSameSizeAs(array);
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        String[] array = new String[]{"single element"};
        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).hasSameSizeAs(array)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Multimap_is_not_of_expected_size() {
        final Multimap<Object, Object> actual = HashMultimap.withSeq().empty();
        String[] array = new String[]{"single element"};
        assertThatThrownBy(
                () -> assertThat(actual).hasSameSizeAs(array)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldHaveSameSizeAs(actual, array, 0, 1).create());
    }

    @Test
    void should_fail_if_array_to_compare_with_is_null() {
        String[] array = null;
        assertThatThrownBy(
                () -> assertThat(HashMultimap.withSeq().empty()).hasSameSizeAs(array)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeAnArray(array).create());
    }

    @Test
    void should_fail_if_comparing_Multimap_size_with_non_array_type() {
        final Multimap<Object, Object> actual = HashMultimap.withSeq().empty();
        Object object = null;
        assertThatThrownBy(
                () -> assertThat(actual).hasSameSizeAs(object)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeAnArray(object).create());
    }
}
