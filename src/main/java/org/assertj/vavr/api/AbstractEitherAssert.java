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

import io.vavr.control.Either;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainSameOnLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainSameOnRight;
import static org.assertj.vavr.api.EitherShouldContainInstanceOf.shouldContainOnRightInstanceOf;

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

    private ComparisonStrategy eitherValueComparisonStrategy;

    AbstractEitherAssert(Either<LEFT, RIGHT> actual, Class<?> selfType) {
        super(actual, selfType);
        this.eitherValueComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Verifies that the actual {@link Either} is left.
     *
     * @return this assertion object.
     */
    public SELF isLeft() {
        assertIsLeft();
        return myself;
    }

    /**
     * Verifies that the actual {@link Either} is right.
     *
     * @return this assertion object.
     */
    public SELF isRight() {
        assertIsRight();
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the given right value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     *
     * @return this assertion object.
     */
    public SELF containsRight(RIGHT expectedValue) {
        assertIsRight();
        checkNotNull(expectedValue);
        if (!eitherValueComparisonStrategy.areEqual(actual.get(), expectedValue))
            throwAssertionError(shouldContainOnRight(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the given left value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     *
     * @return this assertion object.
     */
    public SELF containsLeft(LEFT expectedValue) {
        assertIsLeft();
        checkNotNull(expectedValue);
        if (!eitherValueComparisonStrategy.areEqual(actual.getLeft(), expectedValue))
            throwAssertionError(shouldContainOnLeft(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the instance given as an argument as the right value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     *
     * @return this assertion object.
     */
    public SELF containsRightSame(RIGHT expectedValue) {
        assertIsRight();
        checkNotNull(expectedValue);
        if (actual.get() != expectedValue)
            throwAssertionError(shouldContainSameOnRight(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the instance given as an argument as the left value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     *
     * @return this assertion object.
     */
    public SELF containsLeftSame(LEFT expectedValue) {
        assertIsLeft();
        checkNotNull(expectedValue);
        if (actual.getLeft() != expectedValue)
            throwAssertionError(shouldContainSameOnLeft(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains a right value that is an instance of the argument.
     *
     * @param clazz the expected class of the right value inside the {@link io.vavr.control.Either}.
     *
     * @return this assertion object.
     */
    public SELF containsRightInstanceOf(Class<?> clazz) {
        assertIsRight();
        if (!clazz.isInstance(actual.get()))
            throwAssertionError(shouldContainOnRightInstanceOf(actual, clazz));
        return myself;
    }

    private void checkNotNull(Object expectedValue) {
        checkArgument(expectedValue != null, "The expected value should not be <null>.");
    }

    private void assertIsRight() {
        isNotNull();
        if (actual.isLeft()) throwAssertionError(shouldBeRight(actual));
    }

    private void assertIsLeft() {
        isNotNull();
        if (actual.isRight()) throwAssertionError(shouldBeLeft(actual));
    }
}

