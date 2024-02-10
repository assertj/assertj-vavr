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
 * Copyright 2017-2024 the original author or authors.
 */
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
