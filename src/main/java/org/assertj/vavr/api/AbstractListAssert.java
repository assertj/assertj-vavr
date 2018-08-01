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
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.StandardComparisonStrategy;

import static java.lang.String.format;
import static org.assertj.core.error.ShouldContainAtIndex.shouldContainAtIndex;
import static org.assertj.core.error.ShouldNotBeEmpty.shouldNotBeEmpty;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.util.Preconditions.checkNotNull;
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

    private ComparisonStrategy comparisonStrategy;

    AbstractListAssert(ACTUAL elements, Class<?> selfType) {
        super(elements, selfType);
        this.comparisonStrategy = StandardComparisonStrategy.instance();
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
        if (!comparisonStrategy.areEqual(actualElement, value)) {
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
        if (comparisonStrategy.areEqual(actualElement, value)) {
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
