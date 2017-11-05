package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link io.vavr.control.Option} should be empty.
 *
 * @author Grzegorz Piwowarek
 */
class OptionShouldBeEmpty extends BasicErrorMessageFactory {

    private OptionShouldBeEmpty(Option expected) {
        super("%nExpecting an Option to be empty but was <%s>.", expected.get());
    }

    static OptionShouldBeEmpty shouldBeEmpty(Option actual) {
        return new OptionShouldBeEmpty(actual);
    }
}
