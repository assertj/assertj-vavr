package org.assertj.vavr.api;

import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ObjectAssertFactory;

import io.vavr.collection.List;

public class ListAssert<ELEMENT>
		extends
			AbstractListAssert<ListAssert<ELEMENT>, List<ELEMENT>, ELEMENT, ObjectAssert<ELEMENT>> {

	private final AssertFactory<ELEMENT, ObjectAssert<ELEMENT>> assertFactory;

	ListAssert(List<ELEMENT> actual) {
		super(actual, ListAssert.class);
		this.assertFactory = new ObjectAssertFactory<>();
	}

	@Override
	protected ObjectAssert<ELEMENT> toAssert(ELEMENT value, String description) {
		return assertFactory.createAssert(value).as(description);
	}

	@Override
	protected ListAssert<ELEMENT> newAbstractIterableAssert(Iterable<? extends ELEMENT> iterable) {
		return new ListAssert<>(List.ofAll(iterable));
	}
}
