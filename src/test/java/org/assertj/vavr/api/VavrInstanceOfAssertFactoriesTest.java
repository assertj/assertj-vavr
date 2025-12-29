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
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.HashSet;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.VavrInstanceOfAssertFactories.*;

/**
 * @author Eduard Dudar
 */
class VavrInstanceOfAssertFactoriesTest {

    @Test
    void either_factory_should_allow_either_assertions() {
        // GIVEN
        Object value = Either.left("left");
        // WHEN
        EitherAssert<Object, Object> result = assertThat(value).asInstanceOf(EITHER);
        // THEN
        result.isLeft();
    }

    @Test
    void either_typed_factory_should_allow_either_typed_assertions() {
        // GIVEN
        Object value = Either.right("right");
        // WHEN
        EitherAssert<String, String> result = assertThat(value).asInstanceOf(either(String.class, String.class));
        // THEN
        result.isRight();
    }

    @Test
    void lazy_factory_should_allow_lazy_assertions() {
        // GIVEN
        Object value = Lazy.of(() -> "lazy");
        // WHEN
        LazyAssert<Object> result = assertThat(value).asInstanceOf(LAZY);
        // THEN
        result.isNotEvaluated();
    }

    @Test
    void lazy_typed_factory_should_allow_lazy_typed_assertions() {
        // GIVEN
        Object value = Lazy.of(() -> "lazy");
        // WHEN
        LazyAssert<String> result = assertThat(value).asInstanceOf(lazy(String.class));
        // THEN
        result.isNotEvaluated();
    }

    @Test
    void map_factory_should_allow_map_assertions() {
        // GIVEN
        Object value = HashMap.of("key1", "value1");
        // WHEN
        MapAssert<Object, Object> result = assertThat(value).asInstanceOf(MAP);
        // THEN
        result.containsKey("key1");
    }

    @Test
    void map_typed_factory_should_allow_map_typed_assertions() {
        // GIVEN
        Object value = HashMap.of("key1", "value1");
        // WHEN
        MapAssert<String, String> result = assertThat(value).asInstanceOf(map(String.class, String.class));
        // THEN
        result.containsKey("key1");
    }

    @Test
    void multimap_factory_should_allow_multimap_assertions() {
        // GIVEN
        Object value = HashMultimap.withSet().of("key1", "value1");
        // WHEN
        MultimapAssert<Object, Object> result = assertThat(value).asInstanceOf(MULTIMAP);
        // THEN
        result.containsKey("key1");
    }

    @Test
    void multimap_typed_factory_should_allow_multimap_typed_assertions() {
        // GIVEN
        Object value = HashMultimap.withSet().of("key1", "value1");
        // WHEN
        MultimapAssert<String, String> result = assertThat(value).asInstanceOf(multimap(String.class, String.class));
        // THEN
        result.containsKey("key1");
    }

    @Test
    void option_factory_should_allow_option_assertions() {
        // GIVEN
        Object value = Option.of("maybe");
        // WHEN
        OptionAssert<Object> result = assertThat(value).asInstanceOf(OPTION);
        // THEN
        result.isDefined();
    }

    @Test
    void option_typed_factory_should_allow_option_typed_assertions() {
        // GIVEN
        Object value = Option.of("maybe");
        // WHEN
        OptionAssert<String> result = assertThat(value).asInstanceOf(option(String.class));
        // THEN
        result.isDefined();
    }

    @Test
    void seq_factory_should_allow_seq_assertions() {
        // GIVEN
        Object value = Array.of("value1");
        // WHEN
        SeqAssert<Object> result = assertThat(value).asInstanceOf(SEQ);
        // THEN
        result.hasSize(1);
    }

    @Test
    void seq_typed_factory_should_allow_seq_typed_assertions() {
        // GIVEN
        Object value = Array.of("value1", "value2");
        // WHEN
        SeqAssert<String> result = assertThat(value).asInstanceOf(seq(String.class));
        // THEN
        result.hasSize(2);
    }

    @Test
    void set_factory_should_allow_set_assertions() {
        // GIVEN
        Object value = HashSet.of("value1");
        // WHEN
        SetAssert<Object> result = assertThat(value).asInstanceOf(SET);
        // THEN
        result.hasSize(1);
    }

    @Test
    void set_typed_factory_should_allow_set_typed_assertions() {
        // GIVEN
        Object value = HashSet.of("value1", "value2");
        // WHEN
        SetAssert<String> result = assertThat(value).asInstanceOf(set(String.class));
        // THEN
        result.hasSize(2);
    }

    @Test
    void try_factory_should_allow_try_assertions() {
        // GIVEN
        Object value = Try.of(() -> "should we");
        // WHEN
        TryAssert<Object> result = assertThat(value).asInstanceOf(TRY);
        // THEN
        result.isSuccess();
    }

    @Test
    void try_typed_factory_should_allow_try_typed_assertions() {
        // GIVEN
        Object value = Try.of(() -> "should we");
        // WHEN
        TryAssert<String> result = assertThat(value).asInstanceOf(_try(String.class));
        // THEN
        result.isSuccess();
    }

    @Test
    void validation_factory_should_allow_validation_assertions() {
        // GIVEN
        Object value = Validation.valid("value");
        // WHEN
        ValidationAssert<Object, Object> result = assertThat(value).asInstanceOf(VALIDATION);
        // THEN
        result.isValid();
    }

    @Test
    void validation_typed_factory_should_allow_validation_typed_assertions() {
        // GIVEN
        Object value = Validation.invalid("error");
        // WHEN
        ValidationAssert<Integer, String> result =
                assertThat(value).asInstanceOf(validation(Integer.class, String.class));
        // THEN
        result.isInvalid();
    }

}
