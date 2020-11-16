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
 * Copyright 2017-2020 the original author or authors.
 */
package org.assertj.vavr.api;

import org.assertj.core.error.BasicErrorMessageFactory;
import static java.lang.String.format;

/**
 * Build error message when a value should be present in an {@link io.vavr.control.Option}.
 *
 * @author Grzegorz Piwowarek
 */
class OptionShouldBePresent extends BasicErrorMessageFactory {

    private OptionShouldBePresent() {
        super(format("%nExpecting Option to contain a value but it didn't."));
    }

    /**
     * Indicates that a value should be present in an empty {@link io.vavr.control.Option}.
     *
     * @return a error message factory.
     * @throws java.lang.NullPointerException if option is null.
     */
    static OptionShouldBePresent shouldBePresent() {
        return new OptionShouldBePresent();
    }
}
