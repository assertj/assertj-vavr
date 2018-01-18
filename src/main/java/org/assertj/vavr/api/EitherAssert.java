package org.assertj.vavr.api;

import io.vavr.control.Either;

/**
 * Assertions for {@link io.vavr.control.Either}.
 *
 * @param <LEFT> type of the left value contained in the {@link Either}.
 * @param <RIGHT> type of the right value contained in the {@link Either}.
 *
 * @author Michał Chmielarz
 */
public class EitherAssert<LEFT, RIGHT> extends AbstractEitherAssert<EitherAssert<LEFT, RIGHT>, LEFT, RIGHT> {
  EitherAssert(Either<LEFT, RIGHT> actual) {
    super(actual, EitherAssert.class);
  }
}
