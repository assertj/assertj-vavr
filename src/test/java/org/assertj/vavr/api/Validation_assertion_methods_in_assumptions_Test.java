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

import io.vavr.control.Validation;

import java.util.stream.Stream;

import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Validation_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {

    private static final Validation<String, String> MERRY = Validation.valid("merry");
    private static final Validation<String, String> ORC = Validation.invalid("orc");

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
                assumptionRunner(MERRY,
                        value -> assumeThat(value).isValid(),
                        value -> assumeThat(value).isInvalid()),
                assumptionRunner(MERRY,
                        value -> assumeThat(value).containsValid("merry"),
                        value -> assumeThat(value).containsValid("aragorn")),
                assumptionRunner(MERRY,
                        value -> assumeThat(value).containsValidInstanceOf(String.class),
                        value -> assumeThat(value).containsValidInstanceOf(Integer.class)),
                assumptionRunner(MERRY,
                        value -> assumeThat(value).containsValidSame("merry"),
                        value -> assumeThat(value).containsValidSame(new String("merry"))),
                assumptionRunner(ORC,
                        value -> assumeThat(value).isInvalid(),
                        value -> assumeThat(value).isValid()),
                assumptionRunner(ORC,
                        value -> assumeThat(value).containsInvalid("orc"),
                        value -> assumeThat(value).containsInvalid("sauron")),
                assumptionRunner(ORC,
                        value -> assumeThat(value).containsInvalidInstanceOf(String.class),
                        value -> assumeThat(value).containsInvalidInstanceOf(Integer.class)),
                assumptionRunner(ORC,
                        value -> assumeThat(value).containsInvalidSame("orc"),
                        value -> assumeThat(value).containsInvalidSame(new String("orc")))
        );
    }

}
