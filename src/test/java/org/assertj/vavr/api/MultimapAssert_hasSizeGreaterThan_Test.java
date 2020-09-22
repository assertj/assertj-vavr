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
 * Copyright 2017-2020 the original author or authors.
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
import io.vavr.collection.Multimap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.error.ShouldHaveSizeGreaterThan.shouldHaveSizeGreaterThan;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class MultimapAssert_hasSizeGreaterThan_Test {
    @Test
    void should_pass_if_Multimap_is_of_expected_size() {
        assertThat(HashMultimap.withSeq().of("key", "value")).hasSizeGreaterThan(0);
    }

    @Test
    void should_fail_when_Multimap_is_null() {
        assertThatThrownBy(
                () -> assertThat((Multimap<String, String>) null).hasSizeGreaterThan(0)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldNotBeNull().create());
    }

    @Test
    void should_fail_if_Multimap_is_not_of_expected_size() {
        final Multimap<Object, Object> actual = HashMultimap.withSeq().empty();

        assertThatThrownBy(
                () -> assertThat(actual).hasSizeGreaterThan(500)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldHaveSizeGreaterThan(actual, 0, 500).create());
    }
}
