package org.assertj.vavr.api;

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
 * Copyright 2012-2019 the original author or authors.
 */

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class SetAssert_isEmpty_Test {

    @Test
    void should_pass_if_Set_is_empty() {
        assertThat(HashSet.empty()).isEmpty();
    }

    @Test
    void should_fail_when_Set_is_null() {
        assertThatThrownBy(
              () -> assertThat((Set<String>) null).isEmpty()
        )
              .isInstanceOf(AssertionError.class)
              .hasMessage(actualIsNull());
    }
}
