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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.Conditions;
import org.assertj.core.internal.FieldByFieldComparator;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;
import java.util.function.Consumer;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.ValidationShouldContain.*;
import static org.assertj.vavr.api.ValidationShouldContainInstanceOf.shouldContainInvalidInstanceOf;
import static org.assertj.vavr.api.ValidationShouldContainInstanceOf.shouldContainValidInstanceOf;

/**
 * Assertions for {@link io.vavr.control.Validation}.
 *
 * @param <SELF>    the "self" type of this assertion class.
 * @param <INVALID> type of the value in the case of the invalid {@link io.vavr.control.Validation}.
 * @param <VALID>   type of the value in the case of the valid {@link io.vavr.control.Validation}.
 * @author Michał Chmielarz
 */
abstract class AbstractValidationAssert<SELF extends AbstractValidationAssert<SELF, INVALID, VALID>, INVALID, VALID> extends
        AbstractValueAssert<SELF, Validation<INVALID, VALID>> {

    private final Conditions conditions = Conditions.instance();

    private ComparisonStrategy validationValueComparisonStrategy;

    AbstractValidationAssert(Validation<INVALID, VALID> actual, Class<?> selfType) {
        super(actual, selfType);
        this.validationValueComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} is invalid.
     *
     * @return this assertion object.
     */
    public SELF isInvalid() {
        assertIsInvalid();
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} is valid.
     *
     * @return this assertion object.
     */
    public SELF isValid() {
        assertIsValid();
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} is {@link io.vavr.control.Validation.Valid}
     * and contains the given value.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsValid(VALID expectedValue) {
        assertIsValid();
        if (!validationValueComparisonStrategy.areEqual(actual.get(), expectedValue))
            throwAssertionError(shouldContainValid(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} is {@link io.vavr.control.Validation.Invalid}
     * and contains the given error value.
     *
     * @param expectedErrorValue the expected error value inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsInvalid(INVALID expectedErrorValue) {
        assertIsInvalid();
        checkNotNull(expectedErrorValue);
        if (!validationValueComparisonStrategy.areEqual(actual.getError(), expectedErrorValue))
            throwAssertionError(shouldContainInvalid(actual, expectedErrorValue));
        return myself;
    }

    /**
     * Verifies that the actual valid {@link io.vavr.control.Validation} contains the instance given as an argument.
     *
     * @param expectedValue the expected value inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsValidSame(VALID expectedValue) {
        assertIsValid();
        if (actual.get() != expectedValue)
            throwAssertionError(shouldContainValidSame(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual invalid {@link io.vavr.control.Validation} contains the instance given as an argument.
     *
     * @param expectedErrorValue the expected value inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsInvalidSame(VALID expectedErrorValue) {
        assertIsInvalid();
        checkNotNull(expectedErrorValue);
        if (actual.getError() != expectedErrorValue)
            throwAssertionError(shouldContainInvalidSame(actual, expectedErrorValue));
        return myself;
    }

    /**
     * Verifies that the actual valid {@link io.vavr.control.Validation} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the valid {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsValidInstanceOf(Class<?> clazz) {
        assertIsValid();
        if (!clazz.isInstance(actual.get()))
            throwAssertionError(shouldContainValidInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Verifies that the actual invalid {@link io.vavr.control.Validation} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the invalid {@link io.vavr.control.Validation}.
     * @return this assertion object.
     */
    public SELF containsInvalidInstanceOf(Class<?> clazz) {
        assertIsInvalid();
        if (!clazz.isInstance(actual.getError()))
            throwAssertionError(shouldContainInvalidInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} contains a valid value and gives this value to the given
     * {@link java.util.function.Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     *
     * @param requirement to further assert on the object contained inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     * @throws AssertionError       if the actual {@link io.vavr.control.Validation} is null or invalid.
     * @throws NullPointerException if the given requirement is {@code null}.
     * @throws AssertionError       if the actual value does not satisfy the given requirement.
     */
    public SELF containsValidSatisfying(Consumer<VALID> requirement) {
        assertIsValid();
        requirement.accept(actual.get());
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} contains a valid value which satisfies the given {@link org.assertj.core.api.Condition}.
     *
     * @param condition the given condition.
     * @return this assertion object.
     * @throws AssertionError       if the actual {@link io.vavr.control.Validation} is null or invalid.
     * @throws NullPointerException if the given condition is {@code null}.
     * @throws AssertionError       if the actual value does not satisfy the given condition.
     */
    public SELF containsValidSatisfying(Condition<? super VALID> condition) {
        assertIsValid();
        conditions.assertIs(info, actual.get(), condition);
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} contains an invalid value and gives this value to the given
     * {@link java.util.function.Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     *
     * @param requirement to further assert on the object contained inside the {@link io.vavr.control.Validation}.
     * @return this assertion object.
     * @throws AssertionError       if the actual {@link io.vavr.control.Validation} is null or valid.
     * @throws NullPointerException if the given requirement is {@code null}.
     * @throws AssertionError       if the actual value does not satisfy the given requirement.
     */
    public SELF containsInvalidSatisfying(Consumer<INVALID> requirement) {
        assertIsInvalid();
        requirement.accept(actual.getError());
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.control.Validation} contains an invalid value which satisfies the given {@link org.assertj.core.api.Condition}.
     *
     * @param condition the given condition.
     * @return this assertion object.
     * @throws AssertionError       if the actual {@link io.vavr.control.Validation} is null or valid.
     * @throws NullPointerException if the given condition is {@code null}.
     * @throws AssertionError       if the actual value does not satisfy the given condition.
     */
    public SELF containsInvalidSatisfying(Condition<? super INVALID> condition) {
        assertIsInvalid();
        conditions.assertIs(info, actual.getError(), condition);
        return myself;
    }

    /**
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link io.vavr.control.Validation} value's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given customComparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingValueComparator(Comparator<?> customComparator) {
        validationValueComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Use field/property by field/property comparison (including inherited fields/properties) instead of relying on
     * actual type A <code>equals</code> method to compare the {@link io.vavr.control.Validation} value's object for incoming assertion
     * checks. Private fields are included but this can be disabled using
     * {@link org.assertj.core.api.Assertions#setAllowExtractingPrivateFields(boolean)}.
     *
     * @return {@code this} assertion object.
     */
    @CheckReturnValue
    public SELF usingFieldByFieldValueComparator() {
        return usingValueComparator(new FieldByFieldComparator());
    }

    /**
     * Revert to standard comparison for incoming assertion {@link io.vavr.control.Validation} value checks.
     * <p>
     * This method should be used to disable a custom comparison strategy set by calling
     * {@link #usingValueComparator(Comparator)}.
     *
     * @return {@code this} assertion object.
     */
    @CheckReturnValue
    public SELF usingDefaultRightValueComparator() {
        // fall back to default strategy to compare actual with other objects.
        validationValueComparisonStrategy = StandardComparisonStrategy.instance();
        return myself;
    }

    private void assertIsInvalid() {
        isNotNull();
        if (actual.isValid()) throwAssertionError(shouldBeInvalid(actual));
    }

    private void assertIsValid() {
        isNotNull();
        if (actual.isInvalid()) throwAssertionError(shouldBeValid(actual));
    }

    private void checkNotNull(Object expectedValue) {
        checkArgument(expectedValue != null, "The expected value should not be <null>.");
    }
}
