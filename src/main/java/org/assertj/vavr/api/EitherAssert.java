package org.assertj.vavr.api;

import io.vavr.control.Either;

/**
 * Assertions for {@link io.vavr.control.Either}.
 *
 * @param <LEFT> type of the value on the left contained in the {@link io.vavr.control.Either}.
 * @param <RIGHT> type of the value on the right contained in the {@link io.vavr.control.Either}.
 * @author Alex Dukhno
 */
public class EitherAssert<LEFT, RIGHT> extends AbstractEitherAssert<EitherAssert<LEFT, RIGHT>, LEFT, RIGHT> {
  EitherAssert(Either<LEFT, RIGHT> actual) {
    super(actual, EitherAssert.class);
  }
}
