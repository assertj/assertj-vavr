package org.assertj.vavr.api;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * Creates an error message indicating that an assertion that verifies a map does not contains values.
 * 
 * @author Michał Chmielarz
 */
public class ShouldNotContainValues extends BasicErrorMessageFactory {

  /**
   * Creates a new <code>{@link ShouldNotContainValues}</code>.
   * @param actual the actual values in the failed assertion.
   * @param values the unexpected values.
   * @return the created {@code ErrorMessageFactory}.
   */
  public static ErrorMessageFactory shouldNotContainValues(Object actual, Object values) {
    return new ShouldNotContainValues(actual, values);
  }

  private ShouldNotContainValues(Object actual, Object values) {
    super("%nExpecting:%n  <%s>%nnot to contain values:%n  <%s>", actual, values);
  }
}