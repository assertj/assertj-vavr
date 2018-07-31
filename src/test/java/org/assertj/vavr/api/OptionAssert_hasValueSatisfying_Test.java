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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class OptionAssert_hasValueSatisfying_Test extends BaseTest {

    @Test
    public void should_fail_when_option_is_null() {
        thrown.expectAssertionError(actualIsNull());
        assertThat((Option<String>) null).hasValueSatisfying(s -> {
        });
    }

    @Test
    public void should_fail_when_option_is_empty() {
        thrown.expectAssertionError(OptionShouldBePresent.shouldBePresent().create());
        assertThat(Option.none()).hasValueSatisfying(o -> {
        });
    }

    @Test
    public void should_pass_when_consumer_passes() {
        assertThat(Option.of("something")).hasValueSatisfying(s -> assertThat(s).isEqualTo("something")
          .startsWith("some")
          .endsWith("thing"));
        assertThat(Option.of(10)).hasValueSatisfying(i -> assertThat(i).isGreaterThan(9));
    }

    @Test
    public void should_fail_from_consumer() {
        thrown.expectAssertionError("expected:<\"something[]\"> but was:<\"something[ else]\">");
        assertThat(Option.of("something else"))
          .hasValueSatisfying(s -> assertThat(s).isEqualTo("something"));
    }
}
