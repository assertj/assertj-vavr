package org.assertj.vavr.api;

/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.
  <p>
  Copyright 2012-2018 the original author or authors.
 */

import io.vavr.collection.List;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when a {@link List} should be empty.
 *
 * @author Michał Chmielarz
 */
class ListShouldBeEmpty extends BasicErrorMessageFactory {

    private ListShouldBeEmpty(List expected) {
        super("%nExpecting a List to be empty but was <%s>.", expected.get());
    }

    static ListShouldBeEmpty shouldBeEmpty(List actual) {
        return new ListShouldBeEmpty(actual);
    }
}
