package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Validation} should be invalid.
 *
 * @author Michał Chmielarz
 */
class ValidationShouldBeInvalid extends BasicErrorMessageFactory {

    private ValidationShouldBeInvalid(Validation actual) {
        super("%nExpecting a Validation to be invalid but was valid <%s>.", actual.get());
    }

    static ValidationShouldBeInvalid shouldBeInvalid(Validation actual) {
        return new ValidationShouldBeInvalid(actual);
    }
}
