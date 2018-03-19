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
  Copyright 2012-2018 the original author or authors.
 */

import org.assertj.core.api.AbstractAssert;

import io.vavr.control.Either;

import static org.assertj.core.util.Preconditions.checkArgument;

/**
 * Assertions for {@link io.vavr.control.Either}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <LEFT>  type of the value on the left contained in the {@link io.vavr.control.Either}.
 * @param <RIGHT> type of the value on the right contained in the {@link io.vavr.control.Either}.
 * @author Alex Dukhno
 */
abstract class AbstractEitherAssert<SELF extends AbstractEitherAssert<SELF, LEFT, RIGHT>, LEFT, RIGHT> extends
    AbstractAssert<SELF, Either<LEFT, RIGHT>> {
  AbstractEitherAssert(Either<LEFT, RIGHT> actual, Class<?> selfType) {
    super(actual, selfType);
  }

  /**
   * Verifies that the actual {@link io.vavr.control.Either} is {@link io.vavr.control.Either.Left}
   * and contains the given value.
   *
   * @param left the expected value inside the {@link io.vavr.control.Either}.
   * @return this assertion object.
   */
  public SELF containsOnLeft(LEFT left) {
    isNotNull();
    checkLeftNotNull(left);
    if (!actual.isLeft()) throwAssertionError(EitherShouldContain.shouldContainOnOtherSide(actual, left));
    if (!actual.getLeft().equals(left)) throwAssertionError(EitherShouldContain.shouldContain(actual, left));
    return myself;
  }

  private void checkLeftNotNull(LEFT left) {
    checkNotNull(left, "LEFT");
  }

  /**
   * Verifies that the actual {@link io.vavr.control.Either} is {@link io.vavr.control.Either.Right}
   * and contains the given value.
   *
   * @param right the expected value inside the {@link io.vavr.control.Either}.
   * @return this assertion object.
   */
  public SELF containsOnRight(RIGHT right) {
    isNotNull();
    checkRightNotNull(right);
    if (!actual.isRight()) throwAssertionError(EitherShouldContain.shouldContainOnOtherSide(actual, right));
    if (!actual.get().equals(right)) throwAssertionError(EitherShouldContain.shouldContain(actual, right));
    return myself;
  }

  private void checkRightNotNull(RIGHT right) {
    checkNotNull(right, "RIGHT");
  }

  private void checkNotNull(Object value, String side) {
    checkArgument(value != null, "The expected value on the [" + side + "] should not be <null>.");
  }
}
