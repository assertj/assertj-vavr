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

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapAssert_hasSameSizeAs_Iterable_Test {

    @Test
    void should_pass_if_Map_has_same_size_as_given_iterable() {
        Iterable<String> iterable = List.of("single element");
        assertThat(HashMap.of("key", "value")).hasSameSizeAs(iterable);
    }

    @Test
    void should_fail_when_Map_is_null() {
        Iterable<String> iterable = List.of("single element");
        assertThrows(AssertionError.class,
                () -> assertThat((Map<String, String>) null).hasSameSizeAs(iterable),
                shouldNotBeEmpty().create());
    }

    @Test
    void should_fail_if_Map_is_not_of_expected_size() {
        final HashMap<Object, Object> actual = HashMap.empty();
        Iterable<String> iterable = List.of("single element");
        assertThrows(AssertionError.class,
                () -> assertThat(actual).hasSameSizeAs(iterable),
                shouldHaveSameSizeAs(actual, 0, 1).create());
    }

    @Test
    void should_fail_if_iterable_to_compare_with_is_null() {
        final HashMap<Object, Object> actual = HashMap.empty();
        assertThrows(NullPointerException.class,
                () -> assertThat(actual).hasSameSizeAs(null),
                "The other Iterable to compare actual size with should not be null");
    }
}
