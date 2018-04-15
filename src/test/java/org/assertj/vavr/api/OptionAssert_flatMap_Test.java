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

import java.util.function.Function;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class OptionAssert_flatMap_Test extends BaseTest {

    private static final Function<String, Option<String>> UPPER_CASE_OPTIONAL_STRING = s -> (s
      == null)
      ? Option.none() : Option.of(s.toUpperCase());

    @Test
    public void should_fail_when_option_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((Option<String>) null).flatMap(UPPER_CASE_OPTIONAL_STRING);
    }

    @Test
    public void should_pass_when_option_is_empty() {
        assertThat(Option.<String>none()).flatMap(UPPER_CASE_OPTIONAL_STRING).isEmpty();
    }

    @Test
    public void should_pass_when_option_contains_a_value() {
        assertThat(Option.of("present")).contains("present")
          .flatMap(UPPER_CASE_OPTIONAL_STRING)
          .contains("PRESENT");
    }
}
