/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * Creates an error message indicating that an assertion that verifies a map does not contain values.
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