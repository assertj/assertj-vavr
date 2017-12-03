package org.assertj.vavr.api;

import io.vavr.control.Try;

import org.assertj.core.util.CheckReturnValue;

/**
 * Entry point for assertion methods for {@link io.vavr.control.Try} type.
 *
 * @author Michał Chmielarz
 */
@CheckReturnValue
public final class TryAssertions {

  private TryAssertions() {
  }

  /**
   * Create assertion for {@link io.vavr.control.Try}.
   *
   * @return the created assertion object.
   */
  @CheckReturnValue
  public static <VALUE> TryAssert<VALUE> assertThat(Try<VALUE> actual) {
    return new TryAssert<>(actual);
  }
}
