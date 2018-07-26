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

import org.assertj.core.api.AbstractAssert;

import io.vavr.collection.List;

import static org.assertj.core.error.ShouldBeEmpty.shouldBeEmpty;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;

/**
 * Assertions for {@link io.vavr.collection.List}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <ELEMENT> type of elements contained in the {@link io.vavr.collection.List}.
 * @author Michał Chmielarz
 */
class AbstractListAssert<SELF extends AbstractListAssert<SELF, ELEMENT>, ELEMENT> extends AbstractAssert<SELF, List<ELEMENT>> {

    AbstractListAssert(List<ELEMENT> elements, Class<?> selfType) {
        super(elements, selfType);
    }

    /**
     * Verifies that the actual {@link io.vavr.collection.List} is empty.
     *
     * @return this assertion object.
     */
    public SELF isEmpty() {
        isNotNull();
        if (!actual.isEmpty()) throwAssertionError(shouldBeEmpty(actual));
        return myself;
    }

    /**
     * Verifies that the actual {@link io.vavr.collection.List} has {@code expectedSize}.
     *
     * @return this assertion object.
     */
    public SELF hasSize(int expectedSize) {
        isNotNull();
        if (actual.size() != expectedSize) throwAssertionError(shouldHaveSize(actual, actual.size(), expectedSize));
        return myself;
    }

    // contains

}
