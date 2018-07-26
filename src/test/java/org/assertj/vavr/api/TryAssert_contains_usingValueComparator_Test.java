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
import io.vavr.control.Try;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import java.util.Comparator;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.error.TryShouldContain.shouldContain;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class TryAssert_contains_usingValueComparator_Test extends BaseTest {

    private static Comparator<Foo> FOO_COMPARATOR = Comparator
      .comparing(o -> o.getValue().toLowerCase());

    @Test
    public void should_fail_when_try_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((Option<Foo>) null).usingValueComparator(FOO_COMPARATOR)
          .contains(new Foo("something"));
    }

    @Test
    public void should_fail_if_expected_value_is_null() {
        thrown.expectIllegalArgumentException("The expected value should not be <null>.");

        assertThat(Try.success(new Foo("something"))).usingValueComparator(FOO_COMPARATOR).contains(null);
    }

    @Test
    public void should_pass_if_successful_try_contains_expected_value() {
        assertThat(Try.success(new Foo("something")))
          .usingValueComparator(FOO_COMPARATOR)
          .contains(new Foo("SoMething"));
    }

    @Test
    public void should_fail_if_successful_try_does_not_contain_expected_value() {
        Try<Foo> actual = Try.success(new Foo("something"));
        Foo expectedValue = new Foo("something else");

        thrown.expectAssertionError(shouldContain(actual, expectedValue).create());

        assertThat(actual).usingValueComparator(FOO_COMPARATOR).contains(expectedValue);
    }

    private static class Foo {

        private final String value;

        Foo(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Foo{value='" + value + "'}";
        }
    }
}
