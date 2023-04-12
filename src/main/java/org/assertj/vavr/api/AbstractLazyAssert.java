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
 * Copyright 2017-2023 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.Lazy;

import static org.assertj.vavr.api.LazyShouldBeEvaluated.shouldBeEvaluated;
import static org.assertj.vavr.api.LazyShouldBeNotEvaluated.shouldBeNotEvaluated;

/**
 * Assertions for {@link Lazy}.
 *
 * @param <SELF>  the "self" type of this assertion class.
 * @param <VALUE> type of the value contained in the {@link Lazy}.
 */
abstract class AbstractLazyAssert<SELF extends AbstractLazyAssert<SELF, VALUE>, VALUE> extends
        AbstractValueAssert<SELF, Lazy<VALUE>> {

    AbstractLazyAssert(Lazy<VALUE> actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * Verifies that the actual {@link Lazy} is evaluated.
     *
     * @return this assertion object.
     */
    public SELF isEvaluated() {
        isNotNull();
        if (!actual.isEvaluated()) throwAssertionError(shouldBeEvaluated());
        return myself;
    }

    /**
     * Verifies that the actual {@link Lazy} is not evaluated.
     *
     * @return this assertion object.
     */
    public SELF isNotEvaluated() {
        isNotNull();
        if (actual.isEvaluated()) throwAssertionError(shouldBeNotEvaluated(actual));
        return myself;
    }
}

