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
