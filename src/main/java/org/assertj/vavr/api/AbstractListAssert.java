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
  Copyright 2012-2018 the original author or authors.
 */

import io.vavr.collection.List;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.IndexedObjectEnumerableAssert;
import org.assertj.core.data.Index;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;

import static java.lang.String.format;
import static org.assertj.core.error.ShouldBeSorted.shouldHaveComparableElementsAccordingToGivenComparator;
import static org.assertj.core.error.ShouldContainAtIndex.shouldContainAtIndex;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.vavr.api.ListShouldBeSorted.*;
import static org.assertj.vavr.api.SeqShouldBeAtIndex.shouldBeAtIndex;
import static org.assertj.vavr.api.SeqShouldHaveAtIndex.shouldHaveAtIndex;

/**
 * Assertions for {@link io.vavr.collection.List}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <ELEMENT> type of elements contained in the {@link io.vavr.collection.List}.
 * @author Michał Chmielarz
 */
abstract class AbstractListAssert<SELF extends AbstractListAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>,
        ACTUAL extends List<?extends ELEMENT>,
        ELEMENT,
        ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
        extends AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>
        implements IndexedObjectEnumerableAssert<SELF, ELEMENT> {

    private ComparisonStrategy listElementComparisonStrategy;

    AbstractListAssert(ACTUAL elements, Class<?> selfType) {
        super(elements, selfType);
        this.listElementComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link List} element's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given comparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingElementComparator(Comparator<? super ELEMENT> customComparator) {
        listElementComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.collection.List} contains the given object at the given index.
     * <p>
     * Example:
     * <pre><code class='java'> List&lt;Ring&gt; elvesRings = List.of(vilya, nenya, narya);
     *
     * // assertions will pass
     * assertThat(elvesRings).contains(vilya, atIndex(0));
     * assertThat(elvesRings).contains(nenya, atIndex(1));
     * assertThat(elvesRings).contains(narya, atIndex(2));
     *
     * // assertions will fail
     * assertThat(elvesRings).contains(vilya, atIndex(1));
     * assertThat(elvesRings).contains(nenya, atIndex(2));
     * assertThat(elvesRings).contains(narya, atIndex(0));</code></pre>
     *
     * @param value the object to look for.
     * @param index the index where the object should be stored in the actual group.
     * @return this assertion object.
     * @throws AssertionError if the actual group is {@code null} or empty.
     * @throws NullPointerException if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size of the actual
     *           group.
     * @throws AssertionError if the actual group does not contain the given object at the given index.
     */
    public SELF contains(ELEMENT value, Index index) {
        isNotNull();

        assertNotEmpty();
        assertIndexIsValid(index);

        Object actualElement = actual.get(index.value);
        if (!listElementComparisonStrategy.areEqual(actualElement, value)) {
            throwAssertionError(shouldContainAtIndex(actual, value, index, actual.get(index.value)));
        }

        return myself;
    }

    /**
     * Verifies that the actual group does not contain the given object at the given index.
     * <p>
     * Example:
     * <pre><code class='java'> List&lt;Ring&gt; elvesRings = List.of(vilya, nenya, narya);
     *
     * // assertions will pass
     * assertThat(elvesRings).contains(vilya, atIndex(1));
     * assertThat(elvesRings).contains(nenya, atIndex(2));
     * assertThat(elvesRings).contains(narya, atIndex(0));
     *
     * // assertions will fail
     * assertThat(elvesRings).contains(vilya, atIndex(0));
     * assertThat(elvesRings).contains(nenya, atIndex(1));
     * assertThat(elvesRings).contains(narya, atIndex(2));</code></pre>
     *
     * @param value the object to look for.
     * @param index the index where the object should not be stored in the actual group.
     * @return this assertion object.
     * @throws AssertionError if the actual group is {@code null}.
     * @throws NullPointerException if the given {@code Index} is {@code null}.
     * @throws AssertionError if the actual group contains the given object at the given index.
     */
    public SELF doesNotContain(ELEMENT value, Index index) {
        isNotNull();

        assertNotEmpty();
        assertIndexIsValid(index);

        Object actualElement = actual.get(index.value);
        if (listElementComparisonStrategy.areEqual(actualElement, value)) {
            throwAssertionError(shouldNotContainAtIndex(actual, value, index));
        }

        return myself;
    }

    /**
     * Verifies that the actual object at the given index in the actual group satisfies the given condition.
     *
     * @param condition the given condition.
     * @param index     the index where the object should be stored in the actual group.
     * @return this assertion object.
     * @throws AssertionError if the given {@link io.vavr.collection.List} is {@code null} or empty.
     * @throws NullPointerException if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size of
     *           the given {@link io.vavr.collection.List}.
     * @throws NullPointerException if the given {@code Condition} is {@code null}.
     * @throws AssertionError if the value in the given {@link io.vavr.collection.List} at the given index does not satisfy the given
     *           {@code Condition} .
     */
    public SELF has(Condition<? super ELEMENT> condition, Index index) {
        assertConditionIsMetAtIndex(
                condition,
                index,
                () -> throwAssertionError(shouldHaveAtIndex(actual, condition, index, actual.get(index.value)))
        );

        return myself;
    }

    /**
     * Verifies that the actual object at the given index in the actual group satisfies the given condition (alias of {@link #has(Condition, Index)}).
     *
     * @param condition the given condition.
     * @param index     the index where the object should be stored in the actual group.
     * @return this assertion object.
     * @throws AssertionError            if the given {@link io.vavr.collection.List} is {@code null} or empty.
     * @throws NullPointerException      if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size of
     *                                   the given {@link io.vavr.collection.List}.
     * @throws NullPointerException      if the given {@code Condition} is {@code null}.
     * @throws AssertionError            if the value in the given {@link io.vavr.collection.List} at the given index does not satisfy the given
     *                                   {@code Condition} .
     */
    public SELF is(Condition<? super ELEMENT> condition, Index index) {
        assertConditionIsMetAtIndex(
                condition,
                index,
                () -> throwAssertionError(shouldBeAtIndex(actual, condition, index, actual.get(index.value)))
        );

        return myself;
    }

    /**
     * Verifies that the actual list is sorted in ascending order according to the natural ordering of its elements.
     * <p>
     * All list elements must implement the {@link Comparable} interface and must be mutually comparable (that is,
     * e1.compareTo(e2) must not throw a ClassCastException for any elements e1 and e2 in the list), examples :
     * <ul>
     * <li>a list composed of {"a1", "a2", "a3"} is ok because the element type (String) is Comparable</li>
     * <li>a list composed of Rectangle {r1, r2, r3} is <b>NOT ok</b> because Rectangle is not Comparable</li>
     * <li>a list composed of {True, "abc", False} is <b>NOT ok</b> because elements are not mutually comparable</li>
     * </ul>
     * Empty lists are considered sorted.<br> Unique element lists are considered sorted unless the element type is not Comparable.
     *
     * @return {@code this} assertion object.
     * @throws AssertionError if the actual list is not sorted in ascending order according to the natural ordering of its
     *                        elements.
     * @throws AssertionError if the actual list is <code>null</code>.
     * @throws AssertionError if the actual list element type does not implement {@link Comparable}.
     * @throws AssertionError if the actual list elements are not mutually {@link Comparable}.
     */
    public SELF isSorted() {
        isNotNull();
        if (listElementComparisonStrategy instanceof ComparatorBasedComparisonStrategy) {
            // instead of comparing elements with their natural comparator, use the one set by client.
            Comparator<?> comparator = ((ComparatorBasedComparisonStrategy) listElementComparisonStrategy).getComparator();
            assertIsSortedAccordingToComparator(comparator);
            return myself;
        }

        try {
            // sorted assertion is only relevant if elements are Comparable, we assume they are
            List<Comparable<Object>> comparableList = actual.map(e -> (Comparable<Object>) e);
            // array with 0 or 1 element are considered sorted.
            if (comparableList.size() <= 1) return myself;
            for (int i = 0; i < comparableList.size() - 1; i++) {
                // array is sorted in ascending order iif element i is less or equal than element i+1
                if (comparableList.get(i).compareTo(comparableList.get(i + 1)) > 0)
                    throwAssertionError(shouldBeSorted(i, actual));
            }
        } catch (ClassCastException e) {
            // elements are either not Comparable or not mutually Comparable (e.g. List<Object> containing String and Integer)
            throwAssertionError(shouldHaveMutuallyComparableElements(actual));
        }

        return myself;
    }

    private void assertIsSortedAccordingToComparator(Comparator<?> comparator) {
        checkNotNull(comparator, "The given comparator should not be null");
        try {
            // Empty collections are considered sorted even if comparator can't be applied to their element type
            // We can't verify that point because of erasure type at runtime.
            if (actual.size() == 0) return;
            Comparator rawComparator = comparator;
            if (actual.size() == 1) {
                // Compare unique element with itself to verify that it is compatible with comparator (a ClassCastException is
                // thrown if not). We have to use a raw comparator to compare the unique element of actual ... :(
                rawComparator.compare(actual.get(0), actual.get(0));
                return;
            }
            for (int i = 0; i < actual.size() - 1; i++) {
                // List is sorted in comparator defined order if current element is less or equal than next element
                if (rawComparator.compare(actual.get(i), actual.get(i + 1)) > 0)
                    throwAssertionError(shouldBeSortedAccordingToGivenComparator(i, actual, comparator));
            }
        } catch (ClassCastException e) {
            throwAssertionError(
                    shouldHaveComparableElementsAccordingToGivenComparator(actual, comparator));
        }
        return;
    }

    private void assertConditionIsMetAtIndex(Condition<? super ELEMENT> condition, Index index, Runnable errorProvider) {
        isNotNull();
        checkNotNull(condition, "The condition to evaluate should not be null");

        assertNotEmpty();
        assertIndexIsValid(index);

        if (!condition.matches(actual.get(index.value))) {
            errorProvider.run();
        }
    }

    private void assertIndexIsValid(Index index) {
        checkNotNull(index, "Index should not be null");
        final int maximum = actual.size() - 1;
        if (index.value > maximum) {
            String errorMessage = "Index should be between <0> and <%d> (inclusive) but was:%n <%d>";
            throw new IndexOutOfBoundsException(format(errorMessage, maximum, index.value));
        }
    }

    private void assertNotEmpty() {
        if (actual.isEmpty()) {
            throwAssertionError(shouldNotBeEmpty());
        }
    }

}
