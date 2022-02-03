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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Either} should be left.
 *
 * @author Michał Chmielarz
 */
class EitherShouldBeLeft extends BasicErrorMessageFactory {

    private EitherShouldBeLeft(Either actual) {
        super("%nExpecting an Either to be left but was <%s>.", actual);
    }

    static EitherShouldBeLeft shouldBeLeft(Either actual) {
        return new EitherShouldBeLeft(actual);
    }
}
