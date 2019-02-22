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
import io.vavr.collection.Array;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.EnumerableAssert;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;

import java.util.Comparator;
import java.util.function.BiConsumer;

import static org.assertj.core.error.ShouldBeEmpty.shouldBeEmpty;
import static org.assertj.core.error.ShouldBeNullOrEmpty.shouldBeNullOrEmpty;
import static org.assertj.core.error.ShouldContain.shouldContain;
import static org.assertj.core.error.ShouldContainAnyOf.shouldContainAnyOf;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.internal.Arrays.assertIsArray;
import static org.assertj.core.internal.CommonValidations.failIfEmptySinceActualIsNotEmpty;
import static org.assertj.core.internal.CommonValidations.hasSameSizeAsCheck;
import static org.assertj.core.util.IterableUtil.sizeOf;
import static org.assertj.core.util.Objects.areEqual;
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

    /**
     * Verifies that all the actual map entries satisfy the given {@code entryRequirements}.
     * If the actual map is empty, this assertion succeeds as there is nothing to check.
     *
     * @param entryRequirements the given requirements that each entry must sastify.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given entryRequirements {@link BiConsumer} is {@code null}.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if one or more entries don't satisfy the given requirements.
     */
    public SELF allSatisfy(BiConsumer<? super KEY, ? super VALUE> entryRequirements) {
        checkNotNull(entryRequirements, "The BiConsumer<K, V> expressing the assertions requirements must not be null");
        isNotNull();
        actual.forEach(entry -> entryRequirements.accept(entry._1, entry._2));
        return myself;
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

    /**
     * Verifies that the actual map contains the given entries, in any order.
     * <p>This assertion succeeds if both actual map and given entries are empty.</p>
     *
     * @param entries the given entries.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given argument is {@code null}.
     * @throws NullPointerException if any of the entries in the given array is {@code null}.
     * @throws AssertionError       if the actual map is not empty anf the given argument is an empty array.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map does not contain the given entries.
     */
    public SELF contains(@SuppressWarnings("unchecked") Tuple2<KEY, VALUE>... entries) {
        checkNotNull(entries, "Entries cannot be null");
        isNotNull();
        // if both actual and values are empty, then assertion passes.
        if (actual.isEmpty() && entries.length == 0) return myself;
        failIfEmptySinceActualIsNotEmpty(entries);
        final Set<Tuple2<? extends KEY, ? extends VALUE>> notFound = Array
                .of(entries)
                .foldLeft(HashSet.empty(), (set, tuple) -> {
                    if (actual.contains(tuple)) {
                        return set;
                    } else {
                        return set.add(tuple);
                    }
                });
        if (!notFound.isEmpty()) {
            throwAssertionError(shouldContain(actual, entries, notFound));
        }
        return myself;
    }

    /**
     * Verifies that the actual map contains at least one of the given entries.
     *
     * @param entries the given entries.
     * @return {@code this} assertion object.
     * @throws NullPointerException     if the given argument is {@code null}.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the actual map is not empty anf the given argument is an empty array.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map does not contain any of the given entries.
     */
    public SELF containsAnyOf(@SuppressWarnings("unchecked") Tuple2<KEY, VALUE>... entries) {
        checkNotNull(entries, "Entries cannot be null");
        isNotNull();
        // if both actual and values are empty, then assertion passes.
        if (actual.isEmpty() && entries.length == 0) return myself;
        failIfEmptySinceActualIsNotEmpty(entries);
        for (Tuple2<KEY, VALUE> entry : entries) {
            if (containsEntry(actual, entry)) return myself;
        }
        throw new AssertionError(shouldContainAnyOf(actual, entries).create());
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
        checkNotNull(other, "The other Iterable to compare actual size with should not be null");
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

    private boolean containsEntry(Map<KEY, VALUE> actual, Tuple2<KEY, VALUE> entry) {
        checkNotNull(entry, "Entries to look for should not be null");
        return actual.containsKey(entry._1) && areEqual(actual.get(entry._1).get(), entry._2);
    }
}
