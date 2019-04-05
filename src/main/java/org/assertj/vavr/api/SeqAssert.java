package org.assertj.vavr.api;

import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ObjectAssertFactory;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

public class SeqAssert<ELEMENT>
		extends AbstractSeqAssert<SeqAssert<ELEMENT>, Seq<ELEMENT>, ELEMENT, ObjectAssert<ELEMENT>> {

	private final AssertFactory<ELEMENT, ObjectAssert<ELEMENT>> assertFactory;

	SeqAssert(Seq<ELEMENT> actual) {
		super(actual, SeqAssert.class);
		this.assertFactory = new ObjectAssertFactory<>();
	}

	@Override
	protected ObjectAssert<ELEMENT> toAssert(ELEMENT value, String description) {
		return assertFactory.createAssert(value).as(description);
	}

	@Override
	protected SeqAssert<ELEMENT> newAbstractIterableAssert(Iterable<? extends ELEMENT> iterable) {
		return new SeqAssert<>(List.ofAll(iterable));
	}
}
