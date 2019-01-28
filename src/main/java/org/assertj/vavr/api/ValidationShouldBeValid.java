package org.assertj.vavr.api;

import io.vavr.control.Validation;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Validation} should be valid.
 *
 * @author Michał Chmielarz
 */
class ValidationShouldBeValid extends BasicErrorMessageFactory {

    private ValidationShouldBeValid(Validation actual) {
        super("%nExpecting a Validation to be valid but was invalid <%s>.", actual.getError());
    }

    static ValidationShouldBeValid shouldBeValid(Validation actual) {
        return new ValidationShouldBeValid(actual);
    }
}
