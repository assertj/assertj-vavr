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
 * Copyright 2012-2017 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.vavr.api.OptionShouldBePresent.shouldBePresent;
import static org.assertj.vavr.api.OptionShouldContainInstanceOf.shouldContainInstanceOf;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class OptionAssert_containsInstanceOf_Test {

    @Test
    void should_fail_if_option_is_empty() {
        Option<Object> actual = Option.none();

        assertThatThrownBy(
                () -> assertThat(actual).containsInstanceOf(Object.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldBePresent().create());
    }

    @Test
    void should_pass_if_option_contains_required_type() {
        assertThat(Option.of("something")).containsInstanceOf(String.class)
                .containsInstanceOf(Object.class);
    }

    @Test
    void should_pass_if_option_contains_required_type_subclass() {
        assertThat(Option.of(new SubClass())).containsInstanceOf(ParentClass.class);
    }

    @Test
    void should_fail_if_option_contains_other_type_than_required() {
        Option<ParentClass> actual = Option.of(new ParentClass());

        assertThatThrownBy(
                () -> assertThat(actual).containsInstanceOf(OtherClass.class)
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(shouldContainInstanceOf(actual, OtherClass.class).create());
    }

    private static class ParentClass {
    }

    private static class SubClass extends ParentClass {
    }

    private static class OtherClass {
    }
}