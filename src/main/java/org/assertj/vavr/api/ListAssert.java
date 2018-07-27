package org.assertj.vavr.api;

import io.vavr.collection.List;

public class ListAssert<ELEMENT> extends AbstractListAssert<ListAssert<ELEMENT>, ELEMENT> {

    ListAssert(List<ELEMENT> actual) {
        super(actual, ListAssert.class);
    }

}
