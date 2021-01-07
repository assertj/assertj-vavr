package org.assertj.vavr.api.soft;

import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashMultimap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Multimap;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import static java.lang.String.format;
import static java.util.Comparator.naturalOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.runSoftlyAssertions;
import static org.assertj.vavr.api.soft.SoftVavrAssertionsHelper.verifyErrors;
import static org.junit.jupiter.api.Assertions.fail;

class AutoCloseableSoftVavrAssertionsTest {

    @Test
    void all_assertions_should_pass() {
        try (AutoCloseableSoftVavrAssertions softly = new AutoCloseableSoftVavrAssertions()) {
            softly.assertThat(Option.some("value")).contains("value");
            softly.assertThat(List.of(1, 2)).containsOnly(1, 2);
        }
    }

    @Test
    void should_be_able_to_catch_exceptions_thrown_by_all_proxied_methods() {
        Assertions.setRemoveAssertJRelatedElementsFromStackTrace(false);

        // required to check error message in catch section
        Lazy<Double> evaluated = Lazy.of(Math::random);
        Double evaluatedVal = evaluated.get();

        try (AutoCloseableSoftVavrAssertions softly = new AutoCloseableSoftVavrAssertions()) {
            runSoftlyAssertions(evaluated, softly);
        } catch (MultipleFailuresError e) {
            verifyErrors(evaluatedVal, e);
            return;
        }

        fail("Should not reach here");
    }

}
