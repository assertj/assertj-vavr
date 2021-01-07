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
