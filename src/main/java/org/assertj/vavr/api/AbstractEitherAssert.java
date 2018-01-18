package org.assertj.vavr.api;

/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2017 the original author or authors.
 */

import io.vavr.control.Either;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;

/**
 * Assertions for {@link Either}.
 *
 * @param <SELF> the "self" type of this assertion class.
 * @param <LEFT> type of the left value contained in the {@link Either}.
 * @param <RIGHT> type of the right value contained in the {@link Either}.
 *
 * @author Michał Chmielarz
 */
abstract class AbstractEitherAssert<SELF extends AbstractEitherAssert<SELF, LEFT, RIGHT>, LEFT, RIGHT> extends
  AbstractAssert<SELF, Either<LEFT, RIGHT>> {

    AbstractEitherAssert(Either<LEFT, RIGHT> actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Verifies that the actual {@link Either} is left.
     *
     * @return this assertion object.
     */
    public SELF isLeft() {
        isNotNull();
        if (actual.isRight()) throwAssertionError(shouldBeLeft(actual));
        return myself;
    }

    /**
     * Verifies that the actual {@link Either} is right.
     *
     * @return this assertion object.
     */
    public SELF isRight() {
        isNotNull();
        if (actual.isLeft()) throwAssertionError(shouldBeRight(actual));
        return myself;
    }
}

