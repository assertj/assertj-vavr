package org.assertj.vavr.error;

import io.vavr.control.Either;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link Either} should be left.
 *
 * @author Michał Chmielarz
 */
public class EitherShouldBeLeft extends BasicErrorMessageFactory {

    private EitherShouldBeLeft(Either actual) {
        super("%nExpecting an Either to be left but was right <%s>.", actual.right().get());
    }

    public static EitherShouldBeLeft shouldBeLeft(Either actual) {
        return new EitherShouldBeLeft(actual);
    }
}
