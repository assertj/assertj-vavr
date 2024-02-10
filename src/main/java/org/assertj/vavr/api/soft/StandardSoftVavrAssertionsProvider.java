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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api.soft;

import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.api.StandardSoftAssertionsProvider;
import org.assertj.core.util.CheckReturnValue;
import org.assertj.vavr.api.EitherAssert;
import org.assertj.vavr.api.LazyAssert;
import org.assertj.vavr.api.MapAssert;
import org.assertj.vavr.api.MultimapAssert;
import org.assertj.vavr.api.OptionAssert;
import org.assertj.vavr.api.SeqAssert;
import org.assertj.vavr.api.SetAssert;
import org.assertj.vavr.api.TryAssert;
import org.assertj.vavr.api.ValidationAssert;

public interface StandardSoftVavrAssertionsProvider extends StandardSoftAssertionsProvider {

    /**
     * Creates assertion for {@link io.vavr.control.Either}.
     *
     * @param <LEFT>  the type of a value contained on left by <code>actual {@link Either}</code>.
     * @param <RIGHT> the type of a value contained on right by <code>actual {@link Either}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <LEFT, RIGHT> EitherAssert<LEFT, RIGHT> assertThat(Either<LEFT, RIGHT> actual) {
        return proxy(EitherAssert.class, Either.class, actual);
    }

    /**
     * Creates assertion for {@link Lazy}.
     *
     * @param <VALUE> the type of a value contained by <code>actual {@link Lazy}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <VALUE> LazyAssert<VALUE> assertThat(Lazy<VALUE> actual) {
        return proxy(LazyAssert.class, Lazy.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.control.Option}.
     *
     * @param <VALUE> the type of a value contained by <code>actual {@link Option}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <VALUE> OptionAssert<VALUE> assertThat(Option<VALUE> actual) {
        return proxy(OptionAssert.class, Option.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.control.Try}.
     *
     * @param <VALUE> the type of a value contained by <code>actual {@link Try}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <VALUE> TryAssert<VALUE> assertThat(Try<VALUE> actual) {
        return proxy(TryAssert.class, Try.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.collection.List}.
     *
     * @param <VALUE> the type of elements contained by <code>actual {@link List}</code>.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <VALUE> SeqAssert<VALUE> assertThat(Seq<VALUE> actual) {
        return proxy(SeqAssert.class, Seq.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.control.Validation}.
     *
     * @param <INVALID> type of the value in the case of the invalid {@link Validation}.
     * @param <VALID>   type of the value in the case of the valid {@link Validation}.
     * @param actual    the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <INVALID, VALID> ValidationAssert<INVALID, VALID> assertThat(Validation<INVALID, VALID> actual) {
        return proxy(ValidationAssert.class, Validation.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.collection.Set}.
     *
     * @param <VALUE> the type of elements contained by <code> actual {@link Set}</code>.
     * @param actual the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <VALUE> SetAssert<VALUE> assertThat(Set<VALUE> actual) {
        return proxy(SetAssert.class, Set.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.collection.Map}.
     *
     * @param <KEY>   key type of the {@link Map}.
     * @param <VALUE> value type of the {@link Map}.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <KEY, VALUE> MapAssert<KEY, VALUE> assertThat(Map<KEY, VALUE> actual) {
        return proxy(MapAssert.class, Map.class, actual);
    }

    /**
     * Creates assertion for {@link io.vavr.collection.Multimap}.
     *
     * @param <KEY>   key type of the {@link Multimap}.
     * @param <VALUE> value type of the {@link Multimap}.
     * @param actual  the actual value.
     * @return the created assertion object.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    default <KEY, VALUE> MultimapAssert<KEY, VALUE> assertThat(Multimap<KEY, VALUE> actual) {
        return proxy(MultimapAssert.class, Multimap.class, actual);
    }

}
