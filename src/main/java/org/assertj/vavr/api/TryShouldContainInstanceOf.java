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

import io.vavr.control.Try;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build an error message when a {@link Try} does not contain a value being an instance of an expected class.
 *
 * @author Grzegorz Piwowarek
 */
class TryShouldContainInstanceOf extends BasicErrorMessageFactory {

    private TryShouldContainInstanceOf(String message) {
        super(message);
    }

    /**
     * Indicates that a value should be present in {@link io.vavr.control.Try}.
     *
     * @param value Try to be checked.
     * @return an error message factory.
     * @throws java.lang.NullPointerException if Try is null.
     */
    static TryShouldContainInstanceOf shouldContainInstanceOf(Object value, Class<?> clazz) {
        Try<?> Try = (Try<?>) value;
        if (Try.isSuccess()) {
            return new TryShouldContainInstanceOf(String
              .format("%nExpecting:%n <%s>%nto contain a value that is an instance of:%n <%s>%nbut did contain an instance of:%n <%s>",
                Try.getClass().getSimpleName(), clazz.getName(),
                Try.get().getClass().getName()));
        }
        return new TryShouldContainInstanceOf(String
          .format("%nExpecting:%n <%s>%nto contain a value that is an instance of:%n <%s>%nbut was empty",
            Try.getClass().getSimpleName(), clazz.getName()));
    }
}

