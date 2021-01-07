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
