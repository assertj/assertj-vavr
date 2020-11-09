package org.assertj.vavr.api.soft;

import org.assertj.core.api.AbstractSoftAssertions;
import org.assertj.core.api.SoftAssertionsProvider;
import org.opentest4j.MultipleFailuresError;

import java.util.function.Consumer;

/**
 *
 */
public class SoftVavrAssertions extends AbstractSoftAssertions implements StandardSoftVavrAssertionsProvider {

    /**
     * Convenience method for calling {@link SoftAssertionsProvider#assertSoftly} for these assertion types.
     * Equivalent to {@code SoftVavrAssertions.assertSoftly(SoftVavrAssertions.class, softly)}.
     * @param softly the Consumer containing the code that will make the soft assertions.
     *     Takes one parameter (the SoftVavrAssertions instance used to make the assertions).
     * @throws MultipleFailuresError if possible or SoftAssertionError if any proxied assertion objects threw an {@link AssertionError}
     */
    public static void assertSoftly(Consumer<SoftVavrAssertions> softly) {
        SoftAssertionsProvider.assertSoftly(SoftVavrAssertions.class, softly);
    }

}
