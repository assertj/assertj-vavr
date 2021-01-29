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

import io.vavr.control.Try;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Try}
 * should contain a specific value.
 *
 * @author Grzegorz Piwowarek
 */
class TryShouldContain extends BasicErrorMessageFactory {

    private static final String EXPECTING_TO_CONTAIN = "%nExpecting:%n  <%s>%nto contain:%n  <%s>%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_SAME = "%nExpecting:%n  <%s>%nto contain the instance (i.e. compared with ==):%n  <%s>%nbut did not.";

    private TryShouldContain(String message, Object actual, Object expected) {
        super(message, actual, expected);
    }

    private TryShouldContain(Object expected) {
        super("%nExpecting Try to contain:%n  <%s>%nbut was empty.", expected);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Try} does not contain the provided argument.
     *
     * @param <VALUE>       the type of the value contained in the {@link Try}.
     * @param vTry          the {@link Try} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Try}.
     * @return a error message factory
     */
    static <VALUE> TryShouldContain shouldContain(Try<VALUE> vTry, VALUE expectedValue) {
        return vTry.isSuccess() ?
          new TryShouldContain(EXPECTING_TO_CONTAIN, vTry, expectedValue) :
          shouldContain(expectedValue);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Try} does not contain the provided argument (judging by reference
     * equality).
     *
     * @param <VALUE>       the type of the value contained in the {@link Try}.
     * @param vTry          the {@link Try} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link Try}.
     * @return a error message factory
     */
    static <VALUE> TryShouldContain shouldContainSame(Try<VALUE> vTry, VALUE expectedValue) {
        return vTry.isSuccess() ?
          new TryShouldContain(EXPECTING_TO_CONTAIN_SAME, vTry, expectedValue) :
          shouldContain(expectedValue);
    }

    /**
     * Indicates that an {@link io.vavr.control.Try} is empty so it doesn't contain the expected value.
     *
     * @param expectedValue the value we expect to be in an {@link Try}.
     * @return a error message factory.
     */
    static TryShouldContain shouldContain(Object expectedValue) {
        return new TryShouldContain(expectedValue);
    }
}

