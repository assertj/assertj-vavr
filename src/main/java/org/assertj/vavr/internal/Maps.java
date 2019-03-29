package org.assertj.vavr.internal;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.ShouldContainAnyOf;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

import java.util.function.Predicate;

import static org.assertj.core.error.ShouldContain.shouldContain;
import static org.assertj.core.error.ShouldContainExactly.elementsDifferAtIndex;
import static org.assertj.core.error.ShouldContainExactly.shouldContainExactly;
import static org.assertj.core.error.ShouldContainKeys.shouldContainKeys;
import static org.assertj.core.error.ShouldContainOnly.shouldContainOnly;
import static org.assertj.core.error.ShouldContainOnlyKeys.shouldContainOnlyKeys;
import static org.assertj.core.error.ShouldContainValue.shouldContainValue;
import static org.assertj.core.error.ShouldContainValues.shouldContainValues;
import static org.assertj.core.error.ShouldNotContain.shouldNotContain;
import static org.assertj.core.internal.Arrays.assertIsArray;
import static org.assertj.core.internal.CommonValidations.failIfEmptySinceActualIsNotEmpty;
import static org.assertj.core.internal.CommonValidations.hasSameSizeAsCheck;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.core.util.Preconditions.checkNotNull;

public final class Maps {

    private static final Maps INSTANCE = new Maps();

    private Failures failures = Failures.instance();

    private Maps() {}

    public static Maps instance() {
        return INSTANCE;
    }

    public <K, V> void assertContainsAnyOf(AssertionInfo info, Map<K, V> actual,
                                           Tuple2<K, V>[] entries) {
        doCommonContainsCheck(info, actual, entries);
        // if both actual and values are empty, then assertion passes.
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmptySinceActualIsNotEmpty(entries);
        for (Tuple2<? extends K, ? extends V> entry : entries) {
            if (containsEntry(actual, entry)) return;
        }
        throw failures.failure(info, ShouldContainAnyOf.shouldContainAnyOf(actual, entries));
    }

    /**
     * Asserts that the given {@code Map} contains the given entries, in any order.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Map}.
     * @param entries the entries that are expected to be in the given {@code Map}.
     * @throws NullPointerException     if the array of entries is {@code null}.
     * @throws IllegalArgumentException if the array of entries is empty.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the given {@code Map} is {@code null}.
     * @throws AssertionError           if the given {@code Map} does not contain the given entries.
     */
    public <K, V> void assertContains(AssertionInfo info, Map<K, V> actual,
                                      Tuple2<K, V>[] entries) {
        doCommonContainsCheck(info, actual, entries);
        // if both actual and values are empty, then assertion passes.
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmptySinceActualIsNotEmpty(entries);
        final Set<Tuple2<K, V>> notFound = Array.of(entries).filter(notPresentIn(actual)).toSet();
        if (isNotEmpty(notFound)) {
            throw failures.failure(info, shouldContain(actual, entries, notFound));
        }
    }

    /**
     * Asserts that the given {@code Map} does not contain the given entries.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Map}.
     * @param entries the entries that are expected to be in the given {@code Map}.
     * @throws NullPointerException     if the array of entries is {@code null}.
     * @throws IllegalArgumentException if the array of entries is empty.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the given {@code Map} is {@code null}.
     * @throws AssertionError           if the given {@code Map} contains any of the given entries.
     */
    public <K, V> void assertDoesNotContain(AssertionInfo info, Map<K, V> actual,
                                            Tuple2<K, V>[] entries) {
        failIfNullOrEmpty(entries);
        assertNotNull(info, actual);
        failIfEmptySinceActualIsNotEmpty(entries);
        final Set<Tuple2<K, V>> found = Array.of(entries).filter(actual::contains).toSet();
        if (isNotEmpty(found)) {
            throw failures.failure(info, shouldNotContain(actual, entries, found));
        }
    }

    /**
     * Verifies that the actual {@code Map} contains the given keys.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Map}.
     * @param keys   the given keys.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the array of keys is empty.
     * @throws AssertionError           if the given {@code Map} is {@code null}.
     * @throws AssertionError           if the given {@code Map} does not contain the given keys.
     */
    public <K, V> void assertContainsKeys(AssertionInfo info, Map<K, V> actual,
                                          @SuppressWarnings("unchecked") K... keys) {
        doCommonContainsCheck(info, actual, keys);
        if (doCommonEmptinessChecks(actual, keys)) return;

        Set<K> expected = HashSet.of(keys);
        Set<K> notFound = expected.filter(notPresentIn(actual.keySet()));
        if (isNotEmpty(notFound)) {
            throw failures.failure(info, shouldContainKeys(actual, notFound.toJavaSet()));
        }
    }

    /**
     * Asserts that the given {@code Map} contains the given entries only.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Map}.
     * @param entries the entries that are expected to only be in the given {@code Map}.
     * @throws AssertionError           if the array of entries is {@code null}.
     * @throws AssertionError           if the array of entries is empty.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the given {@code Map} is {@code null}.
     * @throws AssertionError           if the given {@code Map} contains any of the given entries.
     */
    public <K, V> void assertContainsOnly(AssertionInfo info, Map<K, V> actual, Iterable<Tuple2<K, V>> entries) {
        assertNotNull(info, actual);
        failIfNull(entries);
        if (actual.isEmpty() && !entries.iterator().hasNext()) {
            return;
        }
        failIfEmpty(entries);
        Map<K, V> expected = HashMap.ofEntries(entries);
        Map<K, V> notExpected = actual.filter(notPresentIn(expected));
        if (isNotEmpty(notExpected)) {
            Map<K, V> notFound = expected.filter(notPresentIn(actual));
            throw failures.failure(info, shouldContainOnly(actual, expected, notFound, notExpected));
        }
    }

    /**
     * Verifies that the actual map contains only the given entries and nothing else, <b>in order</b>.<br>
     * This assertion should only be used with map that have a consistent iteration order (i.e. don't use it with
     * {@link io.vavr.collection.HashMap}).
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Map}.
     * @param entries the given entries.
     * @throws NullPointerException     if the given entries array is {@code null}.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws IllegalArgumentException if the given entries array is empty.
     * @throws AssertionError           if the actual map does not contain the given entries with same order, i.e. the actual map.
     *                                  contains some or none of the given entries, or the actual map contains more entries than the given ones.
     *                                  or entries are the same but the order is not.
     */
    public <K, V> void assertContainsExactly(AssertionInfo info, Map<K, V> actual,
                                             @SuppressWarnings("unchecked") Tuple2<? extends K, ? extends V>... entries) {
        doCommonContainsCheck(info, actual, entries);
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmpty(entries);
        assertHasSameSizeAs(info, actual, entries);

        final Map<K, V> expectedEntries = asLinkedMap(entries);
        final Map<K, V> notExpected = actual.filter(entry -> !expectedEntries.contains(entry));
        final Map<K, V> notFound = expectedEntries.filter(entry -> !actual.contains(entry));

        if (notExpected.isEmpty() && notFound.isEmpty()) {
            // check entries order
            int index = 0;
            for (K keyFromActual : actual.keySet()) {
                if (areNotEqual(keyFromActual, entries[index]._1)) {
                    Tuple2<K, V> actualEntry = Tuple.of(keyFromActual, actual.get(keyFromActual).get());
                    throw failures.failure(info, elementsDifferAtIndex(actualEntry, entries[index], index));
                }
                index++;
            }
            // all entries are in the same order.
            return;
        }

        throw failures.failure(info, shouldContainExactly(actual, List.of(entries), notFound, notExpected));
    }

    /**
     * Asserts that the given {@code Map} contains the given keys, in any order.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Map}.
     * @param keys    the keys that are expected to be in the given {@code Map}.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the array of keys is empty.
     * @throws AssertionError           if the given {@code Map} is {@code null}.
     * @throws AssertionError           if the given {@code Map} does not contain the given keys.
     */
    public <K, V> void assertContainsOnlyKeys(AssertionInfo info, Map<K, V> actual, K[] keys) {
        doCommonContainsCheck(info, actual, keys);
        if (doCommonEmptinessChecks(actual, keys)) return;

        Set<K> expected = HashSet.of(keys);
        Set<K> notExpected = actual.keySet().filter(notPresentIn(expected));
        if (isNotEmpty(notExpected)) {
            Set<K> notFound = expected.filter(notPresentIn(actual.keySet()));
            throw failures.failure(info, shouldContainOnlyKeys(actual, expected, notFound, notExpected));
        }
    }

    /**
     * Verifies that the actual map contains the given values.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Map}.
     * @param values the given values.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map not contains the given values.
     * @throws NullPointerException if values vararg is {@code null}.
     */
    public <K, V> void assertContainsValues(AssertionInfo info, Map<K, V> actual,
                                            @SuppressWarnings("unchecked") V... values) {
        assertNotNull(info, actual);
        checkNotNull(values, "The array of values to look for should not be null");
        if (actual.isEmpty() && values.length == 0) return;

        Set<V> expected = HashSet.of(values);
        Set<V> notFound = expected.filter(notPresentIn(actual.values()));
        if (isNotEmpty(notFound)) throw failures.failure(info, shouldContainValues(actual, notFound.toJavaSet()));
    }

    /**
     * Verifies that the actual map contain the given value.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Map}.
     * @param value  the given value.
     * @throws AssertionError if the actual map is {@code null}.
     * @throws AssertionError if the actual map not contains the given value.
     */
    public <K, V> void assertContainsValue(AssertionInfo info, Map<K, V> actual, V value) {
        assertNotNull(info, actual);
        if (!actual.containsValue(value)) throw failures.failure(info, shouldContainValue(actual, value));
    }

    /**
     * Asserts that the number of entries in the given {@code Map} has the same size as the other array.
     *
     * @param info  contains information about the assertion.
     * @param map   the given {@code Map}.
     * @param other the group to compare.
     * @throws AssertionError if the given {@code Map} is {@code null}.
     * @throws AssertionError if the given array is {@code null}.
     * @throws AssertionError if the number of entries in the given {@code Map} does not have the same size.
     */
    public void assertHasSameSizeAs(AssertionInfo info, Map<?, ?> map, Object other) {
        assertNotNull(info, map);
        assertIsArray(info, other);
        hasSameSizeAsCheck(info, map, other, map.size());
    }

    private <K, V> void doCommonContainsCheck(AssertionInfo info, Map<K, V> actual,
                                              Tuple2<? extends K, ? extends V>[] entries) {
        assertNotNull(info, actual);
        failIfNull(entries);
    }

    private <K, V> void doCommonContainsCheck(AssertionInfo info, Map<K, V> actual, K[] keys) {
        assertNotNull(info, actual);
        failIfNull(keys);
    }

    private <K, V> boolean doCommonEmptinessChecks(Map<K, V> actual, K[] keys) {
        if (actual.isEmpty() && keys.length == 0) {
            return true;
        }
        failIfEmpty(keys);
        return false;
    }

    private <K, V> boolean containsEntry(Map<K, V> actual, Tuple2<? extends K, ? extends V> entry) {
        checkNotNull(entry, "Entries to look for should not be null");
        return actual.containsKey(entry._1) && areEqual(actual.get(entry._1).get(), entry._2);
    }

    private static <K, V> void failIfEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        checkArgument(entries.length > 0, "The array of entries to look for should not be empty");
    }

    private static <K, V> void failIfEmpty(Iterable<Tuple2<K, V>> entries) {
        checkArgument(entries.iterator().hasNext(), "The entries to look for should not be empty");
    }

    private static <K> void failIfEmpty(K[] keys) {
        checkArgument(keys.length > 0, "The array of keys to look for should not be empty");
    }

    private static <K, V> void failIfNullOrEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        failIfNull(entries);
        failIfEmpty(entries);
    }

    private static <K, V> void failIfNull(Tuple2<? extends K, ? extends V>[] entries) {
        checkNotNull(entries, "The array of entries to look for should not be null");
    }

    private static <K, V> void failIfNull(Iterable<Tuple2<K, V>> entries) {
        checkNotNull(entries, "The entries to look for should not be null");
    }

    private static <K> void failIfNull(K[] keys) {
        checkNotNull(keys, "The array of keys to look for should not be null");
    }

    private static <K> boolean areNotEqual(K actualKey, K expectedKey) {
        return !areEqual(actualKey, expectedKey);
    }

    private static void assertNotNull(AssertionInfo info, Map<?, ?> actual) {
        Objects.instance().assertNotNull(info, actual);
    }

    private static <K, V> Map<K, V> asLinkedMap(Tuple2<? extends K, ? extends V>[] entries) {
        if (entries.length != nonNullEntries(entries).length()) {
            throw new NullPointerException("One of expected entries is null");
        } else {
            return LinkedHashMap.ofEntries(entries);
        }
    }

    private static <K, V> Array<Tuple2<? extends K, ? extends V>> nonNullEntries(Tuple2<? extends K, ? extends V>[] entries) {
        return Array.of(entries).filter(java.util.Objects::nonNull);
    }

    private static <K, V> Predicate<Tuple2<K, V>> notPresentIn(Map<K, V> map) {
        return tuple -> !map.contains(tuple);
    }

    private static <K> Predicate<K> notPresentIn(Set<K> keys) {
        return key -> !keys.contains(key);
    }

    private static <K> Predicate<K> notPresentIn(Seq<K> keys) {
        return key -> !keys.contains(key);
    }

    private static boolean isNotEmpty(Traversable traversable) {
        return !traversable.isEmpty();
    }
}
