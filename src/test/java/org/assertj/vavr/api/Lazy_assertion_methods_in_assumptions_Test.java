package org.assertj.vavr.api;

import io.vavr.Lazy;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.vavr.api.VavrAssumptions.assumeThat;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;

public class Lazy_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(evaluated(Math::random),
                        value -> assumeThat(value).isEvaluated(),
                        value -> assumeThat(value).isNotEvaluated()),
                assumptionRunner(notEvaluated(Math::random),
                        value -> assumeThat(value).isNotEvaluated(),
                        value -> assumeThat(value).isEvaluated())
        );
    }

    private static Lazy<Double> evaluated(Supplier<Double> random) {
        final Lazy<Double> evaluated = Lazy.of(random);
        evaluated.get();
        return evaluated;
    }

    private static Lazy<Double> notEvaluated(Supplier<Double> random) {
        return Lazy.of(random);
    }

}
