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
 * Copyright 2017-2021 the original author or authors.
 */
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
