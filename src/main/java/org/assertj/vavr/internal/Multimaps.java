package org.assertj.vavr.internal;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.control.Option;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

import java.util.function.Predicate;

import static io.vavr.Predicates.not;
import static org.assertj.core.error.ElementsShouldBe.elementsShouldBe;
import static org.assertj.core.error.ShouldContain.shouldContain;
import static org.assertj.core.error.ShouldContainAnyOf.shouldContainAnyOf;
import static org.assertj.core.error.ShouldContainExactly.elementsDifferAtIndex;
import static org.assertj.core.error.ShouldContainExactly.shouldContainExactly;
import static org.assertj.core.error.ShouldContainKeys.shouldContainKeys;
import static org.assertj.core.error.ShouldContainOnly.shouldContainOnly;
import static org.assertj.core.error.ShouldContainOnlyKeys.shouldContainOnlyKeys;
import static org.assertj.core.error.ShouldContainValue.shouldContainValue;
import static org.assertj.core.error.ShouldContainValues.shouldContainValues;
import static org.assertj.core.error.ShouldNotContain.shouldNotContain;
import static org.assertj.core.error.ShouldNotContainKeys.shouldNotContainKeys;
import static org.assertj.core.error.ShouldNotContainValue.shouldNotContainValue;
import static org.assertj.core.internal.Arrays.assertIsArray;
import static org.assertj.core.internal.CommonValidations.failIfEmptySinceActualIsNotEmpty;
import static org.assertj.core.internal.CommonValidations.hasSameSizeAsCheck;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Preconditions.checkArgument;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.vavr.api.ShouldNotContainValues.shouldNotContainValues;

public final class Multimaps {

    private static final Multimaps INSTANCE = new Multimaps();

    private Failures failures = Failures.instance();

    private Conditions conditions = Conditions.instance();

    private Multimaps() {
    }

    public static Multimaps instance() {
        return INSTANCE;
    }

    /**
     * Verifies that the given {@code Multimap} contains the value for given {@code key} that satisfy given {@code valueCondition}.
     *
     * @param <K>            key type
     * @param <V>            value type
     * @param info           contains information about the assertion.
     * @param actual         the given {@code Multimap}.
     * @param key            the given key to check.
     * @param valueCondition the given condition for check value.
     * @throws NullPointerException if the given values is {@code null}.
     * @throws AssertionError       if the actual map is {@code null}.
     * @throws AssertionError       if the actual map not contains the given {@code key}.
     * @throws AssertionError       if the actual map contains the given key, but value does not match the given {@code valueCondition}.
     */
    @SuppressWarnings("unchecked")
    public <K, V> void assertHasEntrySatisfying(AssertionInfo info, Multimap<K, V> actual, K key,
                                                Condition<? super V> valueCondition) {
        conditions.assertIsNotNull(valueCondition);
        assertContainsKeys(info, actual, key);
        Option<Traversable<V>> value = actual.get(key);
        value
                .getOrElse(List.empty())
                .filter(valueCondition::matches)
                .getOrElseThrow(() -> failures.failure(info, elementsShouldBe(actual, value, valueCondition)));
    }

    public <K, V> void assertContainsAnyOf(AssertionInfo info, Multimap<K, V> actual,
                                           Tuple2<K, V>[] entries) {
        doCommonContainsCheck(info, actual, entries);
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmptySinceActualIsNotEmpty(entries);
        for (Tuple2<? extends K, ? extends V> entry : entries) {
            if (containsEntry(actual, entry)) return;
        }
        throw failures.failure(info, shouldContainAnyOf(actual, entries));
    }

    /**
     * Asserts that the given {@code Multimap} contains the given entries, in any order.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Multimap}.
     * @param entries the entries that are expected to be in the given {@code Multimap}.
     * @throws NullPointerException     if the array of entries is {@code null}.
     * @throws IllegalArgumentException if the array of entries is empty.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} does not contain the given entries.
     */
    public <K, V> void assertContains(AssertionInfo info, Multimap<K, V> actual,
                                      Tuple2<K, V>[] entries) {
        doCommonContainsCheck(info, actual, entries);
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmptySinceActualIsNotEmpty(entries);
        final Set<Tuple2<K, V>> notFound = Array.of(entries).filter(entryNotPresentIn(actual)).toSet();
        if (isNotEmpty(notFound)) {
            throw failures.failure(info, shouldContain(actual, entries, notFound));
        }
    }

    /**
     * Asserts that the given {@code Multimap} contains the given entries only.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Multimap}.
     * @param entries the entries that are expected to only be in the given {@code Multimap}.
     * @throws AssertionError       if the array of entries is {@code null}.
     * @throws AssertionError       if the array of entries is empty.
     * @throws NullPointerException if any of the entries in the given array is {@code null}.
     * @throws AssertionError       if the given {@code Multimap} is {@code null}.
     * @throws AssertionError       if the given {@code Multimap} contains any of the given entries.
     */
    public <K, V> void assertContainsOnly(AssertionInfo info, Multimap<K, V> actual, Iterable<Tuple2<K, V>> entries) {
        assertNotNull(info, actual);
        failIfNull(entries);
        if (actual.isEmpty() && !entries.iterator().hasNext()) return;
        failIfEmpty(entries);
        Multimap<K, V> expected = HashMultimap.withSeq().ofEntries(entries);
        Multimap<K, V> notExpected = actual.filter(entryNotPresentIn(expected));
        if (isNotEmpty(notExpected)) {
            Multimap<K, V> notFound = expected.filter(entryNotPresentIn(actual));
            throw failures.failure(info, shouldContainOnly(actual, expected, notFound, notExpected));
        }
    }


    /**
     * Verifies that the actual {@code Multimap} contains only the given entries and nothing else, <b>in order</b>.<br>
     * This assertion should only be used with {@code Multimap} that have a consistent iteration order (i.e. don't use it with
     * {@link io.vavr.collection.HashMultimap}).
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Multimap}.
     * @param entries the given entries.
     * @throws NullPointerException     if the given entries array is {@code null}.
     * @throws AssertionError           if the actual map is {@code null}.
     * @throws IllegalArgumentException if the given entries array is empty.
     * @throws AssertionError           if the actual {@code Multimap} does not contain the given entries with same order, i.e. the actual {@code Multimap}
     *                                  contains some or none of the given entries, or the actual {@code Multimap} contains more entries than the given ones
     *                                  or entries are the same but the order is not.
     */
    public <K, V> void assertContainsExactly(AssertionInfo info, Multimap<K, V> actual,
                                             @SuppressWarnings("unchecked") Tuple2<? extends K, ? extends V>... entries) {
        doCommonContainsCheck(info, actual, entries);
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmpty(entries);
        assertHasSameSizeAs(info, actual, entries);

        final Multimap<K, V> expectedEntries = asLinkedMultimap(entries);
        final Multimap<K, V> notExpected = actual.filter(entry -> !expectedEntries.contains(entry));
        final Multimap<K, V> notFound = expectedEntries.filter(entry -> !actual.contains(entry));

        if (notExpected.isEmpty() && notFound.isEmpty()) {
            // check entries order
            int index = 0;
            for (K keyFromActual : actual.keySet()) {
                if (areNotEqual(keyFromActual, entries[index]._1)) {
                    Tuple2<K, Traversable<V>> actualEntry = Tuple.of(keyFromActual, actual.get(keyFromActual).get());
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
     * Asserts that the given {@code Multimap} does not contain the given entries.
     *
     * @param <K>     key type
     * @param <V>     value type
     * @param info    contains information about the assertion.
     * @param actual  the given {@code Multimap}.
     * @param entries the entries that are expected to be in the given {@code Multimap}.
     * @throws NullPointerException     if the array of entries is {@code null}.
     * @throws IllegalArgumentException if the array of entries is empty.
     * @throws NullPointerException     if any of the entries in the given array is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} contains any of the given entries.
     */
    public <K, V> void assertDoesNotContain(AssertionInfo info, Multimap<K, V> actual,
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
     * Verifies that the actual {@code Multimap} contains the given keys.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param keys   the given keys.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the array of keys is empty.
     * @throws AssertionError           if the given {@code Multimap} is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} does not contain the given keys.
     */
    public <K, V> void assertContainsKeys(AssertionInfo info, Multimap<K, V> actual,
                                          @SuppressWarnings("unchecked") K... keys) {
        doCommonContainsCheck(info, actual, keys);
        if (doCommonEmptinessChecks(actual, keys)) return;

        Set<K> expected = HashSet.of(keys);
        Set<K> notFound = expected.filter(keyNotPresentIn(actual.keySet()));
        if (isNotEmpty(notFound)) {
            throw failures.failure(info, shouldContainKeys(actual, notFound.toJavaSet()));
        }
    }

    /**
     * Asserts that the given {@code Multimap} contains the given keys, in any order.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param keys   the keys that are expected to be in the given {@code Multimap}.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the array of keys is empty.
     * @throws AssertionError           if the given {@code Multimap} is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} does not contain the given keys.
     */
    public <K, V> void assertContainsOnlyKeys(AssertionInfo info, Multimap<K, V> actual, K[] keys) {
        doCommonContainsCheck(info, actual, keys);
        if (doCommonEmptinessChecks(actual, keys)) return;

        Set<K> expected = HashSet.of(keys);
        Set<K> notExpected = actual.keySet().filter(keyNotPresentIn(expected));
        if (isNotEmpty(notExpected)) {
            Set<K> notFound = expected.filter(keyNotPresentIn(actual.keySet()));
            throw failures.failure(info, shouldContainOnlyKeys(actual, expected, notFound, notExpected));
        }
    }

    /**
     * Verifies that the actual {@code Multimap} does not contain the given keys.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param keys   the given keys.
     * @throws NullPointerException     if the array of keys is {@code null}.
     * @throws IllegalArgumentException if the array of keys is empty.
     * @throws AssertionError           if the given {@code Multimap} is {@code null}.
     * @throws AssertionError           if the given {@code Multimap} contains the given keys.
     */
    public <K, V> void assertDoesNotContainKeys(AssertionInfo info, Multimap<K, V> actual,
                                                @SuppressWarnings("unchecked") K... keys) {
        doCommonContainsCheck(info, actual, keys);
        if (doCommonEmptinessChecks(actual, keys)) return;

        Set<K> expected = HashSet.of(keys);
        Set<K> found = expected.filter(keyPresentIn(actual.keySet()));
        if (isNotEmpty(found)) {
            throw failures.failure(info, shouldNotContainKeys(actual, found.toJavaSet()));
        }
    }

    /**
     * Verifies that the actual {@code Multimap} contains the given value.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param value  the given value.
     * @throws AssertionError if the actual {@code Multimap} is {@code null}.
     * @throws AssertionError if the actual {@code Multimap} not contains the given value.
     */
    public <K, V> void assertContainsValue(AssertionInfo info, Multimap<K, V> actual, V value) {
        assertNotNull(info, actual);
        if (!actual.containsValue(value)) throw failures.failure(info, shouldContainValue(actual, value));
    }

    /**
     * Verifies that the actual {@code Multimap} contains the given values.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param values the given values.
     * @throws AssertionError       if the actual {@code Multimap} is {@code null}.
     * @throws AssertionError       if the actual {@code Multimap} not contains the given values.
     * @throws NullPointerException if values vararg is {@code null}.
     */
    public <K, V> void assertContainsValues(AssertionInfo info, Multimap<K, V> actual,
                                            @SuppressWarnings("unchecked") V... values) {
        assertNotNull(info, actual);
        checkNotNull(values, "The array of values to look for should not be null");
        if (actual.isEmpty() && values.length == 0) return;

        Set<V> expected = HashSet.of(values);
        Set<V> notFound = expected.filter(valueNotPresentIn(actual.values()));
        if (isNotEmpty(notFound)) throw failures.failure(info, shouldContainValues(actual, notFound.toJavaSet()));
    }

    /**
     * Verifies that the actual {@code Multimap} does not contain the given value.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param value  the given value.
     * @throws AssertionError if the actual {@code Multimap} is {@code null}.
     * @throws AssertionError if the actual {@code Multimap} contains the given value.
     */
    public <K, V> void assertDoesNotContainValue(AssertionInfo info, Multimap<K, V> actual, V value) {
        assertNotNull(info, actual);
        if (actual.containsValue(value)) throw failures.failure(info, shouldNotContainValue(actual, value));
    }

    /**
     * Verifies that the actual {@code Multimap} does not contain the given values.
     *
     * @param <K>    key type
     * @param <V>    value type
     * @param info   contains information about the assertion.
     * @param actual the given {@code Multimap}.
     * @param values the given values.
     * @throws AssertionError       if the actual {@code Multimap} is {@code null}.
     * @throws AssertionError       if the actual {@code Multimap} contains the given values.
     * @throws NullPointerException if values vararg is {@code null}.
     */
    public <K, V> void assertDoesNotContainValues(AssertionInfo info, Multimap<K, V> actual,
                                                  @SuppressWarnings("unchecked") V... values) {
        assertNotNull(info, actual);
        checkNotNull(values, "The array of values to look for should not be null");
        if (actual.isEmpty() && values.length == 0) return;

        Set<V> expected = HashSet.of(values);
        Set<V> found = expected.filter(valuePresentIn(actual.values()));
        if (isNotEmpty(found)) throw failures.failure(info, shouldNotContainValues(actual, found.toJavaSet()));
    }


    /**
     * Asserts that the number of entries in the given {@code Multimap} has the same size as the other array.
     *
     * @param info  contains information about the assertion.
     * @param multimap   the given {@code Multimap}.
     * @param other the group to compare.
     * @throws AssertionError if the given {@code Multimap} is {@code null}.
     * @throws AssertionError if the given array is {@code null}.
     * @throws AssertionError if the number of entries in the given {@code Multimap} does not have the same size.
     */
    public void assertHasSameSizeAs(AssertionInfo info, Multimap<?, ?> multimap, Object other) {
        assertNotNull(info, multimap);
        assertIsArray(info, other);
        hasSameSizeAsCheck(info, multimap, other, multimap.size());
    }

    private <K, V> void doCommonContainsCheck(AssertionInfo info, Multimap<K, V> actual,
                                              Tuple2<? extends K, ? extends V>[] entries) {
        assertNotNull(info, actual);
        failIfNull(entries);
    }

    private <K, V> void doCommonContainsCheck(AssertionInfo info, Multimap<K, V> actual, K[] keys) {
        assertNotNull(info, actual);
        failIfNull(keys);
    }

    private <K, V> boolean doCommonEmptinessChecks(Multimap<K, V> actual, K[] keys) {
        if (actual.isEmpty() && keys.length == 0) {
            return true;
        }
        failIfEmpty(keys);
        return false;
    }

    private static <K, V> Multimap<K, V> asLinkedMultimap(Tuple2<? extends K, ? extends V>[] entries) {
        if (entries.length != nonNullEntries(Array.of(entries)).length()) {
            throw new NullPointerException("One of expected entries is null");
        } else {
            return LinkedHashMultimap.withSeq().ofEntries(entries);
        }
    }

    private static <K, V> Traversable<Tuple2<? extends K, ? extends V>> nonNullEntries(Traversable<Tuple2<? extends K, ? extends V>> entries) {
        return entries.filter(java.util.Objects::nonNull);
    }

    private <K, V> boolean containsEntry(Multimap<K, V> actual, Tuple2<? extends K, ? extends V> entry) {
        checkNotNull(entry, "Entry to look for should not be null");
        return actual.containsKey(entry._1) && actual.get(entry._1).get().contains(entry._2);
    }

    private static <K> boolean areNotEqual(K actualKey, K expectedKey) {
        return !areEqual(actualKey, expectedKey);
    }

    private static <K, V> void failIfNullOrEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        failIfNull(entries);
        failIfEmpty(entries);
    }

    private static <K, V> void failIfNull(Iterable<Tuple2<K, V>> entries) {
        checkNotNull(entries, "The entries should not be null");
    }

    private static <K> void failIfNull(K[] keys) {
        checkNotNull(keys, "The array of keys to look for should not be null");
    }

    private static <K, V> void failIfNull(Tuple2<? extends K, ? extends V>[] entries) {
        checkNotNull(entries, "The array of entries should not be null");
    }

    private static <K, V> void failIfEmpty(Iterable<Tuple2<K, V>> entries) {
        checkArgument(entries.iterator().hasNext(), "The entries should not be empty");
    }

    private static <K, V> void failIfEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        checkArgument(entries.length > 0, "The array of entries to look for should not be empty");
    }

    private static <K> void failIfEmpty(K[] keys) {
        checkArgument(keys.length > 0, "The array of keys to look for should not be empty");
    }

    private static void assertNotNull(AssertionInfo info, Multimap<?, ?> actual) {
        Objects.instance().assertNotNull(info, actual);
    }

    private static <K, V> Predicate<Tuple2<K, V>> entryNotPresentIn(Multimap<K, V> map) {
        return tuple -> !map.contains(tuple);
    }

    private static <K> Predicate<K> keyNotPresentIn(Set<K> elements) {
        return not(keyPresentIn(elements));
    }

    private static <K> Predicate<K> keyPresentIn(Set<K> elements) {
        return elements::contains;
    }

    private static <V> Predicate<V> valueNotPresentIn(Traversable<V> elements) {
        return not(valuePresentIn(elements));
    }

    private static <V> Predicate<V> valuePresentIn(Traversable<V> elements) {
        return elements::contains;
    }

    private static boolean isNotEmpty(Traversable traversable) {
        return !traversable.isEmpty();
    }

}
