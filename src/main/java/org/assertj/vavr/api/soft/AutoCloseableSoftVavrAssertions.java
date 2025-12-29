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
 * Copyright 2017-2025 the original author or authors.
 */
package org.assertj.vavr.api.soft;

import org.assertj.core.api.AutoCloseableSoftAssertionsProvider;

/**
 * A version of {@link SoftVavrAssertions} that uses try-with-resources statement to automatically call
 * {@link SoftVavrAssertions#assertAll()} so that you don't forget to.
 *
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Test
 *     public void testSoftly() throws Exception {
 *       try (AutoCloseableSoftVavrAssertions softly = new AutoCloseableSoftVavrAssertions()) {
 *         softly.assertThat(1).isEqualTo(2);
 *         softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *       }
 *     }
 *  }</code></pre>
 */
public class AutoCloseableSoftVavrAssertions extends SoftVavrAssertions implements AutoCloseableSoftAssertionsProvider {
}
