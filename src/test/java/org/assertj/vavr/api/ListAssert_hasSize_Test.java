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

import io.vavr.collection.List;
import org.assertj.vavr.test.BaseTest;
import org.junit.Test;

import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class ListAssert_hasSize_Test extends BaseTest {

    @Test
    public void should_pass_if_size_of_List_is_same_as_expected() {
        assertThat(List.empty()).isEmpty();
    }

    @Test
    public void should_fail_when_List_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((List<String>) null).isEmpty();
    }

    @Test
    public void should_fail_if_List_has_different_size_than_expected() {
        List<String> actual = List.of("not-empty");

        thrown.expectAssertionError(shouldHaveSize(actual, actual.size(), 5).create());

        assertThat(actual).hasSize(5);
    }
}
