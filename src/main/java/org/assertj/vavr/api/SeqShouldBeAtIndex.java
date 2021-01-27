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
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.Seq;
import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * Creates an error message indicating that an assertion that verifies a group of elements contains a value at a given index that
 * satisfies a {@link Condition} failed.
 *
 * @author Michał Chmielarz
 */
public class SeqShouldBeAtIndex extends BasicErrorMessageFactory {

  public static <T> ErrorMessageFactory shouldBeAtIndex(Seq<? extends T> actual, Condition<? super T> condition, Index index, T found) {
    return new SeqShouldBeAtIndex(actual, condition, index, found);
  }

  private <T> SeqShouldBeAtIndex(Seq<? extends T> actual, Condition<? super T> condition, Index index, T found) {
    super("%nExpecting:%n <%s>%nat index <%s> to be:%n <%s>%nin:%n <%s>%n", found, index.value, condition, actual);
  }
}
