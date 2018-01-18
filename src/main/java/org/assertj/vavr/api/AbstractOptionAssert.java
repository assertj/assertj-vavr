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

import io.vavr.control.Option;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.Conditions;
import org.assertj.core.internal.FieldByFieldComparator;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.vavr.api.OptionShouldBeEmpty.shouldBeEmpty;
import static org.assertj.vavr.api.OptionShouldBePresent.shouldBePresent;
import static org.assertj.vavr.api.OptionShouldContain.shouldContain;
import static org.assertj.vavr.api.OptionShouldContain.shouldContainSame;
import static org.assertj.vavr.api.OptionShouldContainInstanceOf.shouldContainInstanceOf;

/**
 * Assertions for {@link io.vavr.control.Option}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <VALUE> type of the value contained in the {@link io.vavr.control.Option}.
 * @author Grzegorz Piwowarek
 */
abstract class AbstractOptionAssert<SELF extends AbstractOptionAssert<SELF, VALUE>, VALUE> extends
  AbstractAssert<SELF, Option<VALUE>> {

    private Conditions conditions = Conditions.instance();

    private ComparisonStrategy optionValueComparisonStrategy;

    AbstractOptionAssert(Option<VALUE> actual, Class<?> selfType) {
        super(actual, selfType);
        this.optionValueComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Verifies that there is a value present in the actual {@link io.vavr.control.Option}.
     *
     * @return this assertion object.
     */
    public SELF isDefined() {
        assertValueIsPresent();
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Option} is empty.
     *
     * @return this assertion object.
     */
    public SELF isEmpty() {
        isNotNull();
        if (actual.isDefined()) throwAssertionError(shouldBeEmpty(actual));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Option} contains the given value (alias of {@link #hasValue(Object)}).
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Option}.
     * @return this assertion object.
     */
    public SELF contains(VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);
        if (actual.isEmpty()) throwAssertionError(shouldContain(expectedValue));
        if (!optionValueComparisonStrategy.areEqual(actual.get(), expectedValue))
            throwAssertionError(shouldContain(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Option} contains a value and gives this value to the given
     * {@link java.util.function.Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     *
     * @param requirement to further assert on the object contained inside the {@link io.vavr.control.Option}.
     * @return this assertion object.
     */
    public SELF hasValueSatisfying(Consumer<VALUE> requirement) {
        assertValueIsPresent();
        requirement.accept(actual.get());
        return myself;
    }

    /**
     * Verifies that the actual {@link Option} contains a value which satisfies the given {@link Condition}.
     *
     * @param condition the given condition.
     * @return this assertion object.
     * @throws AssertionError       if the actual {@link Option} is null or empty.
     * @throws NullPointerException if the given condition is {@code null}.
     * @throws AssertionError       if the actual value does not satisfy the given condition.
     */
    public SELF hasValueSatisfying(Condition<? super VALUE> condition) {
        assertValueIsPresent();
        conditions.assertIs(info, actual.get(), condition);
        return myself;
    }

    /**
     * Verifies that the actual {@link Option} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the {@link Option}.
     * @return this assertion object.
     */
    public SELF containsInstanceOf(Class<?> clazz) {
        assertValueIsPresent();
        if (!clazz.isInstance(actual.get())) throwAssertionError(shouldContainInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Use field/property by field/property comparison (including inherited fields/properties) instead of relying on
     * actual type A <code>equals</code> method to compare the {@link Option} value's object for incoming assertion
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
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link Option} value's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given comparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingValueComparator(Comparator<? super VALUE> customComparator) {
        optionValueComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Revert to standard comparison for incoming assertion {@link Option} value checks.
     * <p>
     * This method should be used to disable a custom comparison strategy set by calling
     * {@link #usingValueComparator(Comparator)}.
     *
     * @return {@code this} assertion object.
     */
    @CheckReturnValue
    public SELF usingDefaultValueComparator() {
        // fall back to default strategy to compare actual with other objects.
        optionValueComparisonStrategy = StandardComparisonStrategy.instance();
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Option} contains the instance given as an argument.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Option}.
     * @return this assertion object.
     */
    public SELF containsSame(VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);
        if (actual.isEmpty()) throwAssertionError(shouldContain(expectedValue));
        if (actual.get() != expectedValue) throwAssertionError(shouldContainSame(actual, expectedValue));
        return myself;
    }

    /**
     * Call {@link Option#flatMap(Function) flatMap} on the {@code Option} under test, assertions chained afterwards are performed on the {@code Option} resulting from the flatMap call.
     *
     * @param mapper the {@link Function} to use in the {@link Option#flatMap(Function) flatMap} operation.
     * @return a new {@link org.assertj.vavr.api.AbstractOptionAssert} for assertions chaining on the flatMap of the Option.
     * @throws AssertionError if the actual {@link Option} is null.
     */
    @CheckReturnValue
    public <U> AbstractOptionAssert<?, U> flatMap(Function<? super VALUE, Option<U>> mapper) {
        isNotNull();
        return VavrAssertions.assertThat(actual.flatMap(mapper));
    }

    /**
     * Call {@link Option#map(Function) map} on the {@code Option} under test, assertions chained afterwards are performed on the {@code Option} resulting from the map call.
     *
     * @param mapper the {@link Function} to use in the {@link Option#map(Function) map} operation.
     * @return a new {@link org.assertj.vavr.api.AbstractOptionAssert} for assertions chaining on the map of the Option.
     * @throws AssertionError if the actual {@link Option} is null.
     */
    @CheckReturnValue
    public <U> AbstractOptionAssert<?, U> map(Function<? super VALUE, ? extends U> mapper) {
        isNotNull();
        return VavrAssertions.assertThat(actual.map(mapper));
    }

    private void checkNotNull(Object expectedValue) {
        checkArgument(expectedValue != null, "The expected value should not be <null>.");
    }

    private void assertValueIsPresent() {
        isNotNull();
        if (actual.isEmpty()) throwAssertionError(shouldBePresent());
    }
}

