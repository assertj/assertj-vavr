package org.assertj.vavr.api;

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

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.EnumerableAssert;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;

import java.util.Comparator;

import static org.assertj.core.error.ShouldBeEmpty.shouldBeEmpty;
import static org.assertj.core.error.ShouldBeNullOrEmpty.shouldBeNullOrEmpty;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.internal.Arrays.assertIsArray;
import static org.assertj.core.internal.CommonValidations.hasSameSizeAsCheck;
import static org.assertj.core.util.IterableUtil.sizeOf;
import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * Assertions for {@link Map}.
 *
 * @param <KEY>   key type of the {@link Map}.
 * @param <VALUE> value type of the {@link Map}.
 */
abstract class AbstractMapAssert<SELF extends AbstractMapAssert<SELF, ACTUAL, KEY, VALUE>, ACTUAL extends Map<KEY, VALUE>, KEY, VALUE>
        extends AbstractObjectAssert<SELF, ACTUAL> implements EnumerableAssert<SELF, Tuple2<? extends KEY, ? extends VALUE>> {

    private ComparisonStrategy elementComparisonStrategy;

    AbstractMapAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
        this.elementComparisonStrategy = StandardComparisonStrategy.instance();
    }

    @Override
    public void isNullOrEmpty() {
        if (actual != null && !actual.isEmpty()) throwAssertionError(shouldBeNullOrEmpty(actual));
    }

    @Override
    public void isEmpty() {
        isNotNull();
        if (!actual.isEmpty()) throwAssertionError(shouldBeEmpty(actual));
    }

    @Override
    public SELF isNotEmpty() {
        isNotNull();
        if (actual.isEmpty()) throwAssertionError(shouldNotBeEmpty());
        return myself;
    }

    @Override
    public SELF hasSize(int expectedSize) {
        isNotNull();
        if (actual.size() != expectedSize)
            throwAssertionError(shouldHaveSize(actual, actual.size(), expectedSize));
        return myself;
    }

    @Override
    public SELF hasSameSizeAs(Iterable<?> other) {
        isNotNull();
        checkNotNull(other, "The other Map to compare actual size with should not be null");
        final long expectedSize = sizeOf(other);
        if (actual.size() != expectedSize)
            throwAssertionError(shouldHaveSameSizeAs(actual, actual.size(), expectedSize));
        return myself;
    }

    @Override
    public SELF hasSameSizeAs(Object array) {
        isNotNull();
        assertIsArray(info, array);
        hasSameSizeAsCheck(info, actual, array, actual.size());
        return myself;
    }

    @Override
    public SELF usingElementComparator(Comparator<? super Tuple2<? extends KEY, ? extends VALUE>> customComparator) {
        elementComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    @Override
    public SELF usingDefaultElementComparator() {
        elementComparisonStrategy = StandardComparisonStrategy.instance();
        return myself;
    }

}
