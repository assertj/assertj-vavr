package org.assertj.vavr.api;

import io.vavr.collection.Map;
import io.vavr.collection.Set;

public class SetAssert<VALUE>  extends AbstractSetAssert<SetAssert<VALUE>, Set<VALUE>, VALUE> {

    SetAssert(Set<VALUE> set) {
        super(set, SetAssert.class);
    }

}
