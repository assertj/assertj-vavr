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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldHaveSizeGreaterThan.shouldHaveSizeGreaterThan;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_hasSizeGreaterThan_Test {

    @Test
    void should_pass_if_Map_is_of_expected_size() {
        assertThat(HashMap.of(
                "key1", "value2", 
                "key2", "value2"))
                .hasSizeGreaterThan(1);
    }

    @Test
    void should_fail_when_Map_is_null() {
        assertThatThrownBy(
                () -> assertThat((Map<String, String>) null).hasSizeGreaterThan(1)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Map_is_not_of_expected_size() {
        final Map<Object, Object> actual = HashMap.of(
                                        "key1", "value2",
                                        "key2", "value2");

        assertThatThrownBy(
                () -> assertThat(actual).hasSizeGreaterThan(500)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldHaveSizeGreaterThan(actual, 2, 500).create());
    }
}
