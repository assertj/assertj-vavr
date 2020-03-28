package org.assertj.vavr.api;

import io.vavr.collection.Set;
import org.assertj.core.api.EnumerableAssert;

import java.util.Comparator;

public class AbstractSetAssert<SELF extends AbstractSetAssert<SELF, ACTUAL, VALUE>, ACTUAL extends Set<VALUE>, VALUE>
        extends AbstractValueAssert<SELF, ACTUAL> implements EnumerableAssert<SELF, VALUE> {

    AbstractSetAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    @Override
    public void isNullOrEmpty() {

    }

    @Override
    public void isEmpty() {

    }

    @Override
    public SELF isNotEmpty() {
        return null;
    }

    @Override
    public SELF hasSize(int i) {
        return null;
    }

    @Override
    public SELF hasSameSizeAs(Iterable<?> iterable) {
        return null;
    }

    @Override
    public SELF hasSameSizeAs(Object o) {
        return null;
    }

    @Override
    public SELF usingElementComparator(Comparator<? super VALUE> comparator) {
        return null;
    }

    @Override
    public SELF usingDefaultElementComparator() {
        return null;
    }
}
