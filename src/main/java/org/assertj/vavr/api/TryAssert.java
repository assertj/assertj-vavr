package org.assertj.vavr.api;

import io.vavr.control.Try;

/**
 * Assertions for {@link io.vavr.control.Try}.
 *
 * @param <VALUE> type of the value contained in the {@link io.vavr.control.Try}.
 * @author Grzegorz Piwowarek
 */
public class TryAssert<VALUE> extends AbstractTryAssert<TryAssert<VALUE>, VALUE> {
    TryAssert(Try<VALUE> actual) {
        super(actual, TryAssert.class);
    }
}