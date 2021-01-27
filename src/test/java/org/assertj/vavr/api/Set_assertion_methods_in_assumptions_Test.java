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
 * Copyright 2017-2021 the original author or authors.
 */
package org.assertj.vavr.api;

import io.vavr.collection.Array;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.vavr.api.BaseAssumptionRunner.assumptionRunner;
import static org.assertj.vavr.api.VavrAssumptions.assumeThat;

public class Set_assertion_methods_in_assumptions_Test extends BaseAssumptionsTest {
    private static final String FRODO = "frodo";
    private static final String GIMLI = "gimli";
    private static final String LEGOLAS = "legolas";
    private static final Set<String> CHARACTERS = HashSet.of(FRODO, GIMLI);
    private static final Consumer<String> OK_CONSUMER = value -> assertThat(value).isLowerCase();
    private static final Consumer<String> FAIL_CONSUMER = value -> assertThat(value).isUpperCase();

    public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
        return Stream.of(
            assumptionRunner(CHARACTERS,
                    value -> assumeThat(value).allSatisfy(OK_CONSUMER),
                    value -> assumeThat(value).allSatisfy(FAIL_CONSUMER)),
                assumptionRunner(CHARACTERS,
                        value-> assumeThat(value).containsAnyOf(FRODO),
                        value -> assumeThat(value).containsAnyOf(LEGOLAS)),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).contains(FRODO),
                        value -> assumeThat(value).contains(LEGOLAS)),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).containsAll(List.of(FRODO, GIMLI)),
                        value -> assumeThat(value).containsAll(List.of(FRODO, LEGOLAS))),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).containsExactly(GIMLI, FRODO),
                        value -> assumeThat(value).containsExactly(FRODO)),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).doesNotContain(LEGOLAS),
                        value -> assumeThat(value).doesNotContain(FRODO)),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).hasSameSizeAs(new String[]{"frodo", "gimli"}),
                        value -> assumeThat(value).hasSameSizeAs(new String[]{"frodo"})),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).hasSameSizeAs(Array.of(FRODO, GIMLI)),
                        value -> assumeThat(value).hasSameSizeAs(Array.of(FRODO))),
                assumptionRunner(CHARACTERS,
                        value -> assumeThat(value).hasSize(2),
                        value -> assumeThat(value).hasSize(1))
        );
    }
}
