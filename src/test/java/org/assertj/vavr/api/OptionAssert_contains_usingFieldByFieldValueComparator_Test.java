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
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.OptionShouldContain.shouldContain;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class OptionAssert_contains_usingFieldByFieldValueComparator_Test extends BaseTest {

    @Test
    public void should_fail_when_option_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((Option<Foo>) null).usingFieldByFieldValueComparator()
          .contains(new Foo("something"));
    }

    @Test
    public void should_fail_if_expected_value_is_null() {
        thrown.expectIllegalArgumentException("The expected value should not be <null>.");

        assertThat(Option.of(new Foo("something"))).usingFieldByFieldValueComparator().contains(null);
    }

    @Test
    public void should_pass_if_option_contains_expected_value() {
        assertThat(Option.of(new Foo("something"))).usingFieldByFieldValueComparator()
          .contains(new Foo("something"));
    }

    @Test
    public void should_fail_if_option_does_not_contain_expected_value() {
        Option<Foo> actual = Option.of(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        thrown.expectAssertionError(shouldContain(actual, expectedValue).create());

        assertThat(actual).usingFieldByFieldValueComparator().contains(expectedValue);
    }

    @Test
    public void should_fail_if_option_is_empty() {
        Foo expectedValue = new Foo("test");

        thrown.expectAssertionError(shouldContain(expectedValue).create());

        assertThat(Option.none()).usingFieldByFieldValueComparator().contains(expectedValue);
    }

    private static class Foo {

        private final String value;

        Foo(String value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Foo{" + "value='" + value + '\'' + '}';
        }
    }
}
