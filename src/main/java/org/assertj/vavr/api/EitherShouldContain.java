package org.assertj.vavr.api;

import org.assertj.core.error.BasicErrorMessageFactory;

import io.vavr.control.Either;

/**
 * Build error message when an {@link Either}
 * should contain a specific value.
 *
 * @author Alex Dukhno
 */
class EitherShouldContain extends BasicErrorMessageFactory {
  private static final String EXPECTING_TO_CONTAIN_ON_THE_LEFT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [LEFT]%nbut did not.";
  private static final String EXPECTING_TO_CONTAIN_ON_THE_RIGHT = "%nExpecting:%n  <%s>%nto contain:%n  <%s> on the [RIGHT]%nbut did not.";

  private static final String EXPECTING_ON_THE_LEFT = "%nExpecting:%n  <%s>%nto contain on the [LEFT]:%n  <%s>%nbut had <%s> on the [RIGHT].";
  private static final String EXPECTING_ON_THE_RIGHT = "%nExpecting:%n  <%s>%nto contain on the [RIGHT]:%n  <%s>%nbut had <%s> on the [LEFT].";

  private EitherShouldContain(String format, Object... arguments) {
    super(format, arguments);
  }

  /**
   * Indicates that the provided {@link io.vavr.control.Either} does not contain the provided argument.
   *
   * @param either   the {@link io.vavr.control.Either} which contains a value.
   * @param expected the value we expect to be in the provided {@link io.vavr.control.Either}.
   * @param <LEFT>   the type of the value contained in the {@link io.vavr.control.Either} on the left.
   * @param <RIGHT>  the type of the value contained in the {@link io.vavr.control.Either} on the right.
   * @return an error message factory
   */
  static <LEFT, RIGHT> EitherShouldContain shouldContain(Either<LEFT, RIGHT> either, Object expected) {
    return either.isLeft() ?
        new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_THE_LEFT, either, expected) :
        new EitherShouldContain(EXPECTING_TO_CONTAIN_ON_THE_RIGHT, either, expected);
  }

  /**
   * Indicates that the provided {@link io.vavr.control.Either} does not contain the provided argument on expected side.
   *
   * @param either   the {@link io.vavr.control.Either} which contains a value.
   * @param expected the value we expect to be in the provided {@link io.vavr.control.Either}.
   * @param <LEFT>   the type of the value contained in the {@link io.vavr.control.Either} on the left.
   * @param <RIGHT>  the type of the value contained in the {@link io.vavr.control.Either} on the right.
   * @return an error message factory
   */
  static <LEFT, RIGHT> EitherShouldContain shouldContainOnOtherSide(Either<LEFT, RIGHT> either, Object expected) {
    return either.isRight()
        ? new EitherShouldContain(EXPECTING_ON_THE_LEFT, either, expected, either.get())
        : new EitherShouldContain(EXPECTING_ON_THE_RIGHT, either, expected, either.getLeft());
  }
}
