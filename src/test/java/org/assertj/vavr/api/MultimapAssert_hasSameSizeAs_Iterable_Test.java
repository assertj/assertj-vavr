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

import io.vavr.collection.HashMultimap;
import io.vavr.collection.List;
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MultimapAssert_hasSameSizeAs_Iterable_Test {

    @Test
    void should_pass_if_Multimap_has_same_size_as_given_iterable() {
        Iterable<String> iterable = List.of("single element");
        assertThat(HashMultimap.withSeq().of("key", "value")).hasSameSizeAs(iterable);
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        Iterable<String> iterable = List.of("single element");

        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).hasSameSizeAs(iterable)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Multimap_is_not_of_expected_size() {
        final Multimap<Object, Object> actual = HashMultimap.withSeq().empty();
        Iterable<String> iterable = List.of("single element");

        assertThatThrownBy(
                () -> assertThat(actual).hasSameSizeAs(iterable)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldHaveSameSizeAs(actual, iterable, 0, 1).create());
    }

    @Test
    void should_fail_if_iterable_to_compare_with_is_null() {
        final Multimap<Object, Object> actual = HashMultimap.withSeq().empty();

        assertThatThrownBy(
                () -> assertThat(actual).hasSameSizeAs(null)
        )
                .isInstanceOf(NullPointerException.class)
                .hasMessage("The other Iterable to compare actual size with should not be null");
    }
}
