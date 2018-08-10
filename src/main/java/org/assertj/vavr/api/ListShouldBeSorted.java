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
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.List;
import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

import java.util.Comparator;

/**
 * Creates an error message indicating that an assertion that verifies a group of elements is sorted failed.
 *
 * @author Michał Chmielarz
 */
class ListShouldBeSorted extends BasicErrorMessageFactory {

  static ErrorMessageFactory shouldBeSorted(int i, List<? extends Object> group) {
    return new ListShouldBeSorted(
            "%ngroup is not sorted because element %s:%n <%s>%nis not less or equal than element %s:%n <%s>%ngroup was:%n <%s>",
            i, group.get(i), i + 1, group.get(i + 1), group);
  }

  static ErrorMessageFactory shouldHaveMutuallyComparableElements(Object actual) {
    return new ListShouldBeSorted("%nsome elements are not mutually comparable in group:%n<%s>", actual);
  }

  static ErrorMessageFactory shouldBeSortedAccordingToGivenComparator(int i, List<? extends Object> actual,
                                                                             Comparator<?> comparator) {
    return new ListShouldBeSorted(
            "%ngroup is not sorted according to %s comparator because element %s:%n <%s>%nis not less or equal than element %s:%n <%s>%ngroup was:%n <%s>",
            comparator, i, actual.get(i), i + 1, actual.get(i + 1), actual);
  }

  private ListShouldBeSorted(String format, Object... arguments) {
    super(format, arguments);
  }

}
