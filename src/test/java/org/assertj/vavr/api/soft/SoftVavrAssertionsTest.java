package org.assertj.vavr.api.soft;

import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.runSoftlyAssertions;
import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.verifyErrors;
import static org.junit.jupiter.api.Assertions.fail;

class SoftVavrAssertionsTest {

    @Test
    void all_assertions_should_pass() {
        SoftVavrAssertions softly = new SoftVavrAssertions();
        softly.assertThat(Option.some("value")).contains("value");
        softly.assertThat(List.of(1, 2)).containsOnly(1, 2);
        softly.assertAll();
    }

    @Test
    void should_be_able_to_catch_exceptions_thrown_by_all_proxied_methods() {
        Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);

        // required to check error message in catch section
        Lazy<Double> evaluated = Lazy.of(Math::random);
        Double evaluatedVal = evaluated.get();

        try {
            SoftVavrAssertions softly = new SoftVavrAssertions();
            runSoftlyAssertions(evaluated, softly);
            softly.assertAll();
        } catch (MultipleFailuresError e) {
            verifyErrors(evaluatedVal, e);
            return;
        }

        fail("Should not reach here");
    }

}
