package org.assertj.vavr.api;

/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2019 the original author or authors.
 */

import io.vavr.control.Validation;
import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for {@link Validation}.
 *
 * @param <SELF>    the "self" type of this assertion class.
 * @param <INVALID> type of the value in the case of the invalid {@link Validation}.
 * @param <VALID>   type of the value in the case of the valid {@link Validation}.
 * @author Michał Chmielarz
 */
abstract class AbstractValidationAssert<SELF extends AbstractValidationAssert<SELF, INVALID, VALID>, INVALID, VALID> extends
        AbstractAssert<SELF, Validation<INVALID, VALID>> {

    AbstractValidationAssert(Validation<INVALID, VALID> actual, Class<?> selfType) {
        super(actual, selfType);
    }

}
