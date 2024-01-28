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

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldBeEqual.shouldBeEqual;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import static org.assertj.core.description.EmptyTextDescription.emptyDescription;

class MapAssert_isEqualTo_Test {

    @Test
    void should_pass_if_Map_is_equal_to() {
        assertThat(HashMap.of(
                "key1", "value2",
                "key2", "value2"))
                .isEqualTo(HashMap.of(
                        "key1", "value2",
                        "key2", "value2"));
    }

    @Test
    void should_fail_when_Map_is_null() {
        final Map<String, String> actual = null;

        final Map<String, String> expected = HashMap.of("a", "b");

        assertThatThrownBy(
                () -> assertThat(actual).isEqualTo(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeEqual(actual, expected, STANDARD_REPRESENTATION).newAssertionError(emptyDescription(), STANDARD_REPRESENTATION).getMessage());
    }

    @Test
    void should_fail_if_Map_is_not_equal_to() {
        final Map<Object, Object> actual = HashMap.of(
                                        "key1", "value2",
                                        "key2", "value2");

        final Map<Object, Object> expected = HashMap.of("a", "b");

        assertThatThrownBy(
                () -> assertThat(actual).isEqualTo(expected)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeEqual(actual, expected, STANDARD_REPRESENTATION).newAssertionError(emptyDescription(), STANDARD_REPRESENTATION).getMessage());
    }
}
