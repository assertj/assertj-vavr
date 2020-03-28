package org.assertj.vavr.api;

import io.vavr.collection.Set;
import org.assertj.core.api.ObjectAssert;

public class SetAssert<ELEMENT>
        extends AbstractSetAssert<SetAssert<ELEMENT>, Set<ELEMENT>, ELEMENT, ObjectAssert<ELEMENT>> {

    SetAssert(Set<ELEMENT> set) {
        super(set, SetAssert.class);
    }
}
