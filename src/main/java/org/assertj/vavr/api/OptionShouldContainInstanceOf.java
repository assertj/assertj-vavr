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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build an error message when a value should be instance of a specific class.
 *
 * @author Grzegorz Piwowarek
 */
class OptionShouldContainInstanceOf extends BasicErrorMessageFactory {

    private OptionShouldContainInstanceOf(String message) {
        super(message);
    }

    /**
     * Indicates that a value should be present in an empty {@link io.vavr.control.Option}.
     *
     * @param value Option to be checked.
     * @return an error message factory.
     * @throws java.lang.NullPointerException if option is null.
     */
    static OptionShouldContainInstanceOf shouldContainInstanceOf(Object value, Class<?> clazz) {
        Option<?> option = (Option<?>) value;
        if (option.isDefined()) {
            return new OptionShouldContainInstanceOf(String
              .format("%nExpecting:%n <%s>%nto contain a value that is an instance of:%n <%s>%nbut did contain an instance of:%n <%s>",
                option.getClass().getSimpleName(), clazz.getName(),
                option.get().getClass().getName()));
        }
        return new OptionShouldContainInstanceOf(String
          .format("%nExpecting:%n <%s>%nto contain a value that is an instance of:%n <%s>%nbut was empty",
            option.getClass().getSimpleName(), clazz.getName()));
    }
}

