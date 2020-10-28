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
 * Copyright 2017-2020 the original author or authors.
 */
package org.assertj.vavr.api;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Objects;
import org.assertj.vavr.internal.error.VavrRepresentation;

class AbstractValueAssert<SELF extends AbstractValueAssert<SELF, ACTUAL>, ACTUAL>
        extends AbstractObjectAssert<SELF, ACTUAL>
        implements AbstractVavrAssert<SELF, ACTUAL> {

    private Objects objects = Objects.instance();

    AbstractValueAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
        info().useRepresentation(new VavrRepresentation());
    }

    public ACTUAL actual() {
        return actual;
    }

    @Override
    public SELF withAssertionState(@SuppressWarnings("rawtypes") AbstractVavrAssert assertInstance) {
        this.objects = assertInstance.objects();
        propagateAssertionInfoFrom(assertInstance);
        return myself;
    }

    @Override
    public Objects objects() {
        return objects;
    }

    @Override
    public WritableAssertionInfo info() {
        return info;
    }

    private void propagateAssertionInfoFrom(AbstractVavrAssert assertInstance) {
        this.info.useRepresentation(assertInstance.info().representation());
        this.info.description(assertInstance.info().description());
        this.info.overridingErrorMessage(assertInstance.info().overridingErrorMessage());
    }
}
