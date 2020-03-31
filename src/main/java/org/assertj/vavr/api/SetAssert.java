package org.assertj.vavr.api;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.SortedSet;
import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ObjectAssertFactory;

import java.util.Comparator;

public class SetAssert<ELEMENT>
        extends AbstractSetAssert<SetAssert<ELEMENT>, Set<ELEMENT>, ELEMENT, ObjectAssert<ELEMENT>> {

    private final AssertFactory<ELEMENT, ObjectAssert<ELEMENT>> assertFactory;

    SetAssert(Set<ELEMENT> set) {
        super(set, SetAssert.class);
        this.assertFactory = new ObjectAssertFactory<>();
        if(set instanceof SortedSet) {
            Comparator<ELEMENT> comparator = ((SortedSet<ELEMENT>) set).comparator();
            if (comparator != null) {
                usingElementComparator(comparator);
            }
        }
    }

    @Override
    protected ObjectAssert<ELEMENT> toAssert(ELEMENT value, String description) {
        return assertFactory.createAssert(value).as(description);
    }

    @Override
    protected SetAssert<ELEMENT> newAbstractIterableAssert(Iterable<? extends ELEMENT> iterable) {
        return new SetAssert<>(HashSet.ofAll(iterable));
    }
}
