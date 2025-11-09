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
 * Copyright 2017-2025 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.Lazy;
import org.assertj.core.error.BasicErrorMessageFactory;
import static java.lang.String.format;

/**
 * Build error message when a {@link Lazy} value should be not evaluated.
 */
class LazyShouldBeNotEvaluated extends BasicErrorMessageFactory {

    private LazyShouldBeNotEvaluated(Lazy<?> actual) {
        super(format("%nExpecting Lazy to be not evaluated but it was <%s>", actual));
    }

    /**
     * Indicates that a {@link Lazy} value should be not evaluated.
     *
     * @param actual {@link Lazy} value.
     * @return a error message factory.
     */
    static LazyShouldBeNotEvaluated shouldBeNotEvaluated(Lazy actual) {
        return new LazyShouldBeNotEvaluated(actual);
    }
}
