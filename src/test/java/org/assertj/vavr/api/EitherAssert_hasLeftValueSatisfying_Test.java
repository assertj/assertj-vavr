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

import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class EitherAssert_hasLeftValueSatisfying_Test {

    @Test
    void should_fail_when_either_is_null() {
        Either<Integer, String> actual = null;

        assertThatThrownBy(
                () -> assertThat(actual).hasLeftValueSatisfying(it -> {})
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_if_either_is_right() {
        Either<Integer, String> actual = Either.right("something");

        assertThatThrownBy(
                () -> assertThat(actual).hasLeftValueSatisfying(it -> {})
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBeLeft(actual).create());
    }

    @Test
    void should_fail_if_consumer_fails() {
        Either<Integer, String> actual = Either.left(42);

        assertThatThrownBy(
                () -> assertThat(actual).hasLeftValueSatisfying(it -> Assertions.assertThat(it).isEqualTo(24))
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(format("%nExpecting:%n <42>%nto be equal to:%n <24>%nbut was not."));
    }

    @Test
    void should_pass_if_consumer_passes() {
        Either<Integer, String> actual = Either.left(42);

        assertThat(actual).hasLeftValueSatisfying(it -> Assertions.assertThat(it).isEqualTo(42));
    }
}
