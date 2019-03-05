package org.assertj.vavr.internal;

import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.HashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.ShouldContainAnyOf;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

import static org.assertj.core.error.ShouldContain.shouldContain;
import static org.assertj.core.internal.CommonValidations.failIfEmptySinceActualIsNotEmpty;
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
        failIfNull(entries);
        assertNotNull(info, actual);
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
        failIfNull(entries);
        assertNotNull(info, actual);
        // if both actual and values are empty, then assertion passes.
        if (actual.isEmpty() && entries.length == 0) return;
        failIfEmptySinceActualIsNotEmpty(entries);
        final Set<Tuple2<? extends K, ? extends V>> notFound = Array
                .of(entries)
                .foldLeft(HashSet.empty(), (set, tuple) -> {
                    if (actual.contains(tuple)) {
                        return set;
                    } else {
                        return set.add(tuple);
                    }
                });
        if (!notFound.isEmpty()) {
            throw failures.failure(info, shouldContain(actual, entries, notFound));
        }
    }

    private <K, V> boolean containsEntry(Map<K, V> actual, Tuple2<? extends K, ? extends V> entry) {
        checkNotNull(entry, "Entries to look for should not be null");
        return actual.containsKey(entry._1) && areEqual(actual.get(entry._1).get(), entry._2);
    }

    private static <K, V> void failIfEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        checkArgument(entries.length > 0, "The array of entries to look for should not be empty");
    }

    private static <K, V> void failIfNullOrEmpty(Tuple2<? extends K, ? extends V>[] entries) {
        failIfNull(entries);
        failIfEmpty(entries);
    }

    private static <K, V> void failIfNull(Tuple2<? extends K, ? extends V>[] entries) {
        checkNotNull(entries, "The array of entries to look for should not be null");
    }

    private void assertNotNull(AssertionInfo info, Map<?, ?> actual) {
        Objects.instance().assertNotNull(info, actual);
    }

}
