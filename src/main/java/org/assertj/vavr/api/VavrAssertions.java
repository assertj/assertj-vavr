/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.assertj.core.util.CheckReturnValue;

/**
 * Entry point for assertion methods for different Vavr types. Each method in this class is a static factory for a
 * type-specific assertion object.
 *
 * @author Grzegorz Piwowarek
 */
@CheckReturnValue
public final class VavrAssertions {

    private VavrAssertions() {
    }

    /**
     * Create assertion for {@link io.vavr.control.Either}.
     *
     * @return the created assertion object.
     */
    @CheckReturnValue
    public static <LEFT, RIGHT> EitherAssert<LEFT, RIGHT> assertThat(Either<LEFT, RIGHT> actual) {
        return new EitherAssert<>(actual);
    }

    /**
     * Create assertion for {@link io.vavr.control.Option}.
     *
     * @return the created assertion object.
     */
    @CheckReturnValue
    public static <VALUE> OptionAssert<VALUE> assertThat(Option<VALUE> actual) {
        return new OptionAssert<>(actual);
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
