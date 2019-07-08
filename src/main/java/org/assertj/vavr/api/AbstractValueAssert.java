package org.assertj.vavr.api;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Objects;

class AbstractValueAssert<SELF extends AbstractValueAssert<SELF, ACTUAL>, ACTUAL>
        extends AbstractObjectAssert<SELF, ACTUAL>
        implements AbstractVavrAssert<SELF, ACTUAL> {

    private Objects objects = Objects.instance();

    AbstractValueAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
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
