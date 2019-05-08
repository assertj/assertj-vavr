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
  Copyright 2012-2019 the original author or authors.
 */

import io.vavr.control.Either;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.FieldByFieldComparator;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;
import java.util.function.Consumer;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.vavr.api.EitherShouldBeLeft.shouldBeLeft;
import static org.assertj.vavr.api.EitherShouldBeRight.shouldBeRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainOnRight;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainSameOnLeft;
import static org.assertj.vavr.api.EitherShouldContain.shouldContainSameOnRight;
import static org.assertj.vavr.api.EitherShouldContainInstanceOf.shouldContainOnLeftInstanceOf;
import static org.assertj.vavr.api.EitherShouldContainInstanceOf.shouldContainOnRightInstanceOf;

/**
 * Assertions for {@link Either}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <LEFT>  type of the left value contained in the {@link Either}.
 * @param <RIGHT> type of the right value contained in the {@link Either}.
 * @author Alex Dukhno
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
     * Verifies that the actual {@link io.vavr.control.Either} is {@link io.vavr.control.Either.Right}
     * and contains the given value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsOnRight(RIGHT expectedValue) {
        assertIsRight();
        if (!eitherValueComparisonStrategy.areEqual(actual.get(), expectedValue))
            throwAssertionError(shouldContainOnRight(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} is {@link io.vavr.control.Either.Left}
     * and contains the given value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsOnLeft(LEFT expectedValue) {
        assertIsLeft();
        if (!eitherValueComparisonStrategy.areEqual(actual.getLeft(), expectedValue))
            throwAssertionError(shouldContainOnLeft(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the instance given as an argument as the right value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsRightSame(RIGHT expectedValue) {
        assertIsRight();
        if (actual.get() != expectedValue)
            throwAssertionError(shouldContainSameOnRight(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains the instance given as an argument as the left value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsLeftSame(LEFT expectedValue) {
        assertIsLeft();
        if (actual.getLeft() != expectedValue)
            throwAssertionError(shouldContainSameOnLeft(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual right-sided {@link io.vavr.control.Either} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the right-sided {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsRightInstanceOf(Class<?> clazz) {
        assertIsRight();
        if (!clazz.isInstance(actual.get()))
            throwAssertionError(shouldContainOnRightInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Verifies that the actual left-sided {@link io.vavr.control.Either} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the left-sided {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF containsLeftInstanceOf(Class<?> clazz) {
        assertIsLeft();
        if (!clazz.isInstance(actual.getLeft()))
            throwAssertionError(shouldContainOnLeftInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains a right-sided value and gives this value to the given
     * {@link java.util.function.Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     * <p>
     * Assertions will pass :
     * <pre><code class='java'> // one requirement
     * assertThat(Either.right("something")).hasValueSatisfying(it -&gt; assertThat(it).isEqualTo("something"));
     *
     * // multiple requirements
     * assertThat(Either.right("something")).hasValueSatisfying(it -&gt; {
     *   assertThat(it).isEqualTo("something");
     *   assertThat(it).startsWith("some");
     *   assertThat(it).endsWith("thing");
     * }); </code></pre>
     *
     * Assertions will fail :
     * <pre><code class='java'>
     * assertThat(Either.right("something")).hasValueSatisfying(it -&gt; assertThat(it).isEqualTo("something else"));
     *
     * // fail because Either is left-sided, there is no value to perform assertion on
     * assertThat(Either.left(42)).hasValueSatisfying(it -&gt; {});</code></pre>
     *
     * @param requirement to further assert on the right-sided object contained inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF hasValueSatisfying(Consumer<RIGHT> requirement) {
        assertIsRight();
        requirement.accept(actual.get());
        return myself;
    }


    /**
     * Verifies that the actual {@link io.vavr.control.Either} contains a left-sided value and gives this value to the given
     * {@link java.util.function.Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     * <p>
     * Assertions will pass :
     * <pre><code class='java'> // one requirement
     * assertThat(Either.left(42)).hasLeftValueSatisfying(it -&gt; assertThat(it).isEqualTo(42));
     *
     * // multiple requirements
     * assertThat(Either.left(42)).hasLeftValueSatisfying(it -&gt; {
     *   assertThat(it).isEqualTo(42);
     *   assertThat(it).isLessThan(100);
     *   assertThat(it).isGreaterThan(0);
     * }); </code></pre>
     *
     * Assertions will fail :
     * <pre><code class='java'>
     * assertThat(Either.left(42)).hasLeftValueSatisfying(it -&gt; assertThat(it).isEqualTo(24));
     *
     * // fail because Either is right-sided, there is no value to perform assertion on
     * assertThat(Either.right("something")).hasLeftValueSatisfying(it -&gt; {});</code></pre>
     *
     * @param requirement to further assert on the left-sided object contained inside the {@link io.vavr.control.Either}.
     * @return this assertion object.
     */
    public SELF hasLeftValueSatisfying(Consumer<LEFT> requirement) {
        assertIsLeft();
        requirement.accept(actual.getLeft());
        return myself;
    }

    /**
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link Either} value's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given comparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingValueComparator(Comparator<?> customComparator) {
        eitherValueComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Use field/property by field/property comparison (including inherited fields/properties) instead of relying on
     * actual type A <code>equals</code> method to compare the {@link Either} value's object for incoming assertion
     * checks. Private fields are included but this can be disabled using
     * {@link Assertions#setAllowExtractingPrivateFields(boolean)}.
     *
     * @return {@code this} assertion object.
     */
    @CheckReturnValue
    public SELF usingFieldByFieldValueComparator() {
        return usingValueComparator(new FieldByFieldComparator());
    }

    /**
     * Revert to standard comparison for incoming assertion {@link Either} value checks.
     * <p>
     * This method should be used to disable a custom comparison strategy set by calling
     * {@link #usingValueComparator(Comparator)}.
     *
     * @return {@code this} assertion object.
     */
    @CheckReturnValue
    public SELF usingDefaultRightValueComparator() {
        // fall back to default strategy to compare actual with other objects.
        eitherValueComparisonStrategy = StandardComparisonStrategy.instance();
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
