/*
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

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.OptionShouldBePresent.shouldBePresent;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionAssert_isDefined_Test {

    @Test
    void should_pass_when_Option_is_present() {
        assertThat(Option.of("present")).isDefined();
    }

    @Test
    void should_fail_when_Option_is_empty() {
        assertThrows(AssertionError.class,
                () -> assertThat(Option.none()).isDefined(),
                shouldBePresent().create());
    }

    @Test
    void should_fail_when_Option_is_null() {
        assertThrows(AssertionError.class,
                () -> assertThat((Option<String>) null).isDefined(),
                actualIsNull());
    }
}
