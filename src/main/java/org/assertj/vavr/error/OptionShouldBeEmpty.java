package org.assertj.vavr.error;

import io.vavr.control.Option;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link io.vavr.control.Option} should be empty.
 *
 * @author Grzegorz Piwowarek
 */
public class OptionShouldBeEmpty extends BasicErrorMessageFactory {

    private OptionShouldBeEmpty(Option expected) {
        super("%nExpecting an Option to be empty but was <%s>.", expected.get());
    }

    public static OptionShouldBeEmpty shouldBeEmpty(Option actual) {
        return new OptionShouldBeEmpty(actual);
    }
}
