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

import io.vavr.control.Option;
import org.assertj.core.error.BasicErrorMessageFactory;

/**
 * Build error message when an {@link io.vavr.control.Option} should be empty.
 *
 * @author Grzegorz Piwowarek
 */
class OptionShouldBeEmpty extends BasicErrorMessageFactory {

    private OptionShouldBeEmpty(Option<?> expected) {
        super("%nExpecting an Option to be empty but was <%s>.", expected.get());
    }

    static OptionShouldBeEmpty shouldBeEmpty(Option<?> actual) {
        return new OptionShouldBeEmpty(actual);
    }
}
