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

import org.assertj.core.util.CheckReturnValue;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;

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
     * @param <LEFT> the type of a value contained on left by <code>actual {@link Either}</code>.
     * @param <RIGHT> the type of a value contained on right by <code>actual {@link Either}</code>.
     * @param actual the actual value.
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
     * @param <VALUE> the type of a value contained by <code>actual {@link Option}</code>.
     * @param actual the actual value.
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
     * @param <VALUE> the type of a value contained by <code>actual {@link Try}</code>.
     * @param actual the actual value.
     *
     * @return the created assertion object.
     */
    @CheckReturnValue
    public static <VALUE> TryAssert<VALUE> assertThat(Try<VALUE> actual) {
        return new TryAssert<>(actual);
    }

    /**
     * Create assertion for {@link io.vavr.collection.List}.
     *
     * @param <VALUE> the type of elements contained by <code>actual {@link List}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    public static <VALUE> SeqAssert<VALUE> assertThat(Seq<VALUE> actual) {
        return new SeqAssert<>(actual);
    }
}
