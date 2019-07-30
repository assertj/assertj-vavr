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

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import org.assertj.core.api.EnumerableAssert;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.vavr.internal.Maps;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.StreamSupport;

import static org.assertj.core.error.ShouldBeEmpty.shouldBeEmpty;
import static org.assertj.core.error.ShouldBeNullOrEmpty.shouldBeNullOrEmpty;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.IterableUtil.sizeOf;
import static org.assertj.core.util.Preconditions.checkNotNull;

/**
 * Assertions for {@link Map}.
 *
 * @param <KEY>   key type of the {@link Map}.
 * @param <VALUE> value type of the {@link Map}.
 */
abstract class AbstractMapAssert<SELF extends AbstractMapAssert<SELF, ACTUAL, KEY, VALUE>, ACTUAL extends Map<KEY, VALUE>, KEY, VALUE>
        extends AbstractValueAssert<SELF, ACTUAL> implements EnumerableAssert<SELF, Tuple2<? extends KEY, ? extends VALUE>> {

    private final Maps maps = Maps.instance();
    private ComparisonStrategy elementComparisonStrategy;

    AbstractMapAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
        this.elementComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Verifies that all the actual map entries satisfy the given {@code entryRequirements}.
     * If the actual map is empty, this assertion succeeds as there is nothing to check.
     *
     * @param entryRequirements the given requirements that each entry must satisfy.
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
     * @throws AssertionError       if the actual map is not empty and the given argument is an empty array.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map does not contain the given entries.
     */
    public SELF contains(@SuppressWarnings("unchecked") Tuple2<KEY, VALUE>... entries) {
        maps.assertContains(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map contains at least one of the given entries.
     *
     * @param entries the given entries.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given argument is {@code null}.
     * @throws NullPointerException if any of the entries in the given array is {@code null}.
     * @throws AssertionError       if the actual map is not empty and the given argument is an empty array.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map does not contain any of the given entries.
     */
    public SELF containsAnyOf(@SuppressWarnings("unchecked") Tuple2<KEY, VALUE>... entries) {
        maps.assertContainsAnyOf(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map contains all entries of the given iterable, in any order.
     *
     * @param other the iterable with the given entries.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given argument is {@code null}.
     * @throws NullPointerException if any of the entries in the given iterable is {@code null}.
     * @throws AssertionError       if the actual map is not empty and the given argument is an empty iterable.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map does not contain the given entries.
     */
    @SuppressWarnings("unchecked")
    public SELF containsAllEntriesOf(Iterable<Tuple2<KEY, VALUE>> other) {
        final Tuple2<KEY, VALUE>[] entries = StreamSupport.stream(other.spliterator(), false).toArray(Tuple2[]::new);
        maps.assertContains(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map contains the given entry.
     *
     * @param key   the given key to check.
     * @param value the given value to check.
     * @return {@code this} assertion object.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map does not contain the given entries.
     */
    public SELF containsEntry(KEY key, VALUE value) {
        maps.assertContains(info, actual, array(Tuple.of(key, value)));
        return myself;
    }

    /**
     * Verifies that the actual map does not contain the given entries.
     *
     * @param entries the given entries.
     * @return {@code this} assertion object.
     * @throws NullPointerException     if the given argument is {@code null}.
     * @throws IllegalArgumentException if the given argument is an empty array.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map contains any of the given entries.
     */
    public SELF doesNotContain(Tuple2<KEY, VALUE>... entries) {
        maps.assertDoesNotContain(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map does not contain the given entry.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return {@code this} assertion object.
     * @throws NullPointerException     if the given argument is {@code null}.
     * @throws IllegalArgumentException if the given argument is an empty array.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map contains any of the given entries.
     */
    public SELF doesNotContainEntry(KEY key, VALUE value) {
        maps.assertDoesNotContain(info, actual, array(Tuple.of(key, value)));
        return myself;
    }

    public SELF containsOnly(Iterable<Tuple2<KEY, VALUE>> entries) {
        isNotNull();
        maps.assertContainsOnly(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map contains only the given entries and nothing else, <b>in order</b>.<br>
     * This assertion should only be used with maps that have a consistent iteration order (i.e. don't use it with
     * {@link io.vavr.collection.HashMap}, prefer {@link #containsOnly} methods in that case).
     *
     * @param entries the given entries.
     * @return {@code this} assertions object.
     * @throws NullPointerException     if the given entries array is {@code null}.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws IllegalArgumentException if the given entries array is empty.
     * @throws AssertionError           if the actual map does not contain the given entries with same order, i.e. the actual map
     *                                  contains some or none of the given entries, or the actual map contains more entries than the given ones
     *                                  or entries are the same but the order is not.
     */
    public SELF containsExactly(@SuppressWarnings("unchecked") Tuple2<? extends KEY, ? extends VALUE>... entries) {
        maps.assertContainsExactly(info, actual, entries);
        return myself;
    }

    /**
     * Verifies that the actual map contains the given key.
     *
     * @param key the given key.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map does not contain the given key.
     */
    @SuppressWarnings("unchecked")
    public SELF containsKey(KEY key) {
        return containsKeys(key);
    }

    /**
     * Verifies that the actual map contains the given keys.
     *
     * @param keys the given keys.
     * @return {@code this} assertions object.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map does not contain the given key.
     * @throws IllegalArgumentException if the given argument is an empty array.
     * @throws NullPointerException     if the array of keys is {@code null}.
     */
    public SELF containsKeys(@SuppressWarnings("unchecked") KEY... keys) {
        maps.assertContainsKeys(info, actual, keys);
        return myself;
    }

    /**
     * Verifies that the actual map contains only the given keys and nothing else, in any order.
     *
     * @param keys the given keys that should be in the actual map.
     * @return {@code this} assertions object.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map does not contain the given keys, i.e. the actual map contains some or none
     *                                  of the given keys, or the actual map contains more entries than the given ones.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the given argument is an empty array.
     */
    public SELF containsOnlyKeys(KEY... keys) {
        maps.assertContainsOnlyKeys(info, actual, keys);
        return myself;
    }

    /**
     * Verifies that the actual map does not contain the given key.
     *
     * @param key the given key.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map contains the given key.
     */
    @SuppressWarnings("unchecked")
    public SELF doesNotContainKey(KEY key) {
        return doesNotContainKeys(key);
    }

    /**
     * Verifies that the actual map does not contain the given keys.
     *
     * @param keys the given keys.
     * @return {@code this} assertions object.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws AssertionError           if the actual map contains the given key.
     * @throws IllegalArgumentException if the given argument is an empty array.
     * @throws NullPointerException     if the array of keys is {@code null}.
     */
    public SELF doesNotContainKeys(@SuppressWarnings("unchecked") KEY... keys) {
        maps.assertDoesNotContainKeys(info, actual, keys);
        return myself;
    }

    /**
     * Verifies that the actual map contains the given value.
     *
     * @param value the value to look for.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map does not contain the given value.
     */
    public SELF containsValue(VALUE value) {
        maps.assertContainsValue(info, actual, value);
        return myself;
    }

    /**
     * Verifies that the actual map contains the given values.
     *
     * @param values the values to look for in the actual map.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map does not contain the given values.
     */
    public SELF containsValues(@SuppressWarnings("unchecked") VALUE... values) {
        maps.assertContainsValues(info, actual, values);
        return myself;
    }

    /**
     * Verifies that the actual map does not contain the given value.
     *
     * @param value the value to look for.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map contains the given value.
     */
    public SELF doesNotContainValue(VALUE value) {
        maps.assertDoesNotContainValue(info, actual, value);
        return myself;
    }

    /**
     * Verifies that the actual map does not contain the given values.
     *
     * @param values the values to look for in the actual map.
     * @return {@code this} assertions object.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map contains the given values.
     */
    public SELF doesNotContainValues(@SuppressWarnings("unchecked") VALUE... values) {
        maps.assertDoesNotContainValues(info, actual, values);
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
        checkNotNull(other, "The other Iterable to compare actual size with should not be null");
        final long expectedSize = sizeOf(other);
        if (actual.size() != expectedSize)
            throwAssertionError(shouldHaveSameSizeAs(actual, actual.size(), expectedSize));
        return myself;
    }

    @Override
    public SELF hasSameSizeAs(Object array) {
        maps.assertHasSameSizeAs(info, actual, array);
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
