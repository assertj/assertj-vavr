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

import io.vavr.control.Validation;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.FieldByFieldComparator;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;

import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.vavr.api.ValidationShouldBeInvalid.shouldBeInvalid;
import static org.assertj.vavr.api.ValidationShouldBeValid.shouldBeValid;
import static org.assertj.vavr.api.ValidationShouldContain.*;
import static org.assertj.vavr.api.ValidationShouldContainInstanceOf.shouldContainInvalidInstanceOf;
import static org.assertj.vavr.api.ValidationShouldContainInstanceOf.shouldContainValidInstanceOf;

/**
 * Assertions for {@link Validation}.
 *
 * @param <SELF>    the "self" type of this assertion class.
 * @param <INVALID> type of the value in the case of the invalid {@link Validation}.
 * @param <VALID>   type of the value in the case of the valid {@link Validation}.
 * @author Michał Chmielarz
 */
abstract class AbstractValidationAssert<SELF extends AbstractValidationAssert<SELF, INVALID, VALID>, INVALID, VALID> extends
        AbstractValueAssert<SELF, Validation<INVALID, VALID>> {

    private ComparisonStrategy validationValueComparisonStrategy;

    AbstractValidationAssert(Validation<INVALID, VALID> actual, Class<?> selfType) {
        super(actual, selfType);
        this.validationValueComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Verifies that the actual {@link Validation} is invalid.
     *
     * @return this assertion object.
     */
    public SELF isInvalid() {
        assertIsInvalid();
        return myself;
    }

    /**
     * Verifies that the actual {@link Validation} is valid.
     *
     * @return this assertion object.
     */
    public SELF isValid() {
        assertIsValid();
        return myself;
    }

    /**
     * Verifies that the actual {@link Validation} is {@link Validation.Valid}
     * and contains the given value.
     *
     * @param expectedValue the expected value inside the {@link Validation}.
     * @return this assertion object.
     */
    public SELF containsValid(VALID expectedValue) {
        assertIsValid();
        if (!validationValueComparisonStrategy.areEqual(actual.get(), expectedValue))
            throwAssertionError(shouldContainValid(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual {@link Validation} is {@link Validation.Invalid}
     * and contains the given error value.
     *
     * @param expectedErrorValue the expected error value inside the {@link Validation}.
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
     * Verifies that the actual valid {@link Validation} contains the instance given as an argument.
     *
     * @param expectedValue the expected value inside the {@link Validation}.
     * @return this assertion object.
     */
    public SELF containsValidSame(VALID expectedValue) {
        assertIsValid();
        if (actual.get() != expectedValue)
            throwAssertionError(shouldContainValidSame(actual, expectedValue));
        return myself;
    }

    /**
     * Verifies that the actual invalid {@link Validation} contains the instance given as an argument.
     *
     * @param expectedErrorValue the expected value inside the {@link Validation}.
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
     * Verifies that the actual valid {@link Validation} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the valid {@link Validation}.
     * @return this assertion object.
     */
    public SELF containsValidInstanceOf(Class<?> clazz) {
        assertIsValid();
        if (!clazz.isInstance(actual.get()))
            throwAssertionError(shouldContainValidInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Verifies that the actual invalid {@link Validation} contains a value that is an instance of the argument.
     *
     * @param clazz the expected class of the value inside the invalid {@link Validation}.
     * @return this assertion object.
     */
    public SELF containsInvalidInstanceOf(Class<?> clazz) {
        assertIsInvalid();
        if (!clazz.isInstance(actual.getError()))
            throwAssertionError(shouldContainInvalidInstanceOf(actual, clazz));
        return myself;
    }

    /**
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link Validation} value's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given comparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingValueComparator(Comparator<?> customComparator) {
        validationValueComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Use field/property by field/property comparison (including inherited fields/properties) instead of relying on
     * actual type A <code>equals</code> method to compare the {@link Validation} value's object for incoming assertion
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
     * Revert to standard comparison for incoming assertion {@link Validation} value checks.
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
