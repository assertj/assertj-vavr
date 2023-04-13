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
 * Copyright 2017-2023 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when a {@link Validation} should contain a value being an instance of a specific class.
 *
 * @author Michał Chmielarz
 */
class ValidationShouldContainInstanceOf extends BasicErrorMessageFactory {

    private static final String EXPECTING_TO_CONTAIN_BUT_IS_INVALID = "%nExpecting:%n  <%s>%nto contain a valid:%n  <%s>%nbut was invalid.";
    private static final String EXPECTING_TO_CONTAIN_BUT_IS_VALID = "%nExpecting:%n  <%s>%nto contain an invalid:%n  <%s>%nbut was valid.";
    private static final String EXPECTING_TO_CONTAIN_DIFFERENT_INSTANCE = "%nExpecting:%n <%s>%nto contain a value that is an instance of:%n <%s>%nbut did contain an instance of:%n <%s>";

    private ValidationShouldContainInstanceOf(String message) {
        super(message);
    }

    /**
     * Indicates that a value should be present in a valid {@link Validation}.
     *
     * @param value         Validation to be checked.
     * @param expectedClazz expected class of a value
     * @return an error message factory.
     * @throws NullPointerException if validation is null.
     */
    static ValidationShouldContainInstanceOf shouldContainValidInstanceOf(Object value, Class<?> expectedClazz) {
        Validation<?, ?> validation = (Validation<?, ?>) value;
        if (validation.isValid()) {
            return new ValidationShouldContainInstanceOf(String
              .format(
                EXPECTING_TO_CONTAIN_DIFFERENT_INSTANCE,
                validation.getClass().getSimpleName(),
                expectedClazz.getName(),
                validation.get().getClass().getName()));
        }
        return new ValidationShouldContainInstanceOf(String
          .format(
            EXPECTING_TO_CONTAIN_BUT_IS_INVALID,
            validation.getClass().getSimpleName(),
            expectedClazz.getName()));
    }

    /**
     * Indicates that a value should be present in an invalid {@link Validation}.
     *
     * @param value         Validation to be checked.
     * @param expectedClazz expected class of a left value
     * @return an error message factory.
     * @throws NullPointerException if validation is null.
     */
    static ValidationShouldContainInstanceOf shouldContainInvalidInstanceOf(Object value,
                                                                            Class<?> expectedClazz) {
        Validation<?, ?> validation = (Validation<?, ?>) value;
        if (validation.isInvalid()) {
            return new ValidationShouldContainInstanceOf(String
              .format(
                EXPECTING_TO_CONTAIN_DIFFERENT_INSTANCE,
                validation.getClass().getSimpleName(),
                expectedClazz.getName(),
                validation.getError().getClass().getName()));
        }
        return new ValidationShouldContainInstanceOf(String
          .format(
            EXPECTING_TO_CONTAIN_BUT_IS_VALID,
            validation.getClass().getSimpleName(),
            expectedClazz.getName()));
    }
}

