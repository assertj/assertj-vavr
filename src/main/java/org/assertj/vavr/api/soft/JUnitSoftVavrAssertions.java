package org.assertj.vavr.api.soft;

import org.assertj.core.api.AbstractSoftAssertions;
import org.assertj.core.api.SoftAssertionsRule;

/**
 * Same as {@link SoftVavrAssertions}, but with the following differences: <br>
 * First, it's a junit rule, which can be used without having to call {@link SoftVavrAssertions#assertAll() assertAll()},
 * example:
 * <pre><code class='java'> public class SoftlyTest {
 *
 *     &#064;Rule
 *     public final JUnitSoftVavrAssertions softly = new JUnitSoftVavrAssertions();
 *
 *     &#064;Test
 *     public void testSoftly() throws Exception {
 *       softly.assertThat(1).isEqualTo(2);
 *       softly.assertThat(Lists.newArrayList(1, 2)).containsOnly(1, 2);
 *     }
 *  }</code></pre>
 *
 * Second, the failures are recognized by IDE's (like IntelliJ IDEA) which open a comparison window.
 */
public class JUnitSoftVavrAssertions extends AbstractSoftAssertions implements StandardSoftVavrAssertionsProvider, SoftAssertionsRule {
}