package org.assertj.vavr.api;

import io.vavr.collection.Traversable;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Objects;

abstract class AbstractTraversableAssert<SELF extends AbstractTraversableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>,
        ACTUAL extends Traversable<? extends ELEMENT>,
        ELEMENT,
        ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
        extends AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>
        implements AbstractVavrAssert<SELF, ACTUAL> {

    private Objects objects = Objects.instance();

    AbstractTraversableAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public ACTUAL actual() {
        return actual;
    }

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

    private void propagateAssertionInfoFrom(AbstractVavrAssert<?, ?> assertInstance) {
        this.info.useRepresentation(assertInstance.info().representation());
        this.info.description(assertInstance.info().description());
        this.info.overridingErrorMessage(assertInstance.info().overridingErrorMessage());
    }

}
