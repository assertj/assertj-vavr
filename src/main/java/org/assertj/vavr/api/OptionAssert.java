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

/**
 * Assertions for {@link io.vavr.control.Option}.
 *
 * @param <VALUE> type of the value contained in the {@link io.vavr.control.Option}.
 * @author Grzegorz Piwowarek
 */
public class OptionAssert<VALUE> extends AbstractOptionAssert<OptionAssert<VALUE>, VALUE> {
    OptionAssert(Option<VALUE> actual) {
        super(actual, OptionAssert.class);
    }
}
