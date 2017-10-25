package org.assertj.vavr.api;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2017 the original author or authors.
 */

import io.vavr.control.Option;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Option}
 * should contain a specific value.
 *
 * @author Grzegorz Piwowarek
 */
class OptionShouldContain extends BasicErrorMessageFactory {

    private static final String EXPECTING_TO_CONTAIN = "%nExpecting:%n  <%s>%nto contain:%n  <%s>%nbut did not.";
    private static final String EXPECTING_TO_CONTAIN_SAME = "%nExpecting:%n  <%s>%nto contain the instance (i.e. compared with ==):%n  <%s>%nbut did not.";

    private OptionShouldContain(String message, Object actual, Object expected) {
        super(message, actual, expected);
    }

    private OptionShouldContain(Object expected) {
        super("%nExpecting Optional to contain:%n  <%s>%nbut was empty.", expected);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Option} does not contain the provided argument.
     *
     * @param option      the {@link io.vavr.control.Option} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link io.vavr.control.Option}.
     * @param <VALUE>       the type of the value contained in the {@link io.vavr.control.Option}.
     * @return a error message factory
     */
    static <VALUE> OptionShouldContain shouldContain(Option<VALUE> option, VALUE expectedValue) {
        return option.isDefined() ?
          new OptionShouldContain(EXPECTING_TO_CONTAIN, option, expectedValue) :
          shouldContain(expectedValue);
    }

    /**
     * Indicates that the provided {@link io.vavr.control.Option} does not contain the provided argument (judging by reference
     * equality).
     *
     * @param option      the {@link io.vavr.control.Option} which contains a value.
     * @param expectedValue the value we expect to be in the provided {@link io.vavr.control.Option}.
     * @param <VALUE>       the type of the value contained in the {@link io.vavr.control.Option}.
     * @return a error message factory
     */
    static <VALUE> OptionShouldContain shouldContainSame(Option<VALUE> option, VALUE expectedValue) {
        return option.isDefined() ?
          new OptionShouldContain(EXPECTING_TO_CONTAIN_SAME, option, expectedValue) :
          shouldContain(expectedValue);
    }

    /**
     * Indicates that an {@link io.vavr.control.Option} is empty so it doesn't contain the expected value.
     *
     * @param expectedValue the value we expect to be in an {@link io.vavr.control.Option}.
     * @return a error message factory.
     */
    static OptionShouldContain shouldContain(Object expectedValue) {
        return new OptionShouldContain(expectedValue);
    }
}

