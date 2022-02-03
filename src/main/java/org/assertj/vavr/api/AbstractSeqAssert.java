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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.Seq;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.IndexedObjectEnumerableAssert;
import org.assertj.core.data.Index;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;
import org.assertj.core.util.CheckReturnValue;

import java.util.Comparator;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.error.ShouldBeSorted.shouldHaveComparableElementsAccordingToGivenComparator;
import static org.assertj.core.error.ShouldContainAtIndex.shouldContainAtIndex;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.vavr.api.SeqShouldBeAtIndex.shouldBeAtIndex;
import static org.assertj.vavr.api.SeqShouldBeSorted.*;
import static org.assertj.vavr.api.SeqShouldHaveAtIndex.shouldHaveAtIndex;

/**
 * Assertions for {@link Seq}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <ELEMENT> type of elements contained in the {@link Seq}.
 * @author Michał Chmielarz
 */
abstract class AbstractSeqAssert<SELF extends AbstractSeqAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>,
        ACTUAL extends Seq<? extends ELEMENT>,
        ELEMENT,
        ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
        extends AbstractTraversableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>
        implements IndexedObjectEnumerableAssert<SELF, ELEMENT> {

    private ComparisonStrategy seqElementComparisonStrategy;

    AbstractSeqAssert(ACTUAL elements, Class<?> selfType) {
        super(elements, selfType);
        this.seqElementComparisonStrategy = StandardComparisonStrategy.instance();
    }

    /**
     * Use given custom comparator instead of relying on actual type A <code>equals</code> method to compare the
     * {@link Seq} element's object for incoming assertion checks.
     *
     * @param customComparator the comparator to use for incoming assertion checks.
     * @return {@code this} assertion object.
     * @throws NullPointerException if the given comparator is {@code null}.
     */
    @CheckReturnValue
    public SELF usingElementComparator(Comparator<? super ELEMENT> customComparator) {
        seqElementComparisonStrategy = new ComparatorBasedComparisonStrategy(customComparator);
        return myself;
    }

    /**
     * Verifies that the actual {@link Seq} contains the given object at the given index.
     * <p>
     * Example:
     * <pre><code class='java'> Seq&lt;Ring&gt; elvesRings = List.of(vilya, nenya, narya);
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
        if (!seqElementComparisonStrategy.areEqual(actualElement, value)) {
            throwAssertionError(shouldContainAtIndex(actual, value, index, actual.get(index.value)));
        }

        return myself;
    }

    /**
     * Verifies that the actual group does not contain the given object at the given index.
     * <p>
     * Example:
     * <pre><code class='java'> Seq&lt;Ring&gt; elvesRings = List.of(vilya, nenya, narya);
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
        if (seqElementComparisonStrategy.areEqual(actualElement, value)) {
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
     * @throws AssertionError if the given {@link Seq} is {@code null} or empty.
     * @throws NullPointerException if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size of
     *           the given {@link Seq}.
     * @throws NullPointerException if the given {@code Condition} is {@code null}.
     * @throws AssertionError if the value in the given {@link Seq} at the given index does not satisfy the given
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
     * @throws AssertionError            if the given {@link Seq} is {@code null} or empty.
     * @throws NullPointerException      if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size of
     *                                   the given {@link Seq}.
     * @throws NullPointerException      if the given {@code Condition} is {@code null}.
     * @throws AssertionError            if the value in the given {@link Seq} at the given index does not satisfy the given
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
     * Verifies that the actual sequence is sorted in ascending order according to the natural ordering of its elements.
     * <p>
     * All sequence elements must implement the {@link Comparable} interface and must be mutually comparable (that is,
     * e1.compareTo(e2) must not throw a ClassCastException for any elements e1 and e2 in the sequence), examples :
     * <ul>
     * <li>a sequence composed of {"a1", "a2", "a3"} is ok because the element type (String) is Comparable</li>
     * <li>a sequence composed of Rectangle {r1, r2, r3} is <b>NOT ok</b> because Rectangle is not Comparable</li>
     * <li>a sequence composed of {True, "abc", False} is <b>NOT ok</b> because elements are not mutually comparable</li>
     * </ul>
     * Empty sequences are considered sorted.<br> Unique element sequences are considered sorted unless the element type is not Comparable.
     *
     * @return {@code this} assertion object.
     * @throws AssertionError if the actual sequence is not sorted in ascending order according to the natural ordering of its
     *                        elements.
     * @throws AssertionError if the actual sequence is <code>null</code>.
     * @throws AssertionError if the actual sequence element type does not implement {@link Comparable}.
     * @throws AssertionError if the actual sequence elements are not mutually {@link Comparable}.
     */
    public SELF isSorted() {
        isNotNull();
        if (seqElementComparisonStrategy instanceof ComparatorBasedComparisonStrategy) {
            // instead of comparing elements with their natural comparator, use the one set by client.
            Comparator<?> comparator = ((ComparatorBasedComparisonStrategy) seqElementComparisonStrategy).getComparator();
            assertIsSortedAccordingToComparator(comparator);
            return myself;
        }

        try {
            // sorted assertion is only relevant if elements are Comparable, we assume they are
            Seq<Comparable<Object>> comparableSequence = actual.map(e -> (Comparable<Object>) e);
            // array with 0 or 1 element are considered sorted.
            if (comparableSequence.size() <= 1) return myself;
            for (int i = 0; i < comparableSequence.size() - 1; i++) {
                // array is sorted in ascending order iif element i is less or equal than element i+1
                if (comparableSequence.get(i).compareTo(comparableSequence.get(i + 1)) > 0)
                    throwAssertionError(shouldBeSorted(i, actual));
            }
        } catch (ClassCastException e) {
            // elements are either not Comparable or not mutually Comparable (e.g. Seq<Object> containing String and Integer)
            throwAssertionError(shouldHaveMutuallyComparableElements(actual));
        }

        return myself;
    }

    /**
     * Verifies that the actual sequence is sorted according to the given comparator.<br> Empty sequences are considered sorted whatever
     * the comparator is.<br> One element sequences are considered sorted if the element is compatible with comparator.
     *
     * @param comparator the {@link Comparator} used to compare sequence elements
     * @return {@code this} assertion object.
     * @throws AssertionError       if the actual sequence is not sorted according to the given comparator.
     * @throws AssertionError       if the actual sequence is <code>null</code>.
     * @throws NullPointerException if the given comparator is <code>null</code>.
     * @throws AssertionError       if the actual sequence elements are not mutually comparable according to given Comparator.
     */
    public SELF isSortedAccordingTo(Comparator<? super ELEMENT> comparator) {
        isNotNull();
        assertIsSortedAccordingToComparator(comparator);
        return myself;
    }

    /**
     * Verifies that the actual @{code Seq} contains the value at given {@code Index} that satisfy given {@code requirements}.
     * <p>
     * Example:
     * <pre><code class='java'>
     * Seq&lt;TolkienCharacter&gt; ringBearers = List.of(frodo, elrond, gandalf);
     *
     * // this assertion will pass
     * assertThat(ringBearers).satisfies(
     *     ringBearer -&gt; {
     *         assertThat(ringBearer.getAge()).isGreaterThan(200);
     *         assertThat(ringBearer.getRace()).isEqualTo(ELF);
     *     },
     *     atIndex(1));
     *
     * // this assertion will fail
     * assertThat(ringBearers).satisfies(
     *     ringBearer -&gt; {
     *         assertThat(ringBearer.getRace()).isEqualTo(ELF);
     *     },
     *     atIndex(0);
     * </code></pre>
     *
     * @param requirements the given requirements for the element at {@code Index} to meet.
     * @param index        the index where the object should be stored in the actual {@link Seq}.
     * @return {@code this} assertion object.
     * @throws AssertionError            if the value at given {@code Index} does not satisfy the {@code requirements}.
     * @throws AssertionError            if the actual sequence is {@code null}.
     * @throws NullPointerException      if the given {@code requirements} are {@code null}.
     * @throws NullPointerException      if the given {@code Index} is {@code null}.
     * @throws IndexOutOfBoundsException if the value of the given {@code Index} is equal to or greater than the size
     *                                   of the actual {@code Seq}.
     */
    public SELF satisfies(Consumer<? super ELEMENT> requirements, Index index) {
        isNotNull();
        checkNotNull(requirements, "The Consumer expressing the assertions requirements must not be null");
        assertIndexIsValid(index);
        requirements.accept(actual.get(index.value));
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
                // Seq is sorted in comparator defined order if current element is less or equal than next element
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
        requireNonNull(condition, "The condition to evaluate should not be null");

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
