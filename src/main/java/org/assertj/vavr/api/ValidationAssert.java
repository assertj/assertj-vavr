package org.assertj.vavr.api;

import io.vavr.control.Validation;

/**
 * Assertions for {@link io.vavr.control.Validation}.
 *
 * @param <INVALID> type of the value in the case of the invalid {@link Validation}.
 * @param <VALID>   type of the value in the case of the valid {@link Validation}.
 * @author Michał Chmielarz
 */
public class ValidationAssert<INVALID, VALID> extends AbstractValidationAssert<ValidationAssert<INVALID, VALID>, INVALID, VALID> {

    ValidationAssert(Validation<INVALID, VALID> actual) {
        super(actual, ValidationAssert.class);
    }
}
