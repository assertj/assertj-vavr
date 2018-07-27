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

import static org.assertj.core.error.ShouldContain.shouldContain;
import static org.assertj.core.internal.ErrorMessages.arrayOfValuesToLookForIsNull;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class ListAssert_contains_Test extends BaseTest {

    @Test
    public void should_pass_if_List_contains_all_expected_values() {
        final String value = "something";
        assertThat(List.of(value)).contains(value);
    }

    @Test
    public void should_pass_if_List_and_expected_values_are_empty() {
        assertThat(List.empty()).contains();
    }


    @Test
    public void should_fail_when_List_is_null() {
        thrown.expectAssertionError(actualIsNull());

        assertThat((List<String>) null).contains("something");
    }

    @Test
    public void should_fail_when_expected_values_are_null() {
        thrown.expectNullPointerException(arrayOfValuesToLookForIsNull());

        assertThat(List.of("something")).contains(null);
    }

    @Test
    public void should_fail_if_List_is_not_empty_but_expected_values_are() {
        thrown.expectAssertionError("actual is not empty");

        assertThat(List.of("something")).contains();
    }

    @Test
    public void should_fail_if_List_contains_no_element_from_expected_values() {
        List<String> actual = List.of("a", "b", "c");

        thrown.expectAssertionError(shouldContain(actual, new String[] {"a", "d"}, new String[]{"d"}));

        assertThat(actual).contains("a", "d");
    }
}
