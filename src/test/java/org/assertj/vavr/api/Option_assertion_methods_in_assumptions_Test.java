package org.assertj.vavr.api;

import io.vavr.control.Option;

import java.util.stream.Stream;

import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;
import static org.assertj.core.api.Assertions.assertThat;

public class Option_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final Option<String> ARAGORN = Option.some("aragorn");

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).contains("aragorn"),
                        value -> assumeThat(value).contains("frodo")),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).containsInstanceOf(String.class),
                        value -> assumeThat(value).containsInstanceOf(Integer.class)),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).containsSame("aragorn"),
                        value -> assumeThat(value).containsSame(new String("aragorn"))),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).flatMap(val -> Option.some(val.toUpperCase())).contains("ARAGORN"),
                        value -> assumeThat(value).flatMap(val -> Option.some(val.toUpperCase())).contains("aragorn")),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).map(String::toUpperCase).contains("ARAGORN"),
                        value -> assumeThat(value).map(String::toUpperCase).contains("aragorn")),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).hasValueSatisfying(str -> assertThat(str).isLowerCase()),
                        value -> assumeThat(value).hasValueSatisfying(str -> assertThat(str).isUpperCase())),
                assumptionRunner(ARAGORN,
                        value -> assumeThat(value).hasValueSatisfying(new TestCondition<>(true)),
                        value -> assumeThat(value).hasValueSatisfying(new TestCondition<>(false)))
        );
    }

}
