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
 * Copyright 2017-2025 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_isLeft_Test {

    @Test
    void should_pass_if_Either_is_left() {
        assertThat(Either.left("left")).isLeft();
    }

    @Test
    void should_fail_when_Either_is_null() {
        assertThatThrownBy(
                () -> assertThat((Either<String, String>) null).isLeft()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_Either_is_right() {
        Either<String, String> actual = Either.right("right");

        assertThatThrownBy(
                () -> assertThat(actual).isLeft()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeLeft(actual).create());
    }
}
