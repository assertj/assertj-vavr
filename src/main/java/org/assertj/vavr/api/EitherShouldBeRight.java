package org.assertj.vavr.api;

import io.vavr.control.Either;

import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Either} should be right.
 *
 * @author Michał Chmielarz
 */
class EitherShouldBeRight extends BasicErrorMessageFactory {

    private EitherShouldBeRight(Either actual) {
        super("%nExpecting an Either to be right but was left <%s>.", actual.left().get());
    }

    static EitherShouldBeRight shouldBeRight(Either actual) {
        return new EitherShouldBeRight(actual);
    }
}
