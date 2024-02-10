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
 * Copyright 2017-2024 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.control.Try;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Try_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final Try<String> GIMLI = Try.success("gimli");
    private static final Try<String> SAURON_HAS_THE_RING = Try.failure(new IllegalStateException("Sauron has The Ring"));

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).isSuccess(),
                        value -> assumeThat(value).isFailure()),
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).contains("gimli"),
                        value -> assumeThat(value).contains("aragorn")),
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).containsInstanceOf(String.class),
                        value -> assumeThat(value).containsInstanceOf(Integer.class)),
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).containsSame("gimli"),
                        value -> assumeThat(value).containsSame(new String("gimli"))),
                assumptionRunner(SAURON_HAS_THE_RING,
                        value -> assumeThat(value).failBecauseOf(IllegalStateException.class),
                        value -> assumeThat(value).failBecauseOf(NullPointerException.class)),
                assumptionRunner(SAURON_HAS_THE_RING,
                        value -> assumeThat(value).failReasonHasMessage("Sauron has The Ring"),
                        value -> assumeThat(value).failReasonHasMessage("Gandalf The Grey")),
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).hasValueSatisfying(new TestCondition<>(true)),
                        value -> assumeThat(value).hasValueSatisfying(new TestCondition<>(false))),
                assumptionRunner(GIMLI,
                        value -> assumeThat(value).hasValueSatisfying(str -> assertThat(str).isLowerCase()),
                        value -> assumeThat(value).hasValueSatisfying(str -> assertThat(str).isUpperCase()))
        );
    }

}
