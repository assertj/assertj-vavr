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
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api.soft;

import org.assertj.core.api.AbstractSoftAssertions;
import org.assertj.core.api.SoftAssertionsProvider;
import org.opentest4j.MultipleFailuresError;

import java.util.function.Consumer;

/**
 * Suppose we have a test case and in it we'd like to make numerous assertions. {@code SoftVavrAssertions} enables such feature.
 * You can use them to check result of all assertions, not just a single one:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Test
 *     public void testSoftly() throws Exception {
 *       SoftAssertions softly = new SoftAssertions();
 *       softly.assertThat(1).isEqualTo(2);
 *       softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       softly.assertAll();
 *     }
 *  }</code></pre>
 */
public class SoftVavrAssertions extends AbstractSoftAssertions implements StandardSoftVavrAssertionsProvider {

    /**
     * Convenience method for calling {@link SoftAssertionsProvider#assertSoftly} for these assertion types.
     * Equivalent to {@code SoftVavrAssertions.assertSoftly(SoftVavrAssertions.class, consumer)}.
     * @param softly the Consumer containing the code that will make the soft assertions.
     *     Takes one parameter (the SoftVavrAssertions instance used to make the assertions).
     * @throws MultipleFailuresError if possible or SoftAssertionError if any proxied assertion objects threw an {@link AssertionError}
     */
    public static void assertSoftly(Consumer<SoftVavrAssertions> softly) {
        SoftAssertionsProvider.assertSoftly(SoftVavrAssertions.class, softly);
    }

}
