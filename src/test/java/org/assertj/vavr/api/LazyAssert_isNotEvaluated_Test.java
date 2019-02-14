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
package org.assertj.vavr.api;

import io.vavr.Lazy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.LazyShouldBeNotEvaluated.shouldBeNotEvaluated;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class LazyAssert_isNotEvaluated_Test {

    @Test
    void should_pass_when_Lazy_is_not_evaluated() {
        Lazy<Double> lazy = Lazy.of(Math::random);

        assertThat(lazy).isNotEvaluated();
    }

    @Test
    void should_fail_when_Lazy_is_evaluated() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        lazy.get();

        assertThatThrownBy(
                () -> assertThat(lazy).isNotEvaluated()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeNotEvaluated(lazy).create());
    }

    @Test
    void should_fail_when_Lazy_is_null() {
        Lazy<Double> lazy = null;

        assertThatThrownBy(
                () -> assertThat(lazy).isNotEvaluated()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }
}
