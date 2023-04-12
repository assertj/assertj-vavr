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
 * Copyright 2017-2022 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class TryAssert_isSuccess_Test {

    @Test
    void should_fail_when_try_is_null() {
        assertThatThrownBy(
                () -> assertThat((Try<String>) null).isSuccess()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage(actualIsNull());
    }

    @Test
    void should_fail_when_try_is_failure() {
        assertThatThrownBy(
                () -> assertThat(Try.failure(new NullPointerException("NPE error!"))).isSuccess()
        )
                .isInstanceOf(AssertionError.class)
                .hasMessageMatching(
                        "\\nExpecting Try to be a Success, but was a Failure:\\n" +
                        "- exception class: java\\.lang\\.NullPointerException\\n" +
                        "- message: NPE error!\\n" +
                        "- stack trace:\\n" +
                        "(\\tat .*(\\n)?)+");
    }

    @Test
    void should_pass_when_try_is_success() {
        assertThat(Try.success("OK")).isSuccess();
    }
}
