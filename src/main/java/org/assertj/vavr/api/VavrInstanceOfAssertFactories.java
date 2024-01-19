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
 * Copyright 2017-2023 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.Lazy;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.api.InstanceOfAssertFactory;

@SuppressWarnings({
        "rawtypes", // using Class instance
        "unused", // parameter needed for type inference.
})
public interface VavrInstanceOfAssertFactories {

    /**
     * {@link InstanceOfAssertFactory} for a {@link Either}, assuming {@code Object} as left and right types.
     *
     * @see #either(Class, Class)
     */
    InstanceOfAssertFactory<Either, EitherAssert<Object, Object>> EITHER = either(Object.class, Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Either}.
     *
     * @param <L>   the {@link Either} left type.
     * @param <R>   the {@link Either} right type.
     * @param ltype the left type instance.
     * @param rtype the right type instance.
     * @return the factory instance.
     * @see #EITHER
     */
    static <L, R> InstanceOfAssertFactory<Either, EitherAssert<L, R>> either(Class<L> ltype, Class<R> rtype) {
        return new InstanceOfAssertFactory<>(Either.class, VavrAssertions::<L, R>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Lazy}, assuming {@code Object} as input type.
     *
     * @see #lazy(Class)
     */
    InstanceOfAssertFactory<Lazy, LazyAssert<Object>> LAZY = lazy(Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Lazy}.
     *
     * @param <T>  the {@code Lazy} type.
     * @param type the type instance.
     * @return the factory instance.
     * @see #LAZY
     */
    static <T> InstanceOfAssertFactory<Lazy, LazyAssert<T>> lazy(Class<T> type) {
        return new InstanceOfAssertFactory<>(Lazy.class, VavrAssertions::<T>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Map}, assuming {@code Object} as key and value types.
     *
     * @see #map(Class, Class)
     */
    InstanceOfAssertFactory<Map, MapAssert<Object, Object>> MAP = map(Object.class, Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Map}.
     *
     * @param <K>   the {@link Map} key type.
     * @param <V>   the {@link Map} value type.
     * @param ktype the key type instance.
     * @param vtype the value type instance.
     * @return the factory instance.
     * @see #MAP
     */
    static <K, V> InstanceOfAssertFactory<Map, MapAssert<K, V>> map(Class<K> ktype, Class<V> vtype) {
        return new InstanceOfAssertFactory<>(Map.class, VavrAssertions::<K, V>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Multimap}, assuming {@code Object} as input type.
     *
     * @see #multimap(Class, Class)
     */
    InstanceOfAssertFactory<Multimap, MultimapAssert<Object, Object>> MULTIMAP = multimap(Object.class, Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Multimap}.
     *
     * @param <K>   the {@code Multimap} key type.
     * @param <V>   the {@code Multimap} value type.
     * @param ktype the key type instance.
     * @param vtype the value type instance.
     * @return the factory instance.
     * @see #MULTIMAP
     */
    static <K, V> InstanceOfAssertFactory<Multimap, MultimapAssert<K, V>> multimap(Class<K> ktype, Class<V> vtype) {
        return new InstanceOfAssertFactory<>(Multimap.class, VavrAssertions::<K, V>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Option}, assuming {@code Object} as input type.
     *
     * @see #option(Class)
     */
    InstanceOfAssertFactory<Option, OptionAssert<Object>> OPTION = option(Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Option}.
     *
     * @param <T>  the {@link Option} type.
     * @param type the type instance.
     * @return the factory instance.
     * @see #OPTION
     */
    static <T> InstanceOfAssertFactory<Option, OptionAssert<T>> option(Class<T> type) {
        return new InstanceOfAssertFactory<>(Option.class, VavrAssertions::<T>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Seq}, assuming {@code Object} as input type.
     *
     * @see #seq(Class)
     */
    InstanceOfAssertFactory<Seq, SeqAssert<Object>> SEQ = seq(Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Seq}.
     *
     * @param <T>  the {@link Seq} type.
     * @param type the type instance.
     * @return the factory instance.
     * @see #SEQ
     */
    static <T> InstanceOfAssertFactory<Seq, SeqAssert<T>> seq(Class<T> type) {
        return new InstanceOfAssertFactory<>(Seq.class, VavrAssertions::<T>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Set}, assuming {@code Object} as input type.
     *
     * @see #set(Class)
     */
    InstanceOfAssertFactory<Set, SetAssert<Object>> SET = set(Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Set}.
     *
     * @param <T>  the {@link Set} type.
     * @param type the type instance.
     * @return the factory instance.
     * @see #SET
     */
    static <T> InstanceOfAssertFactory<Set, SetAssert<T>> set(Class<T> type) {
        return new InstanceOfAssertFactory<>(Set.class, VavrAssertions::<T>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Try}, assuming {@code Object} as input type.
     *
     * @see #_try(Class)
     */
    InstanceOfAssertFactory<Try, TryAssert<Object>> TRY = _try(Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Try}.
     *
     * @param <T>  the {@link Try} type.
     * @param type the type instance.
     * @return the factory instance.
     * @see #TRY
     */
    static <T> InstanceOfAssertFactory<Try, TryAssert<T>> _try(Class<T> type) {
        return new InstanceOfAssertFactory<>(Try.class, VavrAssertions::<T>assertThat);
    }

    /**
     * {@link InstanceOfAssertFactory} for a {@link Validation}, assuming {@code Object} as invalid and valid types.
     *
     * @see #validation(Class, Class)
     */
    InstanceOfAssertFactory<Validation, ValidationAssert<Object, Object>> VALIDATION =
            validation(Object.class, Object.class);

    /**
     * {@link InstanceOfAssertFactory} for a {@link Validation}.
     *
     * @param <I>   the {@link Validation} invalid type.
     * @param <V>   the {@link Validation} valid type.
     * @param itype the invalid type instance.
     * @param vtype the valid type instance.
     * @return the factory instance.
     * @see #VALIDATION
     */
    static <I, V> InstanceOfAssertFactory<Validation, ValidationAssert<I, V>> validation(Class<I> itype, Class<V> vtype) {
        return new InstanceOfAssertFactory<>(Validation.class, VavrAssertions::<I, V>assertThat);
    }
}
